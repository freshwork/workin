package org.workin.spring.security;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class SpringSecurityUtils {
	
	private SpringSecurityUtils() {
		
	}
	
	/**
	 * 
	 * get spring's user.
	 * @return
	 * 
	 */
	public static String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null)
			return "";

		return auth.getName();
	}
	
	/**
	 * 
	 * get spring's user or user's subclass instance.
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends User> T getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null)
			return null;
		return (T) principal;
	}
	
}
