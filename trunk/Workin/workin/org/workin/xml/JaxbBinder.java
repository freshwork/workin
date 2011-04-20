package org.workin.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JaxbBinder implements XmlBinder {

	private Marshaller marshaller;

	private Unmarshaller unmarshaller;

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	/**
	 * 
	 * @param types
	 */
	public JaxbBinder(Class<?>... types) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(types);
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml) {
		try {
			xml = (xml == null) ? "" : xml;
			StringReader reader = new StringReader(xml.trim());
			return (T) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toXml(Object root) {
		try {
			StringWriter writer = new StringWriter();
			marshaller.marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toXml(List root, String rootName) {
		try {
			ListWrapper wrapper = new ListWrapper();
			wrapper.list = root;

			JAXBElement<ListWrapper> wrapperElement = new JAXBElement<ListWrapper>(new QName(rootName),
					ListWrapper.class, wrapper);

			StringWriter writer = new StringWriter();
			marshaller.marshal(wrapperElement, writer);

			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static class ListWrapper {
		@XmlAnyElement
		Collection list;
	}
}
