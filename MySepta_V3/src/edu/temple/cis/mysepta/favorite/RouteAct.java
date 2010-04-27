/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.Route;
import edu.temple.cis.mysepta.myclass.Service;

/**
 * @author Yu Liang
 * 
 */
public class RouteAct extends ListActivity {

	List<Route> routeList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routes);

		Bundle bundle = getIntent().getExtras();
		Service service = (Service) bundle.getSerializable("SERVICE");
		SeptaDB septaDB = new SeptaDB(this);
		try {
			septaDB.open();
			routeList = septaDB.getRoute(service.getId());
			septaDB.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		RouteListAdapter routeAdapter = new RouteListAdapter();
		setListAdapter(routeAdapter);

		Button btAdd = (Button) findViewById(R.id.AddRoutes);
		btAdd.setOnClickListener(new AddButtonOnClick());
		Button btDel = (Button) findViewById(R.id.ReturnToServiceList);
		btDel.setOnClickListener(new ReturnButtonOnClick());
	}
	public class RouteListAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return routeList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return routeList.get(position);
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
			Route route = routeList.get(position);
			
			Context context = parent.getContext();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.listitem02, null);
			
			TextView text = (TextView) convertView.findViewById(R.id.ListItem02Text);
			ImageView icon = (ImageView) convertView.findViewById(R.id.ListItem02Icon);
			
			icon.setImageDrawable(context.getResources().getDrawable(R.drawable.route48));
			text.setText(route.toString());
			text.setTextSize(16f);
			text.setTextColor(Color.WHITE);
			
			convertView.setTag(route);

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Route route = (Route) routeList.get(position);
					Intent intent = new Intent(view.getContext(), DateOfServiceAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("ROUTE", route);
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
