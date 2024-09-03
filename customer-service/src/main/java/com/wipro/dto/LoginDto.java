package com.wipro.dto;

public class LoginDto {
	 private String userEmail;
	 private String password;
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginDto(String userEmail, String password) {
		super();
		this.userEmail = userEmail;
		this.password = password;
	}
	
	 
}
