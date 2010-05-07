package edu.temple.cis.mysepta.myclass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import edu.temple.cis.mysepta.InfoAct;
import edu.temple.cis.mysepta.MySepta;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.data.SeptaDB;
import edu.temple.cis.mysepta.favorite.FavoriteStopAct;
import edu.temple.cis.mysepta.news.NewsAct;
import edu.temple.cis.mysepta.search.SearchServiceAct;

public abstract class MySeptaScreen extends Activity {
	private TextView m_mysched;
	private TextView m_news;
	private TextView m_search;
	protected Context mCtx = this;
	protected SeptaDB db = null;
	protected Handler mHandler = new Handler();
	protected Runnable dismiss = new Runnable()	{
        public void run() {
        	changeDisplay();
        	dismissDialog(PROGRESS_DIALOG);
        }
    };
	protected Handler mErrorHandler = new Handler();
	protected Runnable dismissError = new Runnable()	{
        public void run() {
        	openErrorDialog();
        }
    };
    
	public void openErrorDialog(){
		builder = new AlertDialog.Builder(this);  
    	builder.setTitle("MySepta");  
    	builder.setMessage("Cannot connect to server. Please try again at another time.");
    	//Dialog box return button
    	builder.setPositiveButton("Return", new DialogInterface.OnClickListener() {  
    		public void onClick(DialogInterface dialog, int whichButton) {  
    			dialog.dismiss();
    		}  
    	}); //End Return button
    	builder.show();
	}

    protected void changeDisplay(){}
	
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
    protected ProgressDialog progressDialog;
    protected AlertDialog alertDialog;
    protected AlertDialog.Builder builder = null;
    
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(this);
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
		menu.add(0, CLEAR_DB, 0, "Clear Data").setIcon(R.drawable.delete);
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
			  db = new SeptaDB(MySeptaScreen.this);
 			  db.open();
 			  db.upgrade();
 			  db.close();
 			  Toast t = Toast.makeText(mCtx, "Database is cleared.", Toast.LENGTH_SHORT);
 			  t.show();
			  return true;
		  case EXTRA:
			extraFunction();
		    return true;
		  default:
			  return super.onOptionsItemSelected(item);
		  }
		
	}
	
	public void extraFunction(){}
	
	public static final int EXTRA = Menu.FIRST;
	public static final int INFO = Menu.FIRST + 1;
	public static final int CLEAR_DB = Menu.FIRST + 2;
	public static final int EDIT_FAV = Menu.FIRST + 3;
}
