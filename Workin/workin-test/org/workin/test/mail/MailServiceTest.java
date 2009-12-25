package org.workin.test.mail;

import javax.annotation.Resource;

import org.junit.Test;

import org.workin.fortest.spring.SpringContextTestCase;
import org.workin.mail.MailService;

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
		mailService.sendMail("G.Lee");
	}
	
	@Test
	public void mimeMailServiceSendMailTest() {
		String[] send2s = {"junjie.li@elegoninfotech.com"};
		mimeMailService.sendMail("G.Lee", send2s);
	}
}
