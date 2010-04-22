package edu.temple.cis.mysepta2.myschedule;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.temple.cis.mysepta.myclass.MySchedule;
import edu.temple.cis.mysepta2.R;
import edu.temple.cis.mysepta2.R.id;
import edu.temple.cis.mysepta2.R.layout;

public class MyScheduleAdapterView extends RelativeLayout{

	public MyScheduleAdapterView(Context context, MySchedule entry) {
		super(context);
		this.setTag(entry);
		
		View v = inflate(context, R.layout.mysched_row, null);
		
		TextView route = (TextView)v.findViewById(R.id.mysched_row_route);
		TextView time = (TextView)v.findViewById(R.id.mysched_row_time);
		TextView stop = (TextView)v.findViewById(R.id.mysched_row_stop);
		
		route.setText(entry.getRoute());
		time.setText(entry.getTime() + "");
		stop.setText(entry.getStop());
		addView(v);
	}

}
