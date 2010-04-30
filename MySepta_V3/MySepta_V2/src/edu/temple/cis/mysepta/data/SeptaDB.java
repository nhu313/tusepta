package edu.temple.cis.mysepta.data;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.util.ParserException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.FavStop;
import edu.temple.cis.mysepta.myclass.MySchedule;
import edu.temple.cis.mysepta.myclass.MySeptaException;
import edu.temple.cis.mysepta.myclass.Route;
import edu.temple.cis.mysepta.myclass.Schedule;
import edu.temple.cis.mysepta.myclass.Service;
import edu.temple.cis.mysepta.myclass.Stop;

public class SeptaDB extends DBAdapter{
	private static final String bus_s = "http://www.septa.org/schedules/bus/index.html";
	private static final String trolley_s = "http://www.septa.org/schedules/trolley/index.html";
	private static final String rail_s = "http://www.septa.org/schedules/rail/index.html";

	public static long rail_id, trolley_id, bus_id = 0;
	
	public SeptaDB(Context ctx) {
		super(ctx);
	}

	/**
	 * Retrieve favorite stops.
	 * @return Array of favorite stops.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public List<Stop> getFavoriteStopList() throws ParserException{
		List<Stop> stopList = new ArrayList<Stop>();
		Cursor c = super.getFavoriteStop();
		if (c.moveToFirst()){
			int size = c.getCount();
			Stop stop;
			for (int i = 0; i < size; i++){
				stop = new Stop(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3));
				stopList.add(stop);
				c.moveToNext();
			}
		}
		c.close();
		return stopList;
	}
	
    /**
     * Create and/or open a database that will be used for reading and writing. 
     * Once opened successfully, the database is cached, so you can call this 
     * method every time you need to write to the database. Make sure to call 
     * close()  when you no longer need it.
     * 
     * Errors such as bad permissions or a full disk may cause this operation to fail, 
     * but future attempts may succeed if the problem is fixed.
     * 
     * @return A read/writable database until close is called.
     * @throws SQLException Database cannot be opened for writing.
     */
	public DBAdapter open(){
		Log.i(TAG, "Opening database.");
		DBAdapter dbA = super.open();
		if (bus_id == 0){
			insertService();	
		}
		return dbA;
	}
	
	/**
	 * Retrieve list of day of service from the database. If it doesn't 
	 * exist in the database.
	 * @param r Route to retreive day of service information.
	 * @return Array of day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed. 
	 */
	public List<Schedule> getSchedule(long stopID, float time){
		List<Schedule> list = new ArrayList<Schedule>();
		Cursor c = super.getScheduleByStopID(stopID, time);
		if (c.moveToFirst()){
			int size = c.getCount();
			for (int i = 0; i < size; i++){
				list.add(new Schedule(c.getLong(0), c.getLong(1), c.getFloat(2)));
				c.moveToNext();
			}
		}
		return list;
	}
	
	/**
	 * Retrieve list of day of service from the database. If it doesn't 
	 * exist in the database, retrieve it from the website.
	 * @param r Route to retreive day of service information.
	 * @return Array of day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed. 
	 */
	public List<DayOfService> getDayOfService(long routeID, String link) throws ParserException{
		List<DayOfService> list = new ArrayList<DayOfService>();
		Cursor c = super.getAllDayByRoute(routeID);
		if (c.moveToFirst()){
			int size = c.getCount();
			for (int i = 0; i < size; i++){
				list.add(new DayOfService
				(c.getLong(0), c.getLong(1), c.getString(2)));
				c.moveToNext();
			}
		} else {
			ScheduleParser sp = new ScheduleParser();
			sp.parseSchedule(link, this, routeID);
			list = getDayOfService(routeID, link);
		}
		c.close();
		return list;
	}
	
	/**
	 * Retrieve stops given the day of service (with route ID).
	 * @param day Day of service to search for.
	 * @return Array of stops with the given day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public List<Stop> getStop(long dayID) throws ParserException{
		List<Stop> stop = new ArrayList<Stop>();
		Cursor c = super.getAllStopByDayId(dayID);
		if (c.moveToFirst()){
			int size = c.getCount();
			for (int i = 0; i < size; i++){
				stop.add(new Stop(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3)));
				c.moveToNext();
			}
		}
		c.close();
		return stop;
	}
	
	/**
	 * Retrieve an array of the routes with the given service ID.
	 * @param serviceID ID of the service to retrieve.
	 * @return An array of route with the given service ID.
	 * @throws MySeptaException If the database does not contain the given service ID.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public List<Route> getRoute(long serviceID) throws MySeptaException, ParserException{
		Log.i(nhuTag, " Getting route array for " + serviceID);
		List<Route> route = null;
		if (serviceID == rail_id){
			route = getRail();
		} else if (serviceID == trolley_id){
			route = getTrolley();
		} else if (serviceID == bus_id){
			route = getBus();
		} else {
			throw new MySeptaException("Cannot find service ID.");
		}
		return route;
	}
	
	/**
	 * Insert service in the database.
	 */
	private void insertService(){
		rail_id = (int)super.insertService("RR", "Regional Rail", "#477997");
		trolley_id = (int)super.insertService("Trolley", "Trolley Lines", "#539442");
		bus_id = (int)super.insertService("Buses", "Buses", "#41525C");
	}
	
	/**
	 * Retrieve the list of the services in a form of list.
	 * @return List of services.
	 */
	public List<Service> getService(){
		List<Service> list = new ArrayList<Service>();
		Cursor c = super.getAllServices();
		if (c.moveToFirst()){
			for (int i = 0; i < c.getCount(); i++){
				list.add(new Service(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));
				c.moveToNext();
			}
		} else {
			insertService();
			list = getService();
		}
		c.close();
		return list;
	}
	
	/**
	 * Retrieve bus information. Return the list in an array. 
	 * @return Array out bus routes.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	private List<Route> getBus() throws ParserException {
		List<Route> list = new ArrayList<Route>();
		Cursor c = super.getAllRouteByService(bus_id);
		
		if (c.moveToFirst()){
			list = getRouteByCursor(c);
		} else {
			RouteParser rp = new RouteParser();
			list = rp.getRoute(bus_s, this, bus_id);
		}
		c.close();
		return list;
	}
	
	/**
	 * Retrieve trolley information. Return the list in an array.
	 * @return Array of trolley routes.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	private List<Route> getTrolley() throws ParserException {
		List<Route> list = new ArrayList<Route>();
		Cursor c = super.getAllRouteByService(trolley_id);
		if (c.moveToFirst()){
			list = getRouteByCursor(c);
		} else {
			RouteParser rp = new RouteParser();
			list = rp.getRoute(trolley_s, this, trolley_id);
		}
		c.close();
		return list;
	}

	/**
	 * Retrieve regional rail information.
	 * @return Array of regional rail route.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	private List<Route> getRail() throws ParserException{
		List<Route> list = new ArrayList<Route>();
		Cursor c = super.getAllRouteByService(rail_id);
		if (c.moveToFirst()){
			list = getRouteByCursor(c);			
		} else {
			RouteParser rp = new RouteParser();
			list = rp.getRoute(rail_s, this, rail_id);
		}
			c.close();
		return list;
	}
	
	/**
	 * Retrieve route information that cursor c is pointing to. Put it in an array and return it.
	 * @param c Cursor pointed to the first element of the route list.
	 * @return Route array cursor was pointed to.
	 */
	private List<Route> getRouteByCursor(Cursor c){
		List<Route> list = new ArrayList<Route>();
		for (int i = 0; i < c.getCount(); i++){
			list.add(new Route(c.getInt(0), c.getInt(1), c.getString(2), 
					c.getString(3), c.getString(4)));
			c.moveToNext();
		}
		return list; 
	}
	
	public List<MySchedule> getMySchedule(long stopId){
		List<MySchedule> list = new ArrayList<MySchedule>();
		Cursor c = null;
		if (stopId == 0){
			c = super.getFavSchedule();
		} else {
			c = super.getFavScheduleByStopId(stopId);
		}
		if (c.moveToFirst()){
			for (int i = 0; i < c.getCount(); i++){
				list.add(new MySchedule(c.getLong(0), c.getString(1), c.getString(2), c.getFloat(3)));
				c.moveToNext();
			}
		}		
		return list;		
	}
	
	public List<FavStop> getFavRouteList(){
		List<FavStop> list = new ArrayList<FavStop>();
		Cursor c = super.getFavRoute();
		if (c.moveToFirst()){
			for (int i = 0; i < c.getCount(); i++){
				list.add(new FavStop(c.getLong(0), c.getString(1), c.getString(2)));
				c.moveToNext();
			}
		}		
		return list;		
	}
}