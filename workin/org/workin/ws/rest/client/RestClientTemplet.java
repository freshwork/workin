package org.workin.ws.rest.client;

import org.workin.xml.XmlBinder;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public abstract class RestClientTemplet<T> implements RestClient<T> {
	
	// for bind xml <--> object
	protected XmlBinder binder;
	
	// for client class set up
	protected Class<T> clientClass;

	public XmlBinder getBinder() {
		return binder;
	}

	public void setBinder(XmlBinder binder) {
		this.binder = binder;
	}

	public Class<T> getClientClass() {
		return clientClass;
	}

	public void setClientClass(Class<T> clientClass) {
		this.clientClass = clientClass;
	}
}
