package wan.group.septa;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

public class TestDB extends ListActivity{
	
	
	String[] Service2 = {
			"1",
			"2"
	};
	
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.northsouth);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Service2));
    }    
 
    public void onListItemClick(ListView parent, View v, int position, long id) 
    {   
        Toast.makeText(this,"You have selected " + Service2[position],Toast.LENGTH_SHORT).show();
        
        switch (position) {
		case 0: {//Route 10
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 1: {//Route 11
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
    }
  }

}
