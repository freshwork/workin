package org.workin.test.web.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.workin.fortest.BaseTestCase;
import org.workin.web.filter.ResponseHeaderCacheControlFilter;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ResponseHeaderCacheControlFilterTest extends BaseTestCase {
	
	@Test
	public void filterTest() throws IOException, ServletException {
		MockFilterConfig config = new MockFilterConfig();
		MockFilterChain chain = new MockFilterChain();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		config.addInitParameter("expiresSeconds", "1000");

		ResponseHeaderCacheControlFilter filter = new ResponseHeaderCacheControlFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);

		assertEquals("private, max-age=1000", response.getHeader("Cache-Control"));
	}

}
