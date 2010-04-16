package org.workin.notify.listener;

import java.util.ArrayList;
import java.util.List;

import javax.jms.MapMessage;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.workin.exception.ThrowableHandle;
import org.workin.jms.MessageListenerTemplate;
import org.workin.mail.MailService;
import org.workin.mail.Mailer;
import org.workin.notify.producer.NotifyMessageProducer.MailNotifierField;
import org.workin.util.Assert;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class NotifyMessageListener extends MessageListenerTemplate {
	
	
	@Override
	public void onMessage(Message message) {
		
		try {
			Assert.notNull(mailService, "mailService cannot be null!");
			
			MapMessage mapMessage = (MapMessage) message;
			
			String name = mapMessage.getString(MailNotifierField.Name.getValue());
			String email = mapMessage.getString(MailNotifierField.Email.getValue());
			
			if(StringUtils.hasText(name) && StringUtils.hasText(email)) {
					List<String> mailTo = new ArrayList<String>();
					mailTo.add(email);
					
					Mailer mailer = new Mailer(name, mailTo);
					mailService.sendMail(mailer);
					logger.info("MailService sent mail in NotifyMessageListener...");
			}
		} catch (Exception ex) {
			ThrowableHandle.handleThrow("Hit Exception, When execute NotifyTopicListener.onMessage(Message message).", ex, logger);
		}
	}
	
	@Autowired(required=true)
	private MailService mailService;
}
