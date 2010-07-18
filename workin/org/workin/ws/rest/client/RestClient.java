package org.workin.ws.rest.client;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface RestClient<T> {
	
	/**
	 * 
	 * excute REST request return Xml file.
	 * 
	 * @param url
	 * @return
	 * 
	 */
	public String restReturnXml(final String url);
	
	
	/**
	 * 
	 * excute REST request return Object<T>.
	 *  
	 * @param url
	 * @return
	 * 
	 */
	public T restReturnObject(final String url);
	
}
