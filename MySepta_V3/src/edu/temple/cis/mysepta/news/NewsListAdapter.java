/**
 * 
 */
package edu.temple.cis.mysepta.news;

import java.util.List;

import edu.temple.cis.mysepta.R;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Yu Liang
 *
 */
public class NewsListAdapter extends BaseAdapter {

	private final Context context;
	private final List<News> newsList;
	private LayoutInflater inflater;
	
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

		/**
		 * @param context
		 * @param attrs
		 */
		public NewsView(Context context, News news) {
			super(context);
			inflater = LayoutInflater.from(context);
			View convertView = inflater.inflate(R.layout.listitem02, null);
			
			TextView text = (TextView) convertView.findViewById(R.id.ListItem02Text);
			ImageView icon = (ImageView) convertView.findViewById(R.id.ListItem02Icon);
			
			icon.setImageDrawable(context.getResources().getDrawable(R.drawable.news48));
			text.setText(news.toString());
			text.setTextSize(16f);
			text.setTextColor(Color.BLACK);
			
			this.addView(convertView);
		}
		
	}

}
