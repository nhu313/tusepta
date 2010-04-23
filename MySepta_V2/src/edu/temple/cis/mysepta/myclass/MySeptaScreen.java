package edu.temple.cis.mysepta.myclass;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import edu.temple.cis.mysepta.data2.SeptaDB;
import edu.temple.cis.mysepta.news.NewsAct;
import edu.temple.cis.mysepta2.MySepta;
import edu.temple.cis.mysepta2.R;
import edu.temple.cis.mysepta2.search.SearchService;

public class MySeptaScreen extends Activity {
	private TextView m_mysched;
	private TextView m_news;
	private TextView m_search;
	private TextView m_options;
	protected SeptaDB db = null;
	
	
	
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
				Intent intent = new Intent(v.getContext(), MySepta.class);
				showDialog(PROGRESS_DIALOG);
				startActivity(intent);
				dismissDialog(PROGRESS_DIALOG);
				finish();
			}			
		});
		
		m_news = (TextView) findViewById(R.id.header_t_news);
		m_news.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NewsAct.class);
				showDialog(PROGRESS_DIALOG);
				startActivity(intent);
				dismissDialog(PROGRESS_DIALOG);
				finish();
			}
			
		});
		
		m_search = (TextView) findViewById(R.id.header_t_search);
		m_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SearchService.class);
				showDialog(PROGRESS_DIALOG);
				startActivity(intent);
				dismissDialog(PROGRESS_DIALOG);
				finish();
			}
			
		});
		
		m_options = (TextView) findViewById(R.id.header_t_options);
		m_options.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				showDialog(PROGRESS_DIALOG);
/*
				Intent intent = new Intent(v.getContext(), MySepta.class);
				startActivity(intent);
				finish();
				*/
			}
			
		});	   
	}	
	
	protected static final int PROGRESS_DIALOG = 0;
    ProgressDialog progressDialog;
    
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(MySeptaScreen.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading data. Please wait...");
            return progressDialog;
        default:
            return null;
        }
    }
/*
 // Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int total = msg.getData().getInt("total");
            progressDialog.setProgress(total);
            if (total >= 100){
                dismissDialog(PROGRESS_DIALOG);
            }
        }
    };
    */
}