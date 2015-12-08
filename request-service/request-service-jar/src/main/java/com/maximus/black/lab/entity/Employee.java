package com.maximus.black.lab.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * A simple JPA entity which holds employee information in the Employee table.
 * Mapping of this simple type is mentioned in the hibernate.cfg.xml a.k.a Hibernate configuration file.
 * 
 * @author Kamesh
 *
 */
@SuppressWarnings("serial")
@Entity  
@Table(name="employeedata") 
public class Employee implements Serializable{
	//I have not used serial version UID as hibernate usually works well without it in the absence of remoting.
	@Id
	private Integer id;
	
	private String name,department;
	
	public int getId() {  
	    return id;  
	}  
	public void setId(final int id) {  
	    this.id = id;  
	}  

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(final String department) {
		this.department = department;
	}
	@Override
	public String toString(){
		return this.getName() + "|" + this.getId() + "|" + this.getDepartment();
	}

}
