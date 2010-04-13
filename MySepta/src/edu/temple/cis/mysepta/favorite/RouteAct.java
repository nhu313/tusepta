/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import edu.temple.cis.mysepta.R;

/**
 * @author Yu Liang
 *
 */
public class RouteAct extends ListActivity {
	
	List<RouteListAdapter.Holder> holderList;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routes);
		
		Bundle bundle = getIntent().getExtras();
		int serviceID = bundle.getInt("SERVICEID");
		RouteHelper routeHelper = new RouteHelper(this);
		List<Route> routes = routeHelper.getRoutesList();
		
		this.holderList = new ArrayList<RouteListAdapter.Holder>();
		for (int i = 0; i < routes.size(); i++) {
			RouteListAdapter.Holder holder = new RouteListAdapter.Holder();
			holder.route = (Route) routes.get(i);
			holderList.add(holder);
		}
		
		RouteListAdapter routeAdapter = new RouteListAdapter(this, holderList);
		setListAdapter(routeAdapter);
		
		Button btAdd = (Button) findViewById(R.id.AddRoutes);
		btAdd.setOnClickListener(new AddButtonOnClick());
		Button btDel = (Button) findViewById(R.id.ReturnToServiceList);
		btDel.setOnClickListener(new ReturnButtonOnClick());
	}
	
	class AddButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			boolean isAdded = false;
			for (int i = holderList.size() - 1; i >= 0; i--) {
				RouteListAdapter.Holder holder = (RouteListAdapter.Holder) holderList.get(i);
				if (holder.checkBox != null && holder.checkBox.isChecked()) {
					Route route = holder.route;
					//TODO: add the route to favorite;
					isAdded = true;
				}
			}
			if (isAdded) {
				Toast.makeText(v.getContext(), "Routes have been added to your favorite", 
						Toast.LENGTH_LONG).show();
				setResult(RESULT_OK);
				finish();
			} else {
				Toast.makeText(v.getContext(), "Please select routes", 
						Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	class ReturnButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			setResult(RESULT_CANCELED);
			finish();
		}
		
	}
}
