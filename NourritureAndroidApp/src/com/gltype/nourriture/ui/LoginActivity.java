package com.gltype.nourriture.ui;


import org.apache.http.Header;

import com.gltype.nurriture.R; 

import com.loopj.android.http.AsyncHttpClient;  
import com.loopj.android.http.AsyncHttpResponseHandler;  
import com.loopj.android.http.RequestParams;  
  
import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.text.TextUtils;  
import android.view.View;  
import android.widget.EditText;  
import android.widget.TextView;  
import android.widget.Toast;  

public class LoginActivity extends Activity {
	private EditText et_email;
	private EditText et_password;
	 private TextView tv_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		et_email=(EditText) findViewById(R.id.et_email);
		et_password=(EditText) findViewById(R.id.et_pass);
		tv_result = (TextView) findViewById(R.id.tv_result); 
		
	}
	public void login(View v) {  
       
        int id = v.getId();  
       
        switch (id) {  
        case R.id.btn_login:  
           
            String userName = et_email.getText().toString(); 
            String userPass = et_password.getText().toString();  
           
            if (TextUtils.isEmpty(userName.trim())  
                    || TextUtils.isEmpty(userPass.trim())) {  
                Toast.makeText(this, "Email or Password can't be empty", Toast.LENGTH_LONG).show();  
            } else {  
                
                loginByAsyncHttpClientPost(userName, userPass);  
                //loginByAsyncHttpClientGet(userName, userPass);  
            }  
            break;  
        }  
    }  
  
   
    public void loginByAsyncHttpClientPost(String userName, String userPass) {  
        AsyncHttpClient client = new AsyncHttpClient(); 
        String url = "http://172.29.64.31:8000/HelloWorld/servlet/LoginServlet"; 

        RequestParams params = new RequestParams();  
        params.put("email", userName);  
        params.put("password", userPass);
       
        client.post(url, params, new AsyncHttpResponseHandler() {  
           
            @Override  
            public void onSuccess(int statusCode, Header[] headers,  
                    byte[] responseBody) {  
                if (statusCode == 200) {  
                    //tv_result.setText(new String(responseBody)); 
                   String resResult = new String(responseBody);
                    if("Login Success".equals(resResult)){
                    	Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    	startActivity(intent);
                    }
                    else{
                    	 tv_result.setText("Wrong Email or Password");
                    }
                }  
            }  
  
           
            @Override  
            public void onFailure(int statusCode, Header[] headers,  
                    byte[] responseBody, Throwable error) {  
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
