package org.workin.test.ws.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.workin.ws.rest.service.RestXmlService;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@Path(value = "/employeeService")
public interface EmployeeService extends RestXmlService {
	
	@GET
	@Path(value = "/retrievies")
	public AllEmployeeResult getAllEmployee();
	
	@GET
	@Path(value = "/{id}/retrieve")
	public Employee findEmployeeById(@PathParam("id") long employeeId);
	
}
