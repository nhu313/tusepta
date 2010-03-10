/**
 * 
 */
package edu.temple.cis.tusepta.favorite;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import edu.temple.cis.tusepta.R;

/**
 * @author Yu Liang
 *
 */
public class RouteAct extends ListActivity {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routes);
		
		List<Route> routes = FavoriteRouteHelper.getFavoriteRoutes(this);
		RouteListAdapter routeAdapter = new RouteListAdapter(this, routes);
		setListAdapter(routeAdapter);
	}
}
