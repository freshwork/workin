package org.workin.mail.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.workin.exception.ThrowableHandle;
import org.workin.mail.AbstractMailService;
import org.workin.mail.MailPackage;
import org.workin.mail.constant.MailConstants;
import org.workin.util.Assert;
import org.workin.util.CollectionUtils;
import org.workin.util.DateUtils;
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
	public void sendMail(final MailPackage mailPackage) {
		Assert.notNull(mailPackage, "mailPackage" + MESSAGE_SENTMAIL_WHEN_NULL);

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, encoding);

			String iSayHello = StringUtils.hasText(this.sayHelloTo) ? this.sayHelloTo : mailPackage.getSayHelloTo();
			Assert.hasText(iSayHello, "iSayHello" + MESSAGE_SENTMAIL_WHEN_NULL);

			String iMailFrom = StringUtils.hasText(this.mailFrom) ? this.mailFrom : mailPackage.getMailFrom();
			Assert.hasText(iMailFrom, "iMailFrom" + MESSAGE_SENTMAIL_WHEN_NULL);

			String iMailSubject = StringUtils.hasText(this.mailSubject) ? this.mailSubject : mailPackage.getMailSubject();
			Assert.hasText(iMailSubject, "iMailSubject" + MESSAGE_SENTMAIL_WHEN_NULL);

			List<String> iMailTo = CollectionUtils.isEmpty(this.mailTo) ? mailPackage.getMailTo() : this.mailTo;
			Assert.notEmpty(iMailTo, "iMailTo" + MESSAGE_SENTMAIL_WHEN_EMPTY);

			helper.setFrom(iMailFrom);
			helper.setSubject(iMailSubject);

			helper.setTo(iMailTo.toArray((new String[0])));

			if (!CollectionUtils.isEmpty(mailPackage.getMailCCTo())) {
				helper.setCc(mailPackage.getMailCCTo().toArray(new String[0]));
			}

			if (!CollectionUtils.isEmpty(mailPackage.getMailBCCTo())) {
				helper.setBcc(mailPackage.getMailBCCTo().toArray(new String[0]));
			}

			helper.setSentDate(DateUtils.currentDateTime());

			buildContent(helper, iSayHello);
			buildAttachment(helper);

			mailSender.send(mimeMessage);

			for (String sentMailTo : iMailTo) {
				logger.info("Send mail with MimeMailService from {} to {}.", iMailFrom, sentMailTo);
			}
		} catch (MessagingException e) {
			ThrowableHandle.handleThrow("Hit MessagingException, When execute MimeMailService.sendMail()", e, logger);
		} catch (Exception ex) {
			ThrowableHandle.handleThrow("Hit Exception, When execute MimeMailService.sendMail()", ex, logger);
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
	private void buildContent(MimeMessageHelper helper, String sayHelloTo) throws MessagingException {

		try {
			Map context = new HashMap();
			context.put(USER_NAME, sayHelloTo);

			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
			helper.setText(content, true);

		} catch (IOException e) {
			ThrowableHandle.handleThrow("Build mail content fail... FreeMarker template cannot found.", e, logger);
		} catch (TemplateException ex) {
			ThrowableHandle.handleThrow("Build mail content fail... FreeMarker template Error.", ex, logger);
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
		} catch (IOException ex) {
			ThrowableHandle
					.handleThrow("Build mail attachment failling...Attachment file cannot be found.", ex, logger);
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
		this.encoding = StringUtils.hasText(encoding) ? encoding : MailConstants.MAIL_CONTENT_ENCODING;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	private static final String USER_NAME = "userName";

	private static final String MESSAGE_SENTMAIL_WHEN_NULL = "cannot be null, When send mail with MimeMailService.";
	private static final String MESSAGE_SENTMAIL_WHEN_EMPTY = "cannot be empty, When send mail with MimeMailService.";
}
