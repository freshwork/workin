package org.workin.test.notify;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.jms.JMSUser;
import org.workin.notify.producer.NotifyMessageProducer;

public class JMSTopicNotifyTested extends SpringTxTestCase {
	
	@Autowired
	private NotifyMessageProducer notifyMessageProducer;
	
	@Test
	public void sendTopicTest() {
		
		JMSUser bily = new JMSUser();
		bily.setName("Bily-Topic");
		bily.setEmail("zhangchuan.wang@elegoninfotech.com");
		
		JMSUser bobo = new JMSUser();
		bobo.setName("BOBO-Topic");
		bobo.setEmail("avery.wang@elegoninfotech.com");
		
		JMSUser goingmm = new JMSUser();
		goingmm.setName("G.Lee-Topic");
		goingmm.setEmail("junjie.li@elegoninfotech.com");
		
		
		
		JMSUser[] jmsUsers = {goingmm, bily};
		notifyMessageProducer.sendTopic(jmsUsers);
	}
}
