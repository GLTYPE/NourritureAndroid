package com.gltype.nourriture.model;

import java.io.Serializable;

public class Product implements Serializable{
	private long id;
	private String name;
	private String productid;
	private String picture="";
	private String description="";
	private String brand="";
	private String ings="";
	private String value="";
	private String ownerId;
	public Product(String name){
		this.name = name;
	}
	
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getIngs() {
		return ings;
	}
	public void setIngs(String ings) {
		this.ings = ings;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	

}
