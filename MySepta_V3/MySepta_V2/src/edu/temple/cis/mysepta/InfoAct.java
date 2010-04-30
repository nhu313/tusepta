package edu.temple.cis.mysepta;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;

public class InfoAct extends MySeptaScreen {
	TextView data = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		initialize();
		
		final Spinner spinner = (Spinner)findViewById(R.id.info_spinner);
		List<String> spin_list = new ArrayList<String>();
		spin_list.add("Septa Contact");
		spin_list.add("Fares");
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spin_list);
		spinner.setAdapter(aa);
		
		data = (TextView) findViewById(R.id.info_txt);
    	
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0){
					String contact = "Phone: (215) 580-780" 
						+ "\nAddress: 1234 Market Street, Philadelphia PA 19107";
					data.setText(contact);	
				} else {
					String fare = "Cash: $2.00"
						+ "\nToken: 1 for $1.45"
						+ "\n\t\t2 for $2.90" +
						"\n\t\t5 for $7.25" +
						"\n\t\t10 for $14.50" +
						"\nPass: $0.75";
					
					data.setText(fare);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
	}

}