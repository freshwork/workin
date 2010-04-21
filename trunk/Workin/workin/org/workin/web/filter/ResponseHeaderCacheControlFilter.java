package org.workin.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.workin.util.WebUtils;
import org.workin.web.constant.WebConstants;

/**
 * 
 * 
 * For Response set Expires... Header
 * 
 * eg.web.xml
 * 
 * 	<filter>
 * 		<filter-name>cacheControlHeaderFilter</filter-name>
 * 		<filter-class>org.workin.web.filter.ResponseHeaderCacheControlFilter</filter-class>
 * 		<init-param>
 * 			<param-name>expiresSeconds</param-name>
 * 			<param-value>100000</param-value>
 * 		</init-param>
 * 	</filter>
 * 	<filter-mapping>
 * 		<filter-name>cacheControlHeaderFilter</filter-name>
 * 		<url-pattern>/img/*</url-pattern>
 * 	</filter-mapping>
 * 
 * 
 *
 */
public class ResponseHeaderCacheControlFilter {

	private long expiresSeconds;
	
	public void destroy() {
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		WebUtils.setExpiresHeader((HttpServletResponse) response, expiresSeconds);
		chain.doFilter(request, response);
	}

	/**
	 * 
	 * @param filterConfig
	 * 
	 */
	public void init(FilterConfig filterConfig) {
		String expiresSecondsParam = filterConfig.getInitParameter(WebConstants.PARAM_EXPIRES_SECOND);
		if (expiresSecondsParam != null) {
			expiresSeconds = Long.valueOf(expiresSecondsParam);
		} else {
			expiresSeconds = WebUtils.ONE_YEAR_SECONDS;
		}
	}
}
