package org.workin.jms.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.exception.ThrowableHandler;
import org.workin.mail.MailPackage;
import org.workin.mail.MailService;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.domain.StoredLog;
import org.workin.trace.service.BehaviorPerformanceService;
import org.workin.trace.service.StoredLogService;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class DefaultMessageConsumer implements MessageConsumer {
	
	@Autowired(required=false)
	private MailService mailService;
	
	@Autowired(required=false)
	BehaviorPerformanceService behaviorAndPerformanceService;
	
	@Autowired(required=false)
	StoredLogService storedLogService;
	
	@Override
	public void receiveMessage(MailPackage message) {
		try {
			synchronized (this) {
				if(mailService != null) {
					mailService.sendMail(message);
					logger.debug("MailService sent mail in DefaultMessageConsumer...");
				} else {
					logger.debug("Cannot find mailService, Please config...");
				}
			}

		} catch (Exception ex) {
			ThrowableHandler.handleThrow("Hit Exception, When execute DefaultMessageConsumer.receiveMessage()", ex, logger);
		}
	}

	@Override
	public void receiveMessage(BehaviorPerformance message) {
		try {
			synchronized (this) {
				if(behaviorAndPerformanceService != null) {
					behaviorAndPerformanceService.merge(message);
					logger.debug("BehaviorAndPerformanceService merged behaviorPerformance in DefaultMessageConsumer...");
				} else {
					logger.debug("Cannot find behaviorAndPerformanceService, Please config...");
				}
			}
		} catch (Exception ex) {
			ThrowableHandler.handleThrow(
					"Hit Exception, When execute DefaultMessageConsumer.receiveMessage().", ex, logger);
		}	
	}
	
	@Override
	public void receiveMessage(final StoredLog message) {
		try {
			synchronized (this) {
				if(storedLogService != null) {
					storedLogService.merge(message);
					logger.debug("storedLogService merged storedLog in DefaultMessageConsumer...");
				} else {
					logger.debug("Cannot find storedLogService, Please config...");
				}
			}
		} catch (Exception ex) {
			ThrowableHandler.handleThrow(
					"Hit Exception, When execute DefaultMessageConsumer.receiveMessage().", ex, logger);
		}	
	}
	
	
	public void setStoredLogService(StoredLogService storedLogService) {
		this.storedLogService = storedLogService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setBehaviorAndPerformanceService(BehaviorPerformanceService behaviorAndPerformanceService) {
		this.behaviorAndPerformanceService = behaviorAndPerformanceService;
	}

	private transient final Logger logger = LoggerFactory.getLogger(DefaultMessageConsumer.class);
}
