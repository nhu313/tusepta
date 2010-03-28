/**
 * 
 */
package edu.temple.cis.tusepta.favorite;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import edu.temple.cis.tusepta.R;
import edu.temple.cis.tusepta.service.ServiceAct;

/**
 * @author Yu Liang
 *
 */
public class FavoriteRoutesAct extends Activity {

	private final static int MENU_FAVORITE_ADD = Menu.FIRST;
	private final static int MENU_FAVORITE_DELETE = Menu.FIRST + 1;
	private final static int MENU_FAVORITE_SAVE = Menu.FIRST + 2;
	private final static int MENU_FAVORITE_CANCEL = Menu.FIRST + 3;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favoriteroutes);
		
		List<Route> routes = FavoriteRouteHelper.getFavoriteRoutes(this);
		RouteListAdapter routeAdapter = new RouteListAdapter(this, routes);
		ListView favoriteList = (ListView) findViewById(R.id.FavoriteList);
		favoriteList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		favoriteList.setAdapter(routeAdapter);
		
		ImageButton btAdd = (ImageButton) findViewById(R.id.FR_AddFavoriteRoute);
		btAdd.setOnClickListener(new AddButtonOnClick());
		ImageButton btDel = (ImageButton) findViewById(R.id.FR_DelFavoriteRoute);
		btDel.setOnClickListener(new DelButtonOnClick());
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
//		menu.add(0, MENU_FAVORITE_SAVE, 2, R.string.MENU_SAVE)
//				.setIcon(android.R.drawable.ic_menu_save);
//		menu.add(0, MENU_FAVORITE_CANCEL, 3, R.string.MENU_CANCEL)
//				.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
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
		startActivity(intent);
	}
	
	private void handleDelete() {
		ListView favoriteList = (ListView) findViewById(R.id.FavoriteList);
		favoriteList.getSelectedItem();
		//if (!)
	}
}
