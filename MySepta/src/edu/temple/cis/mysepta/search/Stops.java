package edu.temple.cis.mysepta.search;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Stops extends ListActivity{
	
	String[] MFLStops = {
			"Frankford Transportation Center", 
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
			"69th Street Terminal" 
			
    };
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.mfl);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, MFLStops));
     }    
 
    public void onListItemClick(ListView parent, View v, int position, long id) 
    {   
        Toast.makeText(this,"You have selected " + MFLStops[position],Toast.LENGTH_SHORT).show();
        
        switch (position) {
		case 0: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 1: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 2: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 3: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 4: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 5: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
		    startActivity(intent);
			break;
		}
		case 6: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 7: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 8: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 9: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
		    startActivity(intent);
			break;
		}
		case 10: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 11: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 12: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
			startActivity(intent);
			break;
		}
		case 13: {//
			Intent intent = new Intent(this, edu.temple.cis.tusepta.search.SearchSchedule.class);
		    startActivity(intent);
			break;
		}
		
   	}//End switch
  }

}