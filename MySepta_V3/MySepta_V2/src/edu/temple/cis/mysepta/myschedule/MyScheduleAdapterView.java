package edu.temple.cis.mysepta.myschedule;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.MySchedule;

public class MyScheduleAdapterView extends RelativeLayout{

	public MyScheduleAdapterView(Context context, MySchedule entry) {
		super(context);
		this.setTag(entry);
		
		View v = inflate(context, R.layout.mysched_row, null);
		//Get route name
		TextView route = (TextView)v.findViewById(R.id.mysched_row_route);
		route.setText(entry.getRoute());
		//Get time
		TextView time = (TextView)v.findViewById(R.id.mysched_row_time);
		time.setText(entry.getTimeString());
		//Get stop
		TextView stop = (TextView)v.findViewById(R.id.mysched_row_stop);
		stop.setText(entry.getStop());
		//Get image
		ImageView img = (ImageView)v.findViewById(R.id.image);
		if (entry.getServiceId() == SeptaDB.rail_id){
			img.setImageResource(R.drawable.train);
		} else if (entry.getServiceId() == SeptaDB.trolley_id){
			img.setImageResource(R.drawable.trolley);
		} else {
			img.setImageResource(R.drawable.bus);
		}
				
		addView(v);
	}

}
