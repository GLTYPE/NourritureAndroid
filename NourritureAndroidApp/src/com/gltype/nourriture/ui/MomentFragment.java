package com.gltype.nourriture.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.adapter.MomentAdapter;
import com.gltype.nourriture.adapter.SearchIngredientAdapter;
import com.gltype.nourriture.model.Ingredient;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.User;
import com.gltype.nurriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MomentFragment extends Fragment {
	
	private ListView listView;
	
	private Context context;
	private View progresView;
	public  List<Moment> moments;
	private Spinner mSpinner;
	private Button refreshbutton;
	private Button addbutton;
	private int index;
	 private ArrayAdapter<String> adapter;
	 private static final String[] searchType={"Moments","My moments"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity(); 
		 
		View view =  inflater.inflate(R.layout.fragment_moments, null);		
		this.listView = (ListView) view.findViewById(R.id.moments_list);
		this.refreshbutton = (Button) view.findViewById(R.id.bt_refresh);
		this.addbutton = (Button) view.findViewById(R.id.bt_add);
		this.mSpinner = (Spinner) view.findViewById(R.id.sp_moment);
		 adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,searchType);  
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		 mSpinner.setAdapter(adapter);
		 mSpinner.setSelection(0);
		 refresh(0);
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
		// initMyMoments();
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
		MomentAdapter adapter = new MomentAdapter(context, moments);
		listView.setAdapter(adapter);
	}

public void initMyMoments(){
		//moments = new ArrayList<Moment>();
		
	//Moment moment = new Moment("aaaa", "asdhjgfdsah", "");
	//Moment moment1 = new Moment("aaaa", "asdhjgfdsah", "");
	//moments.add(moment);
	//moments.add(moment1);
	
		getMyMoments(HomeFragment.userId);
		MomentAdapter adapter = new MomentAdapter(context, moments);
		listView.setAdapter(adapter);

	}
	
	public void getMyMoments(String ownerId) {				
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments/owner/"+ownerId;
		getInfo(url,1);
	}
	
	public void getTargetMoments() {				
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments";
		getInfo(url,0);
	}
	
	 public User getUserById(String userId) {  	
	        AsyncHttpClient client = new AsyncHttpClient();  
	  	    String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/id/"+userId; 
	  	    final User user=new User();
	        client.get(url,new JsonHttpResponseHandler() {            
	        	 @Override
	        	public void onSuccess(int statusCode, Header[] headers,
	        			JSONObject response) {
					try {
						 
						String userName= response.getString("firstname");
						String userPicture = response.getString("picture");
						 user.setFirstname(userName);
						 user.setPicture(userPicture);
						 
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
	        		
	        		super.onSuccess(statusCode, headers, response);
	        	}
	            @Override
	            public void onFailure(int statusCode, Header[] headers,
	            		String responseString, Throwable throwable) {
	            	super.onFailure(statusCode, headers, responseString, throwable);
	            }               
	         });  	
	        return user;
	    }  
	
	
	public void getInfo(String url,final int tag){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {					
					moments = new ArrayList<Moment>();
					
					int len = response.length();					
					JSONObject jsonObj = null;
					
						for(int i = 0; i < len; i++) {					
							jsonObj = response.getJSONObject(i);
							String id = jsonObj.getString("_id");
						//	String name = jsonObj.getString("name");
							String desc = jsonObj.getString("description");
							
							
							//String date = jsonObj.getString("date");
							String own =  jsonObj.getString("owner_id");				
							String target =  jsonObj.getString("target_id");
							User user=null;
							
							user= getUserById(own);
						
							Moment moment = new Moment(user.getFirstname(), desc, user.getPicture());
							
							moments.add(moment);
						}	
			
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
	
	
	
}
