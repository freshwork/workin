package org.workin.spring.security;

import java.util.LinkedHashMap;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface ResourceDetailsService {
	
	public LinkedHashMap<String, String> getRequestMap() throws Exception;
	
}
