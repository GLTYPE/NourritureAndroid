package com.gltype.nourriture.model;

import java.util.List;

public class Recipe {
	
	private String _id;
	private String name;
	private String discription = "";
	private int value;
	private String ownerId;
	private String ings = "";
	private String rate = "";
	private String picture = "";
	private String moments = "";
	private String __v = "";
	
	public Recipe() {}
	
	public Recipe(String name, String pic) {
		this.name = name;
		this.picture = pic;
	}
	
	public Recipe(String name, String discription, int value, String ownerId,
			String ings) {
		super();
		this.name = name;
		this.discription = discription;
		this.value = value;
		this.ownerId = ownerId;
		this.ings = ings;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getIngs() {
		return ings;
	}
	public void setIngs(String ings) {
		this.ings = ings;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getMoments() {
		return moments;
	}
	public void setMoments(String moments) {
		this.moments = moments;
	}
	public String get__v() {
		return __v;
	}
	public void set__v(String __v) {
		this.__v = __v;
	}
	

}
