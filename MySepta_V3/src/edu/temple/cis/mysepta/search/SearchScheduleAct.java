package edu.temple.cis.mysepta.search;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
	private ArrayAdapter<Schedule> aa;
	List<Schedule> list;
	private static final String[] am_pm = {"AM", "PM"};  
	Spinner spinner1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_schedule);
		initialize();
		
		db = new SeptaDB(this);
		db.open();
		
		stopId = getIntent().getExtras().getLong(DBAdapter.KEY_StopNameID);
		list = db.getSchedule(stopId, MySeptaDataHelper.getTime());
		spinner1 = (Spinner)findViewById(R.id.Spinner01);  
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, am_pm);  
		spinner1.setAdapter(adapter);
		aa = new ArrayAdapter<Schedule>(this, R.layout.list_row_large, list);
		lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		Button bntSearch = (Button) findViewById(R.id.search_bnt_time);
		bntSearch.setOnClickListener(new SearchButtonOnClick());
	    db.close();
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
			aa = new ArrayAdapter<Schedule>(this, R.layout.list_row_large, list);
			lv.setAdapter(aa);
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
