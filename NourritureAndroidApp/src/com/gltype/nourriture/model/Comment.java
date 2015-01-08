package com.gltype.nourriture.model;

import java.io.Serializable;

public class Comment implements Serializable{
	private String id;
	private String username;
	private String ownId;
	private String targetId;
	private String content;
	private String date;
	private String pictureurl;
	public Comment(){
		
	}
	public Comment(String username,String content,String pictureurl,String date){
		this.username = username;
		this.content = content;
		this.pictureurl = pictureurl;
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOwnId() {
		return ownId;
	}
	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPictureurl() {
		return pictureurl;
	}
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}
	
	
}
