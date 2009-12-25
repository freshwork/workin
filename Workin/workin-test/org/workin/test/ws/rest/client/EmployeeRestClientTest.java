package org.workin.test.ws.rest.client;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.test.ws.rest.service.EmployeeServiceImpl;
import org.workin.ws.rest.client.RestHttpClient;
import org.workin.ws.rest.client.RestWebClient;
import org.workin.ws.rest.engine.RestEngine;
import org.workin.xml.JaxbBinder;
import org.workin.xml.XmlBinder;



/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class EmployeeRestClientTest extends BaseTestCase {
	
	RestWebClient<Employee> client = new RestWebClient<Employee>(Employee.class);
	RestWebClient<AllEmployeeResult> resultClient = new RestWebClient<AllEmployeeResult>(AllEmployeeResult.class);
	
	RestHttpClient<Employee> httpClient = new RestHttpClient<Employee>(Employee.class);
	RestHttpClient<AllEmployeeResult> resultHttpClient = new RestHttpClient<AllEmployeeResult>(AllEmployeeResult.class);
	
	public static void main(String[] args) {
		RestEngine.getInstance().start(EmployeeServiceImpl.class, "http://localhost:8080/workin/rest");
		assertEquals(true, RestEngine.getInstance().isServerStarted());
	}
	
	@Before
	public void startEngine() {
		RestEngine.getInstance().start(EmployeeServiceImpl.class, "http://localhost:8080/workin/rest");
		assertEquals(true, RestEngine.getInstance().isServerStarted());
		
		client = new RestWebClient<Employee>(Employee.class);
		resultClient = new RestWebClient<AllEmployeeResult>(AllEmployeeResult.class);
		httpClient = new RestHttpClient<Employee>(Employee.class);
		resultHttpClient = new RestHttpClient<AllEmployeeResult>(AllEmployeeResult.class);
	}
	
	@Test
	public void retrieveEmployeeById() {
		Employee employee = client.restReturnObject(URL_RETRIEVE_EMPLOYEE_BY_ID);
		assertEmployeeByName(employee, "bily");
		
		employee = httpClient.restReturnObject(URL_RETRIEVE_EMPLOYEE_BY_ID);
		assertEmployeeByName(employee, "bily");
	}
	
	@Test
	public void retrieveXMLEmployeeById() {
		String xml = client.restReturnXml(URL_RETRIEVE_EMPLOYEE_BY_ID);
		XmlBinder binder = new JaxbBinder(Employee.class);
		Employee employee = binder.fromXml(xml);
		assertEmployeeByName(employee, "bily");
		
		xml = httpClient.restReturnXml(URL_RETRIEVE_EMPLOYEE_BY_ID);
		employee = binder.fromXml(xml);
		assertEmployeeByName(employee, "bily");
	}
	
	@Test
	public void retrieveXmlEmployees() {
		String xml = resultClient.restReturnXml(URL_RETRIEVE_EMPLOYEE);
		XmlBinder binder = new JaxbBinder(AllEmployeeResult.class);
		AllEmployeeResult result = binder.fromXml(xml);
		assertEmployees(result);
		
		xml = resultHttpClient.restReturnXml(URL_RETRIEVE_EMPLOYEE);
		result = binder.fromXml(xml);
		assertEmployees(result);
	}
	
	@Test
	public void retrieveEmployees() {
		AllEmployeeResult result = resultClient.restReturnObject(URL_RETRIEVE_EMPLOYEE);
		assertEmployees(result);
		
		result = resultHttpClient.restReturnObject(URL_RETRIEVE_EMPLOYEE);
		assertEmployees(result);
	}
	
	/**
	 * 
	 * @param result
	 */
	private void assertEmployees(AllEmployeeResult result) {
		List<Employee> employeeList = result.getEmployeeList();
		assertNotNull("result can not be null!", result);
		assertNotNull("employeeList can not be null!", employeeList);
		assertEquals(3, employeeList.size());
	}
	
	
	/**
	 * 
	 * @param employee
	 * @param name
	 * 
	 */
	private void assertEmployeeByName(Employee employee, String name) {
		assertNotNull("employee can not be null!", employee);
		assertEquals("bily", employee.getName());
	}
	
	private static final String URL_RETRIEVE_EMPLOYEE = "http://localhost:8080/workin/rest/employeeService/retrievies";
	private static final String URL_RETRIEVE_EMPLOYEE_BY_ID = "http://localhost:8080/workin/rest/employeeService/2/retrieve";
}
