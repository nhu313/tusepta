package edu.temple.cis.mysepta;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class FavSchedule extends Activity{
	public ArrayList<String> route = null;
	public ArrayAdapter<String> aa_list = null;
	public ListView list = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        //main.setBackgroundColor(Color.WHITE);

        //header
        LinearLayout header = new LinearLayout(this);
        //header.setBackgroundColor(Color.WHITE);
        header.setMinimumWidth(LayoutParams.FILL_PARENT);
        header.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(this);
        ImageView img_title = new ImageView(this);
        title.setText("My Schedule");
        title.setTextColor(Color.RED);
        title.setTextSize(20);
        title.setMinimumWidth(LayoutParams.FILL_PARENT);
        img_title.setImageResource(R.drawable.septaicon);
        header.addView(title);
        //header.addView(img_title);
        
        //Spinner for route
        ArrayList<String> route = getFavRouteList();
        Spinner spin = new Spinner(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>
        (this, android.R.layout.simple_spinner_item, route);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				aa_list = new ArrayAdapter<String>
				(FavSchedule.this, android.R.layout.simple_list_item_1, getRouteTime(arg2));
				list.setAdapter(aa_list);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        	
        });
        
        //List of Time
        ArrayList<String> time = getAllFavRoute();
        list = new ListView(this);
        aa_list = new ArrayAdapter<String>
        (this, android.R.layout.simple_list_item_1, time);
        list.setAdapter(aa_list);

        main.addView(header);
        main.addView(spin);
        main.addView(list);
        setContentView(main);
    }
    
    public ArrayList<String> getFavRouteList(){
    	ArrayList<String> al = new ArrayList<String>();
    	al.add("Route 1");
    	al.add("Route 2");
    	return al;
    }
    
    public ArrayList<String> getAllFavRoute(){
    	ArrayList<String> al = new ArrayList<String>();
    	al.add("Bus 1 to Center City 4:50");
    	al.add("Bus 1 to Center City 5:50");
    	al.add("Bus 2 to Center City 4:50");
		al.add("Bus 2 to Center City 5:50");
    	return al;
    }
    
    public ArrayList<String> getRouteTime(int route){
    	ArrayList<String> al = new ArrayList<String>();
    	if (route == 0){
    		al.add("Bus 1 to Center City 4:50");
    		al.add("Bus 1 to Center City 5:50");
    	} else {
    		al.add("Bus 2 to Center City 4:50");
    		al.add("Bus 2 to Center City 5:50");
    	}
    	return al;
    }
}
