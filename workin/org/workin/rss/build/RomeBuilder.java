package org.workin.rss.build;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.util.Assert;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class RomeBuilder implements Builder {
	
	@Override
	public SyndFeed buildFeed(List<SyndEntry> syndEntryList) {
		Assert.notNull(this.templetOfFeed, " templetFeed cannot be null, This value need config with ioc container.");

		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		this.templetOfFeed.setEntries(entries);

		for (SyndEntry syndEntry : syndEntryList) {
			entries.add(syndEntry);
		}
		
		logger.debug(" Builded Feed title :{}.", templetOfFeed.getTitle());
		return templetOfFeed;
	}
	
	public void setTempletOfFeed(SyndFeed templetOfFeed) {
		this.templetOfFeed = templetOfFeed;
	}
	
	//	templet for build feed
	private SyndFeed templetOfFeed;

	private static final transient Logger logger = LoggerFactory.getLogger(RomeBuilder.class);
}
