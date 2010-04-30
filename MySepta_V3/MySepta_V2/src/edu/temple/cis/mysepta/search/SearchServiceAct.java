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

public class SearchServiceAct extends MySeptaScreen {
	private SeptaDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDialog(PROGRESS_DIALOG);
		setContentView(R.layout.search);
		initialize();
		
		TextView tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a service:");
		
		db = new SeptaDB(this);
		db.open();		
		List<Service> list = db.getService();
		db.close();
		
    	MyListAdapter<Service> aa = new MyListAdapter<Service>(this, list);
		final ListView lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		
		dismissDialog(PROGRESS_DIALOG);
		
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

}
