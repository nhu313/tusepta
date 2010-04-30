package edu.temple.cis.mysepta.myschedule;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import edu.temple.cis.mysepta.myclass.MySchedule;

public class MyScheduleAdapter extends BaseAdapter{
	private Context context;
	private List<MySchedule> list;
	
	public MyScheduleAdapter(Context context, List<MySchedule> list) {
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
		MySchedule entry = list.get(position);
		return new MyScheduleAdapterView(context, entry);
	}	
}
