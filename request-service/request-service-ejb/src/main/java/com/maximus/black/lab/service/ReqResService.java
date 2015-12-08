package com.maximus.black.lab.service;
import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;

import com.maximus.black.lab.service.dto.EmployeeData;

/**
 * A remote interface to provide services for any CRUD operation.
 * @author kamesh
 *
 */
@Remote
@Stateless
@SessionScoped
public interface ReqResService extends Serializable {
	/**
	 * Get operation to retrieve employee information
	 * @return
	 */
	List<EmployeeData> getEmployeeDetails();
	
	/**
	 * Creates employees in the system
	 * @param emploeeDetails
	 * @return
	 * @throws Exception
	 */
	boolean createEmployeeData(List<EmployeeData> emploeeDetails) throws Exception;

}
