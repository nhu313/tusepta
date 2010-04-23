/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
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
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.Stop;

/**
 * @author Yu Liang
 * 
 */
public class StopAct extends ListActivity {

	public static class Holder {
		CheckBox checkBox;
		Stop stop;
	}

	List<Holder> holderList;
	StopListAdapter stopAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stops);

		Bundle bundle = getIntent().getExtras();
		DayOfService dos = (DayOfService) bundle.getSerializable("DOS");
		SeptaDB septaDB = new SeptaDB(this);
		try {
			septaDB.open();
			this.holderList = new ArrayList<Holder>();
			List<Stop> stopList = septaDB.getStopList(dos);
			for (int i = 0; i < stopList.size(); i++) {
				Holder holder = new Holder();
				holder.stop = (Stop) stopList.get(i);
				this.holderList.add(holder);
			}
			septaDB.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		this.stopAdapter = new StopListAdapter();
		setListAdapter(stopAdapter);

		Button btAdd = (Button) findViewById(R.id.AddStops);
		btAdd.setOnClickListener(new AddButtonOnClick());
		Button btDel = (Button) findViewById(R.id.ReturnToDosList);
		btDel.setOnClickListener(new ReturnButtonOnClick());
	}
	
	public class StopListAdapter extends BaseAdapter {

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
		public View getView(final int position, View convertView, ViewGroup parent) {
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
			SeptaDB septaDB = new SeptaDB(v.getContext());
			try {
				septaDB.open();
				for (int i = holderList.size() - 1; i >= 0; i--) {
					Holder holder = (Holder) holderList.get(i);
					if (holder.checkBox != null && holder.checkBox.isChecked()) {
						Stop stop = holder.stop;
						septaDB.updateFavoriteRoute(stop.getStopID(), 1);
						holderList.remove(i);
						holder.checkBox.setChecked(false);
					}
				}
				septaDB.close();
				setResult(RESULT_OK);
				finish();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	class ReturnButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			setResult(RESULT_CANCELED);
			finish();
		}

	}
}
