package org.workin.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ReflectionUtils {
	
	private ReflectionUtils() {}
	
	/**
	 * 
	 * read object field value whether private or protected, no need use getter.
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 * 
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error(CANNOT_THROW_EXCEPTION, e.getMessage());
		}
		return result;
	}

	

	/**
	 * 
	 * write object field value whether private or protected,no need use setter.
	 * 
	 * @param object
	 * @param fieldName
	 * @param value
	 * 
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error(CANNOT_THROW_EXCEPTION, e.getMessage());
		}
	}

	/**
	 * 
	 * call object method whether private or protected.
	 * 
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 * 
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null)
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 *	
	 * cascaded type transform to up. get object's declaredField.
	 * 	if still can not find object, return null
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 * 
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object, OBJECT_CANNOT_BE_NULL);
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				; // Field not in current class. keep up object's type
			}
		}
		return null;
	}

	/**
	 * 
	 * force set field to accessible.  
	 * 
	 * @param field
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 
	 * cascaded type transform to up. get object's declaredField.
	 * 	if still can not find object, return null
	 * 
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(object, OBJECT_CANNOT_BE_NULL);

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Field not in current class. keep up object's type
			}
		}
		return null;
	}

	/**
	 * 
	 * eg:public UserDao extends HibernateDao<User>.
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 
	 * eg:public UserDao extends HibernateDao<User,Long>.
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}


	/**
	 * 
	 * @param collection
	 * @param propertyName
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 
	 * convert element property to string.
	 * 
	 * @param collection
	 * @param propertyName
	 * @param separator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 
	 * convert value.
	 * 
	 * @param value
	 * @param toType
	 * @return
	 */
	public static Object convertValue(Object value, Class<?> toType) {
		try {
			DateConverter dc = new DateConverter();
			dc.setUseLocaleFormat(true);
			dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
			ConvertUtils.register(dc, Date.class);
			
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param toType
	 * @return
	 * 
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}
	
	/**
	 * 
	 * transform checked exception to unchecked exception, when reflection.
	 * 
	 * @param e
	 * @return
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException)
			return new IllegalArgumentException("Reflection Exception.", e);
		else if (e instanceof InvocationTargetException)
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	private static final String CANNOT_THROW_EXCEPTION = "Can not throw exception{}";
	private static final String OBJECT_CANNOT_BE_NULL = "Object can not be null.";
	
	private static final transient Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
}
