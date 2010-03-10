/**
 * 
 */
package edu.temple.cis.tusepta.favorite;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * @author Yu Liang
 *
 */
public class RouteListAdapter extends BaseAdapter {

	private final Context context;
	private final List<Route> routes;
	
	public RouteListAdapter(Context context, List<Route> routes) {
		this.context = context;
		this.routes = routes;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.routes.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.routes.get(position);
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
		Route route = this.routes.get(position);
		return new RouteView(this.context, route);
	}
	
	private final class RouteView extends LinearLayout {

		private CheckBox check;

		/**
		 * @param context
		 * @param attrs
		 */
		public RouteView(Context context, Route route) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);
			
			this.check = new CheckBox(context);
			this.check.setText(route.toString());
			this.check.setTextSize(16f);
			this.check.setTextColor(Color.WHITE);
			this.addView(this.check, params);

		}
		
	}

}
