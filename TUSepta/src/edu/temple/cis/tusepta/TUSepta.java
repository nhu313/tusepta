package edu.temple.cis.tusepta;

import edu.temple.cis.tusepta.test.TestAct;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @author Yu Liang
 * Wanwisa
 *
 */
public class TUSepta extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent itTest = new Intent(this, TestAct.class);
        startActivity(itTest);
    }
}