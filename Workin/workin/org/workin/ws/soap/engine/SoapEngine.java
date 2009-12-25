package org.workin.ws.soap.engine;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class SoapEngine {
	
	private static boolean serverStarted = false;

	@SuppressWarnings("unchecked")
	public void start(final Class clazz, final String url) {
		
		synchronized(SoapEngine.class) {
			try {
				if(!serverStarted) {
					JaxWsServerFactoryBean soapFactoryBean = new JaxWsServerFactoryBean();
					
					soapFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
					soapFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
					
					soapFactoryBean.setServiceClass(clazz);
					soapFactoryBean.setAddress(url);
					soapFactoryBean.create();
					
					serverStarted = true;
					logger.info("SOAP Server Start...");	
				} else {
					logger.info("SOAP Server Started...");	
				}
			} catch (Exception e) {
				logger.error(" Hit exception, SOAP Server can not be start...");
			}
		}
	}
	
	protected void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	static class SoapEngineHolder {
		static SoapEngine instance = new SoapEngine();
	}

	public static SoapEngine getInstance() {
		return SoapEngineHolder.instance;
	}
	
	public boolean isServerStarted() {
		return serverStarted;
	}	
	
	private static final transient Logger logger = LoggerFactory.getLogger(SoapEngine.class);
}
