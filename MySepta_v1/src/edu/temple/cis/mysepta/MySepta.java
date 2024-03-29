package edu.temple.cis.mysepta;

import java.util.List;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.Route;
import edu.temple.cis.mysepta.myclass.Service;
import edu.temple.cis.mysepta.myclass.Stop;

public class MySepta extends Activity {
	public SeptaDB db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView();
        SeptaDB db = new SeptaDB(this);
        
        try {
			db.open();
			Service[] s = db.getService();
			Route[] rt = db.getRoute(s[0]);
			DayOfService[] day = db.getDayOfService(rt[0]);
			Stop[] list = db.getStop(day[0]);
			ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
			ListView lv = new ListView(this);
			lv.setAdapter(aa);
			setContentView(lv);
		} catch (Exception e) {
			Log.i(db.nhuTag, " EEEEEEERRRROOOR " + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
    
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