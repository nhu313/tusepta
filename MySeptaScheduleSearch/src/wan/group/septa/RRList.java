package wan.group.septa;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ListActivity; 
import android.content.Intent;


public class RRList extends ListActivity{
	
	String[] regionalRail = {
			"Airport Line (R1)",
			"Warminster Line (R2)",
			"Wilmington/Newark Line (R2)",
			"West Trenton Line (R3)",
			"Media/Elwyn Line (R3)",
			"Lansdale/Doylestown Line (R5)",
			"Paoli/Thorndale Line (R5)",
			"Norristown Line (R6)",
			"Cynwyd Line (R6)",
			"Chestnut Hill East Line (R7)",
			"Trenton Line (R7)",
			"Fox Chase Line (R8)",
			"Chestnut Hill West Line (R8)",
			"Glenside Combined"
			
    };
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.rr);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, regionalRail));
     }    
 
    public void onListItemClick(ListView parent, View v, int position, long id) 
    {   
        Toast.makeText(this,"You have selected " + regionalRail[position],Toast.LENGTH_SHORT).show();
        
        switch (position) {
		case 0: {//Airport Line (R1)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 1: {//Warminster Line (R2)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 2: {//Wilmington/Newark Line (R2)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 3: {//West Trenton Line (R3)
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 4: {//Media/Elwyn Line (R3)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 5: {//Lansdale/Doylestown Line (R5)
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 6: {//Paoli/Thorndale Line (R5)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 7: {//Norristown Line (R6)
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 8: {//Cynwyd Line (R6)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 9: {//Chestnut Hill East Line (R7)
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		case 10: {//Trenton Line (R7)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 11: {//Fox Chase Line (R8)
			Intent intent = new Intent(this, wan.group.septa.TrolleyList.class);
			startActivity(intent);
			break;
		}
		case 12: {//Chestnut Hill West Line (R8)
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 13: {//Glenside Combined
			Intent intent = new Intent(this, wan.group.septa.BusList.class);
		    startActivity(intent);
			break;
		}
		
   	}//End switch
  }

}//End Class
