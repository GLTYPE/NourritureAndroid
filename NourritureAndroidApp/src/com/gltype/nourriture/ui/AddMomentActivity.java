package com.gltype.nourriture.ui;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.model.Moment;

import com.gltype.nurriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMomentActivity extends Activity {
	
	
	
	private EditText et_desc;
	private EditText et_pic;
	public Button addButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.add_moment);
		et_desc=(EditText) findViewById(R.id.et_desc);
		et_pic=(EditText) findViewById(R.id.et_picture);	
		addButton = (Button) findViewById(R.id.bt_addmomcommit);
		//progresView = findViewById(R.id.login_progress);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				String desc = et_desc.getText().toString(); 
	            String pic = et_pic.getText().toString();
	            
	           
	            
	          Moment moment = new Moment(HomeFragment.userName, desc, pic);
	          SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss"); 
	         
	          String date  =  sDateFormat.format(new java.util.Date());
	          System.out.println("-------------"+date);
	          date = date.replace("T"," ");
	          date = date.substring(0,18);
	          moment.setTime(date);
	          System.out.println("-------------"+date);
	            if (TextUtils.isEmpty(desc.trim())) {  
	                Toast.makeText(v.getContext(), "Say something!", Toast.LENGTH_LONG).show();  
	            } else {  
	                
	            	addMomemt(moment);  
	                
	            } 			
			}
		});	
		
		super.onCreate(savedInstanceState);
		
	}
	
	
	 public void addMomemt(Moment moment) {  
	        AsyncHttpClient client = new AsyncHttpClient(); 
	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments"; 

	        JSONObject jsonObject = new JSONObject();
	        try {
	        	jsonObject.put("name", moment.getUsername());
	        	jsonObject.put("description",moment.getContent() );
	        	jsonObject.put("date", moment.getTime());  
	        	jsonObject.put("picture",moment.getPictureurl() );
	        	jsonObject.put("token", LoginActivity.token);
				
			} catch (JSONException e) {
			
				e.printStackTrace();
			}
	        StringEntity stringEntity = null; 
	        try {
				stringEntity = new StringEntity(jsonObject.toString());
			} catch (UnsupportedEncodingException e) {
		
				e.printStackTrace();
			} 

	        client.post(AddMomentActivity.this, url, stringEntity, "application/json",  new AsyncHttpResponseHandler() {  
	         
	            @Override  
	            public void onSuccess(int statusCode, Header[] headers,  
	                    byte[] responseBody) {  
	                    
	                  // String resResult = new String(responseBody);  	
	            	 Toast.makeText(AddMomentActivity.this,"Add a new moment" , Toast.LENGTH_LONG).show(); 
	                    	finish();
	                    	//progresView.setVisibility(View.GONE);
	                  
	            }  
	  
	           
	            @Override  
	            public void onFailure(int statusCode, Header[] headers,  
	                    byte[] responseBody, Throwable error) {  
	            	// System.out.println("F-----------------"+statusCode);
	            	 String resResult = new String(responseBody);
	            	 System.out.println("F-----------------"+resResult); 
	            	 Toast.makeText(AddMomentActivity.this,resResult , Toast.LENGTH_LONG).show(); 
	            
	                error.printStackTrace();
	            }  
	        });  
	    }  

}
