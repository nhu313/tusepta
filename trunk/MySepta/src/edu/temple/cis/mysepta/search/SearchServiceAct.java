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
import edu.temple.cis.mysepta.myclass.Service;

/**
 * Activity to display the service search screen. 
 */
public class SearchServiceAct extends MySeptaScreen {
	private TextView tv;
	private MyListAdapter<Service> aa;
	private ListView lv;
	private List<Service> list;
	
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
	 * Change display after loading the data. The function is called 
	 * by MySeptaScreen.
	 */
	protected void changeDisplay(){
		tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a service:");
		
		lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Service t = (Service) lv.getItemAtPosition(arg2);
				long id = t.getId();
				Intent i = new Intent(arg0.getContext(), SearchRouteAct.class);
				i.putExtra(DBAdapter.KEY_ServiceID, id);				
				startActivity(i);
			}
			
		});	
	}
	
	/**
	 * Create new thread and load data from the database.
	 */
	private void loadData(){
		new Thread(){
			public void run(){
				try{
					db = new SeptaDB(mCtx);
					db.open();							
					list = db.getService();
					db.close();				
			    	aa = new MyListAdapter<Service>(mCtx, list);	
				} catch (Exception e){
					mErrorHandler.post(dismissError);
				}				
		    	mHandler.post(dismiss);
			}
		}.start();
	}
}
