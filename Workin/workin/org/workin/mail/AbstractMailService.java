package org.workin.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class AbstractMailService implements MailService {
	
	@Autowired
	protected JavaMailSender mailSender;
	
	protected String mailFrom;

	protected String mailTo;
	
	protected String mailSubject;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
