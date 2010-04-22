package edu.temple.cis.mysepta.data2;

import org.htmlparser.util.ParserException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.MySeptaException;
import edu.temple.cis.mysepta.myclass.Route;
import edu.temple.cis.mysepta.myclass.Schedule;
import edu.temple.cis.mysepta.myclass.Service;
import edu.temple.cis.mysepta.myclass.Stop;

public class SeptaDB extends DBAdapter{
	private static final String bus_s = "http://www.septa.org/schedules/bus/index.html";
	private static final String trolley_s = "http://www.septa.org/schedules/trolley/index.html";
	private static final String rail_s = "http://www.septa.org/schedules/rail/index.html";

	private RouteParser rp = null;
	private ScheduleParser sp = null;
	private Route[] bus = null;
	private Route[] trolley = null;
	private Route[] rail = null;
	private Service[] service = null;
	
	public long rail_id, mfl_id, bsl_id, trolley_id, nhs_id, bus_id;
	
	public SeptaDB(Context ctx) {
		super(ctx);
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
		insertService();
		insertTrain();
		return dbA;
	}

	/**
	 * Retrieve list of day of service from the database. If it doesn't 
	 * exist in the database, retrieve it from the website.
	 * @param r Route to retreive day of service information.
	 * @return Array of day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed. 
	 */
	public Schedule[] getAllSchedule(long stopID){
		Schedule[] list = null;
		Cursor c = super.getAllScheduleByStopID(stopID);
		if (c.moveToFirst()){
			int size = c.getCount();
			list = new Schedule[size];
			for (int i = 0; i < size; i++){
				list[i] = new Schedule(c.getLong(0), c.getLong(1), c.getFloat(2));
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
	public DayOfService[] getDayOfService(long routeID, String link) throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(routeID);
		if (c.moveToFirst()){
			int size = c.getCount();
			day = new DayOfService[size];
			for (int i = 0; i < size; i++){
				day[i] = new DayOfService
				(c.getLong(0), c.getLong(1), c.getString(2));
				c.moveToNext();
			}
		} else {
			if (sp == null){ sp = new ScheduleParser();}
			sp.parseSchedule(link, this, routeID);
			day = getDayOfService(routeID, link);
		}
		c.close();
		return day;
	}
	
	/**
	 * Retrieve stops given the day of service (with route ID).
	 * @param day Day of service to search for.
	 * @return Array of stops with the given day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public Stop[] getStop(long dayID) throws ParserException{
		Stop[] stop = null;
		Cursor c = super.getAllStopByDayId(dayID);
		if (c.moveToFirst()){
			int size = c.getCount();
			stop = new Stop[size];
			for (int i = 0; i < size; i++){
				stop[i] = new Stop(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3));
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
	public Route[] getRoute(long serviceID) throws MySeptaException, ParserException{
		Log.i(nhuTag, " Getting route array for " + serviceID);
		Route[] route = null;
		if (serviceID == rail_id){
			route = getRail();
		} else if (serviceID == trolley_id){
			route = getTrolley();
		} else if (serviceID == bus_id){
			route = getBus();
		} else if (serviceID == mfl_id){
		//	route = getMFL();
		} else if (serviceID == bsl_id){
			//route = getBSL();
		} else if (serviceID == nhs_id){
			//route = getNHS();
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
		mfl_id = (int)super.insertService("MFL", "Market-Frankford Line", "#107DC1");
		bsl_id = (int)super.insertService("BSL", "Broad Street Line", "#F58220");
		trolley_id = (int)super.insertService("Trolley", "Trolley Lines", "#539442");
		nhs_id = (int)super.insertService("NHS", "Norristown High Speed Line", "#9E3E97");
		bus_id = (int)super.insertService("Buses", "Buses", "#41525C");
	}
	
	/**
	 * Retrieve the list of the services in a form of array.
	 * @return Array of services.
	 */
	public Service[] getService(){
		if (service == null){
			Cursor c = super.getAllServices();
			if (c.moveToFirst()){
				service = new Service[c.getCount()];
				for (int i = 0; i < c.getCount(); i++){
					service[i] = new Service(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
					c.moveToNext();
				}
			} else {
				insertService();
				getService();
			}
			c.close();
		}
		return service;
	}
	
	long mflRoute_id = 0, bslRoute_id = 0, nhsRoute_id = 0;
	/**
	 * Insert Market Frankfort Line, Broad Street Line, and Norristown High Speed Line (manually). 
	 */
	private void insertTrain(){
		mflRoute_id = insertRoute(mfl_id, "MFL", "Market-Frankford Line", "http://www.septa.org/schedules/transit/mfl.html");
		bslRoute_id = insertRoute(bsl_id, "BSL", "Broad Street Line", "http://www.septa.org/schedules/transit/bsl.html");
		nhsRoute_id = insertRoute(nhs_id, "NHS", "Norristown High Speed Line", "http://www.septa.org/schedules/transit/nhsl.html");
	}

	/**
	 * Retrieve bus information. Return the list in an array. 
	 * @return Array out bus routes.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	private Route[] getBus() throws ParserException {
		if (bus == null){
			Cursor c = super.getAllRouteByService(bus_id);
			if (!c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				rp.getRoute(bus_s, this, bus_id);
				bus = getBus();
			} else {
				Log.i(nhuTag, "It's not empty.");
				bus = getRouteByCursor(c);
			}
			c.close();
		}
		return bus;
	}
	
	/**
	 * Retrieve trolley information. Return the list in an array.
	 * @return Array of trolley routes.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	private Route[] getTrolley() throws ParserException {
		if (trolley == null){
			Cursor c = super.getAllRouteByService(trolley_id);
			if (!c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				rp.getRoute(trolley_s, this, trolley_id);
				trolley = getTrolley();
			} else {
				trolley = getRouteByCursor(c);
			}
			c.close();
		}
		return trolley;
	}

	/**
	 * Retrieve regional rail information.
	 * @return Array of regional rail route.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	private Route[] getRail() throws ParserException{
		Log.i(nhuTag, "Getting rail");
		if (rail == null){
			Cursor c = super.getAllRouteByService(rail_id);
			if (!c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				rp.getRoute(rail_s, this, rail_id);
				rail = getRail();
			} else {
				rail = getRouteByCursor(c);
			}
			c.close();
		}
		return rail;
	}
	
	/**
	 * Retrieve route information that cursor c is pointing to. Put it in an array and return it.
	 * @param c Cursor pointed to the first element of the route list.
	 * @return Route array cursor was pointed to.
	 */
	private Route[] getRouteByCursor(Cursor c){
		Log.i(nhuTag, "Getting route by cursor");
		Route[] list = new Route[c.getCount()];
		for (int i = 0; i < c.getCount(); i++){
			list[i] = new Route(c.getInt(0), c.getInt(1), c.getString(2), 
					c.getString(3), c.getString(4));
			Log.i(nhuTag, list[i].toString());
			c.moveToNext();
		}
		return list; 
	}
}