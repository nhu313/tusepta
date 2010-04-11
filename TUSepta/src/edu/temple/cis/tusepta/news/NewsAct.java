/**
 * 
 */
package edu.temple.cis.tusepta.news;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.temple.cis.tusepta.R;
import edu.temple.cis.tusepta.Utils;

/**
 * @author Yu Liang
 *
 */
public class NewsAct extends ListActivity {

	private static final String SEPTA = "SEPTA";
	private static final String CAP_FAVORITE = "Favorite News";
	private static final String CAP_ALL = "All News";
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		
		Button btnFavoriteAll = (Button) findViewById(R.id.FavoriteNews);
		btnFavoriteAll.setText(CAP_ALL);
		btnFavoriteAll.setOnClickListener(new FavortieNewsButtonOnClick());
		
		Button ibAll = (Button) findViewById(R.id.AllNews);
		ibAll.setOnClickListener(new AllNewsButtonOnClick());
		
		try {
			Twitter twitter = new TwitterFactory().getInstance("tusepta", "tusepta2010");
			List<Status> statuses = twitter.getFriendsTimeline();
			List<News> newsList = new ArrayList<News>();
			for (Status status : statuses) {
				if (status.getUser().getName().equals(SEPTA)) {
					News news = new News();
					news.setContent(status.getText());
			        newsList.add(news);
				}
		    }
			NewsListAdapter adapter = new NewsListAdapter(this, newsList);
			setListAdapter(adapter);
		} catch (TwitterException te) {
			te.printStackTrace();
			Utils.showMessage(this, "Cannot access Septa's twitter website: " + te.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	class FavortieNewsButtonOnClick implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			Button button = (Button)view;
			if (button.getText().equals(CAP_ALL)) {
				button.setText(CAP_FAVORITE);
			} else {
				button.setText(CAP_ALL);
			}
		}
		
	}
	
	class AllNewsButtonOnClick implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			
		}
		
	}
}
