package org.workin.test.ws.rest.service;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.workin.ws.result.WSResult;
import org.workin.ws.constant.WSConstants;
import org.workin.test.ws.rest.service.Employee;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@XmlRootElement(name="AllEmployeeResult")
@XmlType(name = "AllEmployeeResult", namespace = WSConstants.NS)
public class AllEmployeeResult extends WSResult {
	
	private static final long serialVersionUID = -3034580398998884954L;
	
	private List<Employee> employeeList;
	
	@XmlElementWrapper(name = "employeeList")
	@XmlElement(name = "Employee")
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
}
