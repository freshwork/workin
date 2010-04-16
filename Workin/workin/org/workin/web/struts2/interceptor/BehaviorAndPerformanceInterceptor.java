package org.workin.web.struts2.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.producer.BehaviorAndPerformanceProducer;
import org.workin.util.DateUtils;
import org.workin.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class BehaviorAndPerformanceInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6462877210798477795L;

	@Autowired(required = true)
	BehaviorAndPerformanceProducer behaviorAndPerformanceProducer;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
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
		entity.setUserId(10086);
		entity.setUserName("Admin");
		entity.setRequestIp(getRemoteIpAddress(request));
		entity.setRequestURI(request.getRequestURI());
		entity.setRequestdttm(requestdttm);
		entity.setResponsedttm(responsedttm);
		entity.setSpentTime(spentTime);

		// Use JMS(ActiveMQ) send Queue, implement async store entity to db(BehaviorPerformance).
		behaviorAndPerformanceProducer.sendQueue(entity);

		return result;
	}

	/**
	 * 
	 * Get Remote Ip Address
	 * @param request
	 * @return
	 * 
	 */
	private static final String getRemoteIpAddress(final HttpServletRequest request) {
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

}
