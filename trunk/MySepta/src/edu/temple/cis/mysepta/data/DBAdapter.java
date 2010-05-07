package edu.temple.cis.mysepta.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.temple.cis.mysepta.myclass.MySeptaException;

public class DBAdapter {
	
	    //For Service Table -------------------------------------------
		public static final String KEY_ServiceID = "serviceID";
	    public static final String KEY_ShortName = "shortName";
	    public static final String KEY_LongName = "longName";
	    public static final String KEY_Color = "color";    
	    //For Route Table ---------------------------------------------
	    public static final String KEY_RouteID = "routeID";
	    public static final String KEY_RouteShortName = "routeShortName";
	    public static final String KEY_RouteLongName = "routeLongName";
	    public static final String KEY_URL = "url";
	    //For Day of Service Table ---------------------------------------------
	    public static final String KEY_DayOfServiceID = "dayOfServiceID";
	    public static final String KEY_DayOfService = "dayOfService";
	    //For Stop Table -----------------------------------------------
	    public static final String KEY_StopNameID = "stopID";
	    public static final String KEY_IsFavorite = "favoriteRoute";
	    public static final String KEY_StopName = "stop";
	   //For Schedule Table ---------------------------------------------
	    public static final String KEY_ScheduleID = "scheduleID";
	    public static final String KEY_Schedule = "schedule";  
	    //----------------------------------------------------------------    
	    private static final String autoInt = " integer primary key autoincrement, "; //for create table statement 
	    protected static final String TAG = "SeptaDB";
	    private static final String DATABASE_NAME = "MySepta";    // Database Name
	    private static final String DATABASE_Service = "Service"; // Table 
	    private static final String DATABASE_Route = "Route";	  // Table
	    private static final String DATABASE_DayOfService = "DayOfService"; // Table
	    private static final String DATABASE_Stop = "Stop";       // Table
	    private static final String DATABASE_Schedule = "Schedule"; // Table
	    private static final int DATABASE_VERSION = 1;  // Database Version

	    //-----------------------------------------------------------------------   
	    private final Context context; 
	    private DatabaseHelper DBHelper;
	    private SQLiteDatabase db;

	    protected DBAdapter(Context ctx){
	        this.context = ctx;
	        DBHelper = new DatabaseHelper(context);
	    }
	        
	    private static class DatabaseHelper extends SQLiteOpenHelper {
	        DatabaseHelper(Context context) {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }
	        
	       /**
	        * Create Service, Route, DayOfService, Stop, and Schedule table using 
	        * DATABASE_CREATE_Service, DATABASE_CREATE_Route, DATABASE_CREATE_DayOfService, 
	        * DATABASE_CREATE_Stop, and DATABASE_CREATE_Schedule.
	        * @param db The database to create the tables in. 
	        */
	        @Override
	        public void onCreate(SQLiteDatabase db) {
	            db.execSQL(DATABASE_CREATE_Service);
	            db.execSQL(DATABASE_CREATE_Route);
	            db.execSQL(DATABASE_CREATE_DayOfService);
	            db.execSQL(DATABASE_CREATE_Stop);
	            db.execSQL(DATABASE_CREATE_Schedule);
	        }
	        
	        /**
	         * Drop all the current tables and call onCreate() to recreate them.
	         * @param db The database to upgrade.
	         * @param oldVersion Old version (integer) of the database.
	         * @param newVersion New version (integer) of the database. 
	         */
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            Log.w(TAG, "Upgrading database from version " + oldVersion 
	                    + " to " + newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Service);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Route);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_DayOfService);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Stop);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Schedule);
	            onCreate(db);
	        }
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
	    protected DBAdapter open() throws SQLException{
	        db = DBHelper.getWritableDatabase();
	        return this;
	    }
    
	    public void close(){
	        DBHelper.close();
	    }
	    
	    public void upgrade(){
	    	DBHelper.onUpgrade(db, 1, 2);
	    }
	    
	    //----------------------------------------SERVICE-------------------------------------------//
	    // Statement to create Service table
	    private static final String DATABASE_CREATE_Service =
	    	"create table " + DATABASE_Service + " (" + KEY_ServiceID + autoInt 
	    	+ KEY_ShortName + " text not null, " + KEY_LongName + " text not null, " + KEY_Color + " text not null);"; 

	    /**
	     * Insert a service into the database. If the service already exist, return service ID.
	     * @param shortName Short name of the service.
	     * @param longName Long name of the server.
	     * @param color Color of the service.
	     * @return Service ID of the service.
	     */
	    synchronized public long insertService(String shortName, String longName, String color){
    		Log.i(TAG, " Insert Service " + longName);
	    	//Check if service exist in database
	    	Cursor mCursor = db.query(DATABASE_Service, new String[] {KEY_ServiceID},
	        		KEY_ShortName + "='" + shortName + "' AND " + KEY_LongName + "='"+longName+"'",
	        		null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		//Return service id if it's in the database
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		//Else, insert it in the database
	    		mCursor.close();
	    		ContentValues initialValues = new ContentValues();
	    		initialValues.put(KEY_ShortName, shortName);
	    		initialValues.put(KEY_LongName, longName);
	    		initialValues.put(KEY_Color, color);
	    		return db.insert(DATABASE_Service, null, initialValues);
	    	}
	    }

	    /**
	     * Query the Service table to retrieve all the service's information. Return the cursor to the result. 
	     * @return Cursor object which point to the position before the first entry.
	     */
	    protected Cursor getAllServices(){
	        return db.query(true, DATABASE_Service, new String[] {
	        		KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color}, 
	                null, null, null, null, null, null);
	    }

	    /**
	     * Get a service given a service ID.
	     * @param serviceID Service ID of the service to search for.
	     * @return Cursor object which point to the position before the first entry. 
	     */
	    protected Cursor getServiceByServiceId(long serviceID){
	    	return db.query(true, DATABASE_Service, new String[] {
	        		KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color}, 
	                KEY_ServiceID + "=" + serviceID, null, null, null, null, null);
	    }
	    
	    /**
	     * Check if the Service table is empty.
	     * @return True if the service table is empty. False otherwise.
	     */
	    protected boolean isServiceEmpty(){
	    	Cursor c = null;
			try{
				c = db.query(DATABASE_Service, new String[] {
						KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color}, 
						null, null, null, null, null);
				int numRows = c.getCount();
				if (numRows < 1) 
					return true;
			} catch (SQLException e){
				DBHelper.onCreate(db);
				return true;	
			} finally {
				c.close();
			}
			return false;
		}

	    //------------------------------ROUTE--------------------------------------------------------//
	    // Statement to create Route table
	    private static final String DATABASE_CREATE_Route =
	    	"create table " + DATABASE_Route + " (" + KEY_RouteID + autoInt
	    	+ KEY_ServiceID + " integer not null, " + KEY_RouteShortName + " text not null, " 
	    	+ KEY_RouteLongName + " text not null, " + KEY_URL + " text not null);";
	    
	    /**
	     * Insert a route into the database. If the route already exist, return route ID.
	     * @param serviceID The service ID of the route.
	     * @param routeNumber The route number or name of the route.
	     * @param routeName The long route name of the route.
	     * @param url The URL of the route's schedule.
	     * @return Route ID of the route.
	     */
	    synchronized public long insertRoute(long serviceID, String routeNumber, String routeName, String url){
	    	Log.i(TAG, "Inserting Route [" + serviceID + "] " + routeNumber + " - " + routeName);
	    	//Check if the route is in the database 
	    	Cursor mCursor = db.query(DATABASE_Route, new String[] {KEY_RouteID}, 
	    			KEY_RouteShortName + "='" + routeNumber + "' AND " 
                		+ KEY_RouteLongName + " = '" + routeName + "'", 
                		null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		//If it's in the database, return the route ID
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		//If it's not in the database, insert it and return the route ID.
	    		mCursor.close();
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_ServiceID, serviceID);
		        initialValues.put(KEY_RouteShortName, routeNumber);
		        initialValues.put(KEY_RouteLongName, routeName);
		        initialValues.put(KEY_URL, url);
		        return db.insert(DATABASE_Route, null, initialValues);
	    	}
	    }
	    
	    /**
	     * Retrieve  all the route with the given service ID. Return the cursor to the result.
	     * @param serviceID Service ID of the route to search for.
	     * @return Cursor object which is positioned before the first entry.
	     */
	    protected Cursor getAllRouteByService(long serviceID){
	    	return db.query(true, DATABASE_Route, new String[] {
	                		KEY_RouteID, KEY_ServiceID, KEY_RouteShortName, KEY_RouteLongName,
	                		KEY_URL }, KEY_ServiceID + "=" + serviceID, 
	                		null, null, null, null, null);
	    }
	    
	    /**
	     * Retrieve service ID of the given routeID.
	     * @param routeID Route ID of the service ID to search for.
	     * @return The service ID of route ID.
	     */
	    protected long getServiceIdWithRouteId(long routeID){
	    	long ret = 0;
	    	Cursor c = db.query(true, DATABASE_Route, new String[] {KEY_ServiceID}, 
	    			KEY_RouteID + "=" + routeID, null, null, null, null, null);
	    	if (c.moveToFirst()){
	    		ret = c.getLong(0);
	    	}
    		c.close();
    		return ret;
	    }
	    
	    /**
	     * Update a route.
	     * @param routeID Route ID of the route to update.
	     * @param routeID Service ID of the route to update. 
	     * @param routeShortName Short name of the route. 
	     * @param routeLongName Long name of the route.
	     * @param url URL of the route's schedule.
	     * @return True if the update was successful. False otherwise.
	     */
	    public boolean updateRoute(long serviceID, long routeID, String routeShortName, 
	    		String routeLongName, String favoriteRoute, String url){
	        ContentValues args = new ContentValues();
	        args.put(KEY_ServiceID, serviceID);
	        args.put(KEY_RouteShortName, routeShortName);
	        args.put(KEY_RouteLongName, routeLongName);
	        args.put(KEY_URL, url);
	        return db.update(DATABASE_Route, args, 
	                         KEY_RouteID + "=" + routeID, null) > 0;
	    }
	    
	    /**
	     * Retrieve the route with the given route ID. 
	     * @param routeID Route ID to of the route to search for.
	     * @return Cursor object which point to the position before the first entry.
	     */
	    protected Cursor getRouteByRouteId(long routeID){
	    	return db.query(true, DATABASE_Route, new String[] {
            		KEY_RouteID, KEY_ServiceID, KEY_RouteShortName, KEY_RouteLongName,
            		KEY_URL}, KEY_RouteID + "=" + routeID, 
            		null, null, null, null, null);
	    }
	    
	    //-------------------------------------------------DAY OF SERVICE------------------------------------------//
	    //Statement to create DayOfService Table
	    private static final String DATABASE_CREATE_DayOfService = 
	    	"create table " + DATABASE_DayOfService + " (" + KEY_DayOfServiceID + autoInt 
	    	+ KEY_RouteID + " integer not null, " + KEY_DayOfService + " text not null);";	    

	    /**
	     * Insert day of service (i.e. Weekday to Center City) and return its day of service ID. 
	     * If entry exists in database. Get the day of service ID.
	     * @param routeID ID of the route to insert.
	     * @param day Day of service to insert.
	     * @return Day of service ID.
	     */
	    synchronized public long insertDayOfService(long routeID, String day){
	    	Log.i(TAG, "Inserting day of service [" + routeID + "] " + day);
	    	//Check if the day of service is in the database
	    	Cursor mCursor = db.query(DATABASE_DayOfService, new String[] {KEY_DayOfServiceID}, 
	    			KEY_RouteID + "='" + routeID + "' AND " 
                		+ KEY_DayOfService + " = '" + day + "'", null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		//If it is in the database, get day of service ID and return it.
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		//If it's not in the database, add it to the database and return the day of service ID.
	    		mCursor.close();
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_RouteID, routeID);
		        initialValues.put(KEY_DayOfService, day);
		        return db.insert(DATABASE_DayOfService, null, initialValues);
	    	}
	    }
	    
	    /**
	     * Retrieve all the day of service given the routeID.
	     * @param routeID ID of the route to get the day of service to search for.
	     * @return Cursor object which is positioned before the first entry.
	     */
	    protected Cursor getAllDayByRoute(long routeID){
	    	return db.query(DATABASE_DayOfService, new String[] {
	    			KEY_DayOfServiceID, KEY_RouteID, KEY_DayOfService}, 
	    			KEY_RouteID + " = " + routeID, null, null, null, null, null);
	    }
	    
	    //-------------------------------------------------STOP---------------------------------------------//	    
	    //Statement to create Stop table
	    private static final String DATABASE_CREATE_Stop =
	        "create table " + DATABASE_Stop + " (" + KEY_StopNameID + autoInt
	        + KEY_DayOfServiceID + " integer not null, " + KEY_StopName + " text not null, " 
	        + KEY_IsFavorite + " integer not null);";
	    
	    /**
	     * Insert a stop and return its stop ID. If the entry exists in the database, get the stop ID.
	     * @param dayID Day of service ID of the stop.
	     * @param name Name of the stop.
	     * @return Stop ID.
	     */
	    synchronized protected long insertStop(long dayID, String name){ 	
	    	Log.i(TAG, "Inserting stop [" + dayID + "] " + name);
	    	//Check if the stop ID is in the database
	    	Cursor mCursor = db.query(true, DATABASE_Stop, new String[] {KEY_StopNameID}, 
        		KEY_DayOfServiceID + "=" + dayID + " AND " + KEY_StopName + " = '" + name + "'", 
        		null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		//If it is in the database, get day of stop ID and return it.
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		//If it's not in the database, add it to the database and return the day of stop ID.
	    		mCursor.close();
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_DayOfServiceID, dayID);
		        initialValues.put(KEY_StopName, name);
		        initialValues.put(KEY_IsFavorite, FAV_FALSE);
		        return db.insert(DATABASE_Stop, null, initialValues);
	    	}
	    }

	    /**
	     * Retrieve all the stop given a day ID.
	     * @param dayID ID of the day of service.
	     * @return Cursor object which is positioned before the first entry.
	     */
	    protected Cursor getAllStopByDayId(long dayID) {
	        return db.query(DATABASE_Stop, new String[] {
	        		KEY_StopNameID, KEY_DayOfServiceID, KEY_StopName, KEY_IsFavorite}, 
	                KEY_DayOfServiceID + " = " + dayID, null, null, null, null);
	    }

	    /**
	     * Update stop favorite properties.
	     * @param stopID ID of the stop to update.
	     * @param favorite Number to indicate favorite. (Use DBAdapter.FAV_TRUE or DBAdapter.FAV_False). 
	     * @return True if update was successful. False, otherwise.
	     * @throws MySeptaException when the favorite integer is not 0 or 1.
	     */
	    synchronized public boolean updateFavoriteRoute(long stopID, int favorite) throws MySeptaException{
	    	if (favorite != FAV_TRUE && favorite != FAV_FALSE){
	    		throw new MySeptaException("Incorrect favorite number. Enter 0 for false or 1 for true.");
	    	}
	    	ContentValues args = new ContentValues();
	    	args.put(KEY_IsFavorite, favorite);
	    	return db.update(DATABASE_Stop, args, KEY_StopNameID + "=" + stopID, null) > 0;
	    }
	    
	    //-------------------------------------------Schedule----------------------------------------------//
	    //Statement to create Schedule Table
	    private static final String DATABASE_CREATE_Schedule =
	    	"create table " + DATABASE_Schedule + " (" + KEY_ScheduleID + autoInt
	    	+ KEY_StopNameID + " integer not null, " + KEY_Schedule + " float not null);";

	    /**
	     * Insert a schedule time and return its ID. If the entry exists in the database, get the schedule ID.
	     * @param stopID ID of the stop.
	     * @param sched Time of the schedule in float.
	     * @return Schedule ID
	     */
	    synchronized public long insertSchedule(long stopID, float sched) {
	    	Log.i(TAG, "Inserting schedule [" + stopID + "] - " + sched);
	    	//Check if schedule time with the stop ID is in the database.
	    	Cursor mCursor = db.query(DATABASE_Schedule, new String[] {KEY_ScheduleID},
	                KEY_StopNameID + "=" + stopID + " AND " + KEY_Schedule + "=" + sched,
	                null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		//If it is, return the schedule ID.
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		//Else, insert it in the database and return the result.
	    		mCursor.close();
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_StopNameID, stopID);
		        initialValues.put(KEY_Schedule, sched);
		        return db.insert(DATABASE_Schedule, null, initialValues);
	    	}
	    }

	    /**
		 * Retrieve a particular service time given a stopID.
		 * @param stopID ID of the stop.
		 * @return Cursor pointing to the listing of all the service time that matches that particular stop ID. Cursor is placed before the first entry.
		 */
	    protected Cursor getScheduleByStopID(long stopID, float t) {
	        return db.query(true, DATABASE_Schedule, new String[] {
	        		 KEY_ScheduleID, KEY_StopNameID, KEY_Schedule}, 
	        		 KEY_StopNameID + " = " + stopID + " AND " + KEY_Schedule + " >= " + t, 
	        		null, null, null, KEY_Schedule + " ASC", null);
	    }
	    
	    /**
	     * Retrieve all favorite stop.
	     * @param dayID ID of the day of service.
	     * @return Cursor object which is positioned before the first entry.
	     */
	    protected Cursor getFavoriteStop() {
	        return db.query(DATABASE_Stop, new String[] {
	        		KEY_StopNameID, KEY_DayOfServiceID, KEY_StopName, KEY_IsFavorite}, 
	        		KEY_IsFavorite + " = " + FAV_TRUE, null, null, null, null);
	    }
	    
	    /**
	     * Retrieve all the favorite stop with route name and stop name.
	     * @return Cursor to the data set which is positioned before the first entry.
	     */
	    protected Cursor getFavRoute(){
	    	String sql = "Select DISTINCT Stop.stopID, Stop.stop, Route.routeShortName "
	    		+ "from Route INNER JOIN DayOfService On Route.routeID = DayOfService.RouteID "
	    		+ "INNER JOIN Stop ON DayOfService.dayOfServiceID = Stop.dayOfServiceID "
	    		+ "WHERE Stop.favoriteRoute = 1";
	    	return db.rawQuery(sql, null);
	    }

	    /**
	     * Retrieve favorite schedule for all stop.
	     * @return Cursor to the data set which is positioned before the first entry.
	     */
	    protected Cursor getFavSchedule(float t){
	    	String sql = "Select Route.serviceID, Route.routeShortName, Stop.stop, Schedule.schedule "
	    		+ "from Route INNER JOIN DayOfService On Route.routeID = DayOfService.RouteID "
	    		+ "INNER JOIN Stop ON DayOfService.dayOfServiceID = Stop.dayOfServiceID "
	    		+ "INNER JOIN Schedule ON Stop.stopID = Schedule.stopID "
	    		+ "WHERE Stop.favoriteRoute = 1 AND schedule >= " + t + " order by Schedule.schedule ASC limit 30;";
	    	return db.rawQuery(sql, null);
	    }
	    
	    /**
	     * Retrieve favorite schedule based on stop ID.
	     * @param stopId Stop ID of the schedule you want.
	     * @return Cursor to the data set which is positioned before the first entry.
	     */
	    protected Cursor getFavScheduleByStopId(long stopId, float t){
	    	String sql = "Select Route.serviceID, Route.routeShortName, Stop.stop, Schedule.schedule "
	    		+ "from Route INNER JOIN DayOfService On Route.routeID = DayOfService.RouteID "
	    		+ "INNER JOIN Stop ON DayOfService.dayOfServiceID = Stop.dayOfServiceID "
	    		+ "INNER JOIN Schedule ON Stop.stopID = Schedule.stopID "
	    		+ "WHERE Stop.favoriteRoute = 1 AND schedule >= " + t + " AND Stop." + KEY_StopNameID + "=" + stopId 
	    		+ " order by Schedule.schedule ASC limit 30;";
	    	return db.rawQuery(sql, null);
	    }
	    
	    public static final int FAV_TRUE = 1;
	    public static final int FAV_FALSE = 0;
}