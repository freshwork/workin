package org.workin.test.mail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.fortest.spring.SpringContextTestCase;
import org.workin.mail.MailPackage;
import org.workin.mail.MailService;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class MailServiceTest extends SpringContextTestCase {
	
	@Autowired
	private MailService mimeMailService;
	
	public void setMimeMailService(MailService mimeMailService) {
		this.mimeMailService = mimeMailService;
	}

	@Test
	public void mimeMailServiceSendMailTest() {
		MailPackage mailPackage = new MailPackage("G.Lee");
		mimeMailService.sendMail(mailPackage);
	}
}
