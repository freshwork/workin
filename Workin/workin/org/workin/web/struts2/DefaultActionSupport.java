package org.workin.web.struts2;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.util.StringUtils;
import org.workin.web.constant.WebConstants;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings({"serial","unchecked"})
public abstract class DefaultActionSupport extends ActionSupport {
	
	/**
	 * 
	 * <li>
	 * 		Get 'request' object from servlet's ActionContext.
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
	 *<li>
	 * 		Get 'session' object from 's ActionContext.
	 * </li> 
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
	
	
	public String getSaveToPath() {
		return saveToPath;
	}

	public void setSaveToPath(String saveToPath) {
		this.saveToPath = saveToPath;
	}
	
	/**
	 * 
	 * @param fileFileName
	 * @return
	 * 
	 */
	protected File getFileWithUploadFileName (String fileFileName) {
		
		// write the file to the file specified
		File root = new File(getResourcesPath());

		if (!root.exists()) {
			try {
				root.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new File(root, fileFileName);
	}
	
	
	/**
	 * 
	 * Get Resources path with fileFileName.
	 * 
	 * 
	 * @return resources path
	 * 
	 */
	protected String getResourcesPath() {
		
		StringBuilder resourcesPath;
		StringBuilder paramPath = new StringBuilder(File.separator);
		
		if (StringUtils.hasText(saveToPath)) {
			paramPath.append(this.getSaveToPath());
		} else {
			paramPath.append(WebConstants.FILE_UPLOAD_FOLDER);
		}
		
		resourcesPath = new StringBuilder(ServletActionContext.getServletContext().getRealPath(String.valueOf(paramPath))); 
		
		resourcesPath.append(File.separator);
		resourcesPath.append(this.getRequest().getRemoteUser());
		resourcesPath.append(File.separator);
		
		logger.debug(" show paramPath, when getResourcesPath : {}.", resourcesPath);
		
		return String.valueOf(resourcesPath);
	}
	
	// path, where is file to save
	private String saveToPath;
	
	// for pagination key
	protected static final String PAGE_RESULT_SIZE = "resultSize";
	
	// for all sub action
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
}
