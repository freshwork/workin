package org.workin.core.context;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ApplicationContextUtil {
	
	public static ExtenalContext getThreadContext() {
		return new ThreadLocalContext();
	}
	
}
