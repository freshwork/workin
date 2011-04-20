package org.workin.web.struts2.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 
 * @author <a href="mailto:angellin.yao@elegoninfotech.com">angellin.yao</a>
 * 
 */

public class DoubleConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (Double.class == toClass) {
			String doubleStr = values[0];
			return Double.parseDouble(doubleStr);
		}
		return 0;
	}

	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}

}
