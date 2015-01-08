package com.gltype.nourriture.ui;


import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.model.User;
import com.gltype.nurriture.R; 

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

public class RegisterActivity extends Activity {
	private EditText et_email;
	private EditText et_password;
	private EditText et_firstname;
	private EditText et_lastname;
	private EditText et_role;

	private Button loginBtn;
	private Button signupBtn;
	private View progresView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		et_email=(EditText) findViewById(R.id.et_email);
		et_password=(EditText) findViewById(R.id.et_password);
		et_firstname=(EditText) findViewById(R.id.et_firstname);
		et_lastname=(EditText) findViewById(R.id.et_lastname);
		et_role=(EditText) findViewById(R.id.et_role);
		
		signupBtn = (Button) findViewById(R.id.signupButton);
		//progresView = findViewById(R.id.login_progress);
		
		signupBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String email = et_email.getText().toString(); 
	            String password = et_password.getText().toString();  
	            String firstname = et_firstname.getText().toString(); 
	            String lastname = et_lastname.getText().toString(); 
	            String role = et_role.getText().toString(); 
	            User user= new User(email, password, firstname, lastname, role);
	           
	            if (TextUtils.isEmpty(email.trim())  
	                    || TextUtils.isEmpty(password.trim())) {  
	                Toast.makeText(v.getContext(), "Email or Password can't be empty", Toast.LENGTH_LONG).show();  
	            } else {  
	                
	                loginByAsyncHttpClientPost(user);  
	                //loginByAsyncHttpClientGet(userName, userPass);  
	            }				
			}
		});	
		
		
	}  
   // firstname, lastname, picture, email, about, role, password
    public void loginByAsyncHttpClientPost(User user) {  
        AsyncHttpClient client = new AsyncHttpClient(); 
        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users"; 

        JSONObject jsonObject = new JSONObject();
        try {
        	jsonObject.put("firstname", user.getFirstname());
        	jsonObject.put("lastname", user.getLastname());
        	jsonObject.put("email", user.getEmail());  
        	jsonObject.put("role", user.getRole());
        	jsonObject.put("password", user.getPassword());
			
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

        client.post(RegisterActivity.this, url, stringEntity, "application/json",  new AsyncHttpResponseHandler() {  
         
            @Override  
            public void onSuccess(int statusCode, Header[] headers,  
                    byte[] responseBody) {  
                    
                  // String resResult = new String(responseBody);  	
            	 Toast.makeText(RegisterActivity.this,"Register Successfully" , Toast.LENGTH_LONG).show(); 
                    	Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    	startActivity(intent);
                    	//progresView.setVisibility(View.GONE);
                  
            }  
  
           
            @Override  
            public void onFailure(int statusCode, Header[] headers,  
                    byte[] responseBody, Throwable error) {  
            	// System.out.println("F-----------------"+statusCode);
            	 String resResult = new String(responseBody);
            	 //System.out.println("F-----------------"+resResult); 
            	 Toast.makeText(RegisterActivity.this,resResult , Toast.LENGTH_LONG).show(); 
            
                error.printStackTrace();
            }  
        });  
    }  
   

	

}