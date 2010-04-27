package edu.temple.cis.mysepta;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.FavStop;
import edu.temple.cis.mysepta.myclass.MySchedule;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myschedule.MyScheduleAdapter;
import edu.temple.cis.mysepta.R;

public class MySepta extends MySeptaScreen {
	public List<MySchedule> list;
	public MyScheduleAdapter aa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myschedule);
		
		initialize();
		db = new SeptaDB(this);
		db.open();    	
		
		final Spinner spinner = (Spinner)findViewById(R.id.mysched_spinner);
		List<FavStop> myRoute = db.getFavRouteList();
		myRoute.add(0, new FavStop(0, "", "All"));
		ArrayAdapter<FavStop> routeAdapter = new ArrayAdapter<FavStop>(this, android.R.layout.simple_spinner_item, myRoute);
		routeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(routeAdapter);
		
		list = db.getMySchedule(0);		
    	aa = new MyScheduleAdapter(this, list);
    	final ListView lv = (ListView)findViewById(R.id.mysched_list);
    	lv.setAdapter(aa);
    	
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				FavStop x = (FavStop) spinner.getItemAtPosition(arg2);
				if (x.getStopID() == 0){
					lv.setAdapter(aa);	
				} else {
					MyScheduleAdapter aaByRoute = new MyScheduleAdapter(arg0.getContext(), db.getMySchedule(x.getStopID()));
					lv.setAdapter(aaByRoute);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });

//		db.close();
	}

}
