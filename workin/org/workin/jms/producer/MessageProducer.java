package org.workin.jms.producer;

import java.io.Serializable;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface MessageProducer {
	
	/**
	 * 
	 * Send message with topic queue.
	 * 
	 * @param targetObject
	 * 
	 */
	public abstract void sendQueue(final Serializable... targetObject);
	
	/**
	 * 
	 * Send message with topic style.
	 * 
	 * @param targetObject
	 * 
	 */
	public abstract void sendTopic(final Serializable... targetObject);
	
}
