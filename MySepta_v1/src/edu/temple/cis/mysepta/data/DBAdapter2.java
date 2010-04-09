package edu.temple.cis.mysepta.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter2 {
	
	public String nhuTag = "__________________";
	    //For Service Table -------------------------------------------
		public static final String KEY_ServiceID = "serviceID";
	    public static final String KEY_ShortName = "shortName";
	    public static final String KEY_LongName = "longName";
	    public static final String KEY_Color = "color";    
	    //For Route Table ---------------------------------------------
	    public static final String KEY_RouteID = "routeID";
	    public static final String KEY_RouteNumber = "routeNumber";
	    public static final String KEY_RouteName = "routeName";
	    public static final String KEY_IsFavorite = "favoriteRoute";
	    public static final String KEY_URL = "url";
	    //For Day of Service Table ---------------------------------------------
	    public static final String KEY_DayOfServiceID = "dayOfServiceID";
	    public static final String KEY_DayOfService = "dayOfService";
	    //For Stop Table -----------------------------------------------
	    public static final String KEY_StopID = "stopID";
	    public static final String KEY_Stop = "stop";
	   //For Time Table ---------------------------------------------
	    public static final String KEY_TimeID = "TimeID";
	    public static final String KEY_Time = "time";  
	    //----------------------------------------------------------------    
	    private static final String TAG = "SeptaDB";
	    private static final String DATABASE_NAME = "MySepta";    // Database Name
	    private static final String DATABASE_Service = "Service"; // Table 
	    private static final String DATABASE_Route = "Route";	  // Table
	    private static final String DATABASE_DayOfService = "DayOfService";       // Table
	    private static final String DATABASE_Stop = "Stop";       // Table
	    private static final String DATABASE_Time = "Time"; // Table
	    private static final int DATABASE_VERSION = 1;  // Database Version

	    private static final String autoInt = " integer primary key autoincrement, ";
	    // Create Service Table Statement
	    private static final String DATABASE_CREATE_Service =
	    	"create table " + DATABASE_Service + " (" + KEY_ServiceID + autoInt 
	    	+ KEY_ShortName + " text not null, " + KEY_LongName + " text not null, " + KEY_Color + " text not null);"; 
	    // Create Route Table Statement
	    private static final String DATABASE_CREATE_Route =
	    	"create table " + DATABASE_Route + " (" + KEY_RouteID + autoInt
	    	+ KEY_ServiceID + " integer not null, " + KEY_RouteNumber + " text not null, " 
	    	+ KEY_RouteName + " text not null, " + KEY_IsFavorite + " integer not null, "
	    	+ KEY_URL + " text not null);";
	    //Create Day of service statement
	    private static final String DATABASE_CREATE_DayOfService = 
	    	"create table " + DATABASE_DayOfService + " (" + KEY_RouteID + " integer not null, " 
	    	+ KEY_DayOfServiceID + autoInt + KEY_DayOfService + " text not null);";	    
	    // Create Stop Table Statement
	    private static final String DATABASE_CREATE_Stop =
	        "create table " + DATABASE_Stop + " (" + KEY_StopID + autoInt
	        + KEY_DayOfServiceID + " integer not null, " + KEY_Stop + " text not null);";
	    // Create Time Table Statement
	    private static final String DATABASE_CREATE_Time =
	    	"create table " + DATABASE_Time + " (" + KEY_TimeID + autoInt
	    	+ KEY_StopID + " integer not null, " + KEY_Time + " REAL not null);";
	    //-----------------------------------------------------------------------   
	    private final Context context; 
	    
	    private DatabaseHelper DBHelper;
	    private SQLiteDatabase db;

	    public DBAdapter2(Context ctx){
	        this.context = ctx;
	        DBHelper = new DatabaseHelper(context);
	    }
	        
	    private static class DatabaseHelper extends SQLiteOpenHelper 
	    {
	        DatabaseHelper(Context context) 
	        {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }
	       // Create all six tables
	        @Override
	        public void onCreate(SQLiteDatabase db) 
	        {
	            db.execSQL(DATABASE_CREATE_Service);
	            db.execSQL(DATABASE_CREATE_Route);
	            db.execSQL(DATABASE_CREATE_DayOfService);
	            db.execSQL(DATABASE_CREATE_Stop);
	            db.execSQL(DATABASE_CREATE_Time);
	        }
	        
	        // This function will be used when update version to the database
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
	        int newVersion) 
	        {
	            Log.w(TAG, "Upgrading database from version " + oldVersion 
	                    + " to "
	                    + newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Service);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Route);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_DayOfService);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Stop);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Time);
	            onCreate(db);
	        }
	    }    
	    
	    //---opens the database---
	    public DBAdapter2 open() throws SQLException, Exception {
	    	Log.i(nhuTag, " opening db through adapter");
	        db = DBHelper.getWritableDatabase();
	        return this;
	    }

	    //---closes the database---    
	    public void close(){
	        DBHelper.close();
	    }
	    
	    //----------------------------------------SERVICE-------------------------------------------//
	    //---insert a Service into the database---
	    public long insertService(String shortName, String longName, String color){

	    	Cursor mCursor = db.query(DATABASE_Service, new String[] {
	        		KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color},
	        		KEY_LongName + "=" + "'"+longName+"'",
	        		null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		Log.i(nhuTag, " Insert Service but already in system " 
	    				+ longName + " " + mCursor.getLong(0));
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		mCursor.close();
	    		ContentValues initialValues = new ContentValues();
	    		initialValues.put(KEY_ShortName, shortName);
	    		initialValues.put(KEY_LongName, longName);
	    		initialValues.put(KEY_Color, color);
	    		Log.i(nhuTag, " Insert Service" + longName);
	    		return db.insert(DATABASE_Service, null, initialValues);
	    	}
	    }

	    //---retrieves all the Services---
	    public Cursor getAllServices(){
	        return db.query(DATABASE_Service, new String[] {
	        		KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color}, 
	                null, null, null, null, null);
	    }

	    public boolean isServiceEmpty(){
			try{
				Cursor c = db.query(DATABASE_Service, new String[] {
						KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color}, 
						null, null, null, null, null);
				int numRows = c.getCount();
				c.close();
				if (numRows < 1) 
					return true;
			} catch (SQLException e){
				DBHelper.onCreate(db);
				return true;	
			}
			return false;
		}
	  //------------------------------ROUTE--------------------------------------------------------//
	    
	  //---insert a Route into the database---
	    public long insertRoute(long service_ID, String routeNumber, 
	    		String routeName, long favoriteRoute, String url) 
	    {
	    	Cursor mCursor = db.query(DATABASE_Route, new String[] {
                		KEY_RouteID, KEY_ServiceID, KEY_RouteNumber, KEY_RouteName,
                		KEY_IsFavorite, KEY_URL }, KEY_RouteNumber + "='" + routeNumber + "' AND " 
                		+ KEY_RouteName + " = '" + routeName + "'", 
                		null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		long ret = mCursor.getLong(0);
	    		Log.i(nhuTag, "Inserting route, but route already inserted " + ret);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		mCursor.close();
	    		Log.i(nhuTag, "Inserting Route: " + routeNumber + " " + routeName);
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_ServiceID, service_ID);
		        initialValues.put(KEY_RouteNumber, routeNumber);
		        initialValues.put(KEY_RouteName, routeName);
		        initialValues.put(KEY_IsFavorite, favoriteRoute);
		        initialValues.put(KEY_URL, url);
		        return db.insert(DATABASE_Route, null, initialValues);
	    	}
	    }

	    //---retrieves Route by a particular Service---
	    public Cursor getRouteByService(long serviceID) throws SQLException {
	        Cursor mCursor =
	                db.query(true, DATABASE_Route, new String[] {
	                		KEY_RouteID, KEY_ServiceID, KEY_RouteNumber, KEY_RouteName,
	                		KEY_IsFavorite, KEY_URL }, KEY_ServiceID + "=" + serviceID, 
	                		null, null, null, null, null);
	        if (mCursor.moveToFirst()){
	        	return mCursor;
	        } else {
	        	mCursor.close();
	        	return null;
	        }
	    }

	    //---updates a Route---
	    public boolean updateRoute(long routeID, long service_ID, 
	    String routeNumber, String routeName, String favoriteRoute, String url) 
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_ServiceID, service_ID);
	        args.put(KEY_RouteNumber, routeNumber);
	        args.put(KEY_RouteName, routeName);
	        args.put(KEY_IsFavorite, favoriteRoute);
	        args.put(KEY_URL, url);
	        return db.update(DATABASE_Route, args, 
	                         KEY_RouteID + "=" + routeID, null) > 0;
	    }
	    
	    //-------------------------------------------------DAY OF SERVICE------------------------------------------//
	    public long insertDayOfService(long routeID, String day) 
	    {
	    	Cursor mCursor = db.query(DATABASE_DayOfService, new String[] {
                		KEY_RouteID, KEY_DayOfServiceID, KEY_DayOfService}, KEY_RouteID + "='" + routeID + "' AND " 
                		+ KEY_DayOfService + " = '" + day + "'", null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		long ret = mCursor.getLong(0);
	    		Log.i(nhuTag, "Inserting day, but route already inserted " + ret);
	    		mCursor.close();
	    		return ret;
	    	} else {
	    		mCursor.close();
	    		Log.i(nhuTag, "Inserting day: " + day + " " + routeID);
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_RouteID, routeID);
		        initialValues.put(KEY_DayOfService, day);
		        return db.insert(DATABASE_DayOfService, null, initialValues);
	    	}
	    }
	    
	    //-------------------------------------------------STOP---------------------------------------------//	    
	    //---insert a Stop into the database---
	    public long insertStop(long dayID, String stop) 
	    { 	
	    	Cursor mCursor = db.query(true, DATABASE_Stop, new String[] {
        		KEY_StopID, KEY_DayOfServiceID, KEY_Stop}, 
        		KEY_DayOfServiceID + "=" + dayID + " AND " + KEY_Stop + " = '" + stop + "'", 
        		null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_DayOfServiceID, dayID);
		        initialValues.put(KEY_Stop, stop);
		        return db.insert(DATABASE_Stop, null, initialValues);
	    	}
	    }

	    //---retrieves all the Stop with a particular dayID---
	    public Cursor getAllStop(long dayID) {
	        return db.query(DATABASE_Stop, new String[] {
	        		KEY_StopID, KEY_DayOfServiceID, KEY_Stop}, 
	                KEY_DayOfServiceID + " = " + dayID, null, null, null, null);
	    }

	    //---updates a Stop---
	    public boolean updateStop(long stopID, long route_ID, String stop) 
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_RouteID, route_ID);
	        args.put(KEY_Stop, stop);
	        return db.update(DATABASE_Stop, args, 
	                         KEY_StopID + "=" + stopID, null) > 0;
	    }
	    
	    //-------------------------------------------Time----------------------------------------------//

	  //---insert a Route into the database---
	    public long insertTime(long stopID, float time) 
	    {
	    	Cursor mCursor = db.query(DATABASE_Time, new String[] {
	        		KEY_TimeID, KEY_StopID, KEY_Time},
	                KEY_StopID + "=" + stopID + " AND " + KEY_Time + "=" + time,
	                null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		long ret = mCursor.getLong(0);
	    		mCursor.close();
	    		return ret;
	    	} else {
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_StopID, stopID);
		        initialValues.put(KEY_Time, time);
		        return db.insert(DATABASE_Time, null, initialValues);
	    	}
	    }

	    //---deletes a particular Time---
	    public boolean deleteTime(long TimeID) 
	    {
	        return db.delete(DATABASE_Time, KEY_TimeID + 
	        		"=" + TimeID, null) > 0;
	    }

		/**
		 * Retrieve a particular service based on the stopID.
		 * @param stopID ID of the stop.
		 * @return Cursor with the listing of all the service that matches that particular stop ID.
		 */
	    public Cursor getAllTimes(long stopID) 
	    {
	        return db.query(DATABASE_Time, new String[] {
	        		KEY_StopID, KEY_Time}, KEY_StopID + " = " + stopID, 
	        		null, null, null, null, null);
	    }
}
