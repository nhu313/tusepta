package edu.temple.cis.mysepta.search;

import java.util.List;

import org.htmlparser.util.ParserException;

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
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;

/**
 * Activity for the day of service search screen. 
 */

public class SearchDayOfServiceAct extends MySeptaScreen {
	private List<DayOfService> list = null;
	private MyListAdapter<DayOfService> aa;
	private ListView lv;
	
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
	 * Create a new thread to load the data to the list.
	 */
	private void loadData(){
		new Thread(){
			public void run(){
				try {
					long id = getIntent().getExtras().getLong(DBAdapter.KEY_RouteID);
					String link = getIntent().getExtras().getString(DBAdapter.KEY_URL);
					db = new SeptaDB(mCtx);
					db.open();				
					list = db.getDayOfService(id, link);
					aa = new MyListAdapter<DayOfService>(mCtx, list);
					db.close();
				} catch (ParserException e) {
					mErrorHandler.post(dismissError);
					e.printStackTrace();
				}
				mHandler.post(dismiss);
			}
		}.start();		
	}
	
	/**
	 * Change display after loading the data.
	 */
	protected void changeDisplay(){
		TextView tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a day:");
		
		lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					
				DayOfService t = (DayOfService) lv.getItemAtPosition(arg2); 
				Intent i = new Intent(arg0.getContext(), SearchStopAct.class);
				i.putExtra(DBAdapter.KEY_DayOfServiceID, t.getDayID());
				startActivity(i);
			}
				
		});
	}

}
