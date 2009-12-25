package org.workin.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.workin.mail.MailService;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class MailNotificationHolder implements Notification {

	private MailService mailService;

	@Override
	public void sendNotification(String name, String email) {
		if (mailService != null && StringUtils.hasText(name) && StringUtils.hasText(email)) {
			mailService.sendMail(name, email);
		} else {
			logger.warn("MailService, JMS User Name or Email can not be found...");
		}
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	protected transient final Logger logger = LoggerFactory.getLogger(MailNotificationHolder.class);
}
