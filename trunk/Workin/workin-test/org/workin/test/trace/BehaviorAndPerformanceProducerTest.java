package org.workin.test.trace;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.producer.BehaviorAndPerformanceProducer;
import org.workin.util.DateUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class BehaviorAndPerformanceProducerTest extends SpringTxTestCase {
	
	@Autowired(required=true)
	BehaviorAndPerformanceProducer behaviorAndPerformanceProducer;
	
	@Test
	public void behaviorAndPerformanceProducerSendQueue() {
		BehaviorPerformance targetObject = new BehaviorPerformance();
		
		targetObject.setUserId(10086);
		targetObject.setUserName("Admin");
		targetObject.setRequestIp("192.168.6.6");
		targetObject.setRequestURI("/admin/user.do");
		targetObject.setRequestdttm(DateUtils.currentDateTime());
		targetObject.setResponsedttm(DateUtils.currentDateTime());
		targetObject.setSpentTime(1000L);
		
		behaviorAndPerformanceProducer.sendQueue(targetObject);			
	}
}
