package com.library.dto;

public class CheckoutDTO {
	
	private String c_id;
	private String b_id;
	private String e_id;
	private String rent_date;
	private String return_date;
	
	// constructor
	public CheckoutDTO() {}
	public CheckoutDTO(String b_id, String rent_date) {
		super();
		this.b_id = b_id;
		this.rent_date = rent_date;
	}
	public CheckoutDTO(String c_id, String b_id, String e_id, String rent_date) {
		super();
		this.c_id = c_id;
		this.b_id = b_id;
		this.e_id = e_id;
		this.rent_date = rent_date;
	}
	public CheckoutDTO(String c_id, String b_id, String e_id, String rent_date, String return_date) {
		super();
		this.c_id = c_id;
		this.b_id = b_id;
		this.e_id = e_id;
		this.rent_date = rent_date;
		this.return_date = return_date;
	}
	
	// getter&setter
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public String getB_id() {
		return b_id;
	}
	public void setB_id(String b_id) {
		this.b_id = b_id;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getRent_date() {
		return rent_date;
	}
	public void setRent_date(String rent_date) {
		this.rent_date = rent_date;
	}
	public String getReturn_date() {
		return return_date;
	}
	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}
}
