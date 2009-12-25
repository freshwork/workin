package org.workin.spring.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.workin.util.StringUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public class DefinitionSourceFactoryBean implements FactoryBean {
	
	
	@Override
	public Object getObject() throws Exception {
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> requestMap = buildRequestMap();
		UrlMatcher matcher = getUrlMatcher();
		DefaultFilterInvocationDefinitionSource definitionSource = new DefaultFilterInvocationDefinitionSource(matcher, requestMap);
		return definitionSource;
	}

	
	@Override
	public Class getObjectType() {
		return FilterInvocationDefinitionSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	protected UrlMatcher getUrlMatcher() {
		return new AntUrlPathMatcher();
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected LinkedHashMap<RequestKey, ConfigAttributeDefinition> buildRequestMap() throws Exception {
		LinkedHashMap<String, String> srcMap = resourceDetailsService.getRequestMap();
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> distMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
		
		ConfigAttributeEditor editor = new ConfigAttributeEditor();

		for (Map.Entry<String, String> entry : srcMap.entrySet()) {
			RequestKey key = new RequestKey(entry.getKey(), null);
			if (StringUtils.isNotBlank(entry.getValue())) {
				editor.setAsText(entry.getValue());
				distMap.put(key, (ConfigAttributeDefinition) editor.getValue());
			} else {
				distMap.put(key, ConfigAttributeDefinition.NO_ATTRIBUTES);
			}
		}

		return distMap;
	}
	
	private ResourceDetailsService resourceDetailsService;

	public void setResourceDetailsService(ResourceDetailsService resourceDetailsService) {
		this.resourceDetailsService = resourceDetailsService;
	}
}
