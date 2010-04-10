package wan.group.septa;

//import java.util.List;

//import wan.group.septa.data.SeptaDB2;
//import wan.group.septa.data.Service;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemSelectedListener;

public class ViewsActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = new Intent(this, wan.group.septa.ListViewTest.class);
        		startActivity(intent);
	}

}