package com.gltype.nourriture.ui;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.adapter.HomeProductAdapter;


import com.gltype.nourriture.model.Product;
import com.gltype.nurriture.R;
import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment {
	public static String email;
	public static String token;
	private TextView textView ;
	private GridView newgridView;
	private GridView promotegridView;
	private Context context;
	public List<Product> products;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_home, null);
		context = getActivity();
		textView = (TextView) view.findViewById(R.id.welcome_user);		
		
		 getUserByAsyncHttpClientGet(email);
		 newgridView = (GridView) view.findViewById(R.id.home_view_newList);
		 promotegridView = (GridView) view.findViewById(R.id.home_view_promoteList);
		 newgridView.setVerticalSpacing(25);
		 promotegridView.setVerticalSpacing(25);
		 refresh();
		 
		 
			 
	
		 
		return view;
	}	
	
	
	public void refresh()
	{
		
		getProductsByAsyncHttpClientGet();
		
		
//		products = new ArrayList<Product>();
//		 Product product = new Product("aaaaaa");
//		 Product product1 = new Product("aaaaaa");
//		
//		 products.add(product);
//		 products.add(product1);
		HomeProductAdapter adapter = new HomeProductAdapter(context, products);
		newgridView.setAdapter(adapter);
		promotegridView.setAdapter(adapter);
		
		newgridView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Product product = (Product) arg0.getItemAtPosition(arg2);
				  Intent intent =new Intent(HomeFragment.this.getActivity(), ProductDetialActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("product",product);
				  intent.putExtras(bundle);
				  startActivity(intent);
				
			}
		});
		
		
	}


	
	 public void getProductsByAsyncHttpClientGet() {  	
	        AsyncHttpClient client = new AsyncHttpClient();
	        
	  	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/products"; 
   
	        client.get(url,new JsonHttpResponseHandler() {     
	        
	        
	        	 @Override
	        	public void onSuccess(int statusCode, Header[] headers,
	        			JSONArray response) {
					try {
						products = new ArrayList<Product>();
						JSONObject jsonObject = null;
						if(response.length()<=9){
							for(int i=0; i<response.length();i++){
								jsonObject= response.getJSONObject(i);
							 String productname= jsonObject.getString("name");
							 String img= jsonObject.getString("picture");
							
							 Product product = new Product(productname);
							 product.setPicture(img);
							 product.setProductid(jsonObject.getString("_id"));
							 product.setDescription(jsonObject.getString("description"));
							 product.setValue(jsonObject.getString("values"));
							 product.setBrand(jsonObject.getString("brand"));
							 products.add(product);
						
							}
						}else{
							for(int i=0; i<9;i++){
								jsonObject= response.getJSONObject(i);
							 String productname= jsonObject.getString("name");
							 String img= jsonObject.getString("picture");
							
							 Product product = new Product(productname);
							 product.setPicture(img);
							 products.add(product);
							}
						}
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
	       
	    }  

	 public void getUserByAsyncHttpClientGet(String email) {  	
	        AsyncHttpClient client = new AsyncHttpClient();  
	  	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/mail/"+email; 
      
	        client.get(url,new JsonHttpResponseHandler() {            
	        	 @Override
	        	public void onSuccess(int statusCode, Header[] headers,
	        			JSONObject response) {
					try {
						
						 textView.setText("Hey! "+response.getString("firstname")+" "+response.getString("lastname"));
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
	    }  
	
}
