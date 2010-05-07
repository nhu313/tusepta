package edu.temple.cis.mysepta;

import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;
import edu.temple.cis.mysepta.data.MySeptaDataHelper;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.FavStop;
import edu.temple.cis.mysepta.myclass.MySchedule;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;
import edu.temple.cis.mysepta.myschedule.MyScheduleAdapter;

/**
 * This is the "Home" screen. It displays the schedule of the user's favorite stops. 
 * It has a spinner to let user select a specific stop and a list to display the schedule.
 */
public class MySepta extends MySeptaScreen {
	private List<FavStop> myRoute; //List for spinner
	private ArrayAdapter<FavStop> routeAdapter; //Array adapter for the spinner
	
	private List<MySchedule> list; //Schedule list to display
	private MyScheduleAdapter aa; //Array adapter for all schedule
	private MyScheduleAdapter aaByStop; //Array adapter based on a stop
	private Spinner spinner;
	private ListView lv;
	
	private float time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myschedule);
		
		initialize();
		db = new SeptaDB(mCtx);
		showDialog(PROGRESS_DIALOG);
		time = MySeptaDataHelper.getTime();
		loadData();
	}
	
	/**
	 * Create a new thread to get data from the database.
	 */
	private void loadData(){
		new Thread(){
			public void run(){
				db.open();
				myRoute = db.getFavRouteList();
				myRoute.add(0, new FavStop(0, "", "All"));
				routeAdapter = new ArrayAdapter<FavStop>(mCtx, android.R.layout.simple_spinner_item, myRoute);
				routeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				list = db.getMySchedule(0, time);
				db.close();
				aa = new MyScheduleAdapter(mCtx, list);
		    	mHandler.post(dismiss);
			}
		}.start();
	}

	/**
	 * Change the view after loading the data.
	 */
	protected void changeDisplay(){
		spinner = (Spinner)findViewById(R.id.mysched_spinner);
		spinner.setAdapter(routeAdapter);
		spinner.setOnItemSelectedListener(new RouteOnItemSelectedListener());
		lv = (ListView)findViewById(R.id.mysched_list);
    	lv.setAdapter(aa);
	}
	
	/**
	 * If the item selected is "All", then set the adapter to aa. Else get the new data from the database.
	 * 
	 */
	class RouteOnItemSelectedListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			FavStop x = (FavStop) spinner.getItemAtPosition(arg2);
			if (x.getStopID() == 0){
				lv.setAdapter(aa);	
			} else {
				db.open();
				aaByStop = new MyScheduleAdapter(arg0.getContext(), db.getMySchedule(x.getStopID(), time));
				db.close();
				lv.setAdapter(aaByStop);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	/**
	 * Add a time dialog to the super class onCreateDialog. 
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading data. Please wait...");
            return progressDialog;
        case TIME_DIALOG:
        	return new TimePickerDialog(this,
	                mTimeSetListener, 0, 0, false);        
        default:
            return null;
        }
	}

	/**
	 * Set time to the user's pick. Reload data.
	 */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            time = hourOfDay + minute/100;
	            loadData();
	        }
	};

	/**
	 * Implement the extra function to show the time dialog.
	 */
	@Override
	public void extraFunction() {
		showDialog(TIME_DIALOG);
	}

	/**
	 * Add "Change Time" to the submenu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, EXTRA, 0, "Change Time").setIcon(R.drawable.clock);
		return result;
	}

	//Set the int to differentiate the TIME_DIALOG 
	protected static final int TIME_DIALOG = 2;
}