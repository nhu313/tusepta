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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;

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
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = this.holderList.get(position);
		convertView = new RouteView(this.context, position, holder);
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
			
			CheckBox check = new CheckBox(context);
			check.setText(holder.route.toString());
			check.setTextSize(16f);
			check.setTextColor(Color.WHITE);
			check.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
			holder.checkBox = check;
			
			this.setTag(holder);
			this.addView(check, params);
		}
		
		class MyOnCheckedChangeListener implements OnCheckedChangeListener {

			public MyOnCheckedChangeListener() {
			}
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			}
			
		}
	}
}
