package edu.temple.cis.mysepta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.temple.cis.mysepta.favorite.FavoriteRoutesAct;
import edu.temple.cis.mysepta.news.NewsAct;

/**
 * 
 * @author Yu Liang
 * Wanwisa
 *
 */
public class MySepta extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnNews = (Button) findViewById(R.id.SeptaNewsButton);
        btnNews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NewsAct.class);
		        startActivity(intent);
			}
        	
        });
        
        Button btnFavorite = (Button) findViewById(R.id.FavoriteRouteSettingButton);
        btnFavorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), FavoriteRoutesAct.class);
		        startActivity(intent);
			}
        	
        });
        
    }
}