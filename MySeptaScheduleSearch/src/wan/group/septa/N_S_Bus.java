package wan.group.septa;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

public class N_S_Bus extends ListActivity{
	
	String[] NorthSouth = {
			"North Bound",
			"South Bound"
	};
	
	@Override  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.northsouth);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, NorthSouth));
        Toast.makeText(this,"Please selected North or South Bound ",Toast.LENGTH_SHORT).show();
    }    
 
    public void onListItemClick(ListView parent, View v, int position, long id) 
    {   
        Toast.makeText(this,"You have selected " + NorthSouth[position],Toast.LENGTH_SHORT).show();
        
        switch (position) {
		case 0: {
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
		case 1: {
			Intent intent = new Intent(this, wan.group.septa.RRList.class);
			startActivity(intent);
			break;
		}
    }
  }

}
