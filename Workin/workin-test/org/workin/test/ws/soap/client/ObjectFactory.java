
package org.workin.test.ws.soap.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.workin.test.ws.soap.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Employee_QNAME = new QName("http://soap.ws.workin.org", "Employee");
    private final static QName _GetAllEmployeeResponse_QNAME = new QName("http://soap.ws.workin.org", "getAllEmployeeResponse");
    private final static QName _FindEmployeeById_QNAME = new QName("http://soap.ws.workin.org", "findEmployeeById");
    private final static QName _AllEmployeeResult_QNAME = new QName("http://soap.ws.workin.org", "AllEmployeeResult");
    private final static QName _FindEmployeeByIdResponse_QNAME = new QName("http://soap.ws.workin.org", "findEmployeeByIdResponse");
    private final static QName _GetAllEmployee_QNAME = new QName("http://soap.ws.workin.org", "getAllEmployee");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.workin.test.ws.soap.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindEmployeeByIdResponse }
     * 
     */
    public FindEmployeeByIdResponse createFindEmployeeByIdResponse() {
        return new FindEmployeeByIdResponse();
    }

    /**
     * Create an instance of {@link AllEmployeeResult }
     * 
     */
    public AllEmployeeResult createAllEmployeeResult() {
        return new AllEmployeeResult();
    }

    /**
     * Create an instance of {@link GetAllEmployeeResponse }
     * 
     */
    public GetAllEmployeeResponse createGetAllEmployeeResponse() {
        return new GetAllEmployeeResponse();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link AllEmployeeResult.EmployeeList }
     * 
     */
    public AllEmployeeResult.EmployeeList createAllEmployeeResultEmployeeList() {
        return new AllEmployeeResult.EmployeeList();
    }

    /**
     * Create an instance of {@link FindEmployeeById }
     * 
     */
    public FindEmployeeById createFindEmployeeById() {
        return new FindEmployeeById();
    }

    /**
     * Create an instance of {@link WSResult }
     * 
     */
    public WSResult createWSResult() {
        return new WSResult();
    }

    /**
     * Create an instance of {@link GetAllEmployee }
     * 
     */
    public GetAllEmployee createGetAllEmployee() {
        return new GetAllEmployee();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Employee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "Employee")
    public JAXBElement<Employee> createEmployee(Employee value) {
        return new JAXBElement<Employee>(_Employee_QNAME, Employee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "getAllEmployeeResponse")
    public JAXBElement<GetAllEmployeeResponse> createGetAllEmployeeResponse(GetAllEmployeeResponse value) {
        return new JAXBElement<GetAllEmployeeResponse>(_GetAllEmployeeResponse_QNAME, GetAllEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEmployeeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "findEmployeeById")
    public JAXBElement<FindEmployeeById> createFindEmployeeById(FindEmployeeById value) {
        return new JAXBElement<FindEmployeeById>(_FindEmployeeById_QNAME, FindEmployeeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllEmployeeResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "AllEmployeeResult")
    public JAXBElement<AllEmployeeResult> createAllEmployeeResult(AllEmployeeResult value) {
        return new JAXBElement<AllEmployeeResult>(_AllEmployeeResult_QNAME, AllEmployeeResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindEmployeeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "findEmployeeByIdResponse")
    public JAXBElement<FindEmployeeByIdResponse> createFindEmployeeByIdResponse(FindEmployeeByIdResponse value) {
        return new JAXBElement<FindEmployeeByIdResponse>(_FindEmployeeByIdResponse_QNAME, FindEmployeeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "getAllEmployee")
    public JAXBElement<GetAllEmployee> createGetAllEmployee(GetAllEmployee value) {
        return new JAXBElement<GetAllEmployee>(_GetAllEmployee_QNAME, GetAllEmployee.class, null, value);
    }

}
