package org.workin.jms.producer;

import java.io.Serializable;

import javax.jms.Destination;

import org.workin.exception.ThrowableHandle;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class DefaultMessageProducer extends MessageProducerTemplate {

	@Override
	public void sendMessage(Destination destination, Serializable... targetObject) {
		Assert.notNull(targetObject, "targetObject cannot be null! When execute sendMessage(...).");

		for (Serializable target : targetObject) {
			try {
				jmsTemplate.convertAndSend(destination, target);
			} catch (Exception ex) {
				ThrowableHandle.handleThrow("Hit Exception, When execute DefaultMessageProducer.sendMessage(...).", ex, logger);
			}
		}
	}

}
