package com.gltype.nourriture.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.adapter.HomeProductAdapter;
import com.gltype.nourriture.adapter.HomeRecipeAdapter;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment {

	
	private Context context;
	public static String userPicture;
	public static String userName;
	public static String userId;
	public List<Product> products;
	public List<Recipe> recipes;
	
	private ImageButton refeshButton;
	private TextView textView ;
	private LinearLayout ll_promotePro, ll_promoteRec;
	private ImageView img_proProduct, img_proRecipe;
	
	private int current;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_home, null);
		context = getActivity();
		textView = (TextView) view.findViewById(R.id.welcome_user);		
		refeshButton = (ImageButton) view.findViewById(R.id.bt_home_refresh);
		ll_promotePro = (LinearLayout) view.findViewById(R.id.promote_product);
		ll_promoteRec = (LinearLayout) view.findViewById(R.id.promote_recipe);
		img_proProduct = (ImageView) view.findViewById(R.id.img_proProduct);
		img_proRecipe = (ImageView) view.findViewById(R.id.img_proRecipe);
		
		getUserByAsyncHttpClientGet(LoginActivity.token);
		 
		getProductsByAsyncHttpClientGet();
		changeTextColor(0);
		
		refeshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				changeTextColor(current);
				refresh(current);		
			}
		});
		
		ll_promotePro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeTextColor(0);
				getProductsByAsyncHttpClientGet();
				current = 0;
			}
		});
		
		ll_promoteRec.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeTextColor(1);
				getRecipesByAsyncHttpClientGet();
				current = 1;
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
	
	public void refresh(int whichList)
	{	
		switch (whichList) {
		case 0:
			getProductsByAsyncHttpClientGet();
			break;
		case 1:
			getRecipesByAsyncHttpClientGet();
			break;
		default:
			break;
		}
	}	

	protected void resetTextColor() {
		img_proRecipe.setBackgroundColor(Color.TRANSPARENT);
		img_proProduct.setBackgroundColor(Color.TRANSPARENT);
	}
	
	protected void changeTextColor(int position) {
		resetTextColor();
		switch (position) {
		case 0:
			img_proProduct.setBackgroundColor(Color.GRAY);
			break;
		case 1:
			img_proRecipe.setBackgroundColor(Color.GRAY);
			break;
		}
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
						
						FragmentListRecipes f_promoteRecipes =new FragmentListRecipes(recipes, context);
						getFragmentManager().beginTransaction().replace(R.id.frame_promotelist, f_promoteRecipes).commit();
					
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
						FragmentListProduct f_promoteProduct = new FragmentListProduct(products, context);
						getFragmentManager().beginTransaction().replace(R.id.frame_promotelist, f_promoteProduct).commit();
						
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
