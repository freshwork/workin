package org.workin.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
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
 * eg.
 * 
 * Web.xml
 * 
 * 	<filter>
 * 		<filter-name>responseHeaderCacheControlFilter</filter-name>
 * 		<filter-class>org.workin.web.filter.ResponseHeaderCacheControlFilter</filter-class>
 * 		<init-param>
 * 			<param-name>expiresSeconds</param-name>
 * 			<param-value>3000000</param-value>
 * 		</init-param>
 * 	</filter>
 * 
 * 	<filter-mapping>
 * 		<filter-name>responseHeaderCacheControlFilter</filter-name>
 * 		<url-pattern>/images/*</url-pattern>
 * 	</filter-mapping>
 * 	
 * 	<filter-mapping>
 * 		<filter-name>responseHeaderCacheControlFilter</filter-name>
 * 		<url-pattern>/css/*</url-pattern>
 * 	</filter-mapping>
 * 	
 * 	<filter-mapping>
 * 		<filter-name>responseHeaderCacheControlFilter</filter-name>
 * 		<url-pattern>/js/*</url-pattern>
 * 	</filter-mapping>
 * 
 * @see org.springside.examples.showcase.web.CacheControlHeaderFilter
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ResponseHeaderCacheControlFilter implements Filter {

	private long expiresSeconds;

	@Override
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
	@Override
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
	@Override
	public void init(FilterConfig filterConfig) {
		String expiresSecondsParam = filterConfig.getInitParameter(WebConstants.PARAM_EXPIRES_SECOND);
		if (expiresSecondsParam != null) {
			expiresSeconds = Long.valueOf(expiresSecondsParam);
		} else {
			expiresSeconds = WebUtils.ONE_YEAR_SECONDS;
		}
	}
}
