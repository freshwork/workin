package org.workin.trace.listener;

import javax.jms.MapMessage;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.workin.exception.ThrowableHandle;
import org.workin.jms.MessageListenerTemplate;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.producer.BehaviorAndPerformanceProducer.BPSavedWithKeys;
import org.workin.trace.service.BehaviorPerformanceService;
import org.workin.util.Assert;
import org.workin.util.DateUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class StoreBehaviorAndPerformanceMessageListener extends MessageListenerTemplate {

	@Override
	public void onMessage(Message message) {
		try {
			Assert.notNull(behaviorAndPerformanceService, "behaviorAndPerformanceService cannot be null!");
			
			MapMessage mapMessage = (MapMessage) message;
			BehaviorPerformance behaviorPerformance = new BehaviorPerformance();
			behaviorPerformance.setUserId(mapMessage.getLongProperty(BPSavedWithKeys.USERID.toString()));
			behaviorPerformance.setSpentTime(mapMessage.getLongProperty(BPSavedWithKeys.SPENTTIME.toString()));
			
			behaviorPerformance.setUserName(mapMessage.getStringProperty(BPSavedWithKeys.USERNAME.toString()));
			behaviorPerformance.setRequestIp(mapMessage.getStringProperty(BPSavedWithKeys.REQUESTIP.toString()));
			behaviorPerformance.setRequestURI(mapMessage.getStringProperty(BPSavedWithKeys.REQUESTURI.toString()));
			
			if(StringUtils.hasText(mapMessage.getStringProperty(BPSavedWithKeys.REQUESTDTTM.toString()))) {
				behaviorPerformance.setRequestdttm(DateUtils.stringToDate(mapMessage.getStringProperty(BPSavedWithKeys.REQUESTDTTM.toString())));	
			}
			
			if(StringUtils.hasText(mapMessage.getStringProperty(BPSavedWithKeys.RESPONSEDTTM.toString()))) {
				behaviorPerformance.setResponsedttm(DateUtils.stringToDate(mapMessage.getStringProperty(BPSavedWithKeys.RESPONSEDTTM.toString())));
			}
			
			behaviorAndPerformanceService.merge(behaviorPerformance);
			logger.info("BehaviorAndPerformanceService merged behaviorPerformance in StoreBehaviorAndPerformanceMessageListener...");
		} catch (Exception ex) {
			ThrowableHandle.handleThrow(
					"Hit Exception, When execute StoreBehaviorAndPerformanceMessageListener.onMessage().", ex, logger);
		}

	}

	@Autowired(required = true)
	BehaviorPerformanceService behaviorAndPerformanceService;
}
