package org.workin.notify.queue;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.workin.jms.queue.QueueConsumerTemplate;
import org.workin.notify.Notification;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NotifyQueueConsumer extends QueueConsumerTemplate {

	@Override
	@PostConstruct
	public void start() {
		executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
			public Thread newThread(Runnable runable) {
				return new Thread(runable, "JMS(Email) Notify Queue Consumer");
			}
		});

		executor.scheduleAtFixedRate(this, 0, executeTimeRate, TimeUnit.MILLISECONDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Map message = (Map) jmsTemplate.receiveAndConvert(notifyQueue);
		
		String name = String.valueOf(message.get("name"));
		String email = String.valueOf(message.get("email"));
		if(StringUtils.hasText(name) && StringUtils.hasText(email))
			notification.sendNotification(name, email);
	}

	@Override
	@PreDestroy
	public void stop() {
		try {
			executor.shutdownNow();
			executor.awaitTermination(awaitTermination, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.warn("Hit Exception, When stop NotifyQueueConsumer.", e);
		}
	}

	@Autowired(required = true)
	Notification notification;

	private int executeTimeRate = 1000;

	private int awaitTermination = 10000;

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public void setExecuteTimeRate(int executeTimeRate) {
		this.executeTimeRate = executeTimeRate;
	}

	public void setAwaitTermination(int awaitTermination) {
		this.awaitTermination = awaitTermination;
	}

	protected transient final Logger logger = LoggerFactory.getLogger(NotifyQueueConsumer.class);
}
