package org.workin.test.trace;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.service.BehaviorPerformanceService;
import org.workin.util.DateUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class BehaviorPerformanceServiceTest extends SpringTxTestCase {
	
	@Autowired
	BehaviorPerformanceService behaviorPerformanceService;
	
	@Test
	@Rollback(false)
	public void saveBehaviorPerformanceTest() {
		behaviorPerformanceService.save(createBehaviorPerformance());
	}
	
	private BehaviorPerformance createBehaviorPerformance() {
		BehaviorPerformance entity = new BehaviorPerformance();
		entity.setUserId(0123L);
		entity.setUserName("G.Lee");
		entity.setRequestIp("192.168.1.1");
		entity.setRequestURI("/test.do");
		entity.setResponsedttm(DateUtils.currentDateTime());
		entity.setResponsedttm(DateUtils.currentDateTime());
		entity.setSpentTime(1000);
		
		return entity;
	}
}
