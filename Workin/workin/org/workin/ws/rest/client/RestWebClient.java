package org.workin.ws.rest.client;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.util.StringUtils;
import org.workin.util.Assert;
import org.workin.ws.constant.WSConstants;
import org.workin.xml.JaxbBinder;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class RestWebClient<T> extends RestClientTemplet<T> {

	
	public RestWebClient(final Class<T> clazz) {
		this.clientClass = clazz;
		if(binder == null)
			binder = new JaxbBinder(clazz);
	}
	
	/**
	 * 
	 * excute REST request with WebClient, return xml String .
	 * 
	 * @param url
	 * @return
	 * 
	 */
	public String restReturnXml(final String url) {
		return restReturnXml(url, null);
	}
	
	/**
	 * 
	 * excute REST request with WebClient, return xml String .
	 * 
	 * @param url
	 * @param mediaType
	 * @return
	 * 
	 */
	public String restReturnXml(final String url, String mediaType) {
		return binder.toXml(restReturnObject(url, mediaType));
	}
	
	/**
	 * 
	 * excute REST request with WebClient, return object<T> .
	 * 
	 * @param url
	 * @return
	 * 
	 */
	public T restReturnObject(final String url) {
		return this.restReturnObject(url, null);
	}
	
	
	/**
	 * 
	 * excute REST request with WebClient, return object<T> .
	 * 
	 * @param url
	 * @param mediaType
	 * @return
	 * 
	 */
	public T restReturnObject(final String url, String mediaType) {
		
		String messageSuffix = "{} can not be null, when call RestWebClient.restReturnObject().";
		
		Assert.notNull(clientClass, messageSuffix.replace("{}", "clazz"));
		Assert.notNull(url, messageSuffix.replace("{}", "url"));
		
		return getWebClient(url, mediaType).get(clientClass);
	}
	
	/**
	 * 
	 * excute REST request with WebClient, return xml String .
	 * 
	 * @param url
	 * @param subPath
	 * @return
	 */
	public String restReturnXmlWithSubPath(final String url, final String subPath) {
		return restReturnXmlWithSubPath(url, subPath, null);
	}
	
	
	/**
	 * 
	 * excute REST request with WebClient, return xml String .
	 * 
	 * @param url
	 * @param subPath
	 * @param mediaType
	 * @return
	 * 
	 */
	public String restReturnXmlWithSubPath(final String url, final String subPath, String mediaType) {
		return binder.toXml(restReturnObjectWithSubPath(url, subPath, mediaType));
	}
	
	/**
	 * 
	 * excute REST request with WebClient, return object<T> .
	 * 
	 * @param url
	 * @param subPath
	 * @return
	 * 
	 */
	public T restReturnObjectWithSubPath(final String url, final String subPath) {
		return restReturnObjectWithSubPath(url, subPath, null);
	}
	
	/**
	 * 
	 * excute REST request with WebClient, return object<T> .
	 *  
	 * @param url
	 * @param subPath
	 * @param mediaType
	 * @return
	 * 
	 */
	public T restReturnObjectWithSubPath(final String url, final String subPath, String mediaType) {
		
		String messageSuffix = "{} can not be null, when call RestWebClient.restReturnObjectWithSubPath().";
		
		Assert.notNull(clientClass, messageSuffix.replace("{}", "clazz"));
		Assert.notNull(url, messageSuffix.replace("{}", "url"));
		Assert.notNull(subPath, messageSuffix.replace("{}", "subPath"));
		
		return getWebClient(url, subPath, mediaType).get(clientClass);
	}

	
	/**
	 * 
	 * get WebClient with url and mediaType.
	 * 
	 * @param url
	 * @param mediaType
	 * @return
	 * 
	 */
	private WebClient getWebClient(final String url, String mediaType) {
		return getWebClient(url, null, mediaType);
	}
	
	/**
	 * 
	 * get WebClient with url, subPath and mediaType.
	 * 
	 * @param url
	 * @param subPath
	 * @param mediaType
	 * @return
	 * 
	 */
	private WebClient getWebClient(final String url, final String subPath, String mediaType) {
		mediaType = mediaType == null ? WSConstants.DEFULT_MEDIA_TYPE : mediaType;
		WebClient client = WebClient.create(url);
		client.accept(mediaType);
		if(StringUtils.hasText(subPath))
			client.path(subPath);
		
		return client;
	}
}
