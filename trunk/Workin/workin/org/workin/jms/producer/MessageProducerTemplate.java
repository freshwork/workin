package org.workin.jms.producer;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.workin.jms.JMSUser;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class MessageProducerTemplate implements MessageProducer {
	
	/**
	 * 
	 * build queue map for messageproducer.
	 * 
	 * @param user
	 * @return
	 */
	protected Map<String, String> buildQueueMap(final JMSUser user) {
		Map<String, String> queueMap = new HashMap<String, String>();
		Assert.notNull(user.getName(), "JMSUser name can not be null!");
		Assert.notNull(user.getEmail(), "JMSUser email can not be null!");
		
		queueMap.put(CONSTANT_NAME, user.getName());
		queueMap.put(CONSTANT_EMAIL, user.getEmail());
		
		return queueMap;
	}
	
	/**
	 * 
	 * build mapmessage with session.
	 * 
	 * @param session
	 * @param user
	 * @return
	 * @throws JMSException
	 * 
	 */
	protected MapMessage buildMapMessageWithSession(final Session session, final JMSUser user)throws JMSException {
		Assert.notNull(user.getName(), "JMSUser name can not be null!");
		Assert.notNull(user.getEmail(), "JMSUser email can not be null!");
		
		MapMessage message = session.createMapMessage();
		message.setString(CONSTANT_NAME, user.getName());
		message.setString(CONSTANT_EMAIL, user.getEmail());
		return message;
	}
	
	/**
	 * 
	 * @param millis
	 */
	protected void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	// use spring jmsTemplate
	
	protected JmsTemplate jmsTemplate;
	// use jms destination
	
	protected Destination notifyQueue;
	// use jms destination
	
	protected Destination notifyTopic;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setNotifyTopic(Destination notifyTopic) {
		this.notifyTopic = notifyTopic;
	}
}
