package com.gltype.nourriture.http;

import android.os.Handler;
import android.os.Message;

public class MyHandler extends Handler{
	

	public void onSuccess(String content,int status){
		
	}
	public void onFailure(String content){
		
	}
	
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		String content =(String) msg.obj;
		int status= msg.arg1;
		switch(msg.what){
		
			case 1:{
				
				onSuccess(content,status);
				break;
			}
			case 2:{
				
				onFailure(content);
				break;
			}
		}
		super.handleMessage(msg);
	}	
}
