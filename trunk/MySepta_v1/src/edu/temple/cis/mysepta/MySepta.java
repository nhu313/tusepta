package edu.temple.cis.mysepta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import edu.temple.cis.mysepta.data.DBAdapter;
import edu.temple.cis.mysepta.data.SeptaDB;

public class MySepta extends Activity {
	public SeptaDB db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SeptaDB db = new SeptaDB(this);
        Log.i(db.nhuTag, "in My Septa opening db");
        
        try {
			db.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        db.close();
    }
 
    public void initialize(){
    	db = new SeptaDB(this);

    }
    
    public void getSchedule(){
    	
    }
    
    public void getStop(){
    	
    }
    
    public void getTime(){
    	
    }
}