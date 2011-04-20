package org.workin.xml;

import java.util.List;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public interface XmlBinder {

	public <T> T fromXml(String xml);

	public String toXml(Object root);

	public String toXml(List root, String rootName);
}
