package com.gltype.nourriture.model;

import java.io.Serializable;


public class Ingredient implements Serializable{
	
	private String name;
	private String picture;
	private String description;
	private String value;
	private String faith;
	private String id;
	
	public Ingredient(String id,String name,String value,String picture,String description){
		this.id= id;
		this.name=name;
		this.value = value;
		this.description =description;
		this.picture = picture;
	}
	public Ingredient(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFaith() {
		return faith;
	}
	public void setFaith(String faith) {
		this.faith = faith;
	}
	
	
	
}
