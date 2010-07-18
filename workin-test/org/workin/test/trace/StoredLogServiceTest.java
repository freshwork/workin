package org.workin.test.trace;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.workin.exception.ServiceException;
import org.workin.exception.ThrowableHandler;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.trace.service.StoredLogService;

public class StoredLogServiceTest extends SpringTxTestCase {
	
	@Autowired
	StoredLogService storedLogService;
	
	@Test
	@Rollback(false)
	public void throwAbleLogTest() {
		try {
			throw new ServiceException("Exception");
		} catch (Exception e) {
			ThrowableHandler.storeLogToDb(storedLogService, "User Message: Testing...", e);
		}
	}
}
