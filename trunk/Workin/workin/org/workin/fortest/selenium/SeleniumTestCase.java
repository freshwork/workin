package org.workin.fortest.selenium;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@RunWith(SeleniumTestRunner.class)
public class SeleniumTestCase extends Assert {

	public static final String DEFAULT_URL = "http://localhost:8080";
	public static final String DEFAULT_BROWSER = "*chrome";
	public static final String DEFAULT_SELENIUM_HOST = "localhost";
	public static final String DEFAULT_SELENIUM_PORT = "4444";

	public static final String PROPERTY_FILE = "application.test.properties";
	public static final String PROPERTY_URL_NAME = "selenium.url";
	public static final String PROPERTY_BROWSER_NAME = "selenium.browser";
	public static final String PROPERTY_SELENIUM_HOST_NAME = "selenium.host";
	public static final String PROPERTY_SELENIUM_PORT_NAME = "selenium.port";

	public static final String WAIT_FOR_PAGE = "30000";

	protected static Selenium selenium;

	@BeforeClass
	public static void setUp() throws Exception {
		Properties p = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE);
		String browser = p.getProperty(PROPERTY_BROWSER_NAME, DEFAULT_BROWSER);
		String url = p.getProperty(PROPERTY_URL_NAME, DEFAULT_URL);
		String host = p.getProperty(PROPERTY_SELENIUM_HOST_NAME, DEFAULT_SELENIUM_HOST);
		int port = Integer.valueOf(p.getProperty(PROPERTY_SELENIUM_PORT_NAME, DEFAULT_SELENIUM_PORT));

		selenium = new DefaultSelenium(host, port, browser, url);
		selenium.start();
		selenium.windowFocus();
		selenium.windowMaximize();
	}


	@AfterClass
	public static void tearDown() throws Exception {
		selenium.stop();
	}

	public static Selenium getSelenium() {
		return selenium;
	}

	public static void waitPageLoad() {
		selenium.waitForPageToLoad(WAIT_FOR_PAGE);
	}
}
