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
import org.workin.exception.NestedRuntimeException;
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
	
	// Use can switch interceptor on or off flag.
	private String onOff = CONSTANT_PARAM_ON;
	
	// User define this parameter, for ignore request URI in this intercept action.
	private Set<String> ignoreRequestURIs = Collections.emptySet();

	// User define this parameter, for allowed request URI in this intercept action.
	private Set<String> allowedRequestURIs = Collections.emptySet();


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);

		// Straightforward execute action, If user switch this flag to off.
		if (CONSTANT_PARAM_OFF.equalsIgnoreCase(onOff)) {
			return invocation.invoke();
		}

		String theRequestUri = request.getRequestURI();

		if (!ignoreRequestURIs.isEmpty() && !allowedRequestURIs.isEmpty()) {
			throw new NestedRuntimeException(
					"Hit exception in BehaviorAndPerformanceInterceptor, ignoreRequestURIs and allowedRequestURIs can not both be configured.");
		}

		if (!ignoreRequestURIs.isEmpty()) {
			if (matchIgnoreRequestURIs(ignoreRequestURIs, theRequestUri)) {
				logger.debug("{} be define in ignore request URIs. So don't trace it...", theRequestUri);
				return invocation.invoke();
			} else {
				return executeAopedAction(invocation);
			}
		} else if (!allowedRequestURIs.isEmpty()) {
			if (matchAllowedRequestURIs(allowedRequestURIs, theRequestUri)) {
				logger.debug("{} be define in allowed request URIs. So trace it...", theRequestUri);
				return executeAopedAction(invocation);
			} else {
				return invocation.invoke();
			}
		} else {
			return executeAopedAction(invocation);
		}
	}

	/**
	 * 
	 * Aoped Action, it's means insert some event before and after this action.
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 * 
	 */
	private final String executeAopedAction(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);

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
		entity.setUserId(getUserId());
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
	 * Is match allowed request URIs?.
	 * 
	 * @param extensionCollection
	 * @param requestURI
	 * @return
	 * 
	 */
	private static final boolean matchAllowedRequestURIs(Collection<String> extensionCollection, String requestURI) {
		if (!StringUtils.hasText(requestURI)) {
			return false;
		}

		for (String extension : extensionCollection) {
			logger.debug("In struts2 config file, define allowed request URI: {}", extension);
			if (requestURI.startsWith(extension)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * Is match ignore request URIs?.
	 * 
	 * @param extensionCollection
	 * @param requestURI
	 * @return
	 * 
	 */
	private static final boolean matchIgnoreRequestURIs(Collection<String> extensionCollection, String requestURI) {
		if (!StringUtils.hasText(requestURI)) {
			return true;
		}

		for (String extension : extensionCollection) {
			logger.debug("In struts2 config file, define ignore request URI: {}", extension);
			if (requestURI.startsWith(extension)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 
	 * If you need get user id form other way,u need Override this method only.
	 * 
	 * @return
	 */
	protected long getUserId() {
		return 0L;
	}
	
	/**
	 * 
	 * If you need get user name form other way,u need Override this method only.
	 * 
	 * Get application user name from spring security
	 * 
	 * @return
	 * 
	 */
	protected String getUserName() {
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
	protected String getRemoteIpAddress(final HttpServletRequest request) {
		String remoteIp = SpringSecurityUtils.getCurrentUserIp(); 
		
		if(StringUtils.hasText(remoteIp)) {
			return remoteIp;
		}
		
		remoteIp = request.getHeader("x-forwarded-for");
		
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

	public void setAllowedRequestURIs(String allowedRequestURIs) {
		this.allowedRequestURIs = TextParseUtil.commaDelimitedStringToSet(allowedRequestURIs);
	}

	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}

	private static final String CONSTANT_PARAM_ON = "on";

	private static final String CONSTANT_PARAM_OFF = "off";

	private transient static final Logger logger = LoggerFactory.getLogger(BehaviorAndPerformanceInterceptor.class);
}
