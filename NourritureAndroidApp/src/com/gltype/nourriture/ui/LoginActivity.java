package com.gltype.nourriture.ui;


import com.gltype.nourriture.http.AsyncHttpClient;
import com.gltype.nourriture.http.MyHandler;
import com.gltype.nourriture.utils.ParamsWrapper;
import com.gltype.nurriture.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText et_email;
	private EditText et_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		et_email=(EditText) findViewById(R.id.emailText);
		et_password=(EditText) findViewById(R.id.passwordText);
		
	}
	public void onClick(View view){
		String email = et_email.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		String url = "http://172.29.64.31:8000/HelloWorld/servlet/LoginServlet?";
	
		ParamsWrapper params= new ParamsWrapper();
		params.put("email", email);
		params.put("password", password);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,params, new MyHandler(){
			@Override
			public void onSuccess(String content) {
				Toast.makeText(LoginActivity.this,content,0).show();
				super.onSuccess(content);
			}
			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this,content,0).show();
				super.onFailure(content);
			}
		});
	}
	public void onClick_Post(View view){
		String email = et_email.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		String url = "http://172.28.34.137:8000/HelloWorld/servlet/LoginServlet";
		ParamsWrapper params= new ParamsWrapper();
		params.put("email", email);
		params.put("password", password);
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(url, params, new MyHandler(){
			@Override
			public void onSuccess(String content) {
				//Toast.makeText(LoginActivity.this,content,0).show();
				 Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			     startActivity(intent);
				super.onSuccess(content);
			}
			@Override
			public void onFailure(String content) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this,content,0).show();
				super.onFailure(content);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
