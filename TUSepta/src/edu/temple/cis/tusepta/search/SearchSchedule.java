package edu.temple.cis.tusepta.search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class SearchSchedule extends Activity{
	
	private String day_of_service[];
	private EditText txtscheduleTime;
	private TextView txtresult;
	private Button btnsearch1;
	private Button btnsearch2;
	private Button btnreset;
	private float searchTime = 0;
	private float resultTime = 0;

	/** Called when the activity is first created. */

	@Override

	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.searchschedule);
	day_of_service = new String[4];  
	         day_of_service[0]="Weekday";  
	         day_of_service[1]="Saturday";  
	         day_of_service[2]="Sunday";  
	         day_of_service[3]="Night";  

	Spinner s = (Spinner) findViewById(R.id.Spinner01);  
	ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, day_of_service);  

	         s.setAdapter(adapter);  
	initControls();

	}
	private void initControls(){

	txtscheduleTime = (EditText)findViewById(R.id.txttime);
	txtresult=(TextView)findViewById(R.id.txtresult);
	btnsearch1 = (Button)findViewById(R.id.btnsearch1);
	btnsearch2 = (Button)findViewById(R.id.btnsearch2);
	btnreset = (Button)findViewById(R.id.btnreset);
	btnsearch1.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ search1(); }});
	btnsearch2.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ search2(); }});
	btnreset.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ reset(); }});
	}

	private void search1(){
      
		System.out.println();
	}
	private void search2(){
	      
		System.out.println();
	}
	private void reset(){

	txtscheduleTime.setText("");
	
	}

}
