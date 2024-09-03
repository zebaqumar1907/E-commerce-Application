package com.wipro.dto;

public class CustomerDto {
	private String customerName;
	private String customerEmail;
	private String shippingAddress;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public CustomerDto(String customerName, String customerEmail, String shippingAddress) {
		super();
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.shippingAddress = shippingAddress;
	}
	public CustomerDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
