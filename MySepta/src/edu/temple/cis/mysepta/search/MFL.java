package edu.temple.cis.mysepta.search;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.Schedule;

public class MFL extends ListActivity{
	
public Schedule[] s = null;
public long stopid=189;
private double time;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.searchtime);
        SeptaDB db = new SeptaDB(this);
                       
        try {
			db.open();
			   Schedule[] schedule1 = db.getScheduleCurrentTime(stopid);
	           setListAdapter(new ArrayAdapter<Schedule>(this, android.R.layout.simple_list_item_1, schedule1));
	        db.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			Button bntSearch = (Button) findViewById(R.id.bntsearchtime);
			bntSearch.setOnClickListener(new SearchButtonOnClick());
    	}
		class SearchButtonOnClick implements OnClickListener {
			
			@Override
			public void onClick(View v) {
				EditText txttimesearch=(EditText)findViewById(R.id.txttime);
				time = Double.parseDouble(txttimesearch.getText().toString());
				handleSearch();
			}
		}
		private void handleSearch() {
						
			try {
				SeptaDB db = new SeptaDB(this);
				db.open();
				Schedule[] schedule1 = db.getScheduleSpecificTime(stopid,time);
		        setListAdapter(new ArrayAdapter<Schedule>(this, android.R.layout.simple_list_item_1, schedule1));
		        db.close();
		        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
