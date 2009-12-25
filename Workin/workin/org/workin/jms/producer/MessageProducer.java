package org.workin.jms.producer;

import org.workin.jms.JMSUser;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface MessageProducer {
	
	/**
	 * 
	 * send message to user with queue style.
	 * 
	 * @param user
	 */
	public abstract void sendQueue(final JMSUser... user);
	
	/**
	 * 
	 * send message to user with topic style.
	 * 
	 * @param user
	 */
	public abstract void sendTopic(final JMSUser... user);
	
	
	public static final String CONSTANT_NAME = "name";
	
	public static final String CONSTANT_EMAIL = "email";
}
