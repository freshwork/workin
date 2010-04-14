package org.workin.web.struts2.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.trace.domain.BehaviorPerformance;
import org.workin.trace.service.BehaviorPerformanceService;
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
	
	@Autowired
	BehaviorPerformanceService behaviorAndPerformanceService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		
		// AOP Before
		Date requestDateTime = DateUtils.currentDateTime();
		
		// execute action
		String result = invocation.invoke();
		
		// AOP After
		Date responseDateTime = DateUtils.currentDateTime();
		long spentTime = responseDateTime.getTime() - requestDateTime.getTime();
		
		// Save to DB
		BehaviorPerformance entity = new BehaviorPerformance();
		entity.setUserId(0123L);
		entity.setUserName("G.Lee");
		entity.setRequestIp(getRemoteIpAddress(request));
		entity.setRequestURI(request.getRequestURI());
		entity.setRequestDateTime(requestDateTime);
		entity.setResponseDateTime(responseDateTime);
		entity.setSpentTime(spentTime);
		
		behaviorAndPerformanceService.merge(entity);
		
		return result;
	}
	
	/**
	 * 
	 * Get Remote Ip Address
	 * @param request
	 * @return
	 * 
	 */
	public static final String getRemoteIpAddress(final HttpServletRequest request) {
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
