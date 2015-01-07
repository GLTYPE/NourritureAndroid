package com.gltype.nourriture.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.User;
import com.gltype.nourriture.utils.RoleUtil;
import com.gltype.nurriture.R;

public class UserDetialActivity extends Activity {

	private User user;
	private ImageView img_picture;
	private TextView userFirstname, userLastname,userAbout, userEmail, userRole;
	
	private Button btn_edit;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_detail);
		Intent intent = this.getIntent();
		this.user = (User) intent.getSerializableExtra("user");
		displayUserInfo();	

		btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserDetialActivity.this, UserEditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
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
		
		btn_edit = (Button) findViewById(R.id.btn_edit);
		
		userFirstname.setText(user.getFirstname());
		userLastname.setText(user.getLastname());
		userRole.setText(new RoleUtil(user.getRole()).getRoleStr());
		userEmail.setText(user.getEmail());
		userAbout.setText(user.getAbout());
		if (TextUtils.isEmpty(user.getPicture()))
			img_picture.setImageResource(R.drawable.avatar_tomato);
		
	}
}
