package org.workin.fortest.spring;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class MockWebUtils {

	private static boolean struts2Inited = false;

	/**
	 * 
	 * Init Spring WebApplicationContext in ServletContext.
	 * 
	 * @param servletContext
	 * @param configLocations
	 * 
	 */
	public static void initWebApplicationContext(MockServletContext servletContext, String... configLocations) {
		String configLocationsString = StringUtils.join(configLocations, ",");
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocationsString);
		new ContextLoader().initWebApplicationContext(servletContext);
	}
	
	/**
	 * 
	 * Init Spring WebApplicationContext in ServletContext.
	 * 
	 * @param servletContext
	 * @param applicationContext
	 * 
	 */
	public static void initWebApplicationContext(MockServletContext servletContext,
			ApplicationContext applicationContext) {
		ConfigurableWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setParent(applicationContext);
		wac.setServletContext(servletContext);
		wac.setConfigLocation("");
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		wac.refresh();
	}
	
	public static void closeWebApplicationContext(ServletContext servletContext) {
		new ContextLoader().closeWebApplicationContext(servletContext);
	}

	public static void setRequestToStruts2(HttpServletRequest request) {
		initStruts2();
		ServletActionContext.setRequest(request);
	}

	public static void setResponseToStruts2(HttpServletResponse response) {
		initStruts2();
		ServletActionContext.setResponse(response);
	}

	@SuppressWarnings("unchecked")
	private static void initStruts2() {
		if (!struts2Inited) {
			ActionContext.setContext(new ActionContext(new HashMap()));
			struts2Inited = true;
		}
	}
}
