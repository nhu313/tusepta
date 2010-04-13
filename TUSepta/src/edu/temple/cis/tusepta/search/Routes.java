package edu.temple.cis.tusepta.search;

import android.app.ListActivity;
import wan.group.septa.R;
import wan.group.septa.data.SeptaDB2;
import wan.group.septa.data.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Routes extends ListActivity{
	
private int[] serviceids = {1,2,3,4,5,6};
	
	public Service[] service = null;
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
		int position = 0;
		int serviceid = serviceids[position];
		
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.listview);
        SeptaDB2 db = new SeptaDB2(this);
                
        try {
			db.open();
			if(position == 0)
			{	
	           Service[] service = db.getService();
				//Route[] route = db.getRoute(serviceid);
	         setListAdapter(new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, service));
	         
			}
			if (position == 3)
			{
				Service[] service = db.getService();
				//Route[] route = db.getRoute(serviceid);
				setListAdapter(new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, service));
			}
			if (position == 5)
			{
				Service[] service = db.getService();
				//Route[] route = db.getRoute(serviceid);
				setListAdapter(new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, service));
			}
	        
			
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
		public void onListItemClick(ListView parent, View v, int position, long id) {
			
				Toast.makeText(this, "You have selected " + "service[position]",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, edu.temple.cis.tusepta.search.NorthSouthBound.class);
				startActivity(intent);
				
		}

}