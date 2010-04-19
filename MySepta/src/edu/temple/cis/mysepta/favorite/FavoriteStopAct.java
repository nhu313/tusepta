/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.Stop;

/**
 * @author Yu Liang
 *
 */
public class FavoriteStopAct extends Activity {

	public static class Holder {
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
		
		Button btAdd = (Button) findViewById(R.id.AddFavorite);
		btAdd.setOnClickListener(new AddButtonOnClick());
		Button btDel = (Button) findViewById(R.id.DeleteFavorite);
		btDel.setOnClickListener(new DelButtonOnClick());
	}
	
	private void refreshData() {
		try {
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
			
			LinearLayout layout = new LinearLayout(parent.getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);
			
			CheckBox check = new CheckBox(parent.getContext());
			check.setText(holder.stop.toString());
			check.setTextSize(16f);
			check.setTextColor(Color.WHITE);
			holder.checkBox = check;
			
			layout.setTag(holder);
			layout.addView(check, params);
			convertView = layout;
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
		for (int i = holderList.size() - 1; i >= 0; i--) {
			Holder holder = (Holder) holderList.get(i);
			if (holder.checkBox != null && holder.checkBox.isChecked()) {
				Stop stop = holder.stop;
				//TODO: remove the route from favorite;
				holderList.remove(i);
				holder.checkBox.setChecked(false);
			}
		}
		this.stopAdapter.notifyDataSetChanged();
	}
}
