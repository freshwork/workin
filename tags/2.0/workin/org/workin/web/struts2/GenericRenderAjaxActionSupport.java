package org.workin.web.struts2;

import java.util.Collections;
import java.util.Map;

import org.workin.web.constant.WebConstants;
import org.workin.web.struts2.util.Struts2Utils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class GenericRenderAjaxActionSupport extends AbstractActionSupport {

	private static final long serialVersionUID = -5277680641056848282L;

	@Override
	public String execute() {
		String callbackName = this.getParameter(WebConstants.PARAM_REQUEST_CALLBACK);

		Map<String, String> resultMap = Collections.emptyMap();

		resultMap = buildResult();

		Struts2Utils.renderJsonp(callbackName, resultMap);
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
