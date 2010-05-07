package edu.temple.cis.mysepta.search;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.DBAdapter;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Route;

/**
 * Activity to display the routes search screen. 
 */

public class SearchRouteAct extends MySeptaScreen {
	private SeptaDB db;
	private ListView lv;
	private List<Route> list = null;
	MyListAdapter<Route> aa;
	
	/**
	 * Set the screen to search.xml. Display the progress dialog 
	 * and call another function to load data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search);
		initialize();
		showDialog(PROGRESS_DIALOG);
		loadData();
	}
	
	/**
	 * Create new thread and load data from the database.
	 */
	private void loadData(){
		new Thread(){
			public void run(){
				long id = getIntent().getExtras().getLong(DBAdapter.KEY_ServiceID);		
				try {
					db = new SeptaDB(mCtx);
					db.open();
					list = db.getRoute(id);
					aa = new MyListAdapter<Route>(mCtx, list);
					db.close();
				} catch (Exception e) {
					mErrorHandler.post(dismissError);
				}
				//Notify the handler has finished loading.
				mHandler.post(dismiss);
			}
		}.start();
	}
	
	/**
	 * Change display after loading the data. The function is called 
	 * by MySeptaScreen.
	 */
	protected void changeDisplay(){
		TextView tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a route:");

		lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		lv.setOnItemClickListener(new OnItemClickListener(){
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					Route r = (Route) lv.getItemAtPosition(arg2); 
					Intent i = new Intent(arg0.getContext(), SearchDayOfServiceAct.class);
					i.putExtra(DBAdapter.KEY_RouteID, r.getRoute_id());
					i.putExtra(DBAdapter.KEY_URL, r.getUrl());					
					startActivity(i);
				}
				
		});
	}
}
