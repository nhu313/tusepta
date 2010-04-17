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
import edu.temple.cis.mysepta.myclass.Route;
import edu.temple.cis.mysepta.myclass.Service;

public class Bus extends ListActivity{
	
	public Route[] route = null;
	public Service[] service = null;
				
		@Override  
	    public void onCreate(Bundle savedInstanceState) 
	    {
			super.onCreate(savedInstanceState);  
	        setContentView(R.layout.mylistview3);
	        SeptaDB db = new SeptaDB(this);
	        try {
				db.open();
				 Route[] route = db.getBus();
		         setListAdapter(new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_1, route));
		         db.close();
			        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    }
		public void onListItemClick(ListView parent, View v, int position, long id) {
			
			Toast.makeText(this, "You have selected " + "route",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, edu.temple.cis.mysepta.search.ListViewService.class);
			startActivity(intent);
			
	   }

}
