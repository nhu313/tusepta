package edu.temple.cis.mysepta.search;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.favorite.RouteAct;
//import edu.temple.cis.mysepta.favorite.ServiceAct.ServiceListAdapter;
import edu.temple.cis.mysepta.myclass.Service;

public class ServiceList extends Activity{
	
List<Service> serviceList;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.services);
		
		SeptaDB septaDB = new SeptaDB(this);
		try {
			septaDB.open();
			serviceList = septaDB.getServiceList();
			ListView serviceList = (ListView) findViewById(R.id.ServiceList);
			ServiceListAdapter adapter = new ServiceListAdapter();
			serviceList.setAdapter(adapter);
			septaDB.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
	public class ServiceListAdapter extends BaseAdapter {

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return serviceList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return serviceList.get(position);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Service service = serviceList.get(position);
			
			LinearLayout layout = new LinearLayout(parent.getContext());
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);
			
			Button button = new Button(parent.getContext());
			button.setText(service.toString());
			button.setTag(service);
			button.setTextColor(Color.parseColor(service.getColor()));
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Service service = (Service) view.getTag();
					Intent intent = new Intent(view.getContext(), MyRouteAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("SERVICE", service);
					intent.putExtras(bundle);
					((Activity) view.getContext()).startActivityForResult(intent, 2);
					//Utils.showMessage(view.getContext(), String.valueOf(serviceID));
				}
				
			});
			layout.addView(button, params);
			convertView = layout;
			return convertView;
		}
	}

}
