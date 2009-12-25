package org.workin.mail.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.workin.exception.ThrowableHandle;
import org.workin.mail.AbstractMailService;
import org.workin.mail.constant.MailConstants;
import org.workin.util.Assert;
import org.workin.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class MimeMailService extends AbstractMailService {
	
	
	@Autowired
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		template = freemarkerConfiguration.getTemplate(templateName, encoding);
	}
	
	@Override
	public void sendMail(final String userName) {
		this.sendMail(userName, this.mailTo);
	}
	
	@Override
	public void sendMail(final String userName, final String... send2s) {
		
		Assert.notNull(userName, "userName cannot null, when sendMail by SimpleMailService.");
		Assert.notEmpty(send2s, " send2s cannot empty! who is sent email.");
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, encoding);
			
			if(send2s.length > 1) {
				helper.setBcc(send2s);
			} else {
				helper.setTo(send2s);
			}
			
			helper.setFrom(this.mailFrom);
			helper.setSubject(this.mailSubject);

			buildContent(helper, userName);
			buildAttachment(helper);

			mailSender.send(mimeMessage);
			
			for(String sentMailto: send2s) {
				logger.info("Send mail with MimeMailService from {} to {}.",this.mailFrom, sentMailto);
			}
		} catch (MessagingException e) {
			ThrowableHandle.handleThrow("Build mail failing.", e, logger);
		} catch (Exception e) {
			ThrowableHandle.handleThrow("Send mail failing.", e, logger);
		}
	}
	
	
	/**
	 * 
	 * @param  helper
	 * @param  userName
	 * @throws MessagingException
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void buildContent(MimeMessageHelper helper, String userName) throws MessagingException {

		try {
			Map context = new HashMap();
			context.put(USER_NAME, userName);
			
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
			helper.setText(content, true);
			
		} catch (IOException e) {
			ThrowableHandle.handleThrow("Build mail content fail... FreeMarker template cannot found.", e, logger);
		} catch (TemplateException e) {
			ThrowableHandle.handleThrow("Build mail content fail... FreeMarker template Error.", e, logger);
		}
	}
	
	/**
	 * 
	 * @param  helper
	 * @throws MessagingException
	 * 
	 */
	private void buildAttachment(MimeMessageHelper helper) throws MessagingException {
		try {
			ClassPathResource attachment = new ClassPathResource(attachmentPath);
			helper.addAttachment(attachmentName, attachment.getFile());
		} catch (IOException e) {
			ThrowableHandle.handleThrow("Build mail attachment failling...Attachment file cannot found.", e, logger);
		}
	}
	
	// mail content template with freemarker
	private Template template;
	
	// template name conf
	private String templateName;
	
	// attachment name conf
	private String attachmentName;
	
	// attachment path conf
	private String attachmentPath;
	
	// mail use encoding conf
	private String encoding;
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = StringUtils.hasText(encoding)? encoding : MailConstants.MAIL_CONTENT_ENCODING;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	
	private static final String USER_NAME = "userName";
	
}
