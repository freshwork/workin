package org.workin.test.web.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.workin.fortest.BaseTestCase;
import org.workin.web.filter.UnitiveResponseHeaderFilter;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class UnitiveResponseHeaderFilterTest extends BaseTestCase {
	
	@Test public void test() throws IOException, ServletException {
		MockFilterConfig config = new MockFilterConfig();
		MockFilterChain chain = new MockFilterChain();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		config.addInitParameter("Goingmm", "2009");
		config.addInitParameter("G.Lee", "2012");
		
		UnitiveResponseHeaderFilter filter = new UnitiveResponseHeaderFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		
		assertEquals("2009",response.getHeader("Goingmm"));
		assertEquals("2012",response.getHeader("G.Lee"));
	}

}
