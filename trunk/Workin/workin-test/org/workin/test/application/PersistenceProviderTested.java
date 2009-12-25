package org.workin.test.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.workin.core.persistence.support.PaginationSupport;
import org.workin.core.persistence.support.PersistenceService;
import org.workin.fortest.AbstractTestCase;
import org.workin.test.application.entity.Person;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("unchecked")
public class PersistenceProviderTested extends AbstractTestCase {
	
	/**
	 *.
	 *$(Jpa API testing) Total methods:[24]------------------------------------------
	 * 
	 * -> test_findUniqueByNamedOfQuery(); 	[2]
	 * -> test_findUniqueByProperty(); 		[1]
	 * -> test_findUniqueByPropertys(); 	[1]
	 * -> test_findByNamedOfQuery(); 		[3]
	 * -> test_findByProperty(); 			[3]
	 * -> test_findByPropertys(); 			[3]
	 * -> test_findUniqueByNativeQuery(); 	[2]
	 * -> test_findByNativeQuery(); 		[9]
	 * 
	 *-------------------------------------------------------------------------------
	 *
	**/
	
	// Jpa Test For findUniqueByNamedOfQuery
	public void test_findUniqueByNamedOfQuery() {
		Person person = (Person) this.persistenceService.findUniqueByNamedOfQuery(FIND_PERSON_BY_NAME_EXT, NAME_PARAM[2]);
		assertForFindUniquePerson(person);
		
		person = (Person) this.persistenceService.findUniqueByNamedOfQuery(FIND_PERSON_BY_NAME, getParamMap(2));
		assertForFindUniquePerson(person);
	}	
	
	// Jpa Test For findUniqueByProperty
	public void test_findUniqueByProperty() {
		Person person = (Person) this.persistenceService.findUniqueByProperty(Person.class, NAME_PROPERTY, NAME_PARAM[2]);
		assertForFindUniquePerson(person);
	}
	
	// Jpa Test For findUniqueByPropertys
	public void test_findUniqueByPropertys() {
		Person person = (Person) this.persistenceService.findUniqueByPropertys(Person.class, getParamMap(2));
		assertForFindUniquePerson(person);
	}
	
	// Jpa Test For findByNamedOfQuery
	public void test_findByNamedOfQuery() {
		// step 1:
		List personList = this.persistenceService.findByNamedOfQuery(FIND_ALL_PERSON);
		assertNotNull(personList);
		assertEquals(ALL_PERSON_COUNT, personList.size());
		
		// init again
		personList = null;
		// step 2:
		personList = this.persistenceService.findByNamedOfQuery(FIND_PERSON_BY_NAME_EXT, NAME_PARAM[0]);
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 3:
		personList = this.persistenceService.findByNamedOfQuery(FIND_PERSON_BY_NAME, getParamMap(1));
		assertForFindGoogle(personList);
	}
	
	// Jpa Test For findByProperty
	public void test_findByProperty() {
		// step 1:
		List personList = this.persistenceService.findByProperty(Person.class, NAME_PROPERTY, NAME_PARAM[1]);
		assertForFindGoogle(personList);
		
		// init again
		personList = null;
		// step 2:
		personList = this.persistenceService.findByProperty(Person.class, NAME_PROPERTY, NAME_PARAM[1], 0, PAGE_MAX_SIZE);
		assertForFindGoogle(personList);
		
		// step 3:
		PaginationSupport ps = this.persistenceService.findPaginatedByProperty(Person.class, NAME_PROPERTY, NAME_PARAM[1], 0, PAGE_MAX_SIZE);
		assertForFindGoogle(ps);
	}
	
	// Jpa Test For findByPropertys
	public void test_findByPropertys() {
		// step 1:
		List personList = this.persistenceService.findByPropertys(Person.class, getParamMap(0));
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 2:
		personList = this.persistenceService.findByPropertys(Person.class, getParamMap(0), 0, PAGE_MAX_SIZE);
		assertForFindGoingmm(personList);
		
		// step 3:
		PaginationSupport ps = this.persistenceService.findPaginatedByPropertys(Person.class, getParamMap(0), 0, PAGE_MAX_SIZE);
		assertForFindGoingmm(ps);
	}	
	
	// Jpa Test For findUniqueByNativeQuery
	public void test_findUniqueByNativeQuery() {
		// step 1:
		Person person = (Person) this.persistenceService.
			findUniqueByNativeQuery(Person.class, NATIVE_SQL_FIND_PERSON_BY_NAME_EXT, NAME_PARAM[2]);
		assertForFindUniquePerson(person);
		
		// init again
		person = null;
		// step 2:
		person = (Person) this.persistenceService.
			findUniqueByNativeQuery(Person.class, NATIVE_SQL_FIND_PERSON_BY_NAME, this.getParamMap(2));
		assertForFindUniquePerson(person);
	}
	
	// Jpa Test For findByNativeQuery
	public void test_findByNativeQuery() {
		// step 1:
		List personList = this.persistenceService.findByNativeQuery(NATIVE_SQL_FIND_ALL_PERSON);
		assertNotNull(personList);
		assertEquals(ALL_PERSON_COUNT, personList.size());
		
		// init again
		personList = null;
		// step 2:
		personList = this.persistenceService.findByNativeQuery(NATIVE_SQL_FIND_PERSON_BY_NAME_EXT, NAME_PARAM[0]);
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 3:
		personList = this.persistenceService.findByNativeQuery(NATIVE_SQL_FIND_PERSON_BY_NAME, this.getParamMap(0));
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 4:
		personList = this.persistenceService.findByNativeQuery(Person.class, NATIVE_SQL_FIND_PERSON_BY_NAME_EXT, NAME_PARAM[0]);
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 5:
		personList = this.persistenceService.findByNativeQuery(Person.class, NATIVE_SQL_FIND_PERSON_BY_NAME, this.getParamMap(0));
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 6:
		personList = this.persistenceService.findByNativeQuery(NATIVE_SQL_FIND_PERSON_BY_NAME_EXT, 0, PAGE_MAX_SIZE, NAME_PARAM[0]);
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 7:
		personList = this.persistenceService.findByNativeQuery(NATIVE_SQL_FIND_PERSON_BY_NAME, 0, PAGE_MAX_SIZE, this.getParamMap(0));
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 8:
		personList = this.persistenceService.findByNativeQuery(Person.class, NATIVE_SQL_FIND_PERSON_BY_NAME_EXT, 0, PAGE_MAX_SIZE, NAME_PARAM[0]);
		assertForFindGoingmm(personList);
		
		// init again
		personList = null;
		// step 9:
		personList = this.persistenceService.findByNativeQuery(Person.class, NATIVE_SQL_FIND_PERSON_BY_NAME, 0, PAGE_MAX_SIZE, this.getParamMap(0));
		assertForFindGoingmm(personList);
	}
	
	
	
	/**
	 *
	 *$(SqlMap API testing) Total methods:[5]----------------------------------------
	 * 
	 * -> test_findListBySqlMap(); 		  [1]
	 * -> test_findMapBySqlMap(); 		  [2]
	 * -> test_findPaginatedBySqlMap();   [2]
	 * 
	 *-------------------------------------------------------------------------------
	 *
	**/
	
	// SqlMap Test For findListBySqlMap
	public void test_findListBySqlMap() {
		// step 1:
		List personList = this.persistenceService.findListBySqlMap(FIND_PERSON_BY_SQLMAP, NAME_PARAM[0]);
		assertForFindGoingmm(personList);
	}
	
	// SqlMap Test For findMapBySqlMap
	public void test_findMapBySqlMap() {
		// step 1:
		Map personMap = this.persistenceService.findMapBySqlMap(FIND_PERSON_BY_SQLMAP, NAME_PARAM[0], RETURN_MAP_KEY);
		assertForFindMapGoogle(personMap);
		
		// init again
		personMap = null;
		// step 2:
		personMap = this.persistenceService.findMapBySqlMap(FIND_PERSON_BY_SQLMAP, NAME_PARAM[0], RETURN_MAP_KEY, RETURN_MAP_VALUE);
		assertForFindMapGoogle(personMap);
	}
	
	
	// SqlMap Test For findPaginatedBySqlMap
	public void test_findPaginatedBySqlMap() {
		// step 1:
		PaginationSupport ps = this.persistenceService.findPaginatedBySqlMap(FIND_PERSON_BY_SQLMAP, NAME_PARAM[0], 0, PAGE_MAX_SIZE);
		assertForFindGoingmm(ps);
		
		// init again
		ps = null;
		// step 2:
		ps = this.persistenceService.findPaginatedBySqlMap(FIND_PERSON_BY_SQLMAP, NAME_PARAM[1], 0, PAGE_MAX_SIZE);
		assertForFindGoogle(ps);
	}
	
	/**
	 *
	 *$(Common assert method) Total:[7]----------------------------------------
	 * 
	 * -> assertForFindUniquePerson(Person person);
	 * -> assertForFindGoingmm(PaginationSupport ps);
	 * -> assertForFindGoingmm(List personList);
	 * -> assertForFindGoogle(PaginationSupport ps);
	 * -> assertForFindGoogle(List personList);
	 * -> assertForFindGoogle(List personList);
	 * -> assertForFindMapGoogle(Map personMap);
	 * 
	 *-----------------------------------------------------------------------------------
	 *
	**/
	
	/**
	 * 
	 * Assert For FindUnique Person.
	 * 
	 * @param person
	 */
	public void assertForFindUniquePerson(Person person) {
		assertNotNull(person);
		assertEquals(NAME_PARAM[2], person.getName());
		assertEquals(SEX_RESULT[2], person.getSex());
	}
	
	/**
	 * 
	 * Assert For Find Goingmm PaginationSupport.
	 * 
	 * @param ps
	 * 
	 */
	public void assertForFindGoingmm(PaginationSupport ps) {
		assertNotNull(ps);
		assertEquals(PERSON_GOINGMM_COUNT, ps.getTotalCount());
		assertEquals(PERSON_GOINGMM_COUNT, ((List)ps.getData()).size());
	}
	
	/**
	 * 
	 * Assert For Find Goingmm List.
	 * 
	 * @param persinList
	 * 
	 */
	public void assertForFindGoingmm(List personList) {
		assertNotNull(personList);
		assertEquals(PERSON_GOINGMM_COUNT, personList.size());
	}
	
	/**
	 * 
	 * Assert For Find Google PaginationSupport.
	 * 
	 * @param ps
	 * 
	 */
	public void assertForFindGoogle(PaginationSupport ps) {
		assertNotNull(ps);
		assertEquals(PERSON_GOOGLE_COUNT, ps.getTotalCount());
		assertEquals(PERSON_GOOGLE_COUNT, ((List)ps.getData()).size());
	}
	
	/**
	 * 
	 * Assert For Find Google list.
	 * 
	 * @param persinList
	 * 
	 */
	public void assertForFindGoogle(List personList) {
		assertNotNull(personList);
		assertEquals(PERSON_GOOGLE_COUNT, personList.size());
	}
	
	/**
	 * 
	 * Assert For Find Google map.
	 * 
	 * @param personMap
	 * 
	 */
	public void assertForFindMapGoogle(Map personMap) {
		assertNotNull(personMap);
		assertEquals(PERSON_GOINGMM_COUNT, personMap.size());
	}
	
	
	@Autowired
	private PersistenceService persistenceService;
	
	public static final String NAME_PROPERTY = "name";
	public static final String RETURN_MAP_KEY = "id";
	public static final String RETURN_MAP_VALUE = "name";
	
	public static final int ALL_PERSON_COUNT = 12;
	public static final int PERSON_GOINGMM_COUNT = 6;
	public static final int PERSON_GOOGLE_COUNT = 5;
	public static final int PERSON_UNIQUE_COUNT = 1;
	public static final int PAGE_MAX_SIZE = 10;
	
	public static final String FIND_ALL_PERSON = "findAllPerson";
	public static final String FIND_PERSON_BY_NAME = "findPersonByName";
	public static final String FIND_PERSON_BY_NAME_EXT = "findPersonByNameExt";
	public static final String FIND_PERSON_BY_SQLMAP = "getPersons";
	
	public static final Object[] NAME_PARAM= {"goingmm", "google", "unique"};
	public static final Object[] SEX_RESULT= {"male", "famale", "other"};	
	
	public static final String NATIVE_SQL_FIND_ALL_PERSON = "SELECT * FROM Person as p";
	public static final String NATIVE_SQL_FIND_PERSON_BY_NAME = "SELECT * FROM Person as p WHERE p.name = :name";
	public static final String NATIVE_SQL_FIND_PERSON_BY_NAME_EXT = "SELECT * FROM Person as p WHERE p.name = ?";
	
	/**
	 * 
	 * @param index
	 * @return
	 * 
	 */
	Map getParamMap(int index) {
		Map paramMap = new HashMap();
		paramMap.put(NAME_PROPERTY, NAME_PARAM[index]);
		return paramMap;
	}
	
	// For quickly copy
	public void ctest_() {
		;
	}
	
	
	/**
	 *
	 *$(Testing data collection) Total:[12]----------------------------------------
	 * 
	 * -> All test must flow this data collection(before test insert this datas first)
	 * 
	 * -> Person:[12]
	 * 		-> goingmm: [6] 	male
	 *  	-> google:  [5] 	famale
	 *  	-> unique:  [1]   	other
	 *-----------------------------------------------------------------------------------
	 *
	**/
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','goingmm','male');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','google','famale');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','goingmm','male');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','google','famale');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','goingmm','male');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','google','famale');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','goingmm','male');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','google','famale');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','goingmm','male');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','google','famale');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','goingmm','male');
//	INSERT INTO `person` (`createBy`,`createdttm`,`updateBy`,`updatedttm`,`birthday`,`name`,`sex`) VALUES ('123456','2009-02-10 20:39:25','1',NULL,'2009-02-10 20:39:24','unique','other');
}
