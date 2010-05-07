package edu.temple.cis.mysepta;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;

/**
 * This activity displays SEPTA's contact info and fares. 
 *
 */

public class InfoAct extends MySeptaScreen {
	LinearLayout contact_lay = null;
	LinearLayout fare_lay = null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		initialize();
		
		//Add data to spinner
		final Spinner spinner = (Spinner)findViewById(R.id.info_spinner);
		List<String> spin_list = new ArrayList<String>();
		spin_list.add("SEPTA's Contact");
		spin_list.add("Bus/Trolley/Subway Fares");
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spin_list);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(aa);

		contact_lay = (LinearLayout)findViewById(R.id.info_contact_lay);
    	fare_lay = (LinearLayout)findViewById(R.id.info_fare_lay);
		
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0){
					contact_lay.setVisibility(View.VISIBLE);
					fare_lay.setVisibility(View.INVISIBLE);
				} else {
					contact_lay.setVisibility(View.INVISIBLE);
					fare_lay.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
	}

}