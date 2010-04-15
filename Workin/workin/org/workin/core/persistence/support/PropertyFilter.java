package org.workin.core.persistence.support;

import java.util.Date;

import org.workin.util.Assert;
import org.workin.util.ReflectionUtils;
import org.workin.util.StringUtils;


/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class PropertyFilter {

	public static final String OR_SEPARATOR = "_OR_";

	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE;
	}
	
	public enum LikeMatchPatten {
		P, S, ALL;
	}
	
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private String[] propertyNames = null;

	private Class<?> propertyType = null;

	private Object propertyValue = null;

	private MatchType matchType = null;
	
	private LikeMatchPatten likeMatchPatten = null;
	
	public PropertyFilter() {
	}

	/**
	 * 
	 * @param filterName
	 * @param value
	 * 
	 */
	public PropertyFilter(final String filterName, final String value) {

		String matchTypeStr;
		String matchPattenCode = LikeMatchPatten.ALL.toString();
		String matchTypeCode;
		String propertyTypeCode;
		
		if(filterName.contains("LIKE") && filterName.charAt(0) != 'L') {
			matchTypeStr = StringUtils.substringBefore(filterName, "_");
			matchPattenCode = StringUtils.substring(matchTypeStr, 0, 1);
			matchTypeCode = StringUtils.substring(matchTypeStr, 1, matchTypeStr.length() - 1);
			propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
			
		} else {
			matchTypeStr = StringUtils.substringBefore(filterName, "_");
			matchTypeCode = StringUtils.substring(matchTypeStr, 0, matchTypeStr.length() - 1);
			propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
		}
		
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
			likeMatchPatten = Enum.valueOf(LikeMatchPatten.class, matchPattenCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter name: " + filterName
					+ "Not prepared in accordance with rules, not get more types of property.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter name: " + filterName
					+ "Not prepared in accordance with the rules, attribute value types can not be.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		propertyNames = org.apache.commons.lang.StringUtils.split(propertyNameStr, PropertyFilter.OR_SEPARATOR);
		
		Assert.isTrue(propertyNames.length > 0, "filter name: " + filterName
				+ "Not prepared in accordance with the rules, property names can not be.");

		this.propertyValue = ReflectionUtils.convertStringToObject(value, propertyType);
	}
	
	public boolean isMultiProperty() {
		return (propertyNames.length > 1);
	}

	public String[] getPropertyNames() {
		return propertyNames;
	}

	public String getPropertyName() {
		if (propertyNames.length > 1) {
			throw new IllegalArgumentException("There are not only one property");
		}
		return propertyNames[0];
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public LikeMatchPatten getLikeMatchPatten() {
		return likeMatchPatten;
	}
}
