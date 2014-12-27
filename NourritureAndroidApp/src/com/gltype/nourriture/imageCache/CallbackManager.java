package com.gltype.nourriture.imageCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;


public class CallbackManager {
	
	private ConcurrentHashMap<String, List<ImageLoaderCallback>> callbackMap;
	
	public CallbackManager(){
		callbackMap = new ConcurrentHashMap<String, List<ImageLoaderCallback>>();
	}
	
	
	public void put(String url, ImageLoaderCallback callback){//r
		if(!callbackMap.contains(url)){
			callbackMap.put(url, new ArrayList<ImageLoaderCallback>());
		}
		callbackMap.get(url).add(callback);
	}
	
	
	public void callback(String url,Bitmap bitmap){//r
		List<ImageLoaderCallback> callbacks= callbackMap.get(url);
		if(null ==callbacks){
			return;
		}
		
		for(ImageLoaderCallback callback : callbacks){
			if(null!= callback){
				callback.refesh(url, bitmap);
			}
		}
		callbacks.clear();
		callbackMap.remove(url);
		
	}
}
