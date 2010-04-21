package org.workin.fortest.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.workin.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;

public class WebTestUtils {

	private static boolean struts2ContextInited = false;

	/**
	 * 
	 * 
	 * @param configLocations application context.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext, String... configLocations) {
		String configLocationsString = StringUtils.join(configLocations, ",");
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocationsString);
		new ContextLoader().initWebApplicationContext(servletContext);
	}

	/**
	 * ServletContext init Spring WebApplicationContext.
	 * 
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

	/**
	 *  Close ServletContext Spring WebApplicationContext.
	 */
	public static void closeWebApplicationContext(MockServletContext servletContext) {
		new ContextLoader().closeWebApplicationContext(servletContext);
	}

	/**
	 * 
	 * @param request
	 */
	public static void setRequestToStruts2(HttpServletRequest request) {
		initStruts2ActionContext();
		ServletActionContext.setRequest(request);
	}

	/**
	 * 
	 * @param response
	 */
	public static void setResponseToStruts2(HttpServletResponse response) {
		initStruts2ActionContext();
		ServletActionContext.setResponse(response);
	}


	@SuppressWarnings("unchecked")
	private static void initStruts2ActionContext() {
		if (!struts2ContextInited) {
			ActionContext.setContext(new ActionContext(new HashMap()));
			struts2ContextInited = true;
		}
	}
}
