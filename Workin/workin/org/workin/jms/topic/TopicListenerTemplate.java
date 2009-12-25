package org.workin.jms.topic;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.workin.jms.producer.MessageProducer;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class TopicListenerTemplate implements MessageListener {
	
	public abstract void onMessage(Message message);
	
	
	public static final String CONSTANT_NAME = MessageProducer.CONSTANT_NAME;
	
	public static final String CONSTANT_EMAIL = MessageProducer.CONSTANT_EMAIL;
}
