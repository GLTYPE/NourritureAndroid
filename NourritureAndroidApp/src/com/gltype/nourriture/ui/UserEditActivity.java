package com.gltype.nourriture.ui;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gltype.nourriture.model.User;
import com.gltype.nourriture.utils.MyActivityManager;
import com.gltype.nourriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserEditActivity extends Activity {
	
	private User user;
	private boolean isRight;
//	private ImageView img_picture;
	private EditText et_picture, et_firstname, et_lastname,et_about,
					 et_email, et_password, et_passwordAgain, et_old_password;
	private String old_email;
	private Button btn_save;	
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_edit);
		Intent intent = this.getIntent();
		this.user = (User) intent.getSerializableExtra("user");
		displayUserInfo();	
		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(Button) titleView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(UserEditActivity.this);
				
			}
		});
	}


	private void displayUserInfo() {
		et_picture = (EditText) findViewById(R.id.et_picture);
		et_firstname = (EditText) findViewById(R.id.et_firstname);
		et_lastname = (EditText) findViewById(R.id.et_lastname);
		et_email = (EditText) findViewById(R.id.et_email);
		et_about = (EditText) findViewById(R.id.et_about);
		
		et_old_password = (EditText) findViewById(R.id.et_old_password);
		et_password = (EditText) findViewById(R.id.et_password);
		et_passwordAgain = (EditText) findViewById(R.id.et_password_again);
			
		btn_save = (Button) findViewById(R.id.btn_save);	
		
		old_email = user.getEmail();
		
		et_picture.setText(user.getPicture());
		et_firstname.setText(user.getFirstname());
		et_lastname .setText(user.getLastname());
		et_email.setText(user.getEmail());
		et_about.setText(user.getAbout());
		
		btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editUserInfo(user);
			}
		});
	}

	private void editUserInfo(User user) {
		String psw = et_password.getText().toString();
		String psw_again = et_passwordAgain.getText().toString();
		String old_psd = et_old_password.getText().toString();
		
		if(TextUtils.isEmpty(old_psd)) {
			Toast.makeText(getBaseContext(), "Input old password", Toast.LENGTH_SHORT).show();		
			
		} else if (!TextUtils.equals(psw, psw_again)) {
			Toast.makeText(getBaseContext(), "new password not same", Toast.LENGTH_LONG).show();
//			
//		} else if (!isRightPwd(old_email, old_psd)) {
//			Toast.makeText(getBaseContext(), "wrong password", Toast.LENGTH_LONG).show();
//		
		} else {
			
			user.setFirstname(et_firstname.getText().toString());
			user.setLastname(et_lastname.getText().toString());
			user.setEmail(et_email.getText().toString());
//			String picPath = 
			user.setPicture(et_picture.getText().toString());
			user.setAbout(et_about.getText().toString());
			
			if(!(TextUtils.isEmpty(psw))) {
				user.setPassword(psw);
			} else {	
				user.setPassword(old_psd);
			}
			
			editUserByAsyncHttpClientPut(user);
			finish();
		}
		
	}



	private void editUserByAsyncHttpClientPut(User user) {
		AsyncHttpClient client = new AsyncHttpClient();
		
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/"+user.getUserId();
		
		JSONObject jsonObj = new JSONObject();
		try {
//			{"_id":"54ac22d35bef250646e9d70d","about":"My new about","email":"wangyitong@163.com","firstname":"Yitong","lastname":"Wang","picture":"My pic","role":3,"photos":[],"movies":[],"moments":[]}
//			firstname, lastname, picture, email, about, password
			jsonObj.put("firstname", user.getFirstname());
			jsonObj.put("lastname", user.getLastname());
			jsonObj.put("picture", user.getPicture());
			jsonObj.put("email", user.getEmail());
			jsonObj.put("password", user.getPassword());
			jsonObj.put("about", user.getAbout());
			jsonObj.put("token", LoginActivity.token);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(jsonObj.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

		
		client.put(UserEditActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				Toast.makeText(UserEditActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
				mam.popOneActivity(UserEditActivity.this);
				super.onSuccess(statusCode, headers, response);
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				Toast.makeText(UserEditActivity.this, "Edit Fail", Toast.LENGTH_SHORT).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
	
	private boolean isRightPwd(String email, String password) { 
		AsyncHttpClient client = new AsyncHttpClient(); 
	    String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/connect"; 

	    JSONObject jsonObject = new JSONObject();
	    try {
	    	jsonObject.put("email", email);
	    	jsonObject.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        StringEntity stringEntity = null; 
        try {
        	stringEntity = new StringEntity(jsonObject.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
        client.post(UserEditActivity.this, url, stringEntity, "application/json",  new JsonHttpResponseHandler() {
        	
        	@Override
        	public void onSuccess(int statusCode, Header[] headers,
        			JSONObject response) {
        		isRight = true;
        	}
            @Override
            public void onFailure(int statusCode, Header[] headers,
            		String responseString, Throwable throwable) {
            	isRight = false;
            }   
	           
	    }); 
		Toast.makeText(UserEditActivity.this,new Boolean(isRight).toString(), Toast.LENGTH_SHORT).show();
        
		return isRight;
	}
	@Override
	protected void onDestroy() {
		mam.popOneActivity(this);
		super.onDestroy();
	}
}
