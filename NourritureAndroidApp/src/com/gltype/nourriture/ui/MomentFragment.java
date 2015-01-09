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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gltype.nourriture.R;
import com.gltype.nourriture.adapter.MomentAdapter;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.utils.StringTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MomentFragment extends Fragment {
	
	private ListView listView;
	
	private Context context;
	private View progresView;
	public  List<Moment> moments;
	private Spinner mSpinner;
	private ImageButton refreshbutton;
	private ImageButton addbutton;
	private Button addCommit;
	private EditText et_momentname;
	private EditText et_desc;
	private EditText et_pic;
	private int index;
	private boolean visibility = false;
	private LinearLayout addlayout;
	 private ArrayAdapter<String> adapter;
	//private MomentAdapter momentadapter;
	 private static final String[] searchType={"Moments","My moments"};
	//private User user= null;
//	 public  String userPicture;
//		public  String userName;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity(); 
		 
		View view =  inflater.inflate(R.layout.fragment_moments, null);		
		this.listView = (ListView) view.findViewById(R.id.moments_list);
		this.refreshbutton = (ImageButton) view.findViewById(R.id.bt_refresh);
		this.addbutton = (ImageButton) view.findViewById(R.id.bt_add);
		this.mSpinner = (Spinner) view.findViewById(R.id.sp_moment);
		this.addCommit = (Button) view.findViewById(R.id.bt_addmomcommit);
		et_desc=(EditText)view.findViewById(R.id.et_desc);
		et_pic=(EditText) view.findViewById(R.id.et_picture);
		et_momentname = (EditText) view.findViewById(R.id.et_momentname);
		addlayout = (LinearLayout)view.findViewById(R.id.addlayout);
		 adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,searchType);  
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		 mSpinner.setAdapter(adapter);
		 mSpinner.setSelection(1);
		 refresh(1);
		 mSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			 @Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				index=arg2;
				refresh(index);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		 }); 
		 
		 refreshbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				refresh(index);
				
			}
		});

		 addbutton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
//					Intent intent = new Intent(MomentFragment.this.getActivity(), AddMomentActivity.class);
//                	startActivity(intent);
					if(!visibility){
						addlayout.setVisibility(View.VISIBLE);
						visibility=true;
					}else{
						addlayout.setVisibility(View.GONE);
						visibility=false;
					}
					
				}
			});
		 
		 
		 
		 listView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					 Moment moment = (Moment) arg0.getItemAtPosition(arg2);
					 
					  Intent intent =new Intent(MomentFragment.this.getActivity(), MomentDetialActivity.class);
					  Bundle bundle = new Bundle();
					  bundle.putSerializable("moment",moment);
					  intent.putExtras(bundle);
					  startActivity(intent);
					
				}
			});	
		 
		 addCommit.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("SimpleDateFormat")
				@Override
				public void onClick(View v) {
					
					String desc = et_desc.getText().toString(); 
		            String pic = et_pic.getText().toString();
		            String name = et_momentname.getText().toString();
		          Moment moment = new Moment(name, desc, HomeFragment.userPicture);
		          SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		         
		          String date  =  sDateFormat.format(new java.util.Date());
		          moment.setContentimg(pic);
		          moment.setTime(date);
		          
		            if (TextUtils.isEmpty(desc.trim())) {  
		                Toast.makeText(v.getContext(), "Say something!", Toast.LENGTH_LONG).show();  
		           
		            } else {  
		                
		            	addMomemt(moment);  
		            	
		            	addlayout.setVisibility(View.GONE);
						visibility=false;
		                
		            } 			
				
				}
			});	
		 
		 
		return view;
	}
	
	
	public void refresh(int selectIndex){
		switch(selectIndex){
			case 0:{
				initTargetMoments();
				break;
			}
			case 1:{
				initMyMoments();
				break;
			}
			
		}
	}
	
	
	
	public void initTargetMoments(){
		
		getTargetMoments();

		
	}

public void initMyMoments(){
	
	
		getMyMoments(HomeFragment.userId);
	//	MomentAdapter adapter = new MomentAdapter(context, moments);
		//listView.setAdapter(adapter);
		
	}
	
	public void getMyMoments(String ownerId) {				
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments/target/"+ownerId;
		getInfo(url);
	}
	
	public void getTargetMoments() {				
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments";
		getInfo(url);
	}
	
	 public  void  getUserById(String userId) {  	
	        AsyncHttpClient client = new AsyncHttpClient();  
	  	    String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/"+userId; 
	  	
	        client.get(url,new JsonHttpResponseHandler() {            
	        	 @Override
	        	public void onSuccess(int statusCode, Header[] headers,
	        			JSONObject response) {
					try {
						
						String firstname = response.getString("firstname");
						String lastname = response.getString("lastname");
						int role = response.getInt("role");
						String pic = response.getString("picture");
						String email = response.getString("email");
//						userName = firstname+" "+lastname;
//						userPicture = pic;
						
	
						 
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
	        		
	        		super.onSuccess(statusCode, headers, response);
	        	}
	            @Override
	            public void onFailure(int statusCode, Header[] headers,
	            		String responseString, Throwable throwable) {
	            	System.out.println("============="+responseString);
	            	super.onFailure(statusCode, headers, responseString, throwable);
	            }               
	         });  	
	      
	    }  
	
	
	public  void getInfo(String url){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {					
					moments = new ArrayList<Moment>();
					
					int len = response.length();					
					JSONObject jsonObj = null;
					
						for(int i = len-1; i >=0; i--) {					
							jsonObj = response.getJSONObject(i);
							JSONObject momjsonObj = (JSONObject)jsonObj.get("moment");
							String id = momjsonObj.getString("_id");
							String pic = momjsonObj.getString("picture");
							String desc = momjsonObj.getString("description");					
							String date = StringTools.dateFormat(momjsonObj.getString("date"));							
							String own =  momjsonObj.getString("owner_id");				
							String target =  momjsonObj.getString("target_id");
							
							String comArrStr=null;
							if(!jsonObj.getString("comments").equals("null")){
								 comArrStr = jsonObj.getString("comments");
							}
							JSONObject userjsonObj = (JSONObject)jsonObj.get("user");
							String firstname = userjsonObj.getString("firstname");
							String lastname = userjsonObj.getString("lastname");
							String userpic = userjsonObj.getString("picture");
							String userName = firstname+" "+lastname;
							Moment moment = new Moment(userName,desc,userpic);
							moment.setTime(date);
							moment.setId(id);
							moment.setContentimg(pic);
							moment.setCommentArray(comArrStr);
							moments.add(moment);
						}	
						//momentadapter.notifyDataSetChanged();
						MomentAdapter	momentadapter = new MomentAdapter(context, moments);
						
						listView.setAdapter(momentadapter);
						
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
	
	 public void addMomemt(Moment moment) {  
	        AsyncHttpClient client = new AsyncHttpClient(); 
	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments"; 

	        JSONObject jsonObject = new JSONObject();
	        try {
	        	jsonObject.put("name", moment.getUsername());
	        	jsonObject.put("description",moment.getContent() );
	        	jsonObject.put("date", moment.getTime());  
	        	jsonObject.put("picture",moment.getContentimg() );
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

	        client.post(MomentFragment.this.getActivity(), url, stringEntity, "application/json",  new AsyncHttpResponseHandler() {  
	         
	            @Override  
	            public void onSuccess(int statusCode, Header[] headers,  
	                    byte[] responseBody) {  
	                    
	                  // String resResult = new String(responseBody);  	
	            	 Toast.makeText(MomentFragment.this.getActivity(),"Add a new moment" , Toast.LENGTH_LONG).show(); 
	            	 refresh(index);
	                    	//progresView.setVisibility(View.GONE);
	                  
	            }  
	  
	           
	            @Override  
	            public void onFailure(int statusCode, Header[] headers,  
	                    byte[] responseBody, Throwable error) {  
	            	// System.out.println("F-----------------"+statusCode);
	            	 String resResult = new String(responseBody);
	            	 System.out.println("F-----------------"+resResult); 
	            	 Toast.makeText(MomentFragment.this.getActivity(),resResult , Toast.LENGTH_LONG).show(); 
	            
	                error.printStackTrace();
	            }  
	        });  
	    }  
	
	
}
