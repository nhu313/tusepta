/**
 * 
 */
package edu.temple.cis.tusepta.service;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import edu.temple.cis.tusepta.R;
import edu.temple.cis.tusepta.Utils;

/**
 * @author Yu Liang
 *
 */
public class ServiceAct extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.services);
		
		ListView serviceList = (ListView) findViewById(R.id.ServiceList);
		serviceList.setItemsCanFocus(true);
		serviceList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		serviceList.setOnItemClickListener(new OnItemClickListener());
		List<Service> services = ServiceHelper.getRoutesList(this);
		ServiceListAdapter adapter = new ServiceListAdapter(this, services);
		serviceList.setAdapter(adapter);
	}

	public class OnItemClickListener implements AdapterView.OnItemClickListener {

		/* (non-Javadoc)
		 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Service service = (Service) parent.getAdapter().getItem(position);
			Utils.showMessage(parent.getContext(), service.toString());
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(this, String.valueOf(resultCode), 
				Toast.LENGTH_SHORT).show();
		finish();
	}
	
}
