package com.gltype.nourriture.ui;


import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.R; 

import com.loopj.android.http.AsyncHttpClient;  
import com.loopj.android.http.AsyncHttpResponseHandler;  
import com.loopj.android.http.RequestParams;  
  
import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.text.TextUtils;  
import android.view.LayoutInflater;
import android.view.View;  
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;  
import android.widget.TextView;  
import android.widget.Toast;  

public class LoginActivity extends Activity {
	private EditText et_email;
	private EditText et_password;
	private TextView tv_result;
	private Button loginBtn;
	private Button signupBtn;
	private View progresView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		et_email=(EditText) findViewById(R.id.user_email);
		et_password=(EditText) findViewById(R.id.password);
		tv_result = (TextView) findViewById(R.id.tv_result);
		loginBtn = (Button) findViewById(R.id.loginButton);
		signupBtn = (Button) findViewById(R.id.signupButton);
		//progresView = findViewById(R.id.login_progress);
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName = et_email.getText().toString(); 
	            String userPass = et_password.getText().toString();  
	           
	            if (TextUtils.isEmpty(userName.trim())  
	                    || TextUtils.isEmpty(userPass.trim())) {  
	                Toast.makeText(v.getContext(), "Email or Password can't be empty", Toast.LENGTH_LONG).show();  
	            } else {  
	                
	                loginByAsyncHttpClientPost(userName, userPass);  
	                //loginByAsyncHttpClientGet(userName, userPass);  
	            	
	            }				
			}
		});	
		
		//test
		signupBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);				
			}
		});
	}  
   
    public void loginByAsyncHttpClientPost(String userName, String userPass) {  
    	 AsyncHttpClient client = new AsyncHttpClient(); 
         String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/connect"; 

         JSONObject jsonObject = new JSONObject();
         try {
			jsonObject.put("email", userName);  
			 jsonObject.put("password", userPass);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         StringEntity stringEntity = null; 
         try {
			stringEntity = new StringEntity(jsonObject.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

         client.post(LoginActivity.this, url, stringEntity, "application/json",  new AsyncHttpResponseHandler() {  
          
             @Override  
             public void onSuccess(int statusCode, Header[] headers,  
                     byte[] responseBody) {  
                // if (statusCode == 200) {  
                     
                    String resResult = new String(responseBody);
                  
                     	tv_result.setText(resResult); 
                     	Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     	startActivity(intent);
                     	//progresView.setVisibility(View.GONE);
                   
             }  
   
            
             @Override  
             public void onFailure(int statusCode, Header[] headers,  
                     byte[] responseBody, Throwable error) {  
             	// System.out.println("F-----------------"+statusCode);
             	 String resResult = new String(responseBody);
             	 //System.out.println("F-----------------"+resResult); 
             	tv_result.setText(resResult); 
                 error.printStackTrace();
             }  
         });  
       
    }  
  
    
    public void loginByAsyncHttpClientGet(String userName, String userPass) {  
      
        AsyncHttpClient client = new AsyncHttpClient();  
      
        String url = "http://172.29.64.31:8000/HelloWorld/servlet/LoginServlet";  
       
        RequestParams params = new RequestParams();  
        params.put("email", userName);
        params.put("password", userPass);
          
       
        client.get(url, params,new AsyncHttpResponseHandler() {  
            @Override  
            public void onSuccess(int statusCode, Header[] headers,  
                    byte[] responseBody) {  
               
                System.out  
                        .println("statusCode-------------------" + statusCode);  
               
                for (int i = 0; i < headers.length; i++) {  
                    Header header = headers[i];  
                    System.out.println("header------------Name:"  
                            + header.getName() + ",--Value:"  
                            + header.getValue());  
                }  
              
                tv_result.setText(new String(responseBody));  
            }  
  
            @Override  
            public void onFailure(int statusCode, Header[] headers,  
                    byte[] responseBody, Throwable error) {  
               
                error.printStackTrace();  
            }  
        });  
    }  

	

}
