package org.workin.ws.rest.engine;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class RestEngine {

	private static boolean serverStarted = false;

	public void start(final Class clazz, final String url) {

		synchronized (RestEngine.class) {
			try {
				if (!serverStarted) {
					JAXRSServerFactoryBean serverFactoryBean = new JAXRSServerFactoryBean();

					serverFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
					serverFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());

					serverFactoryBean.setResourceClasses(clazz);
					serverFactoryBean.setResourceProvider(clazz, new SingletonResourceProvider(clazz.newInstance()));

					serverFactoryBean.setAddress(url);
					serverFactoryBean.create();
					serverStarted = true;
					logger.info("REST Server Start...");
				} else {
					logger.info("REST Server Started...");
				}
			} catch (Exception e) {
				logger.error(" Hit exception, REST Server can not be start...");
			}
		}
	}

	static class RestEngineHolder {
		static RestEngine instance = new RestEngine();
	}

	public static RestEngine getInstance() {
		return RestEngineHolder.instance;
	}

	public boolean isServerStarted() {
		return serverStarted;
	}

	private static final transient Logger logger = LoggerFactory.getLogger(RestEngine.class);
}
