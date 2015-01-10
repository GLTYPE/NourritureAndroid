package com.gltype.nourriture.ui;


import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;


import com.gltype.nourriture.R; 
import com.gltype.nourriture.db.dao.UserDao;
import com.gltype.nourriture.model.User;
import com.gltype.nourriture.utils.MyActivityManager;

import com.loopj.android.http.AsyncHttpClient;  
import com.loopj.android.http.AsyncHttpResponseHandler;  

import com.loopj.android.http.JsonHttpResponseHandler;

import com.loopj.android.http.RequestParams;  
  
import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.text.TextUtils;  

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
	private TextView tv_signup;
	private TextView tv_forgetPwd;
	private View progresView;
	public static String token;
//	public static String userId;
	public static int role;
	MyActivityManager mam = MyActivityManager.getInstance();
	public boolean isConnect = false;
	public UserDao userDao = new UserDao(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		et_email=(EditText) findViewById(R.id.user_email);
		et_password=(EditText) findViewById(R.id.password);
		tv_result = (TextView) findViewById(R.id.tv_result);
		loginBtn = (Button) findViewById(R.id.loginButton);
		tv_signup = (TextView) findViewById(R.id.tv_signup);
		tv_forgetPwd = (TextView) findViewById(R.id.forgetPwd);
		//progresView = findViewById(R.id.login_progress);
		
		mam.pushOneActivity(this);
		User user = userDao.find();
		if(user!=null){
			isConnect = true;
			token = user.getToken();
			role = user.getRole();
//			userId = user.getUserId();
			
			System.out.println("-------------------"+role);
		}
		
		
		if(isConnect){
			Intent intent = new Intent(this, MainActivity.class);    
          	startActivity(intent);
          	mam.popOneActivity(this);
          	//tv_result.setText(token);
          	 Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show(); 
		}
		else{
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
			
			//Sign_up
			tv_signup.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
					startActivity(intent);				
				}
			});
			
			//test profile
			tv_forgetPwd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					
				}
			});
		}
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

         client.post(LoginActivity.this, url, stringEntity, "application/json",  new JsonHttpResponseHandler() {  
         
        	 @Override
        	public void onSuccess(int statusCode, Header[] headers,
        			JSONObject response) {
        		
				try {
					token = response.getString("token");
					role = response.getInt("role");
//					userId = response.getString("_id");
					
					isConnect = true;
					userDao.add(token, role);
					tv_result.setText("Login Successfully");
              	Intent intent = new Intent(LoginActivity.this, MainActivity.class);    
              	startActivity(intent);
              	mam.popOneActivity(LoginActivity.this);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		// TODO Auto-generated method stub
        		super.onSuccess(statusCode, headers, response);
        	}
            @Override
            public void onFailure(int statusCode, Header[] headers,
            		String responseString, Throwable throwable) {
            	// TODO Auto-generated method stub
            	tv_result.setText(responseString); 
            	super.onFailure(statusCode, headers, responseString, throwable);
            }   
           
         });  
       
    }  
  

	

}
