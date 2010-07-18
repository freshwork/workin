package org.workin.test.ws.rest.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public Employee findEmployeeById(long employeeId) {
		List<Employee> employeeList = getEmployeeList();
		
		for(Employee employee: employeeList){
			if(employeeId == employee.getId())
				return employee;
		}
		return null;
	}

	@Override
	public AllEmployeeResult getAllEmployee() {
		List<Employee> employeeList = getEmployeeList();
		
		AllEmployeeResult result = new AllEmployeeResult();
		result.setEmployeeList(employeeList);
		
		return result;
	}
	
	
	private List<Employee> getEmployeeList() {
		List<Employee> employeeList = new ArrayList<Employee>();
		
		Employee tom = newEmployee(1, "tom", "1986-01-02");
		Employee bily = newEmployee(2, "bily", "1986-01-01");
		Employee itaxta = newEmployee(3, "itaxta", "1986-01-03");
		
		employeeList.add(tom);
		employeeList.add(bily);
		employeeList.add(itaxta);
		
		return employeeList;
	}
	
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param birthday
	 * @return
	 * 
	 */
	private Employee newEmployee(long id, String name, String birthday) {
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		try {
			employee.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return employee;
	}
}
