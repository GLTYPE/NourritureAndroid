package com.gltype.nourriture.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ParamsWrapper {

	public final ArrayList<NameValue> nameValueArray = new ArrayList<NameValue>();
	public final ArrayList<PathParam> pathParamArray = new ArrayList<PathParam>();
	
	public static class NameValue{
		public final String name;
		public final String value;
		public NameValue(String name,Object value){
			this.name = name;
			this.value = String.valueOf(value);
		}
	}
	
	public static class PathParam{
		public final NameValue param;
		public final String path;
		public PathParam(String name, Object value, String path){
			this.param = new NameValue(name,value);
			this.path = path;
		}
	}
	
	public ParamsWrapper put(String name,String value){
		appendToParamsArray(name,value);
		return this;
	}
	
	public ParamsWrapper put(String name,int value){
		appendToParamsArray(name,value);
		return this;
	}

	public ParamsWrapper put(String name,boolean value){
		appendToParamsArray(name,value);
		return this;
	}

	public ParamsWrapper put(String name,float value){
		appendToParamsArray(name,value);
		return this;
	}

	public ParamsWrapper put(String name,long value){
		appendToParamsArray(name,value);
		return this;
	}

	public ParamsWrapper put(String name,double value){
		appendToParamsArray(name,value);
		return this;
	}
	
	public ParamsWrapper put(String name,String fileName,String path){
		pathParamArray.add(new PathParam(name, fileName, path));
		return this;
	}

	public boolean containsValue(String name){
		boolean contains = false;
		if( name == null ) return contains;
		for(NameValue item : nameValueArray){
			if( name.equalsIgnoreCase( item.name )){
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	private ParamsWrapper appendToParamsArray(String name,Object value){
		if(name != null && value != null 
				&& !"".equals(name) && !"".equals(value) ){
			nameValueArray.add(new NameValue(name, value));
		}
		return this;
	}
	

	
	
	public String getStringParams() throws UnsupportedEncodingException{
		return getStringParams("utf-8");
	}
	
	public String getStringParams(String urlEncoding) throws UnsupportedEncodingException{
		StringBuilder buffer = new StringBuilder();
		for(NameValue param : nameValueArray){
			buffer.append(param.name).append("=")
				.append(URLEncoder.encode(param.value,urlEncoding))
				.append("&");
		}
		if(buffer.length()>0) buffer.deleteCharAt(buffer.length()-1);
		return buffer.length() > 0 ? buffer.toString() : null;
	}
	
}
