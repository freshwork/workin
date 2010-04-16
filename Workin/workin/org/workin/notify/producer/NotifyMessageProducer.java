package org.workin.notify.producer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.workin.exception.ThrowableHandle;
import org.workin.jms.producer.MessageProducerTemplate;
import org.workin.notify.MailNotifier;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NotifyMessageProducer extends MessageProducerTemplate {
	
	/**
	 * 
	 * @author
	 * @see NotifyMessageProducer 
	 *
	 */
	public enum MailNotifierField {
		Name("name"),Email("email");
		
		private String value;
		MailNotifierField(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	@Override
	public void sendQueue(final Serializable... targetObject) {
		sendMessage(notifyQueue, targetObject);
	}

	@Override
	public void sendTopic(final Serializable... targetObject) {
		sendMessage(notifyTopic, targetObject);
	}
	
	
	/**
	 * 
	 * Send Message with simple map.
	 * 
	 * @param destination
	 * @param targetObject
	 *
	 */
	protected void sendMessage(final Destination destination, final Serializable... targetObject) {
		for (Serializable target : targetObject) {
			MailNotifier mailNotifier = new MailNotifier();

			try {
				mailNotifier = (MailNotifier) target;
			} catch (ClassCastException cex) {
				ThrowableHandle.handleThrow("Must be use MailNotifier to sendQueue.", cex, logger);
			} catch (Exception ex) {
				ThrowableHandle.handleThrow("Hit Exception, When execute NotifyMessageProducer.sendQueue().", ex, logger);
			}

			jmsTemplate.convertAndSend(notifyQueue, buildQueueMap(mailNotifier));
		}
	}
	
	/**
	 * 
	 * Build queue map for messageproducer.
	 *  
	 * @param user
	 * @return
	 * 
	 */
	protected Map<String, String> buildQueueMap(final MailNotifier mailNotifier) {
		Map<String, String> queueMap = new HashMap<String, String>();
		Assert.notNull(mailNotifier.getName(), MESSAGE_NOTIFIER_NAME);
		Assert.notNull(mailNotifier.getEmail(), MESSAGE_NOTIFIER_EMAIL);

		queueMap.put(MailNotifierField.Name.getValue(), mailNotifier.getName());
		queueMap.put(MailNotifierField.Email.getValue(), mailNotifier.getEmail());

		return queueMap;
	}
	
	protected static final String MESSAGE_NOTIFIER_NAME = "MailNotifier name cannot be null!";
	protected static final String MESSAGE_NOTIFIER_EMAIL = "MailNotifier email cannot be null!";
}
