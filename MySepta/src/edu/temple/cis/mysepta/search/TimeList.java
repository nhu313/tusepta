package edu.temple.cis.mysepta.search;
import java.util.List;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
//import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
//import edu.temple.cis.mysepta.favorite.DateOfServiceAct;
//import edu.temple.cis.mysepta.favorite.RouteAct.RouteListAdapter;
//import edu.temple.cis.mysepta.myclass.Route;
//import edu.temple.cis.mysepta.myclass.Service;
import edu.temple.cis.mysepta.myclass.Schedule;
import edu.temple.cis.mysepta.myclass.Stop;
//import edu.temple.cis.mysepta.search.MyRouteAct.RouteListAdapter;

public class TimeList extends ListActivity{
	
	List<Schedule> timeList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myroute);

		Bundle bundle = getIntent().getExtras();
		Stop time = (Stop) bundle.getSerializable("STOP");
		SeptaDB septaDB = new SeptaDB(this);
		try {
			septaDB.open();
			timeList = septaDB.getScheduleList(time);
			septaDB.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		RouteListAdapter routeAdapter = new RouteListAdapter();
		setListAdapter(routeAdapter);

		//Button btAdd = (Button) findViewById(R.id.AddRoutes);
		//btAdd.setOnClickListener(new AddButtonOnClick());
		//Button btDel = (Button) findViewById(R.id.ReturnToServiceList);
		//btDel.setOnClickListener(new ReturnButtonOnClick());
	}
	public class RouteListAdapter extends BaseAdapter {

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return timeList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return timeList.get(position);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Schedule t = timeList.get(position);
			
			LinearLayout layout = new LinearLayout(parent.getContext());
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);

			TextView text = new TextView(parent.getContext());
			text.setTextSize(16f);
			text.setTextColor(Color.WHITE);
			text.setClickable(false);
			text.setText(t.toString());
			
			layout.setTag(t);
			layout.addView(text, params);
			convertView = layout;
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Schedule t = (Schedule) timeList.get(position);
					Intent intent = new Intent(view.getContext(), Trolley.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("SCHEDULE", t);
					intent.putExtras(bundle);
					((Activity)view.getContext()).startActivityForResult(intent, 3);
				}
			});
			return convertView;
		}
	}

	class AddButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			
		}

	}
	class ReturnButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}

}
