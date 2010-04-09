package edu.temple.cis.mysepta;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import currentlyUnused.SeptaDB;
import edu.temple.cis.mysepta.data.Route;
import edu.temple.cis.mysepta.data.ScheduleParser;
import edu.temple.cis.mysepta.data.SeptaDB2;
import edu.temple.cis.mysepta.data.Service;

public class MySepta extends Activity {
	public SeptaDB db;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
/*
        TextView tv = (TextView)findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MySepta.this, FavSchedule.class);
				startActivity(i);
				
			}
		});
		*/
		

        SeptaDB2 db = new SeptaDB2(this);
        
        try {
			db.open();
			ScheduleParser sp = new ScheduleParser();
			sp.parseSchedule("http://www.septa.org/schedules/bus/route091.html", db, 5);
			sp.parseSchedule("http://www.septa.org/schedules/rail/gln.html", db, 1);
			
			
			
			
			
			//List<Service> list = db.getService();
			
			/*
			Log.i(db.nhuTag, "Inserted day " + db.insertDayOfService(1, "Weekday to Center City"));
			Log.i(db.nhuTag, "Inserted day " + db.insertDayOfService(1, "Weekday to Home"));
			Log.i(db.nhuTag, "Inserted stop " + db.insertStop(1, "30th St"));
			Log.i(db.nhuTag, "Inserted stop " + db.insertStop(1, "22nd St"));
			*/
//			ScheduleParser sp = new ScheduleParser();
//			sp.parseSchedule("http://www.septa.org/schedules/bus/route091.html", db, 5);
			List<Service> list = db.getService();
			ArrayAdapter<Service> aa = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, list);
			
			ListView lv = new ListView(this);
			lv.setAdapter(aa);
			setContentView(lv);
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