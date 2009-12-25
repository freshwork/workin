package org.workin.test.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.workin.exception.ThrowableHandle;
import org.workin.fortest.BaseTestCase;
import org.workin.spring.SpringContextHolder;
import org.workin.util.ReflectionUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class SpringContextHolderTest extends BaseTestCase {

	@Test
	public void testSpringContextHolderGetBean() {

		try {
			ReflectionUtils.setFieldValue(new SpringContextHolder(), "applicationContext", null);
			SpringContextHolder.getBean("springContextHolder");
			
			fail("No exception throw for applicationContxt hadn't been init.");
		} catch (IllegalStateException e) {
			ThrowableHandle.handle("Hit IllegalStateException,when SpringContextHolder.getBean()", e, logger);
		}

		new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		assertNotNull(SpringContextHolder.getApplicationContext());
		assertNotNull(SpringContextHolder.getBean("springContextHolder"));
		assertNotNull(SpringContextHolder.getBean(SpringContextHolder.class));
	}
}
