/**
 * 
 */
package com.maximus.black.lab.presentation.server;

import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maximus.black.lab.service.ReqResService;
import com.maximus.black.lab.service.dto.EmployeeData;

/**
 * REST resource for the application.
 * Entry point for accessing backend code.
 * The different operations are exposed as webservice APIs here through Java RS.
 * 
 * @author kamesh
 *
 */
@ManagedBean
@RequestScoped
@Path("/employee")
public class EmployeeServiceResource {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@EJB
	ReqResService requestResService;
	
	/**
	 * Retrieves Employee information from the system.
	 * @return Response as a List of Employees and related information
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllEmployee")
	public Response getEmployeeDetails(){
		
		return Response.status(Status.OK).entity(requestResService.getEmployeeDetails()).build();
		
	}
	
	/**
	 * Creates Employee information in the system
	 * @param employeeDetails
	 * @return
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	public Response createEmployeeData(final List<EmployeeData> employeeDetails){
		try {
			getValueObjectsFromJSONPayload(employeeDetails);
			return Response.status(Status.OK).entity(requestResService.createEmployeeData(employeeDetails)).build();
		} catch (Exception e) {
			logger.info("Persist Employees in the database failed ==========================================");
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}

	private void getValueObjectsFromJSONPayload(
			List<EmployeeData> employeeDetails) {
		
		for(EmployeeData empoyee : employeeDetails){
			
			System.out.println("Employee Data : "+ empoyee.getName());
		}
		
	}
		

}
