/**
 * 
 */
package edu.temple.cis.tusepta.service;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Yu Liang
 *
 */
public class ServiceListAdapter extends BaseAdapter {

	private final Context context;
	private final List<Service> serviceList;
	
	public ServiceListAdapter(Context context, List<Service> serviceList) {
		this.context = context;
		this.serviceList = serviceList;
	}

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
		Service service = this.serviceList.get(position);
		return new ServiceView(this.context, service);
	}

	private final class ServiceView extends LinearLayout {

		private TextView textView;

		/**
		 * @param context
		 * @param attrs
		 */
		public ServiceView(Context context, Service service) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);
			
			this.textView = new TextView(context);
			this.textView.setText(service.toString());
			this.textView.setTextSize(16f);
			this.textView.setTextColor(Color.parseColor(service.getColor()));
			
			//this.setBackgroundColor(Color.parseColor(service.getColor()));
			//this.textView.setBackgroundColor(Color.parseColor(service.getColor()));
			this.addView(this.textView, params);
		}
		
	}
}
