package org.workin.test.application;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.workin.core.entity.Idable;
import org.workin.core.persistence.support.PropertyFilter;
import org.workin.fortest.spring.SpringTxTestCase;
import org.workin.test.application.entity.Person;
import org.workin.test.application.service.PersonService;
import org.workin.util.CollectionUtils;


/**
 * 
 * @TransactionConfiguration(defaultRollback = false)
 * 	or
 * @Rollback(false)
 * 
 * Can forcible transaction commit
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public class PersonServiceTest extends SpringTxTestCase {
	
	@Autowired
    private PersonService personService;	
	
	// first init db data
	@Test
	@Rollback(false)
	public void initDb() {
		List personList = personService.getAll(Person.class);
		if(!CollectionUtils.isEmpty(personList))
			personService.batchRemove(personService.getAll(Person.class));
	}
	
	@Test
	@Rollback(false)
	public void executeNamedOfQueryUpdate() {
		personService.save(newPerson());		
		personService.executeNamedOfQuery("update_Person", NEW_USER_NAME_CHANGE, NEW_USER_NAME);
	}
	
	
	@Test
	@Rollback(false)
	public void executeNamedOfQueryDel() {
		personService.executeNamedOfQuery("del_Person", NEW_USER_NAME_CHANGE);
	}
	
	
	// test for add person successful
	@Test
	@Rollback(false)
	public void addPerson(){
		Person person = (Person)personService.save(newPerson());
		assertEquals(NEW_USER_NAME, person.getName());
		assertEquals(NEW_USER_SEX, person.getSex());
		LASTPERSONID = person.getId();
	}
	
	// test for find person By Id successful
	@Test
	public void findPersonById(){
		Person person = (Person) personService.findById(Person.class, LASTPERSONID);
		assertNotNull(person);
		assertEquals(NEW_USER_NAME, person.getName());
		assertEquals(NEW_USER_SEX, person.getSex());
	}
	
	// test for find By CriteriaQuery successful
	@Test
	public void findByCriteriaQuery() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter_name = new PropertyFilter("EQS_name", "goingmm");
		filters.add(filter_name);
		PropertyFilter filter_sex = new PropertyFilter("LIKES_sex", "male");
		filters.add(filter_sex);
		PropertyFilter filter_version = new PropertyFilter("GEL_version", "0");
		filters.add(filter_version);
		
		List<Person> personList = personService.findByCriteriaQuery(filters);
		assertTrue(personList.size() == 1);
		
		for(Person person : personList) {
			assertEquals(NEW_USER_NAME, person.getName());
			assertEquals(NEW_USER_SEX, person.getSex());
		}
	}
	
	
	// test for get all person successful
	@Test
	@Repeat(2)
	public void getAllPerson(){
		List<Person> personList = (List<Person>) personService.getAll(Person.class);
		assertNotNull(personList);
		assertTrue(personList.size() == 1);
	}
	
	// test find person list by SQL_MAP
	@Test
	public void findPersonsBySqlMap() {
		Person person = new Person();
		person.setName(NEW_USER_NAME);
		
		List<Person> personList = this.personService.findPersonsBySqlMap("getPersons", person);
		
		assertNotNull(personList);
		assertTrue(personList.size() == 1);
	}
	
	// test find person list by named Query
	@Test
	public void findPersonsByNamedQuery() {
		Map nameAndValue = new HashMap();
		nameAndValue.put("name", NEW_USER_NAME);
		
		List<Person> personList = this.personService.findPersonsByNamedQuery("findPersonByName", nameAndValue);
		
		assertNotNull(personList);
		assertTrue(personList.size() == 1);
	}
	
	
	// test find person list by Property
	@Test
	public void findPersonsByProperty() {
		List<Person> personList = this.personService.findPersonsByProperty("name", NEW_USER_NAME);
		assertNotNull(personList);
		assertTrue(personList.size() == 1);
	}
	
	
	// test find person list by Property
	@Test
	public void findPersonsByPropertys() {
		Map nameAndValue = new HashMap();
		nameAndValue.put("name", NEW_USER_NAME);
		nameAndValue.put("sex", NEW_USER_SEX);
		List<Person> personList = this.personService.findPersonsByPropertys(nameAndValue);
		
		assertNotNull(personList);
		assertTrue(personList.size() == 1);
	}
	
	// test for update person and address successful
	@Test
	@Rollback(false)
	public void updatePerson() {
		Person person = (Person) personService.findById(Person.class, LASTPERSONID);
		person.setName(NEW_USER_NAME_CHANGE);
		person = (Person)this.personService.merge(person);
		
		assertEquals(NEW_USER_NAME_CHANGE, person.getName());
	}
	
	
	// test mix SqlMap And Jpa find and update persons
	@Test
	@Rollback(false)
	public void mixSqlMapAndJpa() {
		Map nameAndValue = new HashMap();
		nameAndValue.put("name", NEW_USER_NAME_CHANGE);
		nameAndValue.put("sex", NEW_USER_SEX);
		
		// find
		List<Person> personList = this.personService.findPersonsByPropertys(nameAndValue);
		
		// change
		for(Person per : personList) {
			per.setName(NEW_USER_NAME_CHANGE_AGAIN);
		}
		
		// update
		this.personService.batchMerge(personList);
		
		// find again
		List<Person> personListAgain = this.personService.findPersonsByPropertys(nameAndValue);
		
		// assert
		for(Person person : personListAgain) {
			assertEquals(NEW_USER_NAME_CHANGE_AGAIN, person.getName());
		}
	}
	
	// last init db data again
	@Test
	@Rollback(false)
	public void initDbAgain() {
		List personList = personService.getAll(Person.class);
		if(!CollectionUtils.isEmpty(personList))
			personService.batchRemove(personService.getAll(Person.class));
	}
	
	private Idable newPerson(){
		Person person = new Person();
		person.setName(NEW_USER_NAME);
		person.setSex(NEW_USER_SEX);
		person.setBirthday(new Date());
		
		return person;
	}
	
	private static Long LASTPERSONID;
	private static final String NEW_USER_SEX = "male";
	private static final String NEW_USER_NAME = "goingmm";
	private static final String NEW_USER_NAME_CHANGE = "change";
	private static final String NEW_USER_NAME_CHANGE_AGAIN = "change again";
}
