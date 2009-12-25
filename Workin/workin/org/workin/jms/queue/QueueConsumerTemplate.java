package org.workin.jms.queue;

import java.util.concurrent.ScheduledExecutorService;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class QueueConsumerTemplate implements QueueConsumer, Runnable {

	protected ScheduledExecutorService executor;

	protected JmsTemplate jmsTemplate;

	protected Destination notifyQueue;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setExecutor(ScheduledExecutorService executor) {
		this.executor = executor;
	}
}
