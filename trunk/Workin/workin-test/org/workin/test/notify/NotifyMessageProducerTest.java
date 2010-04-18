package org.workin.test.notify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.jms.producer.DefaultMessageProducer;
import org.workin.mail.MailPackage;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NotifyMessageProducerTest extends SpringTxTestCase {
	
	@Autowired(required = true)
	public DefaultMessageProducer defaultMessageProducer;
	
	@Test
	public void sendQueueAndTopicTest() {
	
		
		MailPackage goingmm = new MailPackage();
		goingmm.setSayHelloTo("goingmm");
		
		List<String> mailTos= new ArrayList<String>();
		mailTos.add("junjie.li@elegoninfotech.com");
		
		goingmm.setMailTo(mailTos);
		
		defaultMessageProducer.sendQueue(goingmm);
	}
}
