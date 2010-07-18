package org.workin.test.log;

import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.log.JulToSlf4jHandler;
import org.workin.log.MockAppender;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JulToSlf4jHandlerTest extends BaseTestCase {

	@Test
	public void test() {
		JulToSlf4jHandler init = new JulToSlf4jHandler();
		init.init();

		java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("TestLogger");
		MockAppender appender = new MockAppender();
		appender.addToLogger("TestLogger");

		julLogger.warning("test");
		assertEquals("test", appender.getFirstLog().getMessage());
	}
}