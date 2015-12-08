package com.maximus.black.lab.ejb.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maximus.black.lab.businesslogic.EmployeeDAO;
import com.maximus.black.lab.entity.Employee;
import com.maximus.black.lab.service.ReqResService;
import com.maximus.black.lab.service.ReqResServiceLocal;
import com.maximus.black.lab.service.dto.EmployeeData;
/**
 * An EJB poroviding ways to access employee information from the system.
 * This is injected through CDI in the REST layer.
 * @author kamesh
 *
 */
@Stateless
@Transactional
public class ReqResServiceImpl implements ReqResService,ReqResServiceLocal {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 8458271568960853532L;
	private static final List<EmployeeData> employeeData= new ArrayList<EmployeeData>();
	
	//@Inject
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	
	public List<EmployeeData> getEmployeeDetails() {
		
		try {
			for(Employee employee: employeeDAO.getAllEmployees()){
				logger.info("Retreived Employee ================== {}",employee.toString());
				employeeData.add(new EmployeeData(employee.getName(),employee.getId(),employee.getDepartment()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeData;
	}

	public boolean createEmployeeData(final List<EmployeeData> employeeDetails)
			throws Exception {
		logger.info("Persist Employees in the database ==========================================");
		List<Employee> employeeList;
		try {
			employeeList = new ArrayList<Employee>();
			for(EmployeeData data: employeeDetails){
				final Employee employee = new Employee();
				employee.setName(data.getName());
				employee.setId(data.getId());
				employee.setDepartment(data.getDepartment());
				employeeList.add(employee);
			}
			logger.info("Size of persistent Employees before calling persist ========================================== {}",employeeList.size());
			return employeeDAO.createEmployees(employeeList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Persist failed ================================= Reason : {} ", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

}
