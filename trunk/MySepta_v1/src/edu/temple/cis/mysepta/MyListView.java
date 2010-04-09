package edu.temple.cis.mysepta;
//import java.util.List;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.temple.cis.mysepta.data.SeptaDB2;
import edu.temple.cis.mysepta.data.Service;

public class MyListView extends ListActivity {

	String[] services = { "Regional Rail", "Market-Frankford Line",
			"Broad Street Line", "Trolley Lines", "Norristown High Speed Line",
			"Bus Line"

	};
	
	public Service[] s = null;
	@Override  
  public void onCreate(Bundle savedInstanceState) 
  {
      super.onCreate(savedInstanceState);
      ListView lv = new ListView(this);
      setContentView(lv);
      SeptaDB2 db = new SeptaDB2(this);
      try {
			db.open();
	        //ListView lv = (ListView)findViewById(R.id.serviceList);
	        Service[] s = db.getService();
	        setListAdapter(new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, s));
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      //setListAdapter(new ArrayAdapter<Service>(this,android.R.layout.simple_list_item_1, s));
   }    

	
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
//		Toast.makeText(this, "You have selected " + s[position],
	//			Toast.LENGTH_SHORT).show();
		
		switch (position) {
		case 0: {//Regional Rail
			Intent intent = new Intent(this, MySepta.class);
			startActivity(intent);
			break;
		}
		case 1: {//Market-Frankford Line
			Intent intent = new Intent(this, FavSchedule.class);
			startActivity(intent);
			break;
		}
		case 2: {//Broad Street Line
			Intent intent = new Intent(this, MySepta.class);
			startActivity(intent);
			break;
		}
		case 3: {//Trolley Line
			Intent intent = new Intent(this, MySepta.class);
			startActivity(intent);
			break;
		}
		case 4: {//Norristown High Speed Line
			Intent intent = new Intent(this, MySepta.class);
			startActivity(intent);
			break;
		}
		case 5: {//Bus Line
			Intent intent = new Intent(this, MySepta.class);
		    startActivity(intent);
			break;
		}
		}//End switch

	}//End onListItemClick

}//End Class
