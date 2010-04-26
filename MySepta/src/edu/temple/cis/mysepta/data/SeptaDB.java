package edu.temple.cis.mysepta.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.htmlparser.util.ParserException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import edu.temple.cis.mysepta.myclass.DayOfService;
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
	 * Retrieve the list of the services.
	 * @return Array of services.
	 */
	public List<Service> getServiceList(){
		return Arrays.asList(getService());
	}
	/**
	 * Retrieve a list of the routes with the given service ID.
	 * @param serviceID ID of the service to retrieve.
	 * @return List of route with the given service ID.
	 * @throws MySeptaException If the database does not contain the given service ID.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public List<Route> getRouteList(Service service) throws MySeptaException, ParserException{
		Route[] route = getRoute(service);
		if (route == null) {
			return new ArrayList<Route>();
		} else {
			return Arrays.asList(route);
		}
	}
	/**
	 * Retrieve list of day of service from the database. If it doesn't 
	 * exist in the database, retrieve it from the website.
	 * @param r Route to retreive day of service information.
	 * @return List of day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed. 
	 */
	public List<DayOfService> getDayOfServiceList(Route r) throws ParserException{
		DayOfService[] dos = getDayOfService(r);
		if (dos == null) {
			return new ArrayList<DayOfService>();
		} else {
			return Arrays.asList(dos);
		}
	}
	public List<Stop> getStopList(DayOfService day) throws ParserException{
		Stop[] stop = getStop(day);
		if (stop == null) {
			return new ArrayList<Stop>();
		} else {
			return Arrays.asList(stop);
		}
	}
	public List<Schedule> getScheduleList(Stop t) throws ParserException{
		Schedule[] st  = getSchedule(t);
		if (st == null) {
			return new ArrayList<Schedule>();
		} else {
			return Arrays.asList(st);
		}
	}
	/**
	 * Retrieve list of day of service from the database. If it doesn't 
	 * exist in the database, retrieve it from the website.
	 * @param r Route to retreive day of service information.
	 * @return Array of day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed. 
	 */
	public DayOfService[] getDayOfService(Route r) throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(r.getRoute_id());
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
			sp.parseSchedule(r.getUrl(), this, r.getRoute_id());
			day = getDayOfService(r);
		}
		c.close();
		return day;
	}
	public long t10id = 126;
	public String t10url = "http://www.septa.org/schedules/trolley/route010.html";
	public DayOfService[] getDayOfServiceT10() throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(t10id);
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
			sp.parseSchedule(t10url, this, t10id);
			day = getDayOfServiceT10();
		}
		c.close();
		return day;
	}
	public long t11id = 127;
	public String t11url = "http://www.septa.org/schedules/trolley/route011.html";
	public DayOfService[] getDayOfServiceT11() throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(t11id);
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
			sp.parseSchedule(t11url, this, t11id);
			day = getDayOfServiceT10();
		}
		c.close();
		return day;
	}
	public long busid = 22;
	public String busurl = "http://www.septa.org/schedules/bus/route025.html";
	public DayOfService[] getDayOfServiceBus22() throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(busid);
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
			sp.parseSchedule(busurl, this, busid);
			day = getDayOfServiceT10();
		}
		c.close();
		return day;
	}
	public long mflid = 1;
	public String mflurl = "http://www.septa.org/schedules/transit/mfl.html";
	public DayOfService[] getDayOfServiceMFL() throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(mflid);
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
			sp.parseSchedule(mflurl, this, mflid);
			day = getDayOfServiceT10();
		}
		c.close();
		return day;
	}
	public long bslid = 2;
	public String bslurl = "http://www.septa.org/schedules/transit/bsl.html";
	public DayOfService[] getDayOfServiceBSL() throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(bslid);
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
			sp.parseSchedule(bslurl, this, bslid);
			day = getDayOfServiceT10();
		}
		c.close();
		return day;
	}
	public long nhslid = 3;
	public String nhslurl = "http://www.septa.org/schedules/transit/nhsl.html";
	public DayOfService[] getDayOfServiceNHSL() throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(nhslid);
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
			sp.parseSchedule(nhslurl, this, nhslid);
			day = getDayOfServiceT10();
		}
		c.close();
		return day;
	}
	public long bus21id = 18; //not working
	public String r1url = "http://www.septa.org/schedules/bus/route021.html";
	public DayOfService[] getDayOfServiceBus21() throws ParserException{
		DayOfService[] day = null;
		Cursor c = super.getAllDayByRoute(bus21id);
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
			sp.parseSchedule(r1url, this, bus21id);
			day = getDayOfServiceT10();
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
	
	
	/**
	 * Retrieve stops given the day of service (with route ID).
	 * @param day Day of service to search for.
	 * @return Array of stops with the given day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public Stop[] getStop(DayOfService day) throws ParserException{
		Stop[] stop = null;
		Cursor c = super.getAllStopByDayId(day.getDayID());
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
	//TestStop
	public long t10dayid = 1;
	public Stop[] getStopT10() throws ParserException{
		Stop[] stop = null;
		Cursor c = super.getAllStopByDayId(t10dayid);
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
	//TestStop
	public long r1dayid = 7;
	public Stop[] getStopR1() throws ParserException{
		Stop[] stop = null;
		Cursor c = super.getAllStopByDayId(r1dayid);
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
	//TestStop
	public long busdayid = 37;
	public Stop[] getStopBus21() throws ParserException{
		Stop[] stop = null;
		Cursor c = super.getAllStopByDayId(busdayid);
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
	//Retrieve All Schedule
	public Schedule[] getSchedule(Stop stop) throws ParserException{
		Schedule[] schedule = null;
		Cursor c = super.getAllSchedules(stop.getStopID());
		if (c.moveToFirst()){
			int size = c.getCount();
			schedule = new Schedule[size];
			for (int i = 0; i < size; i++){
				schedule[i] = new Schedule(c.getLong(0), c.getLong(1), c.getFloat(2));
				c.moveToNext();
			}
		}
		c.close();
		return schedule;
	}
	//Retrieve All Schedule
	public long bus21stopid = 189;
	public double t = 10.00;
	public Schedule[] getScheduleBus21() throws ParserException{
		Schedule[] schedule = null;
		Cursor c = super.getSchedulesTime(bus21stopid,t);
		if (c.moveToFirst()){
			int size = c.getCount();
			schedule = new Schedule[size];
			for (int i = 0; i < size; i++){
				schedule[i] = new Schedule(c.getLong(0), c.getLong(1), c.getFloat(2));
				c.moveToNext();
			}
		}
		c.close();
		return schedule;
	}
	//Retrieve All Schedule
	public long bus21stopidT = 189;
	//public double t = 10.00;
	public Schedule[] getScheduleCurrentTime() throws ParserException{
		Schedule[] schedule = null;
		Cursor c = super.getNowSchedules(bus21stopidT);
		if (c.moveToFirst()){
			int size = c.getCount();
			schedule = new Schedule[size];
			for (int i = 0; i < size; i++){
				schedule[i] = new Schedule(c.getLong(0), c.getLong(1), c.getFloat(2));
				c.moveToNext();
			}
		}
		c.close();
		return schedule;
	}
	
	
	//Test Schedule
	public long t10stopid = 1;
	public double t1 = 10.00;
	public Schedule[] getScheduleT10() throws ParserException{
		Schedule[] schedule = null;
		Cursor c = super.getSchedulesTime(t10stopid,t1);
		if (c.moveToFirst()){
			int size = c.getCount();
			schedule = new Schedule[size];
			for (int i = 0; i < size; i++){
				schedule[i] = new Schedule(c.getLong(0), c.getLong(1), c.getFloat(2));
				c.moveToNext();
			}
		}
		c.close();
		return schedule;
	}
	/**
	 * Retrieve list of day of service from the database. If it doesn't 
	 * exist in the database, retrieve it from the website.
	 * @param r Route to retreive day of service information.
	 * @return Array of day of service.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed. 
	 */
	public Schedule[] getScheduleSpecificTime(long stopID, double t){
		Schedule[] list = null;
		Cursor c = super.getSchedulesTime(stopID,t);
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
	public Schedule[] getScheduleCurrentTime(long stopID){
		Schedule[] list = null;
		Cursor c = super.getNowSchedules(stopID);
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
	 * Retrieve favorite stops.
	 * @return Array of favorite stops.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public List<Stop> getFavoriteStopList() throws ParserException{
		List<Stop> stopList = new ArrayList<Stop>();
		Cursor c = super.getFavoriteStopExt();
		if (c.moveToFirst()){
			int size = c.getCount();
			Stop stop;
			for (int i = 0; i < size; i++){
				stop = new Stop(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3));
				stop.setExtRouteName(c.getString(5));
				stopList.add(stop);
				c.moveToNext();
			}
		}
		c.close();
		return stopList;
	}
	
	
	
	/**
	 * Retrieve an array of the routes with the given service ID.
	 * @param serviceID ID of the service to retrieve.
	 * @return An array of route with the given service ID.
	 * @throws MySeptaException If the database does not contain the given service ID.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public Route[] getRoute(Service service) throws MySeptaException, ParserException{
		long serviceID = service.getId();
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
	public Route[] getBus() throws ParserException {
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
	public Route[] getTrolley() throws ParserException {
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
	public Route[] getRail() throws ParserException{
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

    /**
     * Update stop favorite properties.
     * @param stopID ID of the stop to update.
     * @param favorite Number to indicate favorite. (Use DBAdapter.FAV_TRUE or DBAdapter.FAV_False). 
     * @return True if update was successful. False, otherwise.
     * @throws MySeptaException when the favorite integer is not 0 or 1.
     */
    public boolean updateFavoriteRoute(long stopID, int favorite) throws MySeptaException{
    	return super.updateFavoriteRoute(stopID, favorite);
    }
    
}