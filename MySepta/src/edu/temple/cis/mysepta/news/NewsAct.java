/**
 * 
 */
package edu.temple.cis.mysepta.news;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import edu.temple.cis.mysepta.R;
import edu.temple.cis.mysepta.myclass.MySeptaScreen;

/**
 * @author Yu Liang
 *
 */
public class NewsAct extends MySeptaScreen {
	private Context ctx = this;
	private static final String SEPTA = "SEPTA";
	private static ListView list;
	private static NewsListAdapter adapter;	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		initialize();
		refresh();
	}
	
	private void refresh(){
		showDialog(PROGRESS_DIALOG);
		loadData();
	}
	
	protected void changeDisplay(){
		list = (ListView) findViewById(R.id.news_list);
		list.setAdapter(adapter);
	}
	
	private void loadData(){
		new Thread() {
            public void run() {
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
        			adapter = new NewsListAdapter(ctx, newsList);
        		} catch (TwitterException te) {
        			mErrorHandler.post(dismissError);
        			te.printStackTrace();
        		} catch (Exception e) {
        			throw new RuntimeException(e);
        		}
        		mHandler.post(dismiss);
            }
       }.start();		
	}

	@Override
	public void extraFunction() {
		refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, EXTRA, 0, "Refresh").setIcon(R.drawable.refresh);
		return result;
	}
	
}
