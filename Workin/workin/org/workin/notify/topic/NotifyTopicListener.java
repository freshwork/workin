package org.workin.notify.topic;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.workin.exception.ThrowableHandle;
import org.workin.jms.topic.TopicListenerTemplate;
import org.workin.notify.Notification;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NotifyTopicListener extends TopicListenerTemplate {

	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage = (MapMessage) message;
			
			String name = mapMessage.getString(CONSTANT_NAME);
			String email = mapMessage.getString(CONSTANT_EMAIL);
			if(StringUtils.hasText(name) && StringUtils.hasText(email))
				notification.sendNotification(name, email);

		} catch (JMSException e) {
			ThrowableHandle.handle(e);
		}
	}
	
	@Autowired(required = true)
	Notification notification;
	
	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	protected transient final Logger logger = LoggerFactory.getLogger(NotifyTopicListener.class);
}
