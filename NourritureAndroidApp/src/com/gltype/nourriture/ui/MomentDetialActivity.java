package com.gltype.nourriture.ui;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gltype.nourriture.R;
import com.gltype.nourriture.adapter.CommentAdapter;
import com.gltype.nourriture.http.MyAsyncHttpClient;
import com.gltype.nourriture.http.MyHandler;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Comment;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.utils.MyActivityManager;
import com.gltype.nourriture.utils.StringTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MomentDetialActivity extends Activity {
		
	private Moment moment;
	private Button delButton;
	private Button editButton;
	private Button commentButton;
	private Button commentCommit;
	private EditText et_comment;
	private TextView tv_momentdescription;
	private TextView tv_name;
	private TextView tv_date;
	private ImageView userPhoto;
	private ImageView contentPic;
	private boolean visibility = false;
	public  List<Comment> comments;
	public ListView commentList;
	private LinearLayout commentlayout;
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private ImageButton btn_back;
	private JSONArray commentJsonArr= null;
	CommentAdapter commentAdapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.moment_detial);
		Intent intent = this.getIntent(); 
		moment=(Moment)intent.getSerializableExtra("moment");
		String commentArrStr = moment.getCommentArray();
		
		try {
			if(commentArrStr!=null||"".equals(commentArrStr)){
			commentJsonArr = new JSONArray(commentArrStr);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delButton = (Button) findViewById(R.id.bt_momentdelete);
		tv_momentdescription = (TextView) findViewById(R.id.txt_item_content);
		tv_name = (TextView) findViewById(R.id.txt_item_uname);
		editButton = (Button) findViewById(R.id.bt_momentedit);
		commentButton = (Button) findViewById(R.id.bt_addcomment);
		userPhoto= (ImageView) findViewById(R.id.img_item_userphoto);
		contentPic= (ImageView) findViewById(R.id.img_item_content_pic);
		tv_date = (TextView)findViewById(R.id.txt_item_time);
		commentList= (ListView)findViewById(R.id.lv_comment);
		commentlayout = (LinearLayout)findViewById(R.id.commentlayout);
		
		et_comment = (EditText)findViewById(R.id.et_desc);
		commentCommit = (Button)findViewById(R.id.bt_addcomcommit);
		
		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(ImageButton) titleView.findViewById(R.id.btn_back);
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
		if("".equals(moment.getContentimg())||moment.getContentimg()==null){
			contentPic.setVisibility(View.GONE);
		}else{
			SimpleImageLoader.showImg(contentPic,moment.getContentimg());
			
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
		
		
		 commentButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
//					Intent intent = new Intent(MomentFragment.this.getActivity(), AddMomentActivity.class);
//             	startActivity(intent);
					if(!visibility){
						commentlayout.setVisibility(View.VISIBLE);
						visibility=true;
					}else{
						commentlayout.setVisibility(View.GONE);
						visibility=false;
					}
					
				}
			});
		 
		 
		 
		 commentCommit.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("SimpleDateFormat")
				@Override
				public void onClick(View v) {
					
					String desc = et_comment.getText().toString(); 
		           
		      
		          
		          SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		         
		          String date  =  sDateFormat.format(new java.util.Date());
		          Comment comment = new Comment(HomeFragment.userName, desc, HomeFragment.userPicture,date);
		        
		          
		            if (TextUtils.isEmpty(desc.trim())) {  
		                Toast.makeText(v.getContext(), "Say something!", Toast.LENGTH_LONG).show();  
		           
		            } else {  
		                
		            	addComment(comment);  
		            	comments.add(comment);
		            	commentAdapter.notifyDataSetChanged();
		            	commentlayout.setVisibility(View.GONE);
						visibility=false;
		                
		            } 			
				
				}
			});	
		 
		 
		 
		
		super.onCreate(savedInstanceState);
	}
	
	
	
	public void refresh(){
		
		if(commentJsonArr != null){
		getComment();
		}
		else{
			commentAdapter = new CommentAdapter(this, comments);
			commentList.setAdapter(commentAdapter);
		}
	}
	
	
	
	public void getComment(){
		
		
			try {
				int len = commentJsonArr.length();					
				JSONObject jsonObj = null;
				comments = new ArrayList<Comment>();
				
				for(int i = 0; i <len; i++) {
					jsonObj= commentJsonArr.getJSONObject(i);
					JSONObject comjsonObj = (JSONObject)jsonObj.get("comment");
					String date =StringTools.dateFormat(comjsonObj.getString("date"));
					String desc = comjsonObj.getString("comment");
					
				
					JSONObject userjsonObj = (JSONObject)jsonObj.get("user");
					String firstname = userjsonObj.getString("firstname");
					String lastname = userjsonObj.getString("lastname");
					String userpic = userjsonObj.getString("picture");
					String userName = firstname+" "+lastname;
					Comment comment = new Comment(userName, desc, userpic, date);
					comments.add(comment);
					
				}
				 commentAdapter = new CommentAdapter(this, comments);
				commentList.setAdapter(commentAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void deleteMoment(String momentId) {  
	   	 MyAsyncHttpClient client = new MyAsyncHttpClient(); 
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
	
	 public void addComment(Comment comment) {  
	        AsyncHttpClient client = new AsyncHttpClient(); 
	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/comments"; 

	        JSONObject jsonObject = new JSONObject();
	        try {
	        	jsonObject.put("target_id", moment.getId());
	        	jsonObject.put("comment",comment.getContent() );
	        	jsonObject.put("date", comment.getDate());  
	        	jsonObject.put("type","moment" );
	        	jsonObject.put("token", LoginActivity.token);
				
			} catch (JSONException e) {
			
				e.printStackTrace();
			}
	        StringEntity stringEntity = null; 
	        try {
				stringEntity = new StringEntity(jsonObject.toString());
			} catch (UnsupportedEncodingException e) {
		
				e.printStackTrace();
			} 

	        client.post(MomentDetialActivity.this, url, stringEntity, "application/json",  new AsyncHttpResponseHandler() {  
	         
	            @Override  
	            public void onSuccess(int statusCode, Header[] headers,  
	                    byte[] responseBody) {  
	                    
	                  // String resResult = new String(responseBody);  	
	            	 Toast.makeText(MomentDetialActivity.this,"Add a new comment" , Toast.LENGTH_LONG).show(); 
	            	
	                    	//progresView.setVisibility(View.GONE);
	                  
	            }  
	  
	           
	            @Override  
	            public void onFailure(int statusCode, Header[] headers,  
	                    byte[] responseBody, Throwable error) {  
	            	// System.out.println("F-----------------"+statusCode);
	            	 String resResult = new String(responseBody);
	            	 System.out.println("F-----------------"+resResult); 
	            	 Toast.makeText(MomentDetialActivity.this,resResult , Toast.LENGTH_LONG).show(); 
	            
	                error.printStackTrace();
	            }  
	        });  
	    }  
	@Override
	protected void onDestroy() {
		mam.popOneActivity(this);
		super.onDestroy();
	}
}
