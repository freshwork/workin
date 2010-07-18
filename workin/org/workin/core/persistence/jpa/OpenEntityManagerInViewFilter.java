package org.workin.core.persistence.jpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.workin.util.StringUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 * 
 */

public class OpenEntityManagerInViewFilter extends org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter {
	
	private static final String EXCLUDE_SUFFIXS_NAME = "excludeSuffixs";
	private static final String[] DEFAULT_EXCLUDE_SUFFIXS = { ".js", ".css", ".jpg", ".gif" };
	private static final String[] DEFAULT_INCLUDE_SUFFIXS = { ".action", ".do" };

	private static String[] excludeSuffixs = null;
	
	
	@Override
	protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();

		for (String suffix : DEFAULT_INCLUDE_SUFFIXS) {
			if (path.endsWith(suffix))
				return false;
		}

		for (String suffix : excludeSuffixs) {
			if (path.endsWith(suffix))
				return true;
		}

		return false;
	}

	/**
	 * 
	 * init excludeSuffixs.
	 * 
	 */
	@Override
	protected void initFilterBean() throws ServletException {

		String excludeSuffixStr = getFilterConfig().getInitParameter(EXCLUDE_SUFFIXS_NAME);

		if (!StringUtils.isBlankOrNull(excludeSuffixStr)) {
			excludeSuffixs = excludeSuffixStr.split(",");

			for (int i = 0; i < excludeSuffixs.length; i++) {
				excludeSuffixs[i] = "." + excludeSuffixs[i];
			}
		} else {
			excludeSuffixs = DEFAULT_EXCLUDE_SUFFIXS;
		}
	}
}
