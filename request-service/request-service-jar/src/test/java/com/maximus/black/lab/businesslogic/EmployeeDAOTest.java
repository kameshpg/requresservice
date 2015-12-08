package com.maximus.black.lab.businesslogic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.maximus.black.lab.entity.Employee;

public class EmployeeDAOTest {
	
	private final EmployeeDAO daoUnderTest = new EmployeeDAO();
	
	private final List<Employee> listToAdd = new ArrayList<Employee>();

	@Before
	public void setUp() throws Exception {
		Employee emp1 = new Employee();
		emp1.setName("Dave");
		emp1.setDepartment("accounting");
		emp1.setId(1);
		
		listToAdd.add(emp1);
	}

	
	@Ignore
	@Test
	public final void testCreateEmployees() throws Exception {
		Assert.assertEquals("not ok", true, daoUnderTest.createEmployees(listToAdd));
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public final void testGetAllEmployees() {
		fail("Not yet implemented");
	}

}
