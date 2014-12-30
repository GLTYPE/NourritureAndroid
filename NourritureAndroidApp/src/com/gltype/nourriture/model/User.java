package com.gltype.nourriture.model;

public class User {
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String role;
	
	
	public User(String email,String password,String firstname,String lastname,String role){
		this.email=email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
		
		
	}
	public User(){}
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

}
