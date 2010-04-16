package org.workin.test.mail;

import javax.annotation.Resource;

import org.junit.Test;

import org.workin.fortest.spring.SpringContextTestCase;
import org.workin.mail.MailService;
import org.workin.mail.Mailer;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class MailServiceTest extends SpringContextTestCase {
	
	@Resource
	private MailService mailService;
	
	@Resource
	private MailService mimeMailService;
	
	@Test
	public void simpleMailServiceSendMailTest() {
		Mailer mailer = new Mailer("G.Lee");
		mailService.sendMail(mailer);
	}
	
	@Test
	public void mimeMailServiceSendMailTest() {
		Mailer mailer = new Mailer("G.Lee");
		mimeMailService.sendMail(mailer);
	}
}
