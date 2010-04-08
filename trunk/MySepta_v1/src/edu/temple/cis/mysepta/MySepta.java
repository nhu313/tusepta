package edu.temple.cis.mysepta;

import java.util.List;

import currentlyUnused.SeptaDB;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import edu.temple.cis.mysepta.data.Route;
import edu.temple.cis.mysepta.data.SeptaDB2;
import edu.temple.cis.mysepta.data.Service;

public class MySepta extends Activity {
	public SeptaDB db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SeptaDB2 db = new SeptaDB2(this);
        Log.i(db.nhuTag, "in My Septa opening db");
        
        try {
			db.open();
			List<Service> list = db.getService();
	    	for (int i = 0; i < list.size(); i++){
	    		Log.i(db.nhuTag, list.get(i).toString());
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        db.close();
    }
 
    public void printList(List<Route> list){
    	if (list == null){
    		Log.i(db.nhuTag, "AAAAAAAAAAHHHHHHHHHHH");
    		return;
    	}
    	Log.i(db.nhuTag, list.get(list.size()-1).toString());
    	for (int i = 1; i < list.size(); i++){
    		Log.i(db.nhuTag, list.get(i).toString());
    	}
    }
}