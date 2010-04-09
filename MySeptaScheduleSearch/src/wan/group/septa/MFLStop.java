package wan.group.septa;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MFLStop extends ListActivity{
	
	String[] NSBoundStop = { "Frankford Transportation Center", 
			"Margaret-Orthodox (4700 North)", 
			"Church (4300 North)", 
			"Erie-Torresdale (3900 North)", 
			"Tioga (3500 North)", 
			"Allegheny (3200 North)", 
			"Somerset (2700 North)", 
			"Huntingdon (2600 North)", 
			"York-Dauphin (2300 North)", 
			"Berks (1900 North)", 
			"Girard (1200 North)", 
			"Spring Garden (1500 North)", 
			"2nd and Market Sts", 
			"5th and Market Sts", 
			"8th and Market Sts", 
			"11th and Market Sts", 
			"13th and Market Sts", 
			"15th and Market Sts", 
			"30th and Market Sts", 
			"34th and Market Sts", 
			"40th and Market Sts", 
			"46th and Market Sts", 
			"52nd and Market Sts", 
			"56th and Market Sts", 
			"60th and Market Sts Closed for Construction", 
			"63rd and Market Sts", 
			"Millborune 69th Street", 
			"69th Street Terminal", 

	};
	String[] SNBoundStop = { "Regional Rail", "Market-Frankford Line",
			"Broad Street Line", "Trolley Lines", "Norristown High Speed Line",
			"Bus Line"

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, NSBoundStop));
	}
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Toast.makeText(this, "You have selected " + NSBoundStop[position],
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
