package org.workin.notify.producer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;
import org.workin.jms.JMSUser;
import org.workin.jms.producer.MessageProducerTemplate;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NotifyMessageProducer extends MessageProducerTemplate {
	
	
	@Override
	public void sendQueue(final JMSUser... user) {
		for(final JMSUser jmsUser: user) {
			jmsTemplate.convertAndSend(notifyQueue, this.buildQueueMap(jmsUser));
			sleep(1000);
		}
	}

	@Override
	public void sendTopic(final JMSUser... user) {
		for(final JMSUser jmsUser: user) {
			jmsTemplate.send(notifyTopic, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					MapMessage message;
					message = buildMapMessageWithSession(session, jmsUser);	
					
					return message;
				}
			});
			sleep(1000);
		}
	}
}
