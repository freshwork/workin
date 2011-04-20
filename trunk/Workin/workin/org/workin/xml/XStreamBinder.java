package org.workin.xml;

import java.util.List;

import org.workin.exception.NoImplementsException;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class XStreamBinder implements XmlBinder {

	private XStream xstream = new XStream();

	public XStream getXstream() {
		return xstream;
	}

	public XStreamBinder(Class<?>... types) {
		xstream.processAnnotations(types);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXml(String xml) {
		return (T) xstream.fromXML(xml);
	}

	@Override
	public String toXml(Object object) {
		return xstream.toXML(object);
	}

	@Override
	public String toXml(List root, String rootName) {
		throw new NoImplementsException(" No implements this method...");
	}

}
