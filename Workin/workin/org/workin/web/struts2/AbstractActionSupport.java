package org.workin.web.struts2;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.trace.service.StoredLogService;
import org.workin.util.StringUtils;
import org.workin.web.constant.WebConstants;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings({"serial","unchecked"})
public abstract class AbstractActionSupport extends ActionSupport {
	
	@Autowired(required = false)
	protected StoredLogService storedLogService;
	
	public void setStoredLogService(StoredLogService storedLogService) {
		this.storedLogService = storedLogService;
	}
	
	/**
	 * 
	 * <li>
	 * 		Get 'request' object from ServletActionContext.
	 * </li> 
	 * 
	 *
	 *
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest(){
	    return ServletActionContext.getRequest();
	}
	
	/**
	 *
	 *	<li>
	 * 		Get 'session' object from ServletActionContext.
	 * 	</li> 
	 * 
	 * @return HttpSession
	 */
	protected HttpSession getSession(){
	    return ServletActionContext.getRequest().getSession();
	}
	
	
	/**
	 * 
	 * <li>
	 * 		1) Packaging request's getAttribute method.
	 * 		2) Don't need type codes "this.getRequest()" every invoke.
	 * </li>
	 * 
	 *
	 * @param attrKey
	 * @return  
	 *
	 * @return Serializable
	 */
	protected Serializable getAttribute(String attrKey) {
		return (Serializable)this.getRequest().getAttribute(attrKey);
	}
	
	/**
	 *	<li>
	 * 		Get 'ServletContext' object from ServletActionContext.
	 * 	</li> 
	 * 
	 * @return
	 * 
	 */
	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}
	
	
	/**
	 * 
	 * <li>
	 * 		1) Packaging request's setAttribute method.
	 * 		2) Don't need type codes "this.getRequest()" every invoke.
	 * </li>
	 * 
	 *
	 * @param attrKey
	 * @param attrValue  
	 *
	 * @return void
	 */
	protected void setAttribute(String attrKey, Serializable attrValue) {
		this.getRequest().setAttribute(attrKey, attrValue);
	}
	
	/**
	 * 
	 * 1) Packaging request's getParameter method.
	 * 2) Don't need type codes "this.getRequest()" every invoke.
	 * 
	 * @param parameterKey
	 * @return String
	 * 
	 */
	protected String getParameter(String parameterKey) {
		return this.getRequest().getParameter(parameterKey);
	}
	
	/**
	 * 
	 * Packaging request's getParameter method.
	 * 
	 * @return
	 */
	protected Map getParameterMap() {
		return this.getRequest().getParameterMap();
	}
	
	/**
	 * 
	 * <li>
	 * 		1) Packaging ActionSupport's addActionError method.
	 * 		2) Don't need type codes 'this.getText()'every invoke.
	 * </li>
	 * 
	 *
	 * @param errorMessageKey  
	 *
	 * @return void
	 */
	protected void addError(String errorMessageKey) {
		this.addActionError(this.getText(errorMessageKey));
	}
	
	
	/**
	 *
	 * <li>
	 * 	 	1) Packaging ActionSupport's addActionMessage method.
	 *  	2) Don't need type codes 'this.getText()' every invoke.
	 * </li>
	 *
	 * @param messageKey  
	 *
	 * @return void
	 * 
	 */
	protected void addMessage(String messageKey) {
		this.addActionMessage(this.getText(messageKey));
	}
	
	
	
	/**
	 * 
	 * Get page size from system default define.
	 * 
	 * @return
	 * 
	 */
	public int getPageSize() {
		return WebConstants.DEFAULT_PAGE_SIZE;
	}
	
	/**
	 * 
	 * Get Start Index.
	 * 
	 * @param page
	 * @return
	 * 
	 */
	public int getStartIndex(String displayTableId) {
		String pageIndexName = new ParamEncoder(displayTableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		int currentPageNo = StringUtils.isBlankOrNull(getParameter(pageIndexName)) ? 0 : (Integer.parseInt(getParameter(pageIndexName)) - 1);
		return currentPageNo * this.getPageSize();
	}
	
	/**
	 * 
	 * Defalut set Page ResultSize Attribute.
	 * 
	 * @param totalCount
	 * 
	 */
	public void setPageResultSizeAttribute(int totalCount) {
		this.setAttribute(PAGE_RESULT_SIZE, totalCount);
	}

	// for pagination key
	protected static final String PAGE_RESULT_SIZE = "resultSize";
	
	// for all sub action
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
