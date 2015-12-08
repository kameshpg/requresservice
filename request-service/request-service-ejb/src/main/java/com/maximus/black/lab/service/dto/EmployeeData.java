package com.maximus.black.lab.service.dto;

import java.io.Serializable;

public class EmployeeData implements Serializable{

	/**
	 * A VO holding Employee detail
	 */
	private static final long serialVersionUID = -1532939065166397695L;
	
	private String name;
	private Integer id;
	private String department;
	
	public EmployeeData(){
		
	}
	
	public EmployeeData(final String name, final int id, final String department) {
		this.setName(name);
		this.setId(id);
		this.setDepartment(department);
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(final String department) {
		this.department = department;
	}
	
	@Override
	public String toString(){
		return "Employee Information: Name :"+this.getName()+" ID :"+this.getId()+" Department :"+ this.getDepartment();
	}

}
