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
import edu.temple.cis.tusepta.R;
import edu.temple.cis.tusepta.Utils;

/**
 * @author Yu Liang
 *
 */
public class NewsAct extends ListActivity {

	private static final String SEPTA = "SEPTA";
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
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

}
