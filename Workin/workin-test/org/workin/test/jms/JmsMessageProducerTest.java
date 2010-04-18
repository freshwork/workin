package org.workin.test.jms;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.jms.producer.DefaultMessageProducer;
import org.workin.mail.MailPackage;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.domain.StoredLog;
import org.workin.util.DateUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JmsMessageProducerTest extends SpringTxTestCase {
	
	@Autowired(required = true)
	public DefaultMessageProducer defaultMessageProducer;
	
	@Test
	public void userDefaultMessageProducerSendMail() {
	
		
		MailPackage goingmm = new MailPackage();
		goingmm.setSayHelloTo("goingmm");
		
		List<String> mailTos= new ArrayList<String>();
		mailTos.add("junjie.li@elegoninfotech.com");
		
		goingmm.setMailTo(mailTos);
		
		defaultMessageProducer.sendQueue(goingmm);
	}
	
	@Test
	public void userDefaultMessageProducerStorebBehaviorAndPerformance() {
		BehaviorPerformance targetObject = new BehaviorPerformance();
		
		targetObject.setUserId(10086);
		targetObject.setUserName("Admin");
		targetObject.setRequestIp("192.168.6.6");
		targetObject.setRequestURI("/admin/user.do");
		targetObject.setRequestdttm(DateUtils.currentDateTime());
		targetObject.setResponsedttm(DateUtils.currentDateTime());
		targetObject.setSpentTime(1000);
		
		defaultMessageProducer.sendQueue(targetObject);	
	}
	
	@Test
	public void userDefaultMessageProducerStoreLog() {
		StoredLog storedLog = new StoredLog();
		storedLog.setLogdttm(DateUtils.currentDateTime());
		storedLog.setUserMessage("test in JMS");
		storedLog.setWhereClass(this.getClass().getName());
		defaultMessageProducer.sendQueue(storedLog);
	}
	
}
