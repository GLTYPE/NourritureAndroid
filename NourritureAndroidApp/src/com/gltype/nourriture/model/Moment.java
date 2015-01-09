package com.gltype.nourriture.model;

import java.io.Serializable;

import org.json.JSONArray;

public class Moment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String username;
	private String ownId;
	private String targetId;
	private String content;
	private String time;
	private String pictureurl;
	private String contentimg;
	private String commentArray;
	public Moment(){
		
	}
	public Moment(String username,String content,String pictureurl){
		this.username = username;
		this.content = content;
		this.pictureurl = pictureurl;
	}
	
	public Moment(String name,String time){
		this.name = name;
		this.time = time;
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
	public String getContentimg() {
		return contentimg;
	}
	public void setContentimg(String contentimg) {
		this.contentimg = contentimg;
	}
	public String getCommentArray() {
		return commentArray;
	}
	public void setCommentArray(String commentArray) {
		this.commentArray = commentArray;
	}
	
}
