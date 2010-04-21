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
import org.workin.exception.ThrowableHandle;
import org.workin.util.StringUtils;
import org.workin.util.WebUtils;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JCaptchaFilter implements Filter {


	// Define in web.xml 
	public static final String PARAM_CAPTCHA_PARAMTER_NAME = "captchaParamterName";
	public static final String PARAM_CAPTCHA_SERVICE_ID = "captchaServiceId";
	public static final String PARAM_FILTER_PROCESSES_URL = "filterProcessesUrl";
	public static final String PARAM_FAILURE_URL = "failureUrl";
	public static final String PARAM_AUTO_PASS_VALUE = "autoPassValue";

	// Default value define
	public static final String DEFAULT_FILTER_PROCESSES_URL = "/j_spring_security_check";
	public static final String DEFAULT_CAPTCHA_SERVICE_ID = "captchaService";
	public static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

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
		if (StringUtils.isBlank(fConfig.getInitParameter(PARAM_FAILURE_URL)))
			throw new IllegalArgumentException("CaptchaFilter missing failureUrl parameter.");

		failureUrl = fConfig.getInitParameter(PARAM_FAILURE_URL);

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_FILTER_PROCESSES_URL))) {
			filterProcessesUrl = fConfig.getInitParameter(PARAM_FILTER_PROCESSES_URL);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_CAPTCHA_SERVICE_ID))) {
			captchaServiceId = fConfig.getInitParameter(PARAM_CAPTCHA_SERVICE_ID);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_CAPTCHA_PARAMTER_NAME))) {
			captchaParamterName = fConfig.getInitParameter(PARAM_CAPTCHA_PARAMTER_NAME);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_AUTO_PASS_VALUE))) {
			autoPassValue = fConfig.getInitParameter(PARAM_AUTO_PASS_VALUE);
		}
	}

	/**
	 * 
	 * Get CaptchaService instance from ApplicationContext.
	 * 
	 * @param fConfig
	 */
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
		
		// To verify compliance filterProcessesUrl process the request, verify the remaining image to generate the request.
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

	/**
	 * 
	 * Generated verification code image.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * 
	 */
	private void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {

		WebUtils.setNoCacheHeader(response);
		response.setContentType("image/jpeg");

		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
			ThrowableHandle.handle(e, logger);
		} finally {
			out.close();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * 
	 */
	private boolean validateCaptchaChallenge(final HttpServletRequest request) {
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(captchaParamterName);

			if (autoPassValue != null && autoPassValue.equals(challengeResponse)) {
				return true;
			}
			return captchaService.validateResponseForID(captchaID, challengeResponse);
		} catch (CaptchaServiceException e) {
			ThrowableHandle.handle(e, logger);
			return false;
		}
	}

	protected void redirectFailureUrl(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.sendRedirect(request.getContextPath() + failureUrl);
	}
	
	private transient static final Logger logger = LoggerFactory.getLogger(JCaptchaFilter.class);
}
