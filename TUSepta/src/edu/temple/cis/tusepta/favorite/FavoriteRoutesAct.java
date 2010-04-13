/**
 * 
 */
package edu.temple.cis.tusepta.favorite;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import edu.temple.cis.tusepta.R;
import edu.temple.cis.tusepta.service.ServiceAct;

/**
 * @author Yu Liang
 *
 */
public class FavoriteRoutesAct extends Activity {

	private final static int MENU_FAVORITE_ADD = Menu.FIRST;
	private final static int MENU_FAVORITE_DELETE = Menu.FIRST + 1;
	
	RouteListAdapter routeAdapter;
	private List<RouteListAdapter.Holder> holderList;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favoriteroutes);
		
		refreshData();
		
		Button btAdd = (Button) findViewById(R.id.AddFavoriteRoute);
		btAdd.setOnClickListener(new AddButtonOnClick());
		Button btDel = (Button) findViewById(R.id.DeleteFavoriteRoute);
		btDel.setOnClickListener(new DelButtonOnClick());
	}
	
	private void refreshData() {
		RouteHelper routeHelper = new RouteHelper(this);
		List<Route> routes = routeHelper.getFavoriteRoutesList();
		
		this.holderList = new ArrayList<RouteListAdapter.Holder>();
		for (int i = 0; i < routes.size(); i++) {
			RouteListAdapter.Holder holder = new RouteListAdapter.Holder();
			holder.route = (Route) routes.get(i);
			this.holderList.add(holder);
		}
		
		this.routeAdapter = new RouteListAdapter(this, this.holderList);
		ListView favoriteList = (ListView) findViewById(R.id.FavoriteList);
		//favoriteList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		//favoriteList.setItemsCanFocus(false);
		favoriteList.setAdapter(routeAdapter);
	}
	
	class AddButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			handleAdd();
		}
		
	}
	
	class DelButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			handleDelete();
		}
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_FAVORITE_ADD, 0, R.string.MENU_ADD)
				.setIcon(android.R.drawable.ic_menu_add);
		menu.add(0, MENU_FAVORITE_DELETE, 1, R.string.MENU_DELETE)
				.setIcon(android.R.drawable.ic_menu_delete);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case MENU_FAVORITE_ADD:
			handleAdd();
			return true;
		case MENU_FAVORITE_DELETE:
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void handleAdd() {
		Intent intent = new Intent(this, ServiceAct.class);
		startActivityForResult(intent, 1);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			refreshData();
			this.routeAdapter.notifyDataSetChanged();
		}
	}

	private void handleDelete() {
		for (int i = holderList.size() - 1; i >= 0; i--) {
			RouteListAdapter.Holder holder = (RouteListAdapter.Holder) holderList.get(i);
			if (holder.checkBox != null && holder.checkBox.isChecked()) {
				Route route = holder.route;
				//TODO: remove the route from favorite;
				holderList.remove(i);
				holder.checkBox.setChecked(false);
			}
		}
		this.routeAdapter.notifyDataSetChanged();
	}
}
