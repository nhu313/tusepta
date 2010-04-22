package edu.temple.cis.mysepta2.search;

import org.htmlparser.util.ParserException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import edu.temple.cis.mysepta.data2.DBAdapter;
import edu.temple.cis.mysepta.data2.SeptaDB;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myclass.Schedule;
import edu.temple.cis.mysepta2.R;

public class SearchSchedule extends MySeptaScreen {
	private SeptaDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search);
		initialize();
		
		long id = getIntent().getExtras().getLong(DBAdapter.KEY_StopNameID);
		
		Schedule[] list = null;
		db = new SeptaDB(this);
		db.open();
		list = db.getAllSchedule(id);
		
		ArrayAdapter<Schedule> aa = new ArrayAdapter<Schedule>(this, R.layout.list_row_large, list);
		final ListView lv = (ListView)findViewById(R.id.search_list);
		lv.setAdapter(aa);
	}
}
