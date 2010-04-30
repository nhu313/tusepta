/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Route;

/**
 * @author Yu Liang
 *
 */
public class DateOfServiceAct extends MySeptaScreen {

	List<DayOfService> dosList;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dateofservice);
		initialize();
		Bundle bundle = getIntent().getExtras();
		Route route = (Route) bundle.getSerializable("ROUTE");
		SeptaDB septaDB = new SeptaDB(this);
		try {
			septaDB.open();
			dosList = septaDB.getDayOfService(route.getRoute_id(), route.getUrl());
			septaDB.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		ListView listView = (ListView) findViewById(R.id.DateOfServiceList);
		DosListAdapter adapter = new DosListAdapter();
		listView.setAdapter(adapter);
	}

	public class DosListAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		
		@Override
		public int getCount() {
			return dosList.size();
		}

		@Override
		public Object getItem(int location) {
			// TODO Auto-generated method stub
			return dosList.get(location);
		}

		@Override
		public long getItemId(int location) {
			return ((DayOfService) dosList.get(location)).getDayID();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			DayOfService dos = (DayOfService) getItem(position);

			Context context = parent.getContext();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.listitem02, null);
			
			TextView text = (TextView) convertView.findViewById(R.id.ListItem02Text);
			ImageView icon = (ImageView) convertView.findViewById(R.id.ListItem02Icon);
			
			icon.setImageDrawable(context.getResources().getDrawable(R.drawable.date48));
			text.setText(dos.getDay());
			text.setTextSize(16f);
			text.setTextColor(Color.BLACK);
			
			convertView.setTag(dos);
			
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					DayOfService dos = (DayOfService) dosList.get(position);
					//Toast.makeText(view.getContext(), route.getName(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(view.getContext(), StopAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("DOS", dos);
					intent.putExtras(bundle);
					((Activity)view.getContext()).startActivityForResult(intent, 4);
				}
			});

			return convertView;
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
