package org.workin.test.ws.soap.client.test;

import org.junit.Before;
import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.test.ws.soap.client.AllEmployeeResult;
import org.workin.test.ws.soap.client.Employee;
import org.workin.test.ws.soap.client.EmployeeService;
import org.workin.ws.soap.client.SoapClient;
import org.workin.ws.soap.client.SoapClientImpl;
import org.workin.ws.soap.engine.SoapEngine;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class SoapClientTest extends BaseTestCase {

	SoapClient<EmployeeService> client = null;

	EmployeeService service = null;

	EmployeeService cxfService = null;

	public static void main(String[] args) throws Exception {
		new SoapClientTest().startEngine();
		Runtime.getRuntime().exec("cmd /c start scripts\\ws\\wsdl2java.bat");
	}

	@Before
	public void startEngine() {
		SoapEngine.getInstance().start(org.workin.test.ws.soap.service.EmployeeServiceImpl.class, URL_SOAP_SERVER);
		assertEquals(true, SoapEngine.getInstance().isServerStarted());
		// call wsdl2java.bat and the sleep some times, if need
		client = new SoapClientImpl<EmployeeService>(EmployeeService.class);
		service = client.buildSoapService(URL_SOAP_SERVER);
		cxfService = client.buildSoapServiceWithCxf("EmployeeServiceImplService", URL_SOAP_SERVICES);
	}

	@Test
	public void findEmployeeByIdTest() {
		findEmployeeById(service);
	}

	@Test
	public void findEmployeeByIdByCxfServiceTest() {
		findEmployeeById(cxfService);
	}

	@Test
	public void getAllEmployeeTest() {
		getAllEmployee(service);
	}

	@Test
	public void getAllEmployeeByCxfServiceTest() {
		getAllEmployee(cxfService);
	}

	private void findEmployeeById(final EmployeeService service) {
		Employee employee = service.findEmployeeById(3);
		assertEquals("itaxta", employee.getName());
	}

	private void getAllEmployee(final EmployeeService service) {
		AllEmployeeResult result = service.getAllEmployee();
		assertNotNull("AllEmployeeResult can not be null!", result);
		assertNotNull("EmployeeList can not be null!", result.getEmployeeList());
		assertEquals(3, result.getEmployeeList().getEmployee().size());
	}

	private static final String URL_SOAP_SERVER = "http://localhost:8080/workin/soap/employeeService";

	private static final String URL_SOAP_SERVICES = "http://localhost:8080/workin/soap/employeeService?wsdl";
}
