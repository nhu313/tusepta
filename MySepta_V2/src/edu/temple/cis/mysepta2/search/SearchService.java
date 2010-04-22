package edu.temple.cis.mysepta2.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import edu.temple.cis.mysepta.data2.DBAdapter;
import edu.temple.cis.mysepta.data2.SeptaDB;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Service;
import edu.temple.cis.mysepta2.R;

public class SearchService extends MySeptaScreen {
	private SeptaDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		initialize();
		
		TextView tv = (TextView)findViewById(R.id.search_row_title);
		tv.setText("Select a service:");
		
		db = new SeptaDB(this);
		db.open();
		
		Service[] list = db.getService();
		ArrayAdapter<Service> aa = new ArrayAdapter<Service>(this, R.layout.list_row_large, list);
		final ListView lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
		
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Service t = (Service) lv.getItemAtPosition(arg2);
				Intent i = new Intent(arg0.getContext(), SearchRoute.class);
				i.putExtra(DBAdapter.KEY_ServiceID, t.getId());
				startActivity(i);
			}
			
		});
	}

}
