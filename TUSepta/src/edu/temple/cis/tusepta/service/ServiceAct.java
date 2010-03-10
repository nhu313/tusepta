/**
 * 
 */
package edu.temple.cis.tusepta.service;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.temple.cis.tusepta.R;

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
			AlertDialog alert = new AlertDialog.Builder(parent.getContext()).create();
			Service service = (Service) parent.getAdapter().getItem(position);
			alert.setMessage(service.toString());
			alert.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			alert.show();
		}
	}
}
