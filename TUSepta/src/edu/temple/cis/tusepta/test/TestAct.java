/**
 * 
 */
package edu.temple.cis.tusepta.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.temple.cis.tusepta.R;
import edu.temple.cis.tusepta.news.NewsAct;

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
		btTestNews.setOnClickListener(new TestNewButtonOnClish(this));
	}
	
	class TestNewButtonOnClish implements OnClickListener {

		private Context ctx;
		
		TestNewButtonOnClish(Context ctx) {
			this.ctx = ctx;
		}
		@Override
		public void onClick(View arg0) {
			Intent itNews = new Intent(this.ctx, NewsAct.class);
			startActivity(itNews);
		}
		
	}
}
