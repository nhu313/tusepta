package edu.temple.cis.mysepta.search;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Custom adapter to display service, route, day of service, 
 * and schedule for search.
 * @param <T> Type of class of the adapter.
 */

public class MyListAdapter<T> extends BaseAdapter {
	private Context context;
	private List<T> list;
	
	public MyListAdapter(Context context, List<T> list) {
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		T entry = list.get(position);
		return new MyListAdapterView(context, entry);
	}
}
