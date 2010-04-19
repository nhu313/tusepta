/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.temple.cis.mysepta.myclass.Route;

/**
 * @author Yu Liang
 *
 */
public class RouteListAdapter extends BaseAdapter {

	private final Context context;
	private List<Holder> holderList;
	
	public static class Holder {
		CheckBox checkBox;
		Route route;
	}
	
	public RouteListAdapter(Context context, List<Holder> holderList) {
		this.context = context;
		this.holderList = holderList;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.holderList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.holderList.get(position);
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = this.holderList.get(position);
		convertView = new RouteView(this.context, position, holder);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				RouteListAdapter.Holder holder = 
					(RouteListAdapter.Holder) holderList.get(position);
				Route route = holder.route;
				//Toast.makeText(view.getContext(), route.getName(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(view.getContext(), DateOfServiceAct.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("ROUTE", route);
				intent.putExtras(bundle);
				((Activity)view.getContext()).startActivityForResult(intent, 2);
			}
		});
		return convertView;
	}

	private final class RouteView extends LinearLayout {

		/**
		 * @param context
		 * @param attrs
		 */
		public RouteView(Context context, int position, Holder holder) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);

			TextView text = new TextView(context);
			text.setTextSize(16f);
			text.setTextColor(Color.WHITE);
			text.setClickable(false);
			text.setText(holder.route.toString());
			
			this.setTag(holder);
			this.addView(text, params);
		}
	}
}
