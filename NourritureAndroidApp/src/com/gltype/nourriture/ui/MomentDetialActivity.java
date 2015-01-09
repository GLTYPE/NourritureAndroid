package com.gltype.nourriture.ui;


import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.adapter.CommentAdapter;
import com.gltype.nourriture.http.AsyncHttpClient;
import com.gltype.nourriture.http.MyHandler;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Comment;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.utils.MyActivityManager;

import com.gltype.nourriture.R;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MomentDetialActivity extends Activity {
		
	private Moment moment;
	private Button delButton;
	private Button editButton;
	private TextView tv_momentdescription;
	private TextView tv_name;
	private TextView tv_date;
	private ImageView userPhoto;
	private ImageView contentPic;
	public  List<Comment> comments;
	public ListView commentList;
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.moment_detial);
		Intent intent = this.getIntent(); 
		moment=(Moment)intent.getSerializableExtra("moment");
		delButton = (Button) findViewById(R.id.bt_momentdelete);
		tv_momentdescription = (TextView) findViewById(R.id.txt_item_content);
		tv_name = (TextView) findViewById(R.id.txt_item_uname);
		editButton = (Button) findViewById(R.id.bt_momentedit);
		userPhoto= (ImageView) findViewById(R.id.img_item_userphoto);
		contentPic= (ImageView) findViewById(R.id.img_item_content_pic);
		tv_date = (TextView)findViewById(R.id.txt_item_time);
		commentList= (ListView)findViewById(R.id.lv_comment);
		
		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(Button) titleView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(MomentDetialActivity.this);
				
			}
		});
		
		if("".equals(moment.getPictureurl())||moment.getPictureurl()==null){
			userPhoto.setImageResource(R.drawable.usericon);
		}else{
			SimpleImageLoader.showImg(userPhoto,moment.getPictureurl());
			
		}
		tv_momentdescription.setText(moment.getContent());
		tv_name.setText(moment.getUsername());
		tv_date.setText(moment.getTime());
		refresh();
		delButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				deleteMoment(moment.getId());
				
			}
		});
		
		
		editButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent intent =new Intent(MomentDetialActivity.this, MomentEditActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("moment",moment);
				  intent.putExtras(bundle);
				  startActivity(intent);
					mam.popOneActivity(MomentDetialActivity.this);
				
			}
		});
		
		
		super.onCreate(savedInstanceState);
	}
	
	
	
	public void refresh(){
		comments = new ArrayList<Comment>();
		Comment c1 = new Comment("Yitong Wang", "hahahaha", "", "2015-01-08 09:10:22");
		Comment c2 = new Comment("Yitong Wang", "GGGGGGGGG", "", "2015-01-08 09:10:22");
		Comment c3 = new Comment("Junhan Yan", "sadhgfdsg", moment.getPictureurl(), "2015-01-08 09:10:22");
		comments.add(c1);
		comments.add(c2);
		comments.add(c3);
		CommentAdapter commentAdapter = new CommentAdapter(this, comments);
		commentList.setAdapter(commentAdapter);
	}
	
	
	
	public void deleteMoment(String momentId) {  
	   	 AsyncHttpClient client = new AsyncHttpClient(); 
	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments/"+momentId; 
	        JSONObject jsonObject = new JSONObject();
	        try {
				jsonObject.put("token",LoginActivity.token);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
	        client.delete(url, jsonObject, new MyHandler(){
	        	@Override
	        	public void onSuccess(String content, int status) {
	        		if(status==204){
	        		tv_momentdescription.setText(status+" "+content);
	        		mam.popOneActivity(MomentDetialActivity.this);
	        		}
	        	};
	        	@Override
	        	public void onFailure(String content) {
	        		tv_momentdescription.setText(content);
	        		super.onFailure(content);
	        	}
	        });
	      
	   }  
	
	
}
