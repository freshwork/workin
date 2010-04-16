package org.workin.notify.producer;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;
import org.workin.exception.ThrowableHandle;
import org.workin.notify.MailNotifier;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class AdvancedNotifyMessageProducer extends NotifyMessageProducer {
	
	/**
	 * 
	 * Advanced Send Message With MapMessage.
	 * 
	 * @param destination
	 * @param targetObject
	 * 
	 */
	@Override
	protected void sendMessage(final Destination destination, final Serializable... targetObject) {
		for (Serializable target : targetObject) {
			try {

				final MailNotifier mailNotifier = (MailNotifier) target;

				jmsTemplate.send(notifyTopic, new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						MapMessage message = buildMapMessageWithSession(session, mailNotifier);
						return message;
					}
				});

			} catch (ClassCastException cex) {
				ThrowableHandle.handleThrow("Must be use MailNotifier to sendTopic.", cex, logger);
			} catch (Exception ex) {
				ThrowableHandle.handleThrow("Hit Exception, When execute NotifyMessageProducer.sendTopic().", ex, logger);
			}
		}
	}
	
	/**
	 * 
	 * Build mapmessage with session and mailNotifier.
	 * 
	 * @param session
	 * @param mailNotifier
	 * @return
	 * @throws JMSException
	 * 
	 */
	protected MapMessage buildMapMessageWithSession(final Session session, final MailNotifier mailNotifier)
			throws JMSException {
		Assert.notNull(mailNotifier.getName(), MESSAGE_NOTIFIER_NAME);
		Assert.notNull(mailNotifier.getEmail(), MESSAGE_NOTIFIER_EMAIL);

		MapMessage message = session.createMapMessage();
		message.setString(MailNotifierField.Name.getValue(), mailNotifier.getName());
		message.setString(MailNotifierField.Email.getValue(), mailNotifier.getEmail());

		return message;
	}
}
