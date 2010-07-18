package org.workin.test.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.exception.ThrowableHandler;
import org.workin.fortest.BaseTestCase;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ThrowableHandleTested extends BaseTestCase {
	
	private static final String USER_MESSAGE = "User Message: ";
	
	private static final transient Logger logger = LoggerFactory.getLogger(ThrowableHandleTested.class);
	
	
	@Test
	public void testHandleStringThrowable() {
		try {
			throw new Exception("testHandleStringThrowable");
		} catch (Exception e) {
			ThrowableHandler.handle(buildMessage(e), e);
		}
	}

	@Test
	public void testHandleStringThrowableLogger() {
		try {
			throw new Exception("testHandleStringThrowableLogger");
		} catch (Exception e) {
			ThrowableHandler.handle(buildMessage(e), e, logger);
		}
	}

	@Test
	public void testHandleThrowStringThrowable() {
		try {
			throw new Exception("testHandleThrowStringThrowable");
		} catch (Exception e) {
			try {
				ThrowableHandler.handle(buildMessage(e), e);
			} catch (Exception e2) {
				assertEquals(USER_MESSAGE, e2.getMessage());
			}
		}
	}

	@Test
	public void testHandleThrowStringThrowableLogger() {
		try {
			throw new Exception("testHandleThrowStringThrowableLogger");
		} catch (Exception e) {
			try {
				ThrowableHandler.handle(buildMessage(e), e, logger);
			} catch (Exception e2) {
				assertEquals(USER_MESSAGE, e2.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	private String buildMessage(Throwable e) {
		StringBuilder sb = new StringBuilder(USER_MESSAGE);
		sb.append("Hit Exception @ ");
		sb.append(e.getMessage());
		
		return sb.toString();
	}
}
