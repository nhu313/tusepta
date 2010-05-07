package edu.temple.cis.mysepta.search;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.Route;
import edu.temple.cis.mysepta.myclass.Schedule;
import edu.temple.cis.mysepta.myclass.Service;
import edu.temple.cis.mysepta.myclass.Stop;

/**
 * Implement the list view.  
 */

public class MyListAdapterView extends LinearLayout {

	public MyListAdapterView(Context context, Object entry) {
		super(context);
		this.setTag(entry);
		
		View v = inflate(context, R.layout.list_row_med, null);
		TextView txt = (TextView)v.findViewById(R.id.list_row_txt);
		ImageView img = (ImageView)v.findViewById(R.id.image);
		
		//Configure the view for the service screen 
		if (entry instanceof Service){
			Service x = (Service)entry;
			txt.setText(x.toString());
			txt.setTextSize(30);
			txt.setPadding(15, 0, 0, 0);
			if (x.getId() == SeptaDB.rail_id){
				img.setImageResource(R.drawable.train);
			} else if (x.getId() == SeptaDB.trolley_id){
				img.setImageResource(R.drawable.trolley);
			} else {
				img.setImageResource(R.drawable.bus);
			}
		//Configure the view for the route screen
		} else if (entry instanceof Route){
			Route x = (Route)entry;
			txt.setText(x.toString());
			if (x.getService_id() == SeptaDB.rail_id){
				img.setImageResource(R.drawable.train);
			} else if (x.getService_id() == SeptaDB.trolley_id){
				img.setImageResource(R.drawable.trolley);
			} else {
				img.setImageResource(R.drawable.bus);
			}
		//Configure the view for the day of service screen
		} else if (entry instanceof DayOfService){
			DayOfService x = (DayOfService)entry;
			txt.setText(x.toString());
			img.setImageResource(R.drawable.date);
		//Configure the view for the Stop screen
		} else if (entry instanceof Stop){
			Stop x = (Stop) entry;
			txt.setText(x.getStopName());
			img.setImageResource(R.drawable.stop);			
		//Configure the view for the schedule screen
		} else {
			Schedule x = (Schedule) entry;
			txt.setText(x.toString());
			img.setImageResource(R.drawable.clock);
		}
		addView(v);
	}

}
