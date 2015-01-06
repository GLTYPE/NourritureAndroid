package com.gltype.nourriture.ui;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gltype.nourriture.model.User;
import com.gltype.nurriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class EditUserProfile extends Activity implements OnClickListener {
	
	private User user;
	private EditText et_firstname, et_lastname,
					 et_email, et_password, et_passwordAgain;
	private Button btn_save, btn_cancel;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_user_profile);
		Intent intent = this.getIntent();
		displayUserInfo(intent);	
		
	}


	private void displayUserInfo(Intent intent) {
		this.user = (User) intent.getSerializableExtra("user");
		et_firstname = (EditText) findViewById(R.id.et_firstname);
		et_lastname = (EditText) findViewById(R.id.et_lastname);
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		et_passwordAgain = (EditText) findViewById(R.id.et_password_again);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		et_firstname.setText(user.getFirstname());
		et_lastname.setText(user.getLastname());
		et_email.setText(user.getEmail());
		et_password.setText(user.getPassword());
		et_passwordAgain.setText(user.getPassword());
		
		btn_save.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_cancel:
			Intent intent = new Intent(EditUserProfile.this, MainActivity.class);
			startActivity(intent);
		case R.id.btn_save:
			editUserInfo();
		}		
	}

	private void editUserInfo() {
		String psw = et_password.getText().toString();
		String psw_again = et_passwordAgain.getText().toString();
		
		if(psw.equals(psw_again)) {
			
			user.setFirstname(et_firstname.getText().toString());
			user.setLastname(et_lastname.getText().toString());
			user.setEmail(et_email.getText().toString());
			user.setUserId(this.user.getUserId());
			if(!(TextUtils.isEmpty(psw)))
				user.setPassword(psw);
			
			editUserByAsyncHttpClientPut(user);
			finish();
		} else {
			Toast.makeText(getBaseContext(), "password not match", Toast.LENGTH_SHORT).show();
		}
		
	}


	private void editUserByAsyncHttpClientPut(User user) {
		AsyncHttpClient client = new AsyncHttpClient();
		
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/"+user.getUserId();
		
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("firstname", user.getFirstname());
			jsonObj.put("lastname", user.getLastname());
			jsonObj.put("email", user.getEmail());
			jsonObj.put("token", LoginActivity.token);
			if(!(TextUtils.isEmpty(user.getPassword())))
				jsonObj.put("password", user.getPassword());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(jsonObj.toString());
			Toast.makeText(EditUserProfile.this, jsonObj.toString(), Toast.LENGTH_LONG).show();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	        
		client.put(EditUserProfile.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				Toast.makeText(EditUserProfile.this, "Edit Success", Toast.LENGTH_SHORT).show();
				super.onSuccess(statusCode, headers, response);
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				Toast.makeText(EditUserProfile.this, "Edit Fail", Toast.LENGTH_SHORT).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
	
	
}
