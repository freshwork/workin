package org.workin.ws.rest.client;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.exception.ThrowableHandler;
import org.workin.ws.constant.WSConstants;
import org.workin.xml.JaxbBinder;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class RestHttpClient<T> extends RestClientTemplet<T> {
	
	public RestHttpClient(final Class<T> clazz) {
		this.clientClass = clazz;
		if(binder == null)
			binder = new JaxbBinder(clazz);
	}
	
	/**
	 * 
	 * excute REST request and return Object<T>.
	 * 
	 * @param url
	 * @return
	 * 
	 */
	public T restReturnObject(final String url) {
		return binder.fromXml(restReturnXml(url));
	}
	
	/**
	 * 
	 * excute REST request and return xml.
	 * 
	 * @param url
	 * @return
	 * 
	 */
	public String restReturnXml(final String url) {
		
		String xml = null;
		HttpGet get = new HttpGet(url);
		HttpClient httpclient = new DefaultHttpClient();

		try {
			HttpResponse response = httpclient.execute(get);
			xml = buildXml(response);
		} catch (Exception e) {
			ThrowableHandler.handle(e);
		}
		
		logger.info(" excuteXMLRest xml: " + xml);
		return xml;
	}
	
	
	/**
	 * 
	 * excute REST request and return Response.
	 * 
	 * @param url
	 * @return
	 * 
	 */
	public T restResponseReturnObject(final String url) {
		return binder.fromXml(restResponseReturnXml(url));
	}
	
	/**
	 * 
	 * excute REST request and return xml.
	 * 
	 * @param url
	 * @return
	 */
	public String restResponseReturnXml(final String url) {
		
		String xml = null;
		HttpGet get = new HttpGet(url);
		HttpClient httpclient = new DefaultHttpClient();

		try {
			HttpResponse response = httpclient.execute(get);
			StatusLine statusLine = response.getStatusLine();
			
			if (statusLine.getStatusCode() == HttpServletResponse.SC_OK) {
				xml = buildXml(response);
			} else {
				logger.info("Response Status Code:" + statusLine.getStatusCode());
			}
		} catch (Exception e) {
			ThrowableHandler.handle(e);
		}
		
		logger.info(" excuteXMLResponseRest xml: " + xml);
		return xml;
	}
	
	/**
	 * 
	 * build xml with http response.
	 * 
	 * @param response
	 * @return
	 * 
	 */
	private String buildXml(final HttpResponse response) {
		
		StringBuilder xml = new StringBuilder();
		byte[] buffer = new byte[WSConstants.WS_BUFFER_SIZE];
		
		try {
			InputStream ins = response.getEntity().getContent();
			while (ins.read(buffer) != -1) {
				xml.append(new String(buffer, WSConstants.WS_CONTENT_ENCODING));
			}
		} catch (IllegalStateException e) {
			ThrowableHandler.handle(e);
		} catch (IOException e) {
			ThrowableHandler.handle(e);
		} 
		
		return xml.toString();
	}
	
	private static final transient Logger logger = LoggerFactory.getLogger(RestHttpClient.class);
}
