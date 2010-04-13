package edu.temple.cis.mysepta.search;

import android.app.ListActivity;
import edu.temple.cis.mysepta.data.DBAdapter;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.service.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ListView extends ListActivity{
	
	public Service[] service = null;
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.listview);
        SeptaDB db = new SeptaDB(this);
        try {
			db.open();
	        Service[] service = db.getService();
	        setListAdapter(new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, service));
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
     }    

	public void onListItemClick(ListView parent, View v, int position, long id) {
					
		switch (position) {
		case 0: {//Regional Rail
			Toast.makeText(this, "You have selected " + "Regional Rail",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, edu.temple.cis.mysepta.search.Routes.class);
			startActivity(intent);
			break;
		}
		case 1: {//Market-Frankford Line
			Toast.makeText(this, "You have selected " + "Market-Frankford Line",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, edu.temple.cis.mysepta.search.NorthSouthBound.class);
			startActivity(intent);
			break;
		}
		case 2: {//Broad Street Line
			Toast.makeText(this, "You have selected " + "Broad Street Line",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, edu.temple.cis.mysepta.search.NorthSouthBound.class);
			startActivity(intent);
			break;
		}
		case 3: {//Trolley Line
			Toast.makeText(this, "You have selected " + "Trolley Line",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, edu.temple.cis.mysepta.search.Routes.class);
			startActivity(intent);
			break;
		}
		case 4: {//Norristown High Speed Line
			Toast.makeText(this, "You have selected " + "Norristown High Speed Line",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, edu.temple.cis.mysepta.search.NorthSouthBound.class);
			startActivity(intent);
			break;
		}
		case 5: {//Bus Line
			Toast.makeText(this, "You have selected " + "Bus Line",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, edu.temple.cis.mysepta.search.Routes.class);
		    startActivity(intent);
			break;
		}
		}//End switch

	}//End onListItemClick

}