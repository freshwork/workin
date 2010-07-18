package org.workin.rss.fetch;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.workin.exception.ThrowableHandler;
import org.workin.util.Assert;
import org.workin.util.CollectionUtils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class RomeFetcher implements Fetcher {

	@Override
	public SyndFeed fetchFeed() {
		return fetchFeed(this.feedURL);
	}

	@Override
	public SyndFeed fetchFeed(String feedUrl) {
		feedUrl = StringUtils.hasText(feedUrl) ? feedUrl : this.feedURL;
		Assert.notNull(feedUrl, " feedUrl cannot be null, This value need pass in or config with ioc container.");

		SyndFeed feed = null;
		URLConnection url;
		SyndFeedInput input;
		
		String userMessage = "When call fetchFeed()...";
		
		try {
			url = new URL(feedUrl).openConnection();
			
			if (url != null) {
				
				if(!CollectionUtils.isEmpty(this.requestProperty)) {
					for (Map.Entry<String, String> entry : requestProperty.entrySet()) {
						url.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
					}
				}
				
				input = new SyndFeedInput();
				feed = input.build(new XmlReader(url));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			ThrowableHandler.handleThrow(userMessage, e, logger);
		} catch (IOException e) {
			e.printStackTrace();
			ThrowableHandler.handleThrow(userMessage, e, logger);
		} catch (Exception e) {
			e.printStackTrace();
			ThrowableHandler.handleThrow(userMessage, e, logger);
		}

		return feed;
	}

	@Override
	public SyndFeed fetchFeeds() {
		return fetchFeeds(this.feedsURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SyndFeed fetchFeeds(List<String> feedsURL) {
		
		feedsURL = !CollectionUtils.isEmpty(feedsURL) ? feedsURL : this.feedsURL;
		Assert.notEmpty(feedsURL, " feedsURL cannot be null, This value need pass in or config with ioc container.");
		Assert.notNull(this.templetOfFeed, " templetFeed cannot be null, This value need config with ioc container.");

		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		this.templetOfFeed.setEntries(entries);
		
		for(String feedUrl: feedsURL) {
			SyndFeed feed = this.fetchFeed(feedUrl);
			
			if(feed != null) {
				entries.addAll(feed.getEntries());	
			}
		}	

		return templetOfFeed;
	}
	
	@Override
	public void showFeed(SyndFeed feed, OutputStream os) {
		SyndFeedOutput output = new SyndFeedOutput();
		
		String userMessage = "When call showFeed()...";
		
        try {
			output.output(feed,new PrintWriter(os));
		} catch (IOException e) {
			e.printStackTrace();
			ThrowableHandler.handleThrow(userMessage, e, logger);
		} catch (FeedException e) {
			e.printStackTrace();
			ThrowableHandler.handleThrow(userMessage, e, logger);
		}
	}
	
	
	@Override
	public void setFeedURL(final String feedURL) {
		this.feedURL = feedURL;
	}

	@Override
	public void setFeedsURL(final List<String> feedsURL) {
		this.feedsURL = feedsURL;
	}

	@Override
	public void setTempletOfFeed(SyndFeed templetOfFeed) {
		this.templetOfFeed = templetOfFeed;
	}

	@Override
	public void setRequestProperty(final Map<String, String> requestProperty) {
		this.requestProperty = requestProperty;
	}
	
	//	templet for build feed
	private SyndFeed templetOfFeed;
	
	//	feed url for fetch one feed
	private String feedURL;
	
	//	feeds url for fetch one or more feed
	private List<String> feedsURL;
	
	//	requestProperty is url's property
	private Map<String, String> requestProperty;
	
	private static final transient Logger logger = LoggerFactory.getLogger(RomeFetcher.class);
}
