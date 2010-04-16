package org.workin.mail.support;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.workin.exception.ThrowableHandle;
import org.workin.mail.AbstractMailService;
import org.workin.mail.MailPackage;
import org.workin.util.Assert;
import org.workin.util.CollectionUtils;
import org.workin.util.DateUtils;
import org.workin.util.StringUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class SimpleMailService extends AbstractMailService {

	private String textTemplate;

	public void setTextTemplate(String textTemplate) {
		this.textTemplate = textTemplate;
	}

	@Override
	public void sendMail(final MailPackage mailPackage) {
		Assert.notNull(mailPackage, "mailPackage" + MESSAGE_SENTMAIL_WHEN_NULL);

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		String iSayHello = StringUtils.hasText(this.sayHelloTo) ? this.sayHelloTo : mailPackage.getSayHelloTo();
		Assert.hasText(iSayHello, "iSayHello" + MESSAGE_SENTMAIL_WHEN_NULL);

		String iMailFrom = StringUtils.hasText(this.mailFrom) ? this.mailFrom : mailPackage.getMailFrom();
		Assert.hasText(iMailFrom, "iMailFrom" + MESSAGE_SENTMAIL_WHEN_NULL);

		String iMailSubject = StringUtils.hasText(this.mailSubject) ? this.mailSubject : mailPackage.getMailSubject();
		Assert.hasText(iMailSubject, "iMailSubject" + MESSAGE_SENTMAIL_WHEN_NULL);

		List<String> iMailTo = CollectionUtils.isEmpty(this.mailTo) ? mailPackage.getMailTo() : this.mailTo;
		Assert.notEmpty(iMailTo, "iMailTo" + MESSAGE_SENTMAIL_WHEN_EMPTY);

		mailMessage.setFrom(iMailFrom);
		mailMessage.setSubject(iMailSubject);
		mailMessage.setTo(iMailTo.toArray((new String[0])));
		
		if (!CollectionUtils.isEmpty(mailPackage.getMailCCTo())) {
			mailMessage.setCc(mailPackage.getMailCCTo().toArray(new String[0]));
		}

		if (!CollectionUtils.isEmpty(mailPackage.getMailBCCTo())) {
			mailMessage.setBcc(mailPackage.getMailBCCTo().toArray(new String[0]));
		}
		mailMessage.setSentDate(DateUtils.currentDateTime());

		String textContent = String.format(textTemplate, iSayHello, mailMessage.getSentDate());
		mailMessage.setText(textContent);

		try {
			mailSender.send(mailMessage);

			for (String sentMailTo : iMailTo) {
				logger.info("Send mail with SimpleMailService from {} to {}.", iMailFrom, sentMailTo);
			}
		} catch (Exception ex) {
			ThrowableHandle.handleThrow("Hit Exception, When execute SimpleMailService.sendMail()", ex, logger);
		}

	}
	
	private static final String MESSAGE_SENTMAIL_WHEN_NULL = "cannot be null, When send mail with SimpleMailService.";
	private static final String MESSAGE_SENTMAIL_WHEN_EMPTY = "cannot be empty, When send mail with SimpleMailService.";
}
