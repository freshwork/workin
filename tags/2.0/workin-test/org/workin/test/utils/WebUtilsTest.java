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

		//Set If-Modified-Since Header
		request.addHeader("If-Modified-Since", new Date().getTime());
		
		// Header file modification time than the time for small, unmodified file, return false.
		assertEquals(false, WebUtils.checkIfModifiedSince(request, response, (new Date().getTime() - 2000)));
		
		// Header file modification time than the time for large file has been modified to return true, need to transfer content.
		assertEquals(true, WebUtils.checkIfModifiedSince(request, response, (new Date().getTime() + 2000)));
	}

	@Test
	public void checkIfNoneMatch() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		// Not set Header, return true, need to transfer content.
		assertEquals(true, WebUtils.checkIfNoneMatchEtag(request, response, "V1.0"));

		// Set If-None-Match Header
		request.addHeader("If-None-Match", "V1.0,V1.1");
		// has Etag
		assertEquals(false, WebUtils.checkIfNoneMatchEtag(request, response, "V1.0"));
		// not has Etag
		assertEquals(true, WebUtils.checkIfNoneMatchEtag(request, response, "V2.0"));
	}
}
