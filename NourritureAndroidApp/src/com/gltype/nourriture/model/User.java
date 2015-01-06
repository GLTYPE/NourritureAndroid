package com.gltype.nourriture.model;

import java.io.Serializable;

public class User implements Serializable {
	private String userId;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private int role;
	private String picture;
	private String token;
	
	
	public User(String email,String password,String firstname,String lastname,int role){
		this.email=email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}
	public User(String email,String firstname,String lastname,int role){
		this.email=email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}
	public User(){}	
	public User(String token , int role){
		this.token = token;
		this.role = role;
		
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
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
	public int getRole() {
		//1 = consumer / 2 = food supplier / 3 = gastronomist / 4 = admin
//		switch(role) {
//		case 1: return "consumer";
//		case 2: return "food supplier";
//		case 3: return "gastronomist";
//		case 4: return "administer";
//		}
		return this.role;
	}
	
	public void setRole(int role) {
		this.role = role;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

}
