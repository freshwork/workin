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
import org.workin.core.persistence.support.PaginationSupport;
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
	public void firstInitPersonsInDB() {
		clearDBPersons();
	}

	// test for add person successful
	@Test
	@Rollback(false)
	public void addPerson() {
		Person person = (Person) personService.save(newPerson());
		assertEquals(NEW_USER_NAME, person.getName());
		assertEquals(NEW_USER_SEX, person.getSex());
		LASTPERSONID = person.getId();
	}

	// test for find person By Id successful
	@Test
	public void findPersonById() {
		Person person = (Person) personService.findById(Person.class, LASTPERSONID);
		assertNotNull(person);
		assertEquals(NEW_USER_NAME, person.getName());
		assertEquals(NEW_USER_SEX, person.getSex());
	}

	// test for find By CriteriaQuery successful
	@Test
	public void findByCriteriaQuery() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter_name = new PropertyFilter("EQS_name_OR_sex", NEW_USER_NAME);
		filters.add(filter_name);
		PropertyFilter filter_sex = new PropertyFilter("LIKES_sex", "male");
		filters.add(filter_sex);
		PropertyFilter filter_version = new PropertyFilter("GEL_version", "0");
		filters.add(filter_version);

		List<Person> personList = personService.findByCriteriaQuery(Person.class, filters);
		assertTrue(personList.size() == 1);

		for (Person person : personList) {
			assertEquals(NEW_USER_NAME, person.getName());
			assertEquals(NEW_USER_SEX, person.getSex());
		}
	}

	@Test
	public void findPaginationSupportByCriteriaQuery() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter_name = new PropertyFilter("EQS_name_OR_sex", NEW_USER_NAME);
		filters.add(filter_name);
		PropertyFilter filter_sex = new PropertyFilter("LIKES_sex", "male");
		filters.add(filter_sex);
		PropertyFilter filter_version = new PropertyFilter("GEL_version", "0");
		filters.add(filter_version);

		PaginationSupport<Person> ps = personService.findPaginationSupportByCriteriaQuery(Person.class, filters, 0, 5);
		assertTrue(ps.getResult().size() == 1);

		for (Person person : ps.getResult()) {
			assertEquals(NEW_USER_NAME, person.getName());
			assertEquals(NEW_USER_SEX, person.getSex());
		}
	}

	// test for get all person successful
	@Test
	public void getAllPerson() {
		List<Person> personList = (List<Person>) personService.getAll(Person.class);
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
		person = (Person) this.personService.merge(person);

		assertEquals(NEW_USER_NAME_CHANGE, person.getName());
	}

	// test mix SqlMap And Jpa find and update persons
	@Test
	@Rollback(false)
	public void mixIBatisAndJPA() {
		Map nameAndValue = new HashMap();
		nameAndValue.put("name", NEW_USER_NAME_CHANGE);
		nameAndValue.put("sex", NEW_USER_SEX);

		// find
		List<Person> personList = this.personService.findPersonsByPropertys(nameAndValue);

		// change
		for (Person per : personList) {
			per.setName(NEW_USER_NAME_CHANGE_AGAIN);
		}

		// update
		this.personService.batchMerge(personList);

		// find again
		List<Person> personListAgain = this.personService.findPersonsByPropertys(nameAndValue);

		// assert
		for (Person person : personListAgain) {
			assertEquals(NEW_USER_NAME_CHANGE_AGAIN, person.getName());
		}
	}

	@Test
	@Rollback(false)
	public void secondInitPersonsInDB() {
		clearDBPersons();
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

	@Test
	@Rollback(false)
	public void thirdInitPersonsInDB() {
		clearDBPersons();
	}

	@Test
	@Rollback(false)
	@Repeat(30)
	public void addPersonForTestIbatisPaginationSupport() {
		Person person = (Person) personService.save(newPerson());
		assertEquals(NEW_USER_NAME, person.getName());
		assertEquals(NEW_USER_SEX, person.getSex());
		LASTPERSONID = person.getId();
	}

	@Test
	public void findPersonsBySqlMap() {
		Person person = new Person();
		person.setName(NEW_USER_NAME);
		List<Person> personList = this.personService.findPersonsBySqlMap(SQLMAP_ID_GET_PERONS, person);
		assertPersonList(personList);
	}

	@Test
	public void findPersonsByQueryString() {
		List personList = this.personService.find(QUERY_STRING_GET_PERONS_BY_NAME_SEX, NEW_USER_NAME, NEW_USER_SEX);
		assertPersonList(personList);
	}

	@Test
	public void findPaginationSupportPersonsByQueryString() {
		int dynamicSize = 3;
		PaginationSupport personList = this.personService.findPaginationSupport(dynamicSize, PAGINATIONSUPPORT_SIZE,
				QUERY_STRING_GET_PERONS_BY_NAME_SEX, NEW_USER_NAME, NEW_USER_SEX);
		assertPersonList(personList, PAGINATIONSUPPORT_SIZE);
	}

	@Test
	public void findPaginationSupportPersonsBySqlMap() {
		Person person = new Person();
		person.setName(NEW_USER_NAME);

		int dynamicSize = 3;
		PaginationSupport personList = this.personService.findPersonsBySqlMap(SQLMAP_ID_GET_PERONS, person, 0,
				dynamicSize);
		assertPersonList(personList, dynamicSize);

		PaginationSupport personList2 = this.personService.findPersonsBySqlMap(SQLMAP_ID_GET_PERONS, person,
				dynamicSize, PAGINATIONSUPPORT_SIZE);
		assertPersonList(personList2, PAGINATIONSUPPORT_SIZE);
	}

	@Test
	@Rollback(false)
	public void finalInitDb() {
		clearDBPersons();
	}

	/*@Test
	@Rollback(true)
	public void executeProcedure() {
		List<ProcedureParameter> paramList = Lists.newArrayList();
		paramList.add(new ProcedureParameter("P_INVH_REF_TXN_CODE", "INV", ProcedureParameter.INOUT, Types.VARCHAR));
		paramList.add(new ProcedureParameter("P_COMP_CODE", "001", ProcedureParameter.IN, Types.VARCHAR));
		paramList.add(new ProcedureParameter("P_INVH_REF_FROM", "IN", ProcedureParameter.IN, Types.VARCHAR));
		paramList.add(new ProcedureParameter("P_INVH_REF_SYS_ID", null, ProcedureParameter.OUT, Types.NUMERIC));
		paramList.add(new ProcedureParameter("P_INVH_REF_FROM_NUM", 11, ProcedureParameter.IN, Types.NUMERIC));
		paramList.add(new ProcedureParameter("P_INVH_REF_NO", 112, ProcedureParameter.INOUT, Types.NUMERIC));
		paramList.add(new ProcedureParameter("P_M_UOMMODYN_NUM", 1, ProcedureParameter.IN, Types.NUMERIC));
		paramList.add(new ProcedureParameter("P_INVH_TXN_CODE", "INV", ProcedureParameter.IN, Types.VARCHAR));
		paramList.add(new ProcedureParameter("P_INVH_REF_DEL_DT", new Date(), ProcedureParameter.INOUT, Types.DATE));
		paramList.add(new ProcedureParameter("P_M_PRICECHGYN_NUM", 1, ProcedureParameter.OUT, Types.NUMERIC));
		paramList.add(new ProcedureParameter("P_ERR_TYPE", null, ProcedureParameter.OUT, Types.VARCHAR));
		paramList.add(new ProcedureParameter("P_ERR_NO", 0, ProcedureParameter.OUT, Types.NUMERIC));
		Map<String, Object> result = this.personService.executeProcedure("O_DVAL_OT_INV_HEAD_INVH_REFTXN", paramList);
		System.out.println("----> ");
	}*/

	/**
	 * 
	 * @param personList
	 */
	private void assertPersonList(List personList) {
		assertNotNull(personList);
		assertTrue(personList.size() == 30);
	}

	/**
	 * 
	 * @param personList
	 * @param dynamicSize
	 * 
	 */
	private void assertPersonList(PaginationSupport personList, int dynamicSize) {
		assertNotNull(personList);
		assertTrue(personList.getTotalCount() == 30);

		List persionList = (List) personList.getResult();
		assertTrue(persionList.size() == dynamicSize);
	}

	private void clearDBPersons() {
		List personList = personService.getAll(Person.class);
		if (!CollectionUtils.isEmpty(personList))
			personService.batchRemove(personService.getAll(Person.class));
	}

	private Idable newPerson() {
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
	private static final int PAGINATIONSUPPORT_SIZE = 10;
	private static final String SQLMAP_ID_GET_PERONS = "getPersons";
	private static final String QUERY_STRING_GET_PERONS_BY_NAME_SEX = "SELECT p FROM Person p WHERE p.name = ? AND p.sex = ?";
}
