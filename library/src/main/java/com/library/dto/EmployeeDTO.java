package com.library.dto;

public class EmployeeDTO {
	
	private String e_id;
	private String e_name;

	//constructors
	public EmployeeDTO() {}
	public EmployeeDTO(String e_id, String e_name) {
		super();
		this.e_id = e_id;
		this.e_name = e_name;
	}
	
	//getter&setter
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
}
