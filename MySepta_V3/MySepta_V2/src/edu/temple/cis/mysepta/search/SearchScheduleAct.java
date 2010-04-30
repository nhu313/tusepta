package edu.temple.cis.mysepta.search;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.DBAdapter;
import edu.temple.cis.mysepta.data.MySeptaDataHelper;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Schedule;

public class SearchScheduleAct extends MySeptaScreen {
	private float time = 0;
	private long stopId;
	private ListView lv;
	private MyListAdapter<Schedule> aa;
	List<Schedule> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDialog(PROGRESS_DIALOG);
		setContentView(R.layout.search_schedule);
		initialize();
		
		db = new SeptaDB(this);
		db.open();		
		stopId = getIntent().getExtras().getLong(DBAdapter.KEY_StopNameID);
		list = db.getSchedule(stopId, MySeptaDataHelper.getTime());
		
		aa = new MyListAdapter<Schedule>(this, list);
		lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		Button bntSearch = (Button) findViewById(R.id.search_bnt_time);
		bntSearch.setOnClickListener(new SearchButtonOnClick());
	    db.close();
	    dismissDialog(PROGRESS_DIALOG);
}
	class SearchButtonOnClick implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			EditText txttimesearch=(EditText)findViewById(R.id.search_sched_txttime);
			time = Float.parseFloat(txttimesearch.getText().toString());			
			handleSearch();
		}
	}
	
	private void handleSearch() {
					
		try {
			db.open();
			list = db.getSchedule(stopId, time);
			aa = new MyListAdapter<Schedule>(this, list);
			lv.setAdapter(aa);
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
