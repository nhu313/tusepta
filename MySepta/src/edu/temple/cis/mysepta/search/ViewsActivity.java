package edu.temple.cis.mysepta.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import edu.temple.cis.mysepta.R;

public class ViewsActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchmain);
        Intent intent = new Intent(this, edu.temple.cis.mysepta.search.ListView.class);
        		startActivity(intent);
	}
}