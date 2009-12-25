package org.workin.jms.queue;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface QueueConsumer {
	
	public abstract void start();
	
	public abstract void stop();
	
}
