package com.gltype.nourriture.ui;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.adapter.HomeProductAdapter;
import com.gltype.nourriture.adapter.HomeRecipeAdapter;


import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nurriture.R;
import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.R.integer;
import android.view.View.OnClickListener;
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

import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment {

	
	private TextView textView ;
	private GridView recipesgridView;
	private GridView productsgridView;
	private Context context;
	public static String userPicture;
	public static String userName;
	public static String userId;
	public List<Product> products;
	public List<Recipe> recipes;
	private Button refeshButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_home, null);
		context = getActivity();
		textView = (TextView) view.findViewById(R.id.welcome_user);		
		refeshButton = (Button) view.findViewById(R.id.bt_home_refresh);
		 getUserByAsyncHttpClientGet(LoginActivity.token);
		 recipesgridView = (GridView) view.findViewById(R.id.home_view_newList);
		 productsgridView = (GridView) view.findViewById(R.id.home_view_promoteList);
		 recipesgridView.setVerticalSpacing(25);
		 productsgridView.setVerticalSpacing(25);
		 refresh();
		 refeshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				refresh();
				
			}
		});
		 System.out.println("===================Create view");
		 return view;
		
	}
	
	
	@Override
	public void onDestroyView() {
		 System.out.println("===================Destroy view");
		super.onDestroyView();
	}
	public void refresh()
	{		
		getRecipesByAsyncHttpClientGet();
		getProductsByAsyncHttpClientGet();		
		
		HomeProductAdapter adapter = new HomeProductAdapter(context, products);
		HomeRecipeAdapter adapter2 =new HomeRecipeAdapter(context, recipes);
		recipesgridView.setAdapter(adapter2);
		productsgridView.setAdapter(adapter);
		
		recipesgridView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Recipe recipe = (Recipe) arg0.getItemAtPosition(arg2);
				 
				  Intent intent =new Intent(HomeFragment.this.getActivity(), RecipeDetialActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("recipe",recipe);
				  intent.putExtras(bundle);
				  startActivity(intent);
				
			}
		});	
		productsgridView.setOnItemClickListener(new OnItemClickListener(){
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
	
	
	 public void getRecipesByAsyncHttpClientGet() {  	
	        AsyncHttpClient client = new AsyncHttpClient();
	        
	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/receipes"; 

	        client.get(url,new JsonHttpResponseHandler() {     	        
	        	 @Override
	        	public void onSuccess(int statusCode, Header[] headers,
	        			JSONArray response) {
					try {
						recipes = new ArrayList<Recipe>();
						JSONObject jsonObject = null;
						if(response.length()<=9){
							for(int i=0; i<response.length();i++){
								jsonObject= response.getJSONObject(i);
								String name= jsonObject.getString("name");
								String img= jsonObject.getString("picture");
								
								
								String id = jsonObject.getString("_id");
								String desc = jsonObject.getString("description");
								String value = jsonObject.getString("values");
								Recipe recipe =new Recipe(name,Integer.parseInt(value),desc,img);
								recipe.set_id(id);
								recipes.add(recipe);
							}
						}else{
							for(int i=0; i<9;i++){
								jsonObject= response.getJSONObject(i);
								String name= jsonObject.getString("name");
								String img= jsonObject.getString("picture");
								
								
								String id = jsonObject.getString("_id");
								String desc = jsonObject.getString("description");
								String value = jsonObject.getString("values");
								Recipe recipe =new Recipe(name,Integer.parseInt(value),desc,img);
								recipe.set_id(id);
								recipes.add(recipe);
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
								product.setProductid(jsonObject.getString("_id"));
								product.setDescription(jsonObject.getString("description"));
								product.setValue(jsonObject.getString("values"));
								product.setBrand(jsonObject.getString("brand"));
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

	 public void getUserByAsyncHttpClientGet(String token) {  	
	        AsyncHttpClient client = new AsyncHttpClient();  
	  	    String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/token/"+token; 
      
	        client.get(url,new JsonHttpResponseHandler() {            
	        	 @Override
	        	public void onSuccess(int statusCode, Header[] headers,
	        			JSONObject response) {
					try {
						 userName= response.getString("firstname")+" "+response.getString("lastname");
						 userPicture = response.getString("picture");
						 userId = response.getString("_id");
						 textView.setText("Hey! "+userName);
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
