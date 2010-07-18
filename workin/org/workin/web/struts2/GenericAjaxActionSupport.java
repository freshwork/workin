package org.workin.web.struts2;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

/**
 * 
 * @author <a href=" mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@Namespace("/json")
@Result(type = "json", name = "success", params = { "ignoreHierarchy", "false", "excludeProperties","pageSize, errors, texts, locale" })
public abstract class GenericAjaxActionSupport<T> extends AbstractActionSupport {

	private static final long serialVersionUID = -3769638380826499761L;

	protected T jsonResult;

	@Override
	public String execute() {
		this.jsonResult = buildJsonResult();
		return SUCCESS;
	}

	public T getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(T jsonResult) {
		this.jsonResult = jsonResult;
	}

	/**
	 * 
	 * Build result List constants.
	 * 
	 * User must implements this abstract method.
	 * 
	 * @return result map
	 * 
	 */
	public abstract T buildJsonResult();
}
