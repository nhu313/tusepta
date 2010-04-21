package edu.temple.cis.mysepta.search;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.DayOfService;
//import edu.temple.cis.mysepta.myclass.Route;
//import edu.temple.cis.mysepta.myclass.Service;

public class MFL extends ListActivity{
	
public DayOfService[] dayofservice2 = null;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.mylistview);
        SeptaDB db = new SeptaDB(this);
               
        try {
			db.open();
			   DayOfService[] dayofservice2 = db.getDayOfServiceBus22();
	           setListAdapter(new ArrayAdapter<DayOfService>(this, android.R.layout.simple_list_item_1, dayofservice2));
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
		public void onListItemClick(ListView parent, View v, int position, long id) {
			
				Toast.makeText(this, "You have selected " + "dayofservice",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, edu.temple.cis.mysepta.search.Stops.class);
				startActivity(intent);
				
		}

}
