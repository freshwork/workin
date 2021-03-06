package org.workin.test.ws.soap.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.4
 * Tue Dec 01 17:24:37 CST 2009
 * Generated source version: 2.2.4
 * 
 */
 
@WebService(targetNamespace = "http://soap.ws.workin.org", name = "EmployeeService")
@XmlSeeAlso({ObjectFactory.class})
public interface EmployeeService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findEmployeeById", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.client.FindEmployeeById")
    @ResponseWrapper(localName = "findEmployeeByIdResponse", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.client.FindEmployeeByIdResponse")
    @WebMethod
    public org.workin.test.ws.soap.client.Employee findEmployeeById(
        @WebParam(name = "employeeId", targetNamespace = "")
        long employeeId
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAllEmployee", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.client.GetAllEmployee")
    @ResponseWrapper(localName = "getAllEmployeeResponse", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.client.GetAllEmployeeResponse")
    @WebMethod
    public org.workin.test.ws.soap.client.AllEmployeeResult getAllEmployee();
}
