package org.workin.test.ws.rest.client;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.workin.ws.result.WSResult;
import org.workin.ws.constant.WSConstants;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@XmlRootElement(name="AllEmployeeResult")
@XmlType(name = "AllEmployeeResult", namespace = WSConstants.NS)
public class AllEmployeeResult extends WSResult {
	
	private static final long serialVersionUID = -7307999575212295795L;
	
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
