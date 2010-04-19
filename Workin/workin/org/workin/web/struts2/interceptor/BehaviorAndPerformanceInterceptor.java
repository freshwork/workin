package org.workin.web.struts2.interceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.jms.producer.DefaultMessageProducer;
import org.workin.spring.security.SpringSecurityUtils;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.util.DateUtils;
import org.workin.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class BehaviorAndPerformanceInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6462877210798477795L;

	@Autowired(required = true)
	private DefaultMessageProducer defaultMessageProducer;
	
	// Defind by User, for ignore request URI in this intercept action.
	private Set<String> ignoreRequestURIs = Collections.emptySet();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		
		if (!ignoreRequestURIs.isEmpty() && hasIgnoreRequestURI(ignoreRequestURIs, request.getRequestURI())) {
			logger.debug("{} be defind in ignore request URI set. So don't trace it...", request.getRequestURI());
			return invocation.invoke();
		}
		
		// AOP Before - obtain request datetime.
		Date requestdttm = DateUtils.currentDateTime();

		// Handle and execute action logic.
		String result = invocation.invoke();

		// AOP After - obtain response datetime.
		Date responsedttm = DateUtils.currentDateTime();

		// At last calculate spent time between request and response.
		long spentTime = responsedttm.getTime() - requestdttm.getTime();

		// Store entity to db(BehaviorPerformance).
		BehaviorPerformance entity = new BehaviorPerformance();
		entity.setUserName(getUserName());
		entity.setRequestIp(getRemoteIpAddress(request));
		entity.setRequestURI(request.getRequestURI());
		entity.setRequestdttm(requestdttm);
		entity.setResponsedttm(responsedttm);
		entity.setSpentTime(spentTime);

		// Use JMS(ActiveMQ) send Queue, implement async store entity to db(BehaviorPerformance).
		defaultMessageProducer.sendQueue(entity);

		return result;
	}
	
	/**
	 * 
	 * User will defind ignore request URI.
	 * 
	 * @param extensionCollection
	 * @param requestURI
	 * @return
	 * 
	 */
	private static final boolean hasIgnoreRequestURI(Collection<String> extensionCollection, String requestURI) {
		if (!StringUtils.hasText(requestURI)) {
			return true;
		}
		
		for (String extension : extensionCollection) {
			logger.debug("In struts2 config file, defind ignore request URI: {}", extension);
			if (requestURI.startsWith(requestURI)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 
	 * if you need get user form other way,u need Override this method only.
	 * 
	 * Get application user from spring security
	 * 
	 * @return
	 * 
	 */
	protected static final String getUserName() {
		return SpringSecurityUtils.getCurrentUserName();
	}

	/**
	 * 
	 * If you need get IP address form other way,u need Override this method only.
	 * 
	 * Get remote Ip address
	 * 
	 * @param request
	 * @return
	 * 
	 */
	protected static final String getRemoteIpAddress(final HttpServletRequest request) {
		String remoteIp = request.getHeader("x-forwarded-for");

		if (!StringUtils.hasText(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("Proxy-Client-IP");
		}
		if (!StringUtils.hasText(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!StringUtils.hasText(remoteIp) || "unknown".equalsIgnoreCase(remoteIp)) {
			remoteIp = request.getRemoteAddr();
		}
		return remoteIp;
	}

	public void setDefaultMessageProducer(DefaultMessageProducer defaultMessageProducer) {
		this.defaultMessageProducer = defaultMessageProducer;
	}

	public void setIgnoreRequestURIs(String stringOfIgnoreRequestURIs) {
		this.ignoreRequestURIs = TextParseUtil.commaDelimitedStringToSet(stringOfIgnoreRequestURIs);
	}

	private transient static final Logger logger = LoggerFactory.getLogger(BehaviorAndPerformanceInterceptor.class);
}
