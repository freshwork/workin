package org.workin.test.notify;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.notify.MailNotifier;
import org.workin.notify.producer.NotifyMessageProducer;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NotifyMessageProducerTest extends SpringTxTestCase {
	
	@Autowired(required=true)
	private NotifyMessageProducer notifyMessageProducer;
	
	@Test
	public void sendQueueAndTopicTest() {
		
		MailNotifier bily = new MailNotifier();
		bily.setName("Bily");
		bily.setEmail("zhangchuan.wang@elegoninfotech.com");
		
		MailNotifier goingmm = new MailNotifier();
		goingmm.setName("G.Lee");
		goingmm.setEmail("junjie.li@elegoninfotech.com");
		
		notifyMessageProducer.sendQueue(goingmm);
		notifyMessageProducer.sendTopic(goingmm);
	}
}
