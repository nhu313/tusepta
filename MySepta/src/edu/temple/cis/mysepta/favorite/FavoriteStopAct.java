/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.Stop;

/**
 * @author Yu Liang
 *
 */
public class FavoriteStopAct extends Activity {

	public static class Holder {
		TextView text;
		ImageView icon;
		CheckBox checkBox;
		Stop stop;
	}
	
	FavoriteStopListAdapter stopAdapter;
	private List<Holder> holderList;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favoriteroutes);
		
		refreshData();
		
		ListView listView = (ListView) findViewById(R.id.FavoriteList);
		this.stopAdapter = new FavoriteStopListAdapter();
		listView.setAdapter(this.stopAdapter);
		
		Button btAdd = (Button) findViewById(R.id.AddFavorite);
		btAdd.setOnClickListener(new AddButtonOnClick());
		Button btDel = (Button) findViewById(R.id.DeleteFavorite);
		btDel.setOnClickListener(new DelButtonOnClick());
	}
	
	private void refreshData() {
		try {
			holderList = new ArrayList<Holder>();
			SeptaDB septaDB = new SeptaDB(this);
			septaDB.open();
			List<Stop> stopList = septaDB.getFavoriteStopList();
			for (int i = 0; i < stopList.size(); i++) {
				Holder holder = new Holder();
				holder.stop = (Stop) stopList.get(i);
				this.holderList.add(holder);
			}
			septaDB.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public class FavoriteStopListAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return holderList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return holderList.get(position);
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
			Holder holder = holderList.get(position);
			Context context = parent.getContext();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.listitem01, null);
			
			holder.text = (TextView) convertView.findViewById(R.id.ListItem01Text);
			holder.icon = (ImageView) convertView.findViewById(R.id.ListItem01Icon);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.ListItem01Check);

			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.stop48));
			holder.text.setText(holder.stop.toString());
			holder.text.setTextSize(16f);
			holder.text.setTextColor(Color.WHITE);
			return convertView;
		}
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
			this.stopAdapter.notifyDataSetChanged();
		}
	}

	private void handleDelete() {
		try {
			SeptaDB septaDB = new SeptaDB(this);
			septaDB.open();
			for (int i = holderList.size() - 1; i >= 0; i--) {
				Holder holder = (Holder) holderList.get(i);
				if (holder.checkBox != null && holder.checkBox.isChecked()) {
					Stop stop = holder.stop;
					septaDB.updateFavoriteRoute(stop.getStopID(), 0);
					holderList.remove(i);
					holder.checkBox.setChecked(false);
				}
			}
			this.stopAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
