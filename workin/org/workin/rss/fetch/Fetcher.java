package org.workin.rss.fetch;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.workin.rss.templet.FeedTemplet;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface Fetcher extends FeedTemplet {
	
	/**
	 * fetch feed.
	 * 
	 * @return
	 * 
	 */
	public SyndFeed fetchFeed();
	
	/**
	 * fetch feed with feedUrl.
	 * 
	 * @param feedUrl
	 * @return
	 * 
	 */
	public SyndFeed fetchFeed(String feedUrl);
	
	/**
	 * 
	 * fetch feeds.
	 * 
	 * @return
	 * 
	 */
	public SyndFeed fetchFeeds();
	
	/**
	 * 
	 * fetch feeds with feedsURL.
	 * 
	 * @param feedsURL
	 * @return
	 */
	public SyndFeed fetchFeeds(List<String> feedsURL);
	
	/**
	 * 
	 * show feed.
	 * 
	 * @param feed
	 * @param os
	 * 
	 */
	public void showFeed(SyndFeed feed, OutputStream os);
	
	
	/**
	 * 
	 * @param feedURL
	 */
	public void setFeedURL(final String feedURL);
	
	
	/**
	 * 
	 * @param feedsURL
	 */
	public void setFeedsURL(final List<String> feedsURL);
	
	/**
	 * 
	 * @param requestProperty
	 */
	public void setRequestProperty(final Map<String, String> requestProperty);
}
