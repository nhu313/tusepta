/**
 * 
 */
package edu.temple.cis.mysepta.favorite;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import edu.temple.cis.mysepta.InfoAct;
import edu.temple.cis.mysepta.MySepta;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.myclass.DayOfService;
import edu.temple.cis.mysepta.myclass.Stop;
import edu.temple.cis.mysepta.news.NewsAct;
import edu.temple.cis.mysepta.search.SearchServiceAct;

/**
 * @author Yu Liang
 * 
 */
public class StopAct extends ListActivity {

	public static class Holder {
		TextView text;
		ImageView icon;
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
		initialize();
		
		Bundle bundle = getIntent().getExtras();
		DayOfService dos = (DayOfService) bundle.getSerializable("DOS");
		SeptaDB septaDB = new SeptaDB(this);
		try {
			septaDB.open();
			this.holderList = new ArrayList<Holder>();
			List<Stop> stopList = septaDB.getStop(dos.getDayID());
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder = holderList.get(position);
			Context context = parent.getContext();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.listitem01, null);
			
			holder.text = (TextView) convertView.findViewById(R.id.ListItem01Text);
			holder.icon = (ImageView) convertView.findViewById(R.id.ListItem01Icon);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.ListItem01Check);

			holder.icon.setImageDrawable(context.getResources().getDrawable(
					R.drawable.stop48));
			holder.text.setText(holder.stop.toString());
			holder.text.setTextSize(16f);
			holder.text.setTextColor(Color.BLACK);
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
	
	private TextView m_mysched;
	private TextView m_news;
	private TextView m_search;
	private TextView m_options;
	protected SeptaDB db = null;
	
	
	
	@Override
	protected void onStop() {
		if (db != null){
			db.close();
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (db != null){
			db.close();
		}
		super.onDestroy();
	}

	protected void initialize(){
		m_mysched = (TextView) findViewById(R.id.header_t_mysched);
		m_mysched.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (db != null){
					db.close();
				}
				Intent intent = new Intent(v.getContext(), MySepta.class);
				startActivity(intent);
				finish();
			}			
		});
		
		m_news = (TextView) findViewById(R.id.header_t_news);
		m_news.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (db != null){
					db.close();
				}
				Intent intent = new Intent(v.getContext(), NewsAct.class);
				startActivity(intent);
				finish();
			}
			
		});
		
		m_search = (TextView) findViewById(R.id.header_t_search);
		m_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (db != null){
					db.close();
				}
				Intent intent = new Intent(v.getContext(), SearchServiceAct.class);
				startActivity(intent);
				finish();
			}
			
		}); 
	}	
	
	protected static final int PROGRESS_DIALOG = 0;
	protected static final int DELETE_DB_DIALOG = 1;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    AlertDialog.Builder builder = null;
    
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading data. Please wait...");
            return progressDialog;
        default:
            return null;
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, EDIT_FAV, 0, R.string.edit_fav).setIcon(R.drawable.fav);
		menu.add(0, INFO, 0, R.string.info).setIcon(R.drawable.info);
		menu.add(0, CLEAR_DB, 0, "Clear Database").setIcon(R.drawable.delete_n);
		menu.add(0, EXIT_APP, 0, "Exit").setIcon(R.drawable.exit);
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;  
		switch (item.getItemId()) {
		  case EDIT_FAV:
			intent = new Intent(this, FavoriteStopAct.class);
			startActivity(intent);
			finish();
			return true;
		  case INFO:
			intent = new Intent(this, InfoAct.class);
			startActivity(intent);
			finish();
			return true;
		  case CLEAR_DB:
			  db = new SeptaDB(StopAct.this);
 			  db.open();
 			  db.upgrade();
 			  db.close();
			  return true;
		  case EXIT_APP:
			onDestroy();
		    return true;
		  default:
			  return super.onOptionsItemSelected(item);
		  }
		
	}

	public static final int EDIT_FAV = Menu.FIRST;
	public static final int INFO = Menu.FIRST + 1;
	public static final int CLEAR_DB = Menu.FIRST + 2;
	public static final int EXIT_APP = Menu.FIRST + 3;
}
