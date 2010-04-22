package edu.temple.cis.mysepta.myclass;

import android.app.Activity;
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
	
	protected void initialize(){
		m_mysched = (TextView) findViewById(R.id.header_t_mysched);
		m_mysched.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MySepta.class);
				startActivity(intent);
				finish();
			}			
		});
		
		m_news = (TextView) findViewById(R.id.header_t_news);
		m_news.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NewsAct.class);
				startActivity(intent);
				finish();
			}
			
		});
		
		m_search = (TextView) findViewById(R.id.header_t_search);
		m_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SearchService.class);
				startActivity(intent);
				finish();
			}
			
		});
		
		m_options = (TextView) findViewById(R.id.header_t_options);
		m_options.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MySepta.class);
				startActivity(intent);
				finish();
			}
			
		});
		
	}	
}
