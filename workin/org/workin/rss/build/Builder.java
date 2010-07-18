package org.workin.rss.build;

import java.util.List;

import org.workin.rss.templet.FeedTemplet;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface Builder extends FeedTemplet {
	
	/**
	 * 
	 * @param syndEntryList
	 * @return
	 * 
	 */
	public SyndFeed buildFeed(List<SyndEntry> syndEntryList);
}
