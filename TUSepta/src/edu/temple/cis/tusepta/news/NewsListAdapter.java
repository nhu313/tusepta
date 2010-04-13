/**
 * 
 */
package edu.temple.cis.mysepta.news;

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
public class NewsListAdapter extends BaseAdapter {

	private final Context context;
	private final List<News> newsList;
	
	public NewsListAdapter(Context context, List<News> newsList) {
		this.context = context;
		this.newsList = newsList;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.newsList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.newsList.get(position);
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
		News news = this.newsList.get(position);
		return new NewsView(this.context, news);
	}
	
	private final class NewsView extends LinearLayout {

		private TextView textView;

		/**
		 * @param context
		 * @param attrs
		 */
		public NewsView(Context context, News news) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);
			
			this.textView = new TextView(context);
			this.textView.setText(news.toString());
			this.textView.setTextSize(16f);
			this.textView.setTextColor(Color.WHITE);
			this.addView(this.textView, params);

		}
		
	}

}
