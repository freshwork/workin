package org.workin.test.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.util.ReflectionUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class ReflectionUtilsTest extends BaseTestCase {
	

	@Test
	public void getFieldValue() {
		TestBean bean = new TestBean();
		assertEquals(1, ReflectionUtils.getFieldValue(bean, "privateField"));
		assertEquals(1, ReflectionUtils.getFieldValue(bean, "publicField"));
	}

	@Test
	public void setFieldValue() {
		TestBean bean = new TestBean();
		ReflectionUtils.setFieldValue(bean, "privateField", 2);
		assertEquals(2, bean.inspectPrivateField());

		ReflectionUtils.setFieldValue(bean, "publicField", 2);
		assertEquals(2, bean.inspectPublicField());
	}

	@Test
	public void invokeMethod() {
		TestBean bean = new TestBean();
		assertEquals("hello calvin", ReflectionUtils.invokeMethod(bean, "privateMethod", new Class[] { String.class },
				new Object[] { "calvin" }));
	}

	@Test
	public void getSuperClassGenricType() {
		assertEquals(String.class, ReflectionUtils.getSuperClassGenricType(TestBean.class));
		assertEquals(Long.class, ReflectionUtils.getSuperClassGenricType(TestBean.class, 1));
		assertEquals(Object.class, ReflectionUtils.getSuperClassGenricType(TestBean2.class));
		assertEquals(Object.class, ReflectionUtils.getSuperClassGenricType(TestBean3.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void convertElementPropertyToString() {
		List list = new ArrayList();
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		list.add(bean1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);
		list.add(bean2);

		assertEquals("1,2", ReflectionUtils.convertElementPropertyToString(list, "id", ","));
	}

	@Test
	public void convertReflectionExceptionToUnchecked() {
		IllegalArgumentException iae = new IllegalArgumentException();
		//ReflectionException,normal
		RuntimeException e = ReflectionUtils.convertReflectionExceptionToUnchecked(iae);
		assertEquals(iae, e.getCause());
		assertEquals("Reflection Exception.", e.getMessage());

		//InvocationTargetException,extract it's target exception.
		Exception ex = new Exception();
		e = ReflectionUtils.convertReflectionExceptionToUnchecked(new InvocationTargetException(ex));
		assertEquals(ex, e.getCause());
		assertEquals("Reflection Exception.", e.getMessage());

		//UncheckedException, ignore it.
		RuntimeException re = new RuntimeException("abc");
		e = ReflectionUtils.convertReflectionExceptionToUnchecked(re);
		assertEquals("abc", e.getMessage());

		//Unexcepted Checked exception.
		e = ReflectionUtils.convertReflectionExceptionToUnchecked(ex);
		assertEquals("Unexpected Checked Exception.", e.getMessage());

	}
	
	
	
	/**
	 * 
	 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
	 *
	 * @param <T>
	 * @param <PK>
	 */
	public static class ParentBean<T, PK> {
	}

	public static class TestBean extends ParentBean<String, Long> {
		private int privateField = 1;
		private int publicField = 1;

		public int getPublicField() {
			return publicField + 1;
		}

		public void setPublicField(int publicField) {
			this.publicField = publicField + 1;
		}

		public int inspectPrivateField() {
			return privateField;
		}

		public int inspectPublicField() {
			return publicField;
		}

		@SuppressWarnings("unused")
		private String privateMethod(String text) {
			return "hello " + text;
		}
	}

	@SuppressWarnings("unchecked")
	public static class TestBean2 extends ParentBean {
	}

	public static class TestBean3 {
		int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
	
}
