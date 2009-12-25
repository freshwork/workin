package org.workin.mail.support;

import org.springframework.mail.SimpleMailMessage;
import org.workin.exception.ThrowableHandle;
import org.workin.mail.AbstractMailService;
import org.workin.util.Assert;
import org.workin.util.DateUtils;

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
	public void sendMail(final String userName) {
		this.sendMail(userName, this.mailTo);
	}
	
	@Override
	public void sendMail(final String userName, final String... send2s) {
		
		Assert.notNull(userName, "userName cannot null, when sendMail by SimpleMailService.");
		Assert.notEmpty(send2s, " send2s cannot empty! who is sent email.");
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(this.mailFrom);
		
		if(send2s.length > 1) {
			mailMessage.setBcc(send2s);
		} else {
			mailMessage.setTo(send2s);
		}
			
		mailMessage.setSubject(this.mailSubject);
		mailMessage.setSentDate(DateUtils.getNow());
		
		String textContent = String.format(textTemplate, userName, DateUtils.getNow());
		mailMessage.setText(textContent);
		
		try {
			mailSender.send(mailMessage);
			
			for(String sentMailto: send2s) {
				logger.info("Send mail with SimpleMailService from {} to {}.",this.mailFrom, sentMailto);
			}
		} catch (Exception e) {
			ThrowableHandle.handleThrow("Mail send fail...", e, logger);
		}
	}
}
