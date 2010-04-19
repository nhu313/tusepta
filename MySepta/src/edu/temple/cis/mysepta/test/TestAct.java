/**
 * 
 */
package edu.temple.cis.mysepta.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.favorite.FavoriteStopAct;
import edu.temple.cis.mysepta.news.NewsAct;

/**
 * @author Yu Liang 
 *
 */
public class TestAct extends Activity {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		Button btTestNews = (Button) findViewById(R.id.TestNews);
		btTestNews.setOnClickListener(new TestNewsButtonOnClish(this));
		
		Button btTestFavorteRoutes = (Button) findViewById(R.id.TestFavoriteRoutes);
		btTestFavorteRoutes.setOnClickListener(new TestFavoriteButtonOnClish(this));
	}
	
	class TestNewsButtonOnClish implements OnClickListener {

		private Context ctx;
		
		TestNewsButtonOnClish(Context ctx) {
			this.ctx = ctx;
		}
		@Override
		public void onClick(View arg0) {
			Intent itNews = new Intent(this.ctx, NewsAct.class);
			startActivity(itNews);
		}
		
	}

	class TestFavoriteButtonOnClish implements OnClickListener {

		private Context ctx;
		
		TestFavoriteButtonOnClish(Context ctx) {
			this.ctx = ctx;
		}
		@Override
		public void onClick(View arg0) {
			Intent itFavorite = new Intent(this.ctx, FavoriteStopAct.class);
			startActivity(itFavorite);
		}
		
	}
}
