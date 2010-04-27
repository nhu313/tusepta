package edu.temple.cis.mysepta.myschedule;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.myclass.MySchedule;

public class MyScheduleAdapterView extends RelativeLayout{

	public MyScheduleAdapterView(Context context, MySchedule entry) {
		super(context);
		this.setTag(entry);
		
		View v = inflate(context, R.layout.mysched_row, null);
		
		TextView route = (TextView)v.findViewById(R.id.mysched_row_route);
		TextView time = (TextView)v.findViewById(R.id.mysched_row_time);
		TextView stop = (TextView)v.findViewById(R.id.mysched_row_stop);
		
		route.setText(entry.getRoute());
		time.setText(entry.getTimeString());
		stop.setText(entry.getStop());
		addView(v);
	}

}
