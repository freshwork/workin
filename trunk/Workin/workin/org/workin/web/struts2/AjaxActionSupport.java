package org.workin.web.struts2;

import java.util.Collections;
import java.util.Map;

import org.workin.web.constant.WebConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class AjaxActionSupport extends DefaultActionSupport {
	
	private static final long serialVersionUID = -5277680641056848282L;
	
	
	@Override
	public String execute() {
		String callbackName = this.getParameter(WebConstants.PARAM_REQUEST_CALLBACK);
		
		Map<String, String> map = Collections.emptyMap();
		
		map = buildResult();
		
		Struts2Utils.renderJsonp(callbackName, map);
		return null;	
	}
	
	/**
	 * 
	 * Build request map constants.
	 * 
	 * User must implements this abstract method.
	 * 
	 * @return result map
	 * 
	 */
	public abstract Map<String, String> buildResult();
}
