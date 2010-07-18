package org.workin.mail;

import java.util.List;

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
	
	protected String sayHelloTo;
	
	protected String mailFrom;
	
	protected String mailSubject;
	
	protected List<String> mailTo;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setSayHelloTo(String sayHelloTo) {
		this.sayHelloTo = sayHelloTo;
	}


	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}


	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}


	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
