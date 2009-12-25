package org.workin.test.utils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
import java.util.Date;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.workin.fortest.BaseTestCase;
import org.workin.util.WebUtils;

public class WebUtilsTest extends BaseTestCase {

	@Test
	public void checkIfModified() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		assertEquals(true, WebUtils.checkIfModifiedSince(request, response, (new Date().getTime() - 2000)));

		request.addHeader("If-Modified-Since", new Date().getTime());
		assertEquals(false, WebUtils.checkIfModifiedSince(request, response, (new Date().getTime() - 2000)));
		assertEquals(true, WebUtils.checkIfModifiedSince(request, response, (new Date().getTime() + 2000)));
	}
	
	@Test
	public void checkAccetptGzip() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		request.addHeader("Accept-Encoding", "gzip,deflate");
		assertTrue(WebUtils.checkAccetptGzip(request));

		request = new MockHttpServletRequest();
		request.addHeader("Accept-Encoding", "deflate");
		assertFalse(WebUtils.checkAccetptGzip(request));

		request = new MockHttpServletRequest();
		assertFalse(WebUtils.checkAccetptGzip(request));
	}
}
