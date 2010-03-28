package wan.group.db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter extends Activity {
	
    //For Service Table -------------------------------------------
	public static final String KEY_ServiceID = "serviceID";
    public static final String KEY_ShortName = "shortName";
    public static final String KEY_LongName = "longName";
    public static final String KEY_Color = "color";    
    //For Route Table ---------------------------------------------
    public static final String KEY_RouteID = "routeID";
    public static final String KEY_Service_ID = "service_ID";
    public static final String KEY_RouteNumber = "routeNumber";
    public static final String KEY_RouteName = "routeName";
    public static final String KEY_FavoriteRoute = "favoriteRoute";
    //For Stop Table -----------------------------------------------
    public static final String KEY_StopID = "stopID";
    public static final String KEY_Route_ID = "route_ID";
    public static final String KEY_Stop = "stop";
   //For Schedule Table ---------------------------------------------
    public static final String KEY_ScheduleID = "scheduleID";
    public static final String KEY_Stop_ID = "stop_ID";
    public static final String KEY_DayOfService = "dayOfService";
    public static final String KEY_Direction = "direction";
    public static final String KEY_Time = "time";  
    //----------------------------------------------------------------    
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "MySepta";
    private static final String DATABASE_Service = "Service";
    private static final String DATABASE_Route = "Route";
    private static final String DATABASE_Stop = "Stop";
    private static final String DATABASE_Schedule = "Schedule";
    private static final int DATABASE_VERSION = 1;

    // Create Service Table
    private static final String DATABASE_CREATE_Service =
        "create table Service (seviceID integer primary key autoincrement, "
        + "shortName text not null, longName text not null, " 
        + "color text not null);";
    // Create Route Table
    private static final String DATABASE_CREATE_Route =
        "create table Route (routeID integer primary key autoincrement, "
        + "service_ID integer not null, routeNumber text not null, " 
        + "routeName text not null, favoriteRoute integer not null);";
    // Create Stop Table
    private static final String DATABASE_CREATE_Stop =
        "create table Stop (stopID integer primary key autoincrement, "
        + "route_ID integer not null, stop text not null);";
    // Create Schedule Table
    private static final String DATABASE_CREATE_Schedule =
        "create table Schedule (scheduleID integer primary key autoincrement, "
        + "stop_ID integer not null, dayOfService text not null, " 
        + "direction text not null, time integer not null);";
        
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

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE_Service);
            db.execSQL(DATABASE_CREATE_Route);
            db.execSQL(DATABASE_CREATE_Stop);
            db.execSQL(DATABASE_CREATE_Schedule);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }    
    
    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
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
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ShortName, shortName);
        initialValues.put(KEY_LongName, longName);
        initialValues.put(KEY_Color, color);
        return db.insert(DATABASE_Service, null, initialValues);
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
        		KEY_ServiceID, 
        		KEY_ShortName,
        		KEY_LongName,
                KEY_Color}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---retrieves a particular Service---
    public Cursor getService(long serviceID) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_Service, new String[] {
                		KEY_ServiceID,
                		KEY_ShortName, 
                		KEY_LongName,
                		KEY_Color
                		}, 
                		KEY_ServiceID + "=" + serviceID, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
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
    
  //------------------------------ROUTE--------------------------------------------------------//
    
  //---insert a Route into the database---
    public long insertRoute(long service_ID, String routeNumber, String routeName, long favoriteRoute) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_Service_ID, service_ID);
        initialValues.put(KEY_RouteNumber, routeNumber);
        initialValues.put(KEY_RouteName, routeName);
        return db.insert(DATABASE_Route, null, initialValues);
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
        		KEY_RouteID, 
        		KEY_Service_ID,
        		KEY_RouteNumber,
                KEY_RouteName,
                KEY_FavoriteRoute},
                null, 
                null, 
                null, 
                null,
                null,
                null);
    }

    //---retrieves a particular Route---
    public Cursor getRoute(long routeID) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_Route, new String[] {
                		KEY_RouteID,
                		KEY_Service_ID, 
                		KEY_RouteNumber,
                		KEY_RouteName,
                		KEY_FavoriteRoute
                		}, 
                		KEY_RouteID + "=" + routeID, 
                		null,
                		null, 
                		null, 
                		null,
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a Route---
    public boolean updateRoute(long routeID, long service_ID, 
    String routeNumber, String routeName, String favoriteRoute) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_Service_ID, service_ID);
        args.put(KEY_RouteNumber, routeNumber);
        args.put(KEY_RouteName, routeName);
        args.put(KEY_FavoriteRoute, favoriteRoute);
        return db.update(DATABASE_Route, args, 
                         KEY_RouteID + "=" + routeID, null) > 0;
    }
    
    //-------------------------------------------------STOP---------------------------------------------//
  
    //---insert a Stop into the database---
    public long insertStop(long route_ID, String stop) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_Route_ID, route_ID);
        initialValues.put(KEY_Stop, stop);
        return db.insert(DATABASE_Stop, null, initialValues);
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
        		KEY_StopID, 
        		KEY_Route_ID,
        		KEY_Stop}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---retrieves a particular Stop---
    public Cursor getStop(long stopID) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_Stop, new String[] {
                		KEY_StopID,
                		KEY_Route_ID, 
                		KEY_Stop
                		}, 
                		KEY_StopID + "=" + stopID, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a Stop---
    public boolean updateStop(long stopID, long route_ID, String stop) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_Route_ID, route_ID);
        args.put(KEY_Stop, stop);
        return db.update(DATABASE_Stop, args, 
                         KEY_StopID + "=" + stopID, null) > 0;
    }
    
    //-------------------------------------------SCHEDULE----------------------------------------------//

  //---insert a Route into the database---
    public long insertSchedule(long stop_ID, String dayOfService, String direction, String time) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_Stop_ID, stop_ID);
        initialValues.put(KEY_DayOfService, dayOfService);
        initialValues.put(KEY_Direction, direction);
        initialValues.put(KEY_Time, time);
        return db.insert(DATABASE_Schedule, null, initialValues);
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
        		KEY_ScheduleID, 
        		KEY_Stop_ID,
        		KEY_DayOfService,
                KEY_Direction,
                KEY_Time},
                null, 
                null, 
                null, 
                null,
                null,
                null);
    }

    //---retrieves a particular Schedule---
    public Cursor getSchedule(long scheduleID) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_Schedule, new String[] {
                		KEY_ScheduleID,
                		KEY_Stop_ID, 
                		KEY_DayOfService,
                		KEY_Direction,
                		KEY_Time
                		}, 
                		KEY_RouteID + "=" + scheduleID, 
                		null,
                		null, 
                		null, 
                		null,
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a Schedule---
    public boolean updateSchedule(long scheduleID, long stop_ID, 
    String dayOfService, String direction, String time) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_Stop_ID, stop_ID);
        args.put(KEY_DayOfService, dayOfService);
        args.put(KEY_Direction, direction);
        args.put(KEY_Time, time);
        return db.update(DATABASE_Schedule, args, 
                         KEY_ScheduleID + "=" + scheduleID, null) > 0;
    }
    

}