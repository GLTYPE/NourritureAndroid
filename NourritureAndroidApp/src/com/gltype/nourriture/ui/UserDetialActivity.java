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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.db.dao.UserDao;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.User;
import com.gltype.nourriture.utils.MyActivityManager;
import com.gltype.nourriture.utils.RoleUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserDetialActivity extends Activity {

	private User user;
	private ImageView img_picture;
	private TextView userFirstname, userLastname,userAbout, userEmail, userRole;
	
	private Button btn_edit;	
	private Button btn_signout;	
	private UserDao dao= new UserDao(this);
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private ImageButton btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_detail);
		Intent intent = this.getIntent();
		this.user = (User) intent.getSerializableExtra("user");
		displayUserInfo();	

		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(ImageButton) titleView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(UserDetialActivity.this);
				
			}
		});
		btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserDetialActivity.this, UserEditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivity(intent);
				mam.popOneActivity(UserDetialActivity.this);
			}
		});
		
		btn_signout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				signOut();
			}
		});
		
	}


	private void displayUserInfo() {
		img_picture = (ImageView) findViewById(R.id.img_picture);
		userFirstname = (TextView) findViewById(R.id.tv_firstname);
		userLastname = (TextView) findViewById(R.id.tv_lastname);
		userRole = (TextView) findViewById(R.id.tv_role);
		userEmail = (TextView) findViewById(R.id.tv_email);
		userAbout = (TextView) findViewById(R.id.tv_about);
		btn_signout= (Button) findViewById(R.id.btn_signout);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		
		userFirstname.setText(user.getFirstname());
		userLastname.setText(user.getLastname());
		userRole.setText(new RoleUtil(user.getRole()).getRoleStr());
		userEmail.setText(user.getEmail());
		userAbout.setText(user.getAbout());
		SimpleImageLoader.showImg(img_picture, user.getPicture());
		
	}
	
	 public void signOut() {  
    	 AsyncHttpClient client = new AsyncHttpClient(); 
         String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/disconnect"; 

         JSONObject jsonObject = new JSONObject();
         try {
			jsonObject.put("token", LoginActivity.token);  
			 
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

         client.post(UserDetialActivity.this, url, stringEntity, "application/json",  new JsonHttpResponseHandler() {  
         
        	 @Override
        	public void onSuccess(int statusCode, Header[] headers,
        			JSONObject response) {
        		
        		 dao.delete(LoginActivity.token);
        		 Intent intent = new Intent();  
        		 intent.setClass(UserDetialActivity.this, LoginActivity.class);  
        		 mam.finishAllActivity();
        		 startActivity(intent); 
        		
        		super.onSuccess(statusCode, headers, response);
        	}
            @Override
            public void onFailure(int statusCode, Header[] headers,
            		String responseString, Throwable throwable) {
            	
            
            	super.onFailure(statusCode, headers, responseString, throwable);
            }   
           
         });  
       
    }  
	
	 @Override
	protected void onDestroy() {
		mam.popOneActivity(this);
		super.onDestroy();
	}
}
