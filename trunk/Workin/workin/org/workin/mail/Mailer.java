package org.workin.mail;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class Mailer implements Serializable {
	
	private static final long serialVersionUID = 2217633397753382169L;
	
	private String sayHelloTo;
	
	private String mailFrom;
	
	private String mailSubject;
	
	private List<String> mailTo;
	
	private List<String> mailCCTo;
	
	private List<String> mailBCCTo;
	
	public Mailer() {
	}
	
	public Mailer(String sayHelloTo) {
		this.sayHelloTo = sayHelloTo;
	}
	
	
	public Mailer(String sayHelloTo, List<String> mailTo) {
		this.sayHelloTo = sayHelloTo;
		this.mailTo = mailTo;
	}
	
	public Mailer(String sayHelloTo, String mailSubject, List<String> mailTo) {
		this.sayHelloTo = sayHelloTo;
		this.mailSubject = mailSubject;
		this.mailTo = mailTo;
	}
	
	public String getSayHelloTo() {
		return sayHelloTo;
	}

	public void setSayHelloTo(String sayHelloTo) {
		this.sayHelloTo = sayHelloTo;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
	public List<String> getMailTo() {
		return mailTo;
	}

	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}

	public List<String> getMailCCTo() {
		return mailCCTo;
	}

	public void setMailCCTo(List<String> mailCCTo) {
		this.mailCCTo = mailCCTo;
	}

	public List<String> getMailBCCTo() {
		return mailBCCTo;
	}

	public void setMailBCCTo(List<String> mailBCCTo) {
		this.mailBCCTo = mailBCCTo;
	}
}
