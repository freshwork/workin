package org.workin.ws.soap.client;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface SoapClient<T> {
	
	/**
	 * 
	 * @param url
	 * @return
	 * 
	 */
	public T buildSoapService(final String url);
	
	/**
	 * 
	 * @param nameSpace
	 * @param serviceName
	 * @param url
	 * @return
	 * 
	 */
	public T buildSoapServiceWithCxf(final String nameSpace, final String serviceName, final String url);
	
	/**
	 * 
	 * @param serviceName
	 * @param url
	 * @return
	 * 
	 */
	public T buildSoapServiceWithCxf(final String serviceName, final String url);
	
	
	/**
	 * 
	 * @param nameSpace
	 * @param serviceName
	 * @param url
	 * @param mtomEnabled
	 * @return
	 * 
	 */
	public T buildSoapServiceWithCxf(final String nameSpace, final String serviceName, final String url, final boolean mtomEnabled);
	
}
