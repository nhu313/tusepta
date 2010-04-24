package edu.temple.cis.mysepta.search;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.favorite.StopAct;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.Route;

public class DayOfServiceAct extends ListActivity{
	
    List<DayOfService> dosList;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydayofservice);
		
		Bundle bundle = getIntent().getExtras();
		Route route = (Route) bundle.getSerializable("ROUTE");
		SeptaDB septaDB = new SeptaDB(this);
		try {
			septaDB.open();
			dosList = septaDB.getDayOfServiceList(route);
			septaDB.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}

	public class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dosList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dosList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return ((DayOfService) dosList.get(position)).getDayID();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			DayOfService dos = dosList.get(position);
			LinearLayout layout = new LinearLayout(parent.getContext());
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);

			TextView text = new TextView(parent.getContext());
			text.setTextSize(16f);
			text.setTextColor(Color.WHITE);
			text.setClickable(false);
			text.setText(dos.toString());
			
			layout.setTag(dos);
			layout.addView(text, params);
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					DayOfService dos = (DayOfService) dosList.get(position);
					//Toast.makeText(view.getContext(), route.getName(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(view.getContext(), StopAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("DOS", dos);
					intent.putExtras(bundle);
					((Activity)view.getContext()).startActivityForResult(intent, 3);
				}
			});
			return convertView;
		}	
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}

}
