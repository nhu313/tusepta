package wan.group.septa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper{
	
	private static String DB_PATH = "/data/data/wan.group.septa/databases";
	private static String DB_NAME = "MySeptaDB";
	private static SQLiteDatabase myDB;
	private final Context myContext;

	public DataBaseHelper(Context context) {
	super(context, DB_NAME, null, 1);
	this.myContext=context;

	}

	public void createDataBase() throws IOException{
	boolean dbExist = checkDB();
	if(dbExist){

	}else{
	this.getReadableDatabase();
	try{
	copyDB();
	}catch (IOException e) {
	throw new Error("Database could not be copied");
	}
	}
	}

	private boolean checkDB(){
	SQLiteDatabase checkDB = null;
	try{
	String myPath = DB_PATH + DB_NAME;
	checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}catch (SQLiteException e) {
	//database does not yet exist
	}
	if(checkDB != null){
	checkDB.close();
	}
	return checkDB != null ? true : false;
	}

	private void copyDB() throws IOException {

	//open local as input stream
	InputStream myInputStream = myContext.getAssets().open(DB_NAME);

	//path to newly created && empty db

	String outFileName = DB_PATH + DB_NAME;

	//open empty db as output stream

	OutputStream myOutputStream = new FileOutputStream(outFileName);

	//transfer bytes

	byte[] buffer = new byte[1024];
	int length;
	while ((length = myInputStream.read(buffer))>0){
	myOutputStream.write(buffer, 0, length);
	}
	myOutputStream.flush();
	myOutputStream.close();
	myInputStream.close();
	}
	public void openDB() throws SQLiteException{
	String myPath = DB_PATH + DB_NAME;
	myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
	@Override
	public synchronized void close(){
	if(myDB != null)
	myDB.close();
	super.close();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	// TODO Auto-generated method stub
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// TODO Auto-generated method stub
	}
	public Cursor getAllServices() 
    {
        return myDB.query("Service", new String[] {
        		"_id", 
        		"ShortName",
        		"LongName",
                "Color"}, 
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
                myDB.query(true, "Service", new String[] {
                		"_id",
                		"ShortName", 
                		"LongName",
                		"Color"
                		}, 
                		"_id" + "=" + serviceID, 
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
	
	

}
