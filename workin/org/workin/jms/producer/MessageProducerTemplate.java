package org.workin.jms.producer;

import java.io.Serializable;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class MessageProducerTemplate implements MessageProducer {
	
	@Override
	public void sendQueue(Serializable... targetObject) {
		sendMessage(notifyQueue, targetObject);
	}

	@Override
	public void sendTopic(Serializable... targetObject) {
		sendMessage(notifyTopic, targetObject);
	}
	
	public abstract void sendMessage(final Destination destination, final Serializable... targetObject);
	
	// use spring jmsTemplate
	@Autowired
	protected JmsTemplate jmsTemplate;
	
	// use jms destination
	@Autowired
	protected Destination notifyQueue;
	
	// use jms destination
	@Autowired
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


	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
