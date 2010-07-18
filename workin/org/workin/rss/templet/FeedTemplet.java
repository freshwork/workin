package org.workin.rss.templet;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface FeedTemplet {
	
	/**
	 * 
	 * setup templetOfFeed for 
	 * 		Builder.buildFeed() 
	 * 		and 
	 * 		Fetcher.fetchFeeds()
	 * 
	 * User can new SyndFeed() and then call setSyndFeed(SyndFeed templetFeed).
	 * 
	 * But this way is not commended, in our advice, 
	 * it's much better to dynamically configure the necessary parameter template in IOC container
	 *	
	 * @param templetFeed
	 * 
	 */
	public void setTempletOfFeed(SyndFeed templetOfFeed);
}
