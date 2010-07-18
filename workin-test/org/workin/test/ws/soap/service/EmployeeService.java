package org.workin.test.ws.soap.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import org.workin.ws.constant.WSConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@WebService(name = "EmployeeService", targetNamespace = WSConstants.NS)
@BindingType(value=SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public interface EmployeeService {
	
	public AllEmployeeResult getAllEmployee();
	
	public Employee findEmployeeById(@WebParam(name = "employeeId")  long employeeId);
	
}
