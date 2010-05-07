package edu.temple.cis.mysepta.search;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.DBAdapter;
import edu.temple.cis.mysepta.data.MySeptaDataHelper;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.MySeptaException;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Schedule;

public class SearchScheduleAct extends MySeptaScreen {
	private float time = 0;
	private long stopId;
	private ListView lv;
	private MyListAdapter<Schedule> aa;
	private List<Schedule> list;
	private static final String[] am_pm = {"AM", "PM"};  
	private Spinner spinner1;
	
	/**
	 * Set the screen to search.xml. Display the progress dialog 
	 * and call another function to load data.
	 */	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDialog(PROGRESS_DIALOG);
		setContentView(R.layout.search_schedule);
		initialize();
		loadData();
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
					stopId = getIntent().getExtras().getLong(DBAdapter.KEY_StopNameID);
					list = db.getSchedule(stopId, MySeptaDataHelper.getTime());
					db.close();
					aa = new MyListAdapter<Schedule>(mCtx, list);
				} catch (Exception e){}
		    	mHandler.post(dismiss);
			}
		}.start();
	}
	
	/**
	 * Change display after loading the data. The function is called 
	 * by MySeptaScreen.
	 */
	protected void changeDisplay(){		
		lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		Button bntSearch = (Button) findViewById(R.id.search_bnt_time);
		bntSearch.setOnClickListener(new SearchButtonOnClick());
	    
	    spinner1 = (Spinner)findViewById(R.id.Spinner01);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, am_pm);
	    spinner1.setAdapter(adapter);
	}

	class SearchButtonOnClick implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			EditText txttimesearch=(EditText)findViewById(R.id.search_sched_txttime);
			String s = (String)spinner1.getSelectedItem();
			time = Float.parseFloat(txttimesearch.getText().toString());			
			if(s == "PM"){
				time = time + 12;
			}
			handleSearch();
		}
	}
	
	private void handleSearch() {
		try {
			db.open();
			list = db.getSchedule(stopId, time);
			aa = new MyListAdapter<Schedule>(this, list);
			lv.setAdapter(aa);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        db.close();
		}
	}
	
	/**
	 * Implement the new addition in the submenu. 
	 */
	@Override
	public void extraFunction() {
		try {
			db.open();
			db.updateFavoriteRoute(stopId, DBAdapter.FAV_TRUE);
		} catch (MySeptaException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		Toast t = Toast.makeText(mCtx, "Added stop to favorite.", Toast.LENGTH_SHORT);
        t.show(); 
	}

	/**
	 * Add "Add Stop" to the sub menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, EXTRA, 0, "Add Stop").setIcon(R.drawable.add);
		return result;
	}
}
