package edu.temple.cis.mysepta.search;

import java.util.List;

import org.htmlparser.util.ParserException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.DBAdapter;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Stop;

public class SearchStopAct extends MySeptaScreen {
	private SeptaDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search);
		initialize();

		TextView tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a stop:");
		
		long id = getIntent().getExtras().getLong(DBAdapter.KEY_DayOfServiceID);
		
		List<Stop> list = null;
		try {
			db = new SeptaDB(this);
			db.open();
			list = db.getStop(id);
		
			ArrayAdapter<Stop> aa = new ArrayAdapter<Stop>(this, R.layout.list_row_large, list);
			final ListView lv = (ListView)findViewById(R.id.search_list);
			lv.setAdapter(aa);
			lv.setOnItemClickListener(new OnItemClickListener(){
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					Stop t = (Stop) lv.getItemAtPosition(arg2); 
					Intent i = new Intent(arg0.getContext(), SearchScheduleAct.class);
					i.putExtra(DBAdapter.KEY_StopNameID, t.getStopID());
					startActivity(i);
				}
				
			});

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
