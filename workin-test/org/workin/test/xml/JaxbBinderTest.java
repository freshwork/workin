package org.workin.test.xml;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.BeforeClass;
import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.test.xml.jaxb.Role;
import org.workin.test.xml.jaxb.User;
import org.workin.xml.JaxbBinder;
import org.workin.xml.XmlBinder;
import org.workin.xml.JaxbBinder.ListWrapper;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class JaxbBinderTest extends BaseTestCase {
	
	private static XmlBinder binder;
	
	@BeforeClass
	public static void setUp() {
		binder = new JaxbBinder(User.class, ListWrapper.class);
	}
	
	@Test
	public void bindObject2Xml() throws DocumentException {
		User user = new User();
		user.setId(Long.valueOf(123));
		user.setName("G.Lee");
		user.setPassword("a12345");

		user.getInterests().add("girl");
		user.getInterests().add("football");

		user.getHouses().put("CD", "Hourse-CD");
		user.getHouses().put("NC", "Hourse-NC");

		user.getRoles().add(new Role(Long.valueOf(1), "Admin"));
		user.getRoles().add(new Role(Long.valueOf(2), "User"));
		
		String xml = binder.toXml(user);
		logger.info(" bind with bindObject2Xml: {}", xml);
		assertXmlByDom4j(xml);
	}

	@Test
	public void bindXml2Object() {
		
		String xml = generateXmlByDom4j();
		
		User user = binder.fromXml(xml);

		logger.info("Jaxb Xml to Object result:\n" + user);

		assertEquals(Long.valueOf(123), user.getId());
		assertEquals(2, user.getRoles().size());
		assertEquals("Admin", user.getRoles().get(0).getName());

		assertEquals(2, user.getInterests().size());
		assertEquals("girl", user.getInterests().get(0));

		assertEquals(2, user.getHouses().size());
		assertEquals("Hourse-CD", user.getHouses().get("CD"));
	}
	
	@Test
	public void toXmlWithListAsRoot() {
		User goingmm = new User();
		goingmm.setId(Long.valueOf(123));
		goingmm.setName("G.Lee");
		goingmm.getInterests().add("girl");
		goingmm.getInterests().add("football");

		goingmm.getHouses().put("CD", "Hourse-CD");
		goingmm.getHouses().put("NC", "Hourse-NC");

		goingmm.getRoles().add(new Role(Long.valueOf(1), "Admin"));
		goingmm.getRoles().add(new Role(Long.valueOf(2), "User"));
		
		
		User bily = new User();
		bily.setId(Long.valueOf(234));
		bily.setName("Bily");
		
		bily.getInterests().add("boy");
		bily.getInterests().add("ball");

		bily.getHouses().put("CD", "CD-Hourse");
		bily.getHouses().put("CQ", "CQ-Hourse");

		bily.getRoles().add(new Role(Long.valueOf(2), "User"));
		
		List<User> userList = new ArrayList<User>();
		
		userList.add(goingmm);
		userList.add(bily);

		String xml = binder.toXml(userList, "userList");
		logger.info("Jaxb Object List to Xml result:\n" + xml);
	}
	
	private String generateXmlByDom4j() {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("user").addAttribute("id", "123");

		root.addElement("name").setText("G.Lee");

		//List<Role>
		Element roles = root.addElement("roles");
		roles.addElement("role").addAttribute("id", "1").addAttribute("name", "Admin");
		roles.addElement("role").addAttribute("id", "2").addAttribute("name", "User");

		//List<String>
		Element interests = root.addElement("interests");
		interests.addElement("interest").addText("girl");
		interests.addElement("interest").addText("football");

		//Map<String,String>
		Element houses = root.addElement("houses");
		houses.addElement("house").addAttribute("key", "CD").addText("Hourse-CD");
		houses.addElement("house").addAttribute("key", "NC").addText("Hourse-NC");

		return document.asXML();
	}
	
	private void assertXmlByDom4j(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Element user = doc.getRootElement();
		assertEquals("123", user.attribute("id").getValue());

		Element adminRole = (Element) doc.selectSingleNode("//roles/role[@id=1]");
		assertEquals(2, adminRole.getParent().elements().size());
		assertEquals("Admin", adminRole.attribute("name").getValue());

		Element interests = (Element) doc.selectSingleNode("//interests");
		assertEquals(2, interests.elements().size());
		assertEquals("girl", ((Element) interests.elements().get(0)).getText());

		Element house = (Element) doc.selectSingleNode("//houses/house[@key='CD']");
		assertEquals("Hourse-CD", house.getText());
	}
}
