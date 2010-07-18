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
import org.workin.core.persistence.support.PaginationSupport;
import org.workin.exception.ThrowableHandler;
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
		return PaginationSupport.PAGESIZE;
	}
	
	/**
	 * 
	 * Get start index, Using request's parameter('pageNo').
	 * 
	 * @return
	 * 
	 */
	public int getStartIndexUsingKey() {
		return getStartIndexWithPageNo(getPageNoWithParameterKey(WebConstants.RES_PARAM_PAGE_NUMBER));
	}
	
	/**
	 * 
	 * Get start index, Using request's parameter('parameterKey').
	 * 
	 * @param parameterKey
	 * @return
	 * 
	 * 
	 */
	public int getStartIndexUsingKey(final String parameterKey) {
		return getStartIndexWithPageNo(getPageNoWithParameterKey(parameterKey));
	}
	
	/**
	 * 
	 * Get start index, Using default displaytag Id.
	 * 
	 * @return
	 * 
	 */
	public int getStartIndexUsingId() {
		return getStartIndexWithPageNo(getPageNoWithDisplayTagId(WebConstants.DISPLAYTABLE_ID));
	}
	
	/**
	 * 
	 * Get start index, Using displaytag Id.
	 * 
	 * @param displayTagId
	 * @return
	 * 
	 */
	public int getStartIndexUsingId(final String displayTagId) {
		return getStartIndexWithPageNo(getPageNoWithDisplayTagId(displayTagId));
	}
	
	/**
	 * 
	 * Defalut set Page ResultSize Attribute.
	 * 
	 * @param totalCount
	 * 
	 */
	public void setPageResultSizeAttribute(int totalCount) {
		this.setAttribute(WebConstants.PAGE_RESULT_SIZE, totalCount);
	}
	
	/**
	 * 
	 * Calculate Start Index With Page number.
	 * 
	 * @param pageNo
	 * @return
	 * 
	 */
	private int getStartIndexWithPageNo(int pageNo) {
		return (pageNo > 0)? (pageNo-1) * this.getPageSize() : 0;
	}
	
	/**
	 * 
	 * @param parameterKey
	 * @return
	 * 
	 */
	private int getPageNoWithParameterKey(final String parameterKey) {
		int pageNumber = 0;
		String parameterPageNumber = this.getParameter(parameterKey);
		
		if(parameterPageNumber != null){
			try {
				pageNumber = Integer.parseInt(parameterPageNumber);	
			} catch (Exception ex) {
				ThrowableHandler.handle("Paramber PageNo invalid!", ex, logger);
			}
		}
		
		return pageNumber;
	}
	
	/**
	 * 
	 * @param displayTagId
	 * @return
	 * 
	 */
	private int getPageNoWithDisplayTagId(final String displayTagId) {
		String pageIndexName = new ParamEncoder(displayTagId).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		return StringUtils.isBlankOrNull(getParameter(pageIndexName)) ? 0 : Integer.parseInt(getParameter(pageIndexName));
	}
	
	// for all sub action
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
