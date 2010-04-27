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
import edu.temple.cis.mysepta.myclass.MySeptaException;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Route;

public class SearchRouteAct extends MySeptaScreen {
	private SeptaDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDialog(PROGRESS_DIALOG);
		setContentView(R.layout.search);
		initialize();
		
		TextView tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a route:");

		long id = getIntent().getExtras().getLong(DBAdapter.KEY_ServiceID);
		List<Route> list = null;
		try {
			db = new SeptaDB(this);
			db.open();
			list = db.getRoute(id);
		
			ArrayAdapter<Route> aa = new ArrayAdapter<Route>(this, R.layout.list_row_med, list);
			final ListView lv = (ListView)findViewById(R.id.search_list);
			lv.setAdapter(aa);
			dismissDialog(PROGRESS_DIALOG);
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
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MySeptaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
