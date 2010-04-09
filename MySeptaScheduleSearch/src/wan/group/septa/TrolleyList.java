package wan.group.septa;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ListActivity; 
import android.content.Intent;

public class TrolleyList extends ListActivity{
	
	String[] trolley = {
			"Route 10",
			"Route 11",
			"Route 13",
			"Route 15",
			"Route 34",
			"Route 36",
			"Route 101",
			"Route 102"	
			
    };
	
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.trolley);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, trolley));
    }    
 
    public void onListItemClick(ListView parent, View v, int position, long id) 
    {   
        Toast.makeText(this,"You have selected " + trolley[position],Toast.LENGTH_SHORT).show();
        
        switch (position) {
		case 0: {//Route 10
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 1: {//Route 11
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 2: {//Route 13
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 3: {//Route 15
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 4: {//Route 34
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 5: {//Route 36
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
		    startActivity(intent);
			break;
		}
		case 6: {//Route 101
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
		case 7: {//Route 102
			Intent intent = new Intent(this, wan.group.septa.NorthOrSouthBound.class);
			startActivity(intent);
			break;
		}
    }//End switch
  }
}
