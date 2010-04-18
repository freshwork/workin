package org.workin.jms.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.exception.ThrowableHandle;
import org.workin.mail.MailPackage;
import org.workin.mail.MailService;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.service.BehaviorPerformanceService;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class DefaultMessageConsumer implements MessageConsumer {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	BehaviorPerformanceService behaviorAndPerformanceService;
	
	
	@Override
	public void receiveMessage(MailPackage message) {
		try {
			if(mailService != null) {
				mailService.sendMail(message);
				logger.debug("MailService sent mail in DefaultMessageConsumer...");
			} else {
				logger.debug("Cannot find mailService, Please config...");
			}
		} catch (Exception ex) {
			ThrowableHandle.handleThrow("Hit Exception, When execute DefaultMessageConsumer.receiveMailPackage()", ex, logger);
		}
	}

	@Override
	public void receiveMessage(BehaviorPerformance message) {
		try {
			if(behaviorAndPerformanceService != null) {
				behaviorAndPerformanceService.merge((BehaviorPerformance)message);
				logger.info("BehaviorAndPerformanceService merged behaviorPerformance in DefaultMessageConsumer...");
			} else {
				logger.debug("Cannot find behaviorAndPerformanceService, Please config...");
			}
		} catch (Exception ex) {
			ThrowableHandle.handleThrow(
					"Hit Exception, When execute StoreBehaviorAndPerformanceMessageConsumer.onMessage().", ex, logger);
		}	
	}
	
	
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setBehaviorAndPerformanceService(BehaviorPerformanceService behaviorAndPerformanceService) {
		this.behaviorAndPerformanceService = behaviorAndPerformanceService;
	}


	private transient final Logger logger = LoggerFactory.getLogger(DefaultMessageConsumer.class);
}
