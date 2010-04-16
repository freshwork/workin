package org.workin.trace.producer;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;
import org.workin.exception.ThrowableHandle;
import org.workin.jms.producer.MessageProducerTemplate;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.util.Assert;
import org.workin.util.DateUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class BehaviorAndPerformanceProducer extends MessageProducerTemplate {

	/**
	 * 
	 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
	 *
	 */
	public enum BPSavedWithKeys {
		USERID, SPENTTIME,USERNAME, REQUESTIP, REQUESTURI, REQUESTDTTM, RESPONSEDTTM;
	}

	@Override
	public void sendMessage(Destination destination, Serializable... targetObject) {
		Assert.notNull(targetObject, "targetObject cannot be null!When sendMessage.");
		
		for (Serializable target : targetObject) {
			try {
				final BehaviorPerformance behaviorPerformance = (BehaviorPerformance) target;

				jmsTemplate.send(destination, new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						MapMessage message = buildMapMessageWithSession(session, behaviorPerformance);
						return message;
					}
				});

			} catch (ClassCastException cex) {
				ThrowableHandle.handleThrow("Must be use BehaviorPerformance to sendMessage().", cex, logger);
			} catch (Exception ex) {
				ThrowableHandle.handleThrow(
						"Hit Exception, When execute BehaviorAndPerformanceProducer.sendMessage().", ex, logger);
			}
		}
	}

	/**
	 * 
	 * Build mapmessage with session and BehaviorPerformance.
	 * 
	 * @param session
	 * @param behaviorPerformance
	 * @return
	 * @throws JMSException
	 * 
	 */
	protected MapMessage buildMapMessageWithSession(final Session session, final BehaviorPerformance behaviorPerformance)
			throws JMSException {
		Assert.notNull(behaviorPerformance, "behaviorPerformance cannot be null!When build MapMessage with session.");

		MapMessage message = session.createMapMessage();
		
		logger.debug("send behaviorPerformance UserId: {}",  behaviorPerformance.getUserId());
		message.setLong(BPSavedWithKeys.USERID.toString(), behaviorPerformance.getUserId());
		
		logger.debug("send behaviorPerformance SpentTime: {}",  behaviorPerformance.getSpentTime());
		message.setLong(BPSavedWithKeys.SPENTTIME.toString(), behaviorPerformance.getSpentTime());
		
		logger.debug("send behaviorPerformance UserName: {}",  behaviorPerformance.getUserName());
		message.setString(BPSavedWithKeys.USERNAME.toString(), behaviorPerformance.getUserName());
		
		logger.debug("send behaviorPerformance RequestIp: {}",  behaviorPerformance.getRequestIp());
		message.setString(BPSavedWithKeys.REQUESTIP.toString(), behaviorPerformance.getRequestIp());
		
		logger.debug("send behaviorPerformance RequestURI: {}",  behaviorPerformance.getRequestURI());
		message.setString(BPSavedWithKeys.REQUESTURI.toString(), behaviorPerformance.getRequestURI());
		
		logger.debug("send behaviorPerformance Requestdttm: {}",  DateUtils.dateToString(behaviorPerformance.getRequestdttm()));
		message.setString(BPSavedWithKeys.REQUESTDTTM.toString(), DateUtils.dateToString(behaviorPerformance.getRequestdttm()));
		
		logger.debug("send behaviorPerformance Responsedttm: {}",  DateUtils.dateToString(behaviorPerformance.getResponsedttm()));
		message.setString(BPSavedWithKeys.RESPONSEDTTM.toString(), DateUtils.dateToString(behaviorPerformance.getResponsedttm()));
		
		return message;
	}
}
