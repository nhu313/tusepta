package edu.temple.cis.mysepta.search;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Spinner;
import android.widget.Toast;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
//import edu.temple.cis.mysepta.data.DBAdapter;
import edu.temple.cis.mysepta.myclass.Schedule;

public class Schedules extends ListActivity{
	
public Schedule[] schedule = null;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.mylistview);
        SeptaDB db = new SeptaDB(this);
               
        try {
			db.open();
			   Schedule[] schedule = db.getScheduleBus21();
	           setListAdapter(new ArrayAdapter<Schedule>(this, android.R.layout.simple_list_item_1, schedule));
	           //Spinner localSpinner = (Spinner)findViewById(R.id.color_spinner);
	           //ArrayAdapter<Schedule> adapter = ArrayAdapter(this,schedule,R.layout.my_normal_spinner_item_style);
	       	   //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	       	   //localSpinner.setAdapter(adapter);        
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
		public void onListItemClick(ListView parent, View v, int position, long id) {
			
				Toast.makeText(this, "You have selected " + "Schedule",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, edu.temple.cis.mysepta.search.ListViewService.class);
				startActivity(intent);
				
		}


}
