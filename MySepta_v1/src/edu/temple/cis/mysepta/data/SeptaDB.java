package edu.temple.cis.mysepta.data;

import org.htmlparser.util.ParserException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class SeptaDB extends DBAdapter{
	private static final String bus_s = "http://www.septa.org/schedules/bus/index.html";
	private static final String trolley_s = "http://www.septa.org/schedules/trolley/index.html";
	private static final String rail_s = "http://www.septa.org/schedules/rail/index.html";

	private RouteParser rp = null;
	private Route[] bus = null;
	private Route[] trolley = null;
	private Route[] rail = null;
	private Service[] service = null;
	
	private long rail_id, mfl_id, bsl_id, trolley_id, nhs_id, bus_id;
	
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
		Log.i(TAG, "Opening database");
		DBAdapter dbA = super.open();
		insertService();
		insertTrain();
		return dbA;
	}
	
	/**
	 * Retrieve an array of the routes with the given service ID.
	 * @param serviceID ID of the service to retrieve.
	 * @return An array of route with the given service ID.
	 * @throws MySeptaException If the database does not contain the given service ID.
	 * @throws ParserException If the creation of the underlying Lexer cannot be performed.
	 */
	public Route[] getRouteArray(long serviceID) throws MySeptaException, ParserException{
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
	public void insertTrain(){
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
			if (c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				//bus = rp.getRoute(bus_s, this, bus_id).toArray(array);
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
			if (c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				//trolley = rp.getRoute(trolley_s, this, trolley_id);
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
		if (rail == null){
			Cursor c = super.getAllRouteByService(rail_id);
			if (c.moveToFirst()){
				if (rp == null){rp = new RouteParser();}
				//rail = rp.getRoute(rail_s, this, rail_id);
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
		Route[] list = new Route[c.getCount()];
		for (int i = 0; i < c.getCount(); i++){
			int f = c.getInt(4);
			boolean fav = (f == FAV_TRUE) ? true:false;
			list[i] = new Route(c.getInt(0), c.getInt(1), c.getString(2), 
					c.getString(3), fav, c.getString(5));
			c.moveToNext();
		}
		return list; 
	}
}