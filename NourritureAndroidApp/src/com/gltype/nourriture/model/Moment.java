package com.gltype.nourriture.model;

public class Moment {
	private int id;
	private String username;
	
	private String content;
	private String time;
	public Moment(){
		
	}
	public Moment(String username,String content){
		this.username = username;
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
