package org.workin.test.xml;

import org.junit.BeforeClass;
import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.test.xml.xstream.Role;
import org.workin.test.xml.xstream.User;
import org.workin.xml.XStreamBinder;
import org.workin.xml.XmlBinder;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class XmlBinderTest extends BaseTestCase {
	
	private static XmlBinder binder;
	
	@BeforeClass
	public static void setUp() {
		binder = new XStreamBinder(User.class, Role.class);
	}
	
	@Test
	public void binderObject2Xml() {
		User user = new User();
		
		user.setId(Long.valueOf(123));
		user.setName("G.Lee");
		user.setPassword("a12345");
		user.setEmail("goingmm@gmail.com");
		
		user.getRoles().add(new Role(1L, "Admin"));
		user.getRoles().add(new Role(2L, "User"));
		
		user.getInterests().add("girl");
		user.getInterests().add("football");

		user.getHouses().put("CD", "Hourse-CD");
		user.getHouses().put("NC", "Hourse-NC");

		logger.info(" bind with binderObject2Xml: {}", binder.toXml(user));
	}

	@Test
	public void binderXml2Object() {
		
		String xml = "<user id=\"123\"><name>G.Lee</name><email>goingmm@gmail.com</email><roles>"
			+ "<role name=\"Admin\" id=\"1\"/><role name=\"User\" id=\"1\"/></roles>"
			+ "<interest>girl</interest><interest>football</interest><houses>"
			+ "<item key=\"CD\"><value>Hourse-CD</value></item><item key=\"NC\">"
			+ "<value>Hourse-NC</value></item></houses></user>";
		
		logger.info(" bind with binderXml2Object: {}", binder.fromXml(xml));
	}
}
