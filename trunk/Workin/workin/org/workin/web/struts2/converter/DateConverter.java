package org.workin.web.struts2.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.util.DateUtils;
import org.workin.util.StringUtils;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

/**
 * 
 * @author <a href="mailto:angellin.yao@elegoninfotech.com">angellin.yao</a>
 * 
 */
@SuppressWarnings("unchecked")
public class DateConverter extends DefaultTypeConverter {

	private static final transient Logger logger = LoggerFactory.getLogger(DateConverter.class);

	private static final String DATE_PATTERN = "dd-MMM-yyyy";

	private static final String DATE_PATTERN_CN = "yyyy-mm-dd";

	private static final String DATE_PATTERN_SLASH = "yyyy/mm/dd";

	/**
	 * 
	 * Convert value between types.
	 * 
	 */
	@Override
	public Object convertValue(Map ognlContext, Object value, Class toType) {
		Object result = null;
		if (Date.class == toType) {
			result = doConvertToDate(value);
		} else if (String.class == toType) {
			if (value instanceof Date)
				result = DateUtils.dateToString((Date) value, DATE_PATTERN);
		}
		return result;
	}

	/**
	 * Convert String to Date.
	 * 
	 * @param value
	 * @return
	 */
	private Date doConvertToDate(Object value) {
		Date result = null;

		if (value instanceof String) {

			try {
				result = DateUtils.parseDate((String) value, new String[] { DATE_PATTERN, DATE_PATTERN_CN,
						DATE_PATTERN_SLASH });
			} catch (ParseException ex) {
				ex.printStackTrace();
				logger.error(" Parse date with PATTERN hit error!" + ex.getMessage());
			}

			// all patterns failed, try a milliseconds constructor
			if (result == null && !StringUtils.isBlankOrNull((String) value)) {
				try {
					result = new Date(Long.valueOf((String) value));
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(" Converting from milliseconds to Date fails!" + ex.getMessage());
				}
			}

		} else if (value instanceof Object[]) {

			Object[] array = (Object[]) value;

			if ((array != null) && (array.length >= 1)) {
				// fix the bug that exception occur while array is empty
				value = array[0];
				if (!StringUtils.isBlankOrNull(value.toString())) {
					result = doConvertToDate(array[0]);
				}
			}

		} else if (Date.class.isAssignableFrom(value.getClass())) {

			result = (Date) value;

		}

		return result;
	}
}
