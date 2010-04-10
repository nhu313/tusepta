package wan.group.septa;

import android.app.ListActivity;
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
		int position = 2;
		int serviceid = serviceids[position];
		
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.listview);
        SeptaDB2 db = new SeptaDB2(this);
                
        try {
			db.open();
			if(position == 0)
			{	
	         //Service[] service = db.getService();
				//Route[] route = db.getRoute(serviceid);
			}
			if (position == 3)
			{
				//Route[] route = db.getRoute(serviceid);
			}
			if (position == 5)
			{
				//Route[] route = db.getRoute(serviceid);
			}
	        setListAdapter(new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_1, service));
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
     }    

}
