package wan.group.septa;

//import java.util.List;

import java.util.List;

import wan.group.septa.data.Route;
import wan.group.septa.data.SeptaDB2;
import wan.group.septa.data.Service;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyListView extends ListActivity {

	//String[] services = { "Regional Rail", "Market-Frankford Line",
	//		"Broad Street Line", "Trolley Lines", "Norristown High Speed Line",
	//		"Bus Line"

	//};
	
	public Service[] ser = null;
	public List<Service> services2 = null;
	public List<Route> route2 = null;
	int one = 0;
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.listview);
        SeptaDB2 db = new SeptaDB2(this);
        try {
			db.open();
	        //ListView lv = (ListView)findViewById(R.id.serviceList);
	        //List<Service> services2 = db.getServices();
			List<Route> route2 = db.getRoute(one);
	        //Service[] ser = db.getService();
	        setListAdapter(new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_1, route2));
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
     }    

	public void onListItemClick(ListView parent, View v, int position, long id) {
		
		Toast.makeText(this, "You have selected " + "RouteRoute",//ser[position],
				Toast.LENGTH_SHORT).show();
		
		switch (position) {
		case 0: {//Regional Rail
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 1: {//Market-Frankford Line
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 2: {//Broad Street Line
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 3: {//Trolley Line
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 4: {//Norristown High Speed Line
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 5: {//Bus Line
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		}//End switch

	}//End onListItemClick

}//End Class
