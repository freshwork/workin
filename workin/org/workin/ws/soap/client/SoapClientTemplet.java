package org.workin.ws.soap.client;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class SoapClientTemplet<T> implements SoapClient<T> {
	
	protected Class<?> serviceInterfaceClass;
	
	public Class<?> getServiceInterfaceClass() {
		return serviceInterfaceClass;
	}

	public void setServiceInterfaceClass(Class<?> serviceInterfaceClass) {
		this.serviceInterfaceClass = serviceInterfaceClass;
	}
}
