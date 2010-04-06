package edu.temple.cis.mysepta.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DBAdapter {
	
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
	    //For Stop Table -----------------------------------------------
	    public static final String KEY_StopID = "stopID";
	    public static final String KEY_Stop = "stop";
	   //For Schedule Table ---------------------------------------------
	    public static final String KEY_ScheduleID = "scheduleID";
	    public static final String KEY_DayOfService = "dayOfService";
	    public static final String KEY_Direction = "direction";
	    public static final String KEY_Time = "time";  
	    //----------------------------------------------------------------    
	    private static final String TAG = "SeptaDB";
	    private static final String DATABASE_NAME = "MySepta";    // Database Name
	    private static final String DATABASE_Service = "Service"; // Table 
	    private static final String DATABASE_Route = "Route";	  // Table
	    private static final String DATABASE_Stop = "Stop";       // Table
	    private static final String DATABASE_Schedule = "Schedule"; // Table
	    private static final String DATABASE_BackupStop = "BackupStop";  // Table
	    private static final String DATABASE_BackupSchedule = "BackupSchedule"; // Table
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
	    // Create Stop Table Statement
	    private static final String DATABASE_CREATE_Stop =
	        "create table " + DATABASE_Stop + " (" + KEY_StopID + autoInt
	        + KEY_RouteID + " integer not null, " + KEY_Stop + " text not null);";
	    // Create Schedule Table Statement
	    private static final String DATABASE_CREATE_Schedule =
	    	"create table " + DATABASE_Schedule + " (" + KEY_ScheduleID + autoInt
	    	+ KEY_StopID + " integer not null, " + KEY_DayOfService + " text not null, "
	    	+ KEY_Direction + " text not null, " + KEY_Time + " REAL not null);";
	    // Create BackupStop Table Statement
	    private static final String DATABASE_CREATE_BackupStop =
	        "create table " + DATABASE_BackupStop + " (" + KEY_StopID + autoInt
	        + KEY_RouteID + " integer not null, " + KEY_Stop + " text not null);";
	    // Create Schedule Table Statement
	    private static final String DATABASE_CREATE_BackupSchedule =
	    	"create table " + DATABASE_BackupSchedule + " (" + KEY_ScheduleID + autoInt
	    	+ KEY_StopID + " integer not null, " + KEY_DayOfService + " text not null, "
	    	+ KEY_Direction + " text not null, " + KEY_Time + " REAL not null);";
	    //-----------------------------------------------------------------------   
	    private final Context context; 
	    
	    private DatabaseHelper DBHelper;
	    private SQLiteDatabase db;

	    public DBAdapter(Context ctx) 
	    {
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
	            db.execSQL(DATABASE_CREATE_Stop);
	            db.execSQL(DATABASE_CREATE_Schedule);
	            db.execSQL(DATABASE_CREATE_BackupStop);
	            db.execSQL(DATABASE_CREATE_BackupSchedule);
	            
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
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Stop);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_Schedule);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_BackupStop);
	            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_BackupStop);
	            onCreate(db);
	        }
	    }    
	    
	    //---opens the database---
	    public DBAdapter open() throws SQLException, Exception 
	    {
	    	Log.i(nhuTag, " opening db through adapter");
	        db = DBHelper.getWritableDatabase();
	        return this;
	    }

	    //---closes the database---    
	    public void close() 
	    {
	        DBHelper.close();
	    }
	    
	    //----------------------------------------SERVICE-------------------------------------------//
	    //---insert a Service into the database---
	    public long insertService(String shortName, String longName, String color) 
	    {
	    	Cursor mCursor = db.query(DATABASE_Service, new String[] {
	        		KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color},
	        		KEY_LongName + "=" + "'"+longName+"'",
	        		null, null, null, null);
	    	
	    	if (mCursor.moveToFirst()){
	    		Log.i(nhuTag, " Insert Service but already in system " 
	    				+ longName + " " + mCursor.getLong(0));
	    		return mCursor.getLong(0);
	    	} else {
	    		ContentValues initialValues = new ContentValues();
	    		initialValues.put(KEY_ShortName, shortName);
	    		initialValues.put(KEY_LongName, longName);
	    		initialValues.put(KEY_Color, color);
	    		Log.i(nhuTag, " Insert Service" + longName);
	    		return db.insert(DATABASE_Service, null, initialValues);
	    	}
	    }

	    //---deletes a particular Service---
	    public boolean deleteService(long serviceID) 
	    {
	        return db.delete(DATABASE_Service, KEY_ServiceID + 
	        		"=" + serviceID, null) > 0;
	    }

	    //---retrieves all the Services---
	    public Cursor getAllServices() 
	    {
	        return db.query(DATABASE_Service, new String[] {
	        		KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color}, 
	                null, null, null, null, null);
	    }

	    //---retrieves a particular Service---
	    public Cursor getService(long serviceID) throws SQLException 
	    {
	        Cursor mCursor = db.query(DATABASE_Service, new String[] {
	        		KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color},
	        		KEY_ServiceID + "=" + serviceID, null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }

	    //---updates a Service---
	    public boolean updateService(long serviceID, String shortName, 
	    String longName, String color) 
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_ShortName, shortName);
	        args.put(KEY_LongName, longName);
	        args.put(KEY_Color, color);
	        return db.update(DATABASE_Service, args, 
	                         KEY_ServiceID + "=" + serviceID, null) > 0;
	    }
	    
	    public boolean isServiceEmpty(){
			try{
				Cursor c = db.query(DATABASE_Service, new String[] {
						KEY_ServiceID, KEY_ShortName, KEY_LongName, KEY_Color}, 
						null, null, null, null, null);
				int numRows = c.getCount();
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
	    		return mCursor.getLong(0);
	    	} else {
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

	    //---deletes a particular Route---
	    public boolean deleteRoute(long routeID) 
	    {
	        return db.delete(DATABASE_Route, KEY_RouteID + 
	        		"=" + routeID, null) > 0;
	    }

	    //---retrieves all the Routes---
	    public Cursor getAllRoutes() 
	    {
	        return db.query(DATABASE_Route, new String[] {
	        		KEY_RouteID, KEY_ServiceID, KEY_RouteNumber,
	                KEY_RouteName, KEY_IsFavorite, KEY_URL},
	                null, null, null, null, null, null);
	    }

	    //---retrieves a particular Route---
	    public Cursor getRoute(long routeID) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_Route, new String[] {
	                		KEY_RouteID, KEY_ServiceID, KEY_RouteNumber, KEY_RouteName,
	                		KEY_IsFavorite, KEY_URL }, KEY_RouteID + "=" + routeID, 
	                		null, null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
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
	    
	    //-------------------------------------------------STOP---------------------------------------------//
	  
	    //---insert a Stop into the database---
	    public long insertStop(long route_ID, String stop) 
	    { 	
	    	Cursor mCursor = db.query(true, DATABASE_Stop, new String[] {
        		KEY_StopID, KEY_RouteID, KEY_Stop}, 
        		KEY_StopID + "=" + route_ID + " AND " + KEY_Stop + " = '" + stop + "'", 
        		null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		return mCursor.getLong(0);
	    	} else {
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_RouteID, route_ID);
		        initialValues.put(KEY_Stop, stop);
		        return db.insert(DATABASE_Stop, null, initialValues);
	    	}
	    }

	    //---deletes a particular Stop---
	    public boolean deleteStop(long stopID) 
	    {
	        return db.delete(DATABASE_Stop, KEY_StopID + 
	        		"=" + stopID, null) > 0;
	    }

	    //---retrieves all the Stop---
	    public Cursor getAllStop() 
	    {
	        return db.query(DATABASE_Stop, new String[] {
	        		KEY_StopID, KEY_RouteID, KEY_Stop}, 
	                null, null, null, null, null);
	    }

	    //---retrieves a particular Stop---
	    public Cursor getStop(long stopID) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_Stop, new String[] {
	                		KEY_StopID, KEY_RouteID, KEY_Stop}, 
	                		KEY_StopID + "=" + stopID, 
	                		null, null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
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
	    
	    //-------------------------------------------SCHEDULE----------------------------------------------//

	  //---insert a Route into the database---
	    public long insertSchedule(long stop_ID, String dayOfService, String direction, float time) 
	    {
	    	Cursor mCursor = db.query(DATABASE_Schedule, new String[] {
	        		KEY_ScheduleID, KEY_StopID, KEY_DayOfService,
	                KEY_Direction, KEY_Time},
	                null, null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		return mCursor.getLong(0);
	    	} else {
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_StopID, stop_ID);
		        initialValues.put(KEY_DayOfService, dayOfService);
		        initialValues.put(KEY_Direction, direction);
		        initialValues.put(KEY_Time, time);
		        return db.insert(DATABASE_Schedule, null, initialValues);
	    	}
	    }

	    //---deletes a particular Schedule---
	    public boolean deleteSchedule(long scheduleID) 
	    {
	        return db.delete(DATABASE_Schedule, KEY_ScheduleID + 
	        		"=" + scheduleID, null) > 0;
	    }

	    //---retrieves all the Schedule---
	    public Cursor getAllSchedules() 
	    {
	        return db.query(DATABASE_Schedule, new String[] {
	        		KEY_ScheduleID, KEY_StopID, KEY_DayOfService,
	                KEY_Direction, KEY_Time},
	                null, null, null, null, null, null);
	    }

	    //---retrieves a particular Schedule---
	    public Cursor getSchedule(long scheduleID) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_Schedule, new String[] {
	                		KEY_ScheduleID, KEY_StopID, KEY_DayOfService,
	                		KEY_Direction, KEY_Time}, KEY_ScheduleID + "=" + scheduleID, 
	                		null, null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }

	    //---updates a Schedule---
	    public boolean updateSchedule(long scheduleID, long stop_ID, 
	    String dayOfService, String direction, float time) 
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_StopID, stop_ID);
	        args.put(KEY_DayOfService, dayOfService);
	        args.put(KEY_Direction, direction);
	        args.put(KEY_Time, time);
	        return db.update(DATABASE_Schedule, args, 
	                         KEY_ScheduleID + "=" + scheduleID, null) > 0;
	    }
	    
	  //-------------------------------------------------BackupSTOP---------------------------------------------//
	    
	    //---insert a BackupStop into the database---
	    public long insertBackupStop(long route_ID, String stop) 
	    {
	    	Cursor mCursor = db.query(true, DATABASE_Stop, new String[] {
	        		KEY_StopID, KEY_RouteID, KEY_Stop}, 
	        		KEY_StopID + "=" + route_ID + " AND " + KEY_Stop + " = '" + stop + "'", 
	        		null, null, null, null, null);
	    	if (mCursor.moveToFirst()){
	    		return mCursor.getLong(0);
	    	} else {
		        ContentValues initialValues = new ContentValues();
		        initialValues.put(KEY_RouteID, route_ID);
		        initialValues.put(KEY_Stop, stop);
		        return db.insert(DATABASE_BackupStop, null, initialValues);
		    	}
	    }

	    //---deletes a particular Stop---
	    public boolean deleteBackupStop(long stopID) 
	    {
	        return db.delete(DATABASE_BackupStop, KEY_StopID + 
	        		"=" + stopID, null) > 0;
	    }

	    //---retrieves all the Stop---
	    public Cursor getAllBackupStop() 
	    {
	        return db.query(DATABASE_BackupStop, new String[] {
	        		KEY_StopID, KEY_RouteID, KEY_Stop}, 
	                null, null, null, null, null);
	    }

	    //---retrieves a particular Stop---
	    public Cursor getBackupStop(long stopID) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_BackupStop, new String[] {
	                		KEY_StopID, KEY_RouteID, KEY_Stop}, 
	                		KEY_StopID + "=" + stopID, 
	                		null, null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }

	    //---updates a Stop---
	    public boolean updateBackupStop(long stopID, long route_ID, String stop) 
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_RouteID, route_ID);
	        args.put(KEY_Stop, stop);
	        return db.update(DATABASE_BackupStop, args, 
	                         KEY_StopID + "=" + stopID, null) > 0;
	    }
	    
	    //-------------------------------------------BackupSCHEDULE----------------------------------------------//

	  //---insert a Route into the database---
	    public long insertBackupSchedule(long stop_ID, String dayOfService, String direction, float time) 
	    {
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_StopID, stop_ID);
	        initialValues.put(KEY_DayOfService, dayOfService);
	        initialValues.put(KEY_Direction, direction);
	        initialValues.put(KEY_Time, time);
	        return db.insert(DATABASE_BackupSchedule, null, initialValues);
	    }

	    //---deletes a particular Schedule---
	    public boolean deleteBackupSchedule(long scheduleID) 
	    {
	        return db.delete(DATABASE_BackupSchedule, KEY_ScheduleID + 
	        		"=" + scheduleID, null) > 0;
	    }

	    //---retrieves all the Schedule---
	    public Cursor getAllBackupSchedules() 
	    {
	        return db.query(DATABASE_BackupSchedule, new String[] {
	        		KEY_ScheduleID, KEY_StopID, KEY_DayOfService,
	                KEY_Direction, KEY_Time},
	                null, null, null, null, null, null);
	    }

	    //---retrieves a particular Schedule---
	    public Cursor getBackupSchedule(long scheduleID) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_BackupSchedule, new String[] {
	                		KEY_ScheduleID, KEY_StopID, KEY_DayOfService, 
	                		KEY_Direction, KEY_Time}, 
	                		KEY_ScheduleID + "=" + scheduleID, 
	                		null, null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }

	    //---updates a Schedule---
	    public boolean updateBackupSchedule(long scheduleID, long stop_ID, 
	    String dayOfService, String direction, float time) 
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_StopID, stop_ID);
	        args.put(KEY_DayOfService, dayOfService);
	        args.put(KEY_Direction, direction);
	        args.put(KEY_Time, time);
	        return db.update(DATABASE_BackupSchedule, args, 
	                         KEY_ScheduleID + "=" + scheduleID, null) > 0;
	    }
}
