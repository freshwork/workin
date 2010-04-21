package org.workin.test.security.jcaptcha;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.workin.fortest.util.WebTestUtils;
import org.workin.web.security.jcaptcha.JCaptchaFilter;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JCaptchaFilterTest extends Assert {

	private static String failUrl = "403.jsp";
	private static MockFilterConfig config = new MockFilterConfig();

	private MockFilterChain chain = new MockFilterChain();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();

	private JCaptchaFilter filter = new JCaptchaFilter();

	@BeforeClass
	public static void setUpBeforeClass() {
		MockServletContext context = (MockServletContext) config.getServletContext();
		WebTestUtils.initWebApplicationContext(context, "/security/applicationContext-security.xml");
		config.addInitParameter(JCaptchaFilter.PARAM_FAILURE_URL, failUrl);
	}

	@AfterClass
	public static void tearDownAfterClass() {
		MockServletContext context = (MockServletContext) config.getServletContext();
		WebTestUtils.closeWebApplicationContext(context);
	}

	@Test
	public void displayImage() throws ServletException, IOException {
		request.setServletPath("/img/capatcha.jpg");

		filter.init(config);
		filter.doFilter(request, response, chain);

		assertEquals(200, response.getStatus());
		assertEquals("image/jpeg", response.getContentType());
		assertEquals(true, response.getContentAsByteArray().length > 0);
	}

	@Test
	public void validateWithErrorCaptcha() throws ServletException, IOException {
		request.setServletPath(JCaptchaFilter.DEFAULT_FILTER_PROCESSES_URL);
		request.setParameter(JCaptchaFilter.PARAM_CAPTCHA_PARAMTER_NAME, "12345678ABCDEFG");

		filter.init(config);
		filter.doFilter(request, response, chain);

		assertEquals(failUrl, response.getRedirectedUrl());
	}

	@Test
	public void validateWithAutoPass() throws ServletException, IOException {
		String autoPassValue = "1234";
		config.addInitParameter(JCaptchaFilter.PARAM_AUTO_PASS_VALUE, autoPassValue);

		request.setServletPath(JCaptchaFilter.DEFAULT_FILTER_PROCESSES_URL);
		request.setParameter(JCaptchaFilter.DEFAULT_CAPTCHA_PARAMTER_NAME, autoPassValue);

		filter.init(config);
		filter.doFilter(request, response, chain);

		assertEquals(null, response.getRedirectedUrl());
	}
}
