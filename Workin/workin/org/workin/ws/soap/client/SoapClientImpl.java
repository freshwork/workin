package org.workin.ws.soap.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.workin.exception.ThrowableHandle;
import org.workin.util.Assert;
import org.workin.ws.constant.WSConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public class SoapClientImpl<T> extends SoapClientTemplet<T> {
	
	public SoapClientImpl(final Class<?> interfaceClazz) {
		this.serviceInterfaceClass = interfaceClazz;
	}

	@Override
	public T buildSoapService(String url) {
		JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();
		client.setAddress(url);
		client.setServiceClass(serviceInterfaceClass);

		return (T) client.create();
	}

	@Override
	public T buildSoapServiceWithCxf(String nameSpace, String serviceName, String url) {
		return buildSoapServiceWithCxf(nameSpace, serviceName, url, true);
	}
	
	@Override
	public T buildSoapServiceWithCxf(String serviceName, String url) {
		return buildSoapServiceWithCxf(WSConstants.NS, serviceName, url, true);
	}
	
	@Override
	public T buildSoapServiceWithCxf(String nameSpace, String serviceName, String url, boolean mtomEnabled) {
		String messageSuffix = "{} can not be null, when call SoapClientImpl.buildSoapServiceWithCxf().";
		Assert.notNull(nameSpace, messageSuffix.replace("{}", "nameSpace"));
		Assert.notNull(serviceName, messageSuffix.replace("{}", "serviceName"));
		Assert.notNull(url, messageSuffix.replace("{}", "url"));
		
		Service service =  null;
		
		try {
			URL wsdlURL = new URL(url);
			QName qName = new QName(nameSpace, serviceName);
			service = Service.create(wsdlURL, qName);
			
			if(service != null) {
				SOAPBinding binding = (SOAPBinding)((BindingProvider)(T) service.getPort(this.serviceInterfaceClass)).getBinding();
				binding.setMTOMEnabled(mtomEnabled);
				return (T) service.getPort(this.serviceInterfaceClass); 
			}
		} catch (Exception e) {
			ThrowableHandle.handle(e);
		}
		return null;
	}
}
