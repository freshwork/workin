package org.workin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.workin.core.persistence.support.PropertyFilter;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class PersistenceUtils {
	
	private PersistenceUtils() {
	}
	
	/**
	 * 
	 * @see #buildPropertyFilters(HttpServletRequest, String)
	 * 
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request) {
		return buildPropertyFilters(request, "filter_");
	}

	/**
	 * 
	 * eg.
	 * filter_EQS_name
	 * filter_LIKES_name_OR_email
	 * 
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request, final String filterPrefix) {
		
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		Map<String, String> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix);

		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isNotBlank(value)) {
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}
		return filterList;
	}
}
