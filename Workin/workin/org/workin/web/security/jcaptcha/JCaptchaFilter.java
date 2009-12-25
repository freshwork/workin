package org.workin.web.security.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.workin.util.StringUtils;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JCaptchaFilter implements Filter {

	public static final String CAPTCHA_PARAMTER_NAME_PARAM = "captchaParamterName";
	public static final String CAPTCHA_SERVICE_ID_PARAM = "captchaServiceId";
	public static final String FILTER_PROCESSES_URL_PARAM = "filterProcessesUrl";
	public static final String FAILURE_URL_PARAM = "failureUrl";
	public static final String AUTO_PASS_VALUE_PARAM = "autoPassValue";

	public static final String DEFAULT_FILTER_PROCESSES_URL = "/j_spring_security_check";
	public static final String DEFAULT_CAPTCHA_SERVICE_ID = "captchaService";
	public static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

	private static final Logger logger = LoggerFactory.getLogger(JCaptchaFilter.class);

	private String failureUrl;
	private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;
	private String captchaServiceId = DEFAULT_CAPTCHA_SERVICE_ID;
	private String captchaParamterName = DEFAULT_CAPTCHA_PARAMTER_NAME;
	private String autoPassValue;

	private CaptchaService captchaService;

	public void init(final FilterConfig fConfig) throws ServletException {
		initParameters(fConfig);
		initCaptchaService(fConfig);
	}

	private void initParameters(final FilterConfig fConfig) {
		if (StringUtils.isBlank(fConfig.getInitParameter(FAILURE_URL_PARAM)))
			throw new IllegalArgumentException("CaptchaFilter缺少failureUrl参数");

		failureUrl = fConfig.getInitParameter(FAILURE_URL_PARAM);

		if (StringUtils.isNotBlank(fConfig.getInitParameter(FILTER_PROCESSES_URL_PARAM))) {
			filterProcessesUrl = fConfig.getInitParameter(FILTER_PROCESSES_URL_PARAM);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(CAPTCHA_SERVICE_ID_PARAM))) {
			captchaServiceId = fConfig.getInitParameter(CAPTCHA_SERVICE_ID_PARAM);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(CAPTCHA_PARAMTER_NAME_PARAM))) {
			captchaParamterName = fConfig.getInitParameter(CAPTCHA_PARAMTER_NAME_PARAM);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(AUTO_PASS_VALUE_PARAM))) {
			autoPassValue = fConfig.getInitParameter(AUTO_PASS_VALUE_PARAM);
		}
	}

	
	private void initCaptchaService(final FilterConfig fConfig) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		captchaService = (CaptchaService) context.getBean(captchaServiceId);
	}

	public void destroy() {
	}

	public void doFilter(final ServletRequest theRequest, final ServletResponse theResponse, final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) theRequest;
		HttpServletResponse response = (HttpServletResponse) theResponse;
		String servletPath = request.getServletPath();

		if (StringUtils.startsWith(servletPath, filterProcessesUrl)) {
			boolean validated = validateCaptchaChallenge(request);
			if (validated) {
				chain.doFilter(request, response);
			} else {
				redirectFailureUrl(request, response);
			}
		} else {
			genernateCaptchaImage(request, response);
		}
	}


	private void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
			logger.error(e.getMessage(), e);
		} finally {
			out.close();
		}
	}

	private boolean validateCaptchaChallenge(final HttpServletRequest request) {
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(captchaParamterName);

			if (autoPassValue != null && autoPassValue.equals(challengeResponse))
				return true;
			return captchaService.validateResponseForID(captchaID, challengeResponse);
		} catch (CaptchaServiceException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	protected void redirectFailureUrl(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.sendRedirect(request.getContextPath() + failureUrl);
	}
}
