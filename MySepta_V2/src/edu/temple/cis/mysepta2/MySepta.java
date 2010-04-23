package edu.temple.cis.mysepta2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import edu.temple.cis.mysepta.data2.SeptaDB;
import edu.temple.cis.mysepta.myclass.MySchedule;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta2.myschedule.MyScheduleAdapter;

public class MySepta extends MySeptaScreen {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myschedule);
		
		initialize();
    	
		db = new SeptaDB(this);
		db.firstOpen();
		
		//Generating fake data
    	List<MySchedule> list = new ArrayList<MySchedule>();
    	list.add(new MySchedule("Bus 1", "Center City", (float)3.4));
    	list.add(new MySchedule("Bus 2", "Home City", (float)12.4));
    	list.add(new MySchedule("Bus 3", "North City", (float)5.4));
    	
    	MyScheduleAdapter aa = new MyScheduleAdapter(this, list);
    	ListView lv = (ListView)findViewById(R.id.mysched_list);
    	lv.setAdapter(aa);
	}

}
