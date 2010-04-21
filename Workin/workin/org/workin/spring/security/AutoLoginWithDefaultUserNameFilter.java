package org.workin.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class AutoLoginWithDefaultUserNameFilter {
	
	private UserDetailsService userDetailsService;

	private boolean enabled = false;

	private String defaultUserName;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (enabled && SpringSecurityUtils.getCurrentUser() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(defaultUserName);

			if (userDetails == null) {
				throw new RuntimeException(" DefaultUserName: " + defaultUserName + "Cannot be found!");
			}

			SpringSecurityUtils.saveUserDetailsToContext(userDetails, (HttpServletRequest) request);
		}

		chain.doFilter(request, response);
	}

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Required
	public void setDefaultUserName(String defaultUserName) {
		this.defaultUserName = defaultUserName;
	}
	
}
