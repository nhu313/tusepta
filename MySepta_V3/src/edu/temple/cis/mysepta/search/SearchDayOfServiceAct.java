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
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;

public class SearchDayOfServiceAct extends MySeptaScreen {
	private SeptaDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		showDialog(PROGRESS_DIALOG);
		initialize();

		TextView tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a day:");
		
		long id = getIntent().getExtras().getLong(DBAdapter.KEY_RouteID);
		String link = getIntent().getExtras().getString(DBAdapter.KEY_URL);
		
		List<DayOfService> list = null;
		try {
			db = new SeptaDB(this);
			db.open();
			list = db.getDayOfService(id, link);
		
			ArrayAdapter<DayOfService> aa = new ArrayAdapter<DayOfService>(this, R.layout.list_row_med, list);
			final ListView lv = (ListView)findViewById(R.id.search_list);
			lv.setAdapter(aa);
			dismissDialog(PROGRESS_DIALOG);
			lv.setOnItemClickListener(new OnItemClickListener(){
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					DayOfService t = (DayOfService) lv.getItemAtPosition(arg2); 
					Intent i = new Intent(arg0.getContext(), SearchStopAct.class);
					i.putExtra(DBAdapter.KEY_DayOfServiceID, t.getDayID());
					startActivity(i);
				}
				
			});
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
