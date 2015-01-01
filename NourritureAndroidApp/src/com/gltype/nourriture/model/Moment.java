package com.gltype.nourriture.model;

public class Moment {
	private String id;
	private String username;
	
	private String content;
	private String time;
	private String pictureurl;
	public Moment(){
		
	}
	public Moment(String username,String content,String pictureurl){
		this.username = username;
		this.content = content;
		this.pictureurl = pictureurl;
	}
	
	public String getPictureurl() {
		return pictureurl;
	}
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
