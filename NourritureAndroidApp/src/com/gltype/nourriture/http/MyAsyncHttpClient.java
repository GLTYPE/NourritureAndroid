package com.gltype.nourriture.http;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.gltype.nourriture.utils.ParamsWrapper;
import com.gltype.nourriture.utils.StringTools;
import com.gltype.nourriture.utils.ParamsWrapper.NameValue;


import android.os.Message;
public class MyAsyncHttpClient {
	protected static final int SUCCESS = 1;
	protected static final int FAILURE = 2;
	
	
	public void get(final String url,final ParamsWrapper params, final MyHandler myhandler){
		verifyUrl(url);
		new Thread(){
			public void run(){
				HttpClient client =new DefaultHttpClient(); 
			
				try {
					HttpGet httpGet = new HttpGet(url+params.getStringParams());
					HttpResponse response = client.execute(httpGet);
					InputStream is = response.getEntity().getContent();
					String content = StringTools.getInputStream(is);
					Message msg = new Message();
					msg.what=SUCCESS;
					msg.obj= content;
					myhandler.sendMessage(msg);
				} catch (Exception e) {
				
					Message msg = new Message();
					msg.what=FAILURE;
					msg.obj = "Fail to requset";
					myhandler.sendMessage(msg);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}//get
	
	public void post(final String url,final ParamsWrapper params,final MyHandler myhandler){
		verifyUrl(url);
		new Thread(){
			public void run(){
				HttpClient client =new DefaultHttpClient(); 
				HttpPost httpPost = new HttpPost(url);
				try {
					List<NameValuePair> parameters = new ArrayList<NameValuePair>();
					for(NameValue item :params.nameValueArray){
						parameters.add(new BasicNameValuePair(item.name, item.value));
					}
					
					httpPost.setEntity(new UrlEncodedFormEntity(parameters,"UTF-8"));
					HttpResponse response = client.execute(httpPost);
					InputStream is = response.getEntity().getContent();
					String content = StringTools.getInputStream(is);
					Message msg = new Message();
					msg.what=SUCCESS;
					msg.obj= content;
					myhandler.sendMessage(msg);
				} catch (Exception e) {
				
					Message msg = new Message();
					msg.what=FAILURE;
					msg.obj = "Fail to requset";
					myhandler.sendMessage(msg);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		
	}
	
	
	public void delete(final String url,final JSONObject jsonObject,final MyHandler myhandler){
		verifyUrl(url);
		new Thread(){
			public void run(){
				HttpClient client =new DefaultHttpClient(); 
				HttpDeleteWithBody httpDelete= new HttpDeleteWithBody(url);
				httpDelete.setHeader("Content-Type", "application/json; charset=UTF-8");
				httpDelete.setHeader("X-Requested-With", "XMLHttpRequest");
				 StringEntity stringEntity = null; 
				try {
					stringEntity = new StringEntity(jsonObject.toString());
					
					httpDelete.setEntity(stringEntity);
					HttpResponse response = client.execute(httpDelete);
					String content="Delete successfully!";
				
					//InputStream is = response.getEntity().getContent();
					//content = StringTools.getInputStream(is);
					
					Message msg = new Message();
					msg.what=SUCCESS;
					msg.obj= content;
					msg.arg1=response.getStatusLine().getStatusCode();
					myhandler.sendMessage(msg);
				} catch (Exception e) {
				
					Message msg = new Message();
					msg.what=FAILURE;
					msg.obj = "Fail to requset";
					//msg.arg1=response.getStatusLine().getStatusCode();
					myhandler.sendMessage(msg);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		
	}
		
		
	
	
	private void verifyUrl(String url){
		if(url == null) throw new IllegalArgumentException("Connection url cannot be null");
	}

} //class
