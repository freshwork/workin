package org.workin.test.log;

import org.apache.log4j.MDC;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.fortest.BaseTestCase;
import org.workin.log.MockAppender;
import org.workin.log.TraceUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class TraceUtilsTested extends BaseTestCase {

	Logger logger = LoggerFactory.getLogger(TraceUtilsTested.class);

	@Test public void test() {
		MockAppender appender = new MockAppender();
		appender.addToLogger(TraceUtilsTested.class);

		//begin trace
		TraceUtils.beginTrace();
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		assertEquals(10, ((String) MDC.get(TraceUtils.TRACE_ID_KEY)).length());

		//log message
		logger.trace("message");
		assertEquals("message", appender.getFirstLog().getMessage());
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));

		//end trace
		TraceUtils.endTrace();
		assertNull(MDC.get(TraceUtils.TRACE_ID_KEY));
	}

}
