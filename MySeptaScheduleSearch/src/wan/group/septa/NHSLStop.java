package wan.group.septa;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NHSLStop extends ListActivity{
	
	
	String[] N_NHSLStop = { "69th Street Terminal",  
			"Parkview",  
			"West Overbrook",  
			"Penfield",  
			"Beechwood-Brookline",  
			"Wynnewood Rd",  
			"Ardmore Junction",  
			"Ardmore Avenue",  
			"Haverford",  
			"Bryn Mawr",  
			"Rosemont",  
			"Garrett Hill",  
			"Stadium",  
			"Villanova",  
			"Radnor",  
			"County Line",  
			"Matsonford",  
			"Gulph Mills",  
			"Hughes Park",  
			"King Manor",  
			"Bridgeport",  
			"Norristown Transportation Ctr" 

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, N_NHSLStop));
	}
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Toast.makeText(this, "You have selected " + N_NHSLStop[position],
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

}
