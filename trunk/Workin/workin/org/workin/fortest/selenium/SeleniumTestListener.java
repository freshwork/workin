package org.workin.fortest.selenium;

import java.io.File;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.thoughtworks.selenium.Selenium;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class SeleniumTestListener extends RunListener {

	@Override
	public void testFailure(Failure failure) {
		Selenium selenium = SeleniumTestCase.getSelenium();
		String imgFilePath = System.getProperty("java.io.tmpdir") + File.separator
				+ failure.getDescription().getDisplayName() + ".png";
		try {
			selenium.captureScreenshot(imgFilePath);
			System.err.println("Saved screenshot " + imgFilePath);
		} catch (Exception e) {
			System.err.println("Couldn't save screenshot " + imgFilePath + ": " + e.getMessage());
		}
	}
}
