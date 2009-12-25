package org.workin.test.rss;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.fortest.spring.SpringContextTestCase;
import org.workin.rss.build.Builder;
import org.workin.rss.fetch.Fetcher;
import org.workin.rss.fetch.RomeFetcher;
import org.workin.util.DateUtils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class RssBuildTest extends SpringContextTestCase {

	@Test
	public void buildRss() {
		
		List<SyndEntry> syndEntryList = new ArrayList<SyndEntry>();
		
		SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("BuildRss Test");
		entry.setAuthor("G.Lee");
		entry.setLink("http://g.cn");
		entry.setPublishedDate(DateUtils.getNow());
		syndEntryList.add(entry);
		
		SyndEntry entry2 = new SyndEntryImpl();
		entry2.setTitle("2_BuildRss Test");
		entry2.setAuthor("G.Lee.2");
		entry2.setLink("http://g.cn/2");
		entry2.setPublishedDate(DateUtils.getNow());
		syndEntryList.add(entry2);
		
		SyndFeed feed = rssBuilder.buildFeed(syndEntryList);
		assertNotNull(feed);
		assertEquals(2, feed.getEntries().size());
		
		Fetcher fetcher = new RomeFetcher();
		fetcher.showFeed(feed, System.out);
	}
	
	@Autowired
	Builder rssBuilder;
}
