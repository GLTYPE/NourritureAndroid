package com.gltype.nourriture.ui;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nourriture.utils.MyActivityManager;
import com.gltype.nourriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


public class RecipeEditActivity extends Activity {
	private Recipe recipe;
	private EditText recipeName;

	private EditText recipeDescription;
	private EditText recipeValue;
	private EditText recipeImg;
	private Button editButton;
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private Button btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_edit);
		Intent intent = this.getIntent(); 
		recipe=(Recipe)intent.getSerializableExtra("recipe");
		this.recipeName = (EditText) findViewById(R.id.et_recipename);
		
		this.recipeDescription = (EditText) findViewById(R.id.et_recipedescription);
		this.recipeValue = (EditText) findViewById(R.id.et_recipevalue);
		this.recipeImg = (EditText) findViewById(R.id.et_recipeimg);
		
		//this.recipeName.setText(LoginActivity.token);
		this.recipeName.setText(recipe.getName());
		
		this.recipeDescription.setText(recipe.getDescription());
		this.recipeImg.setText(recipe.getPicture());
		this.recipeValue.setText(recipe.getValue()+"");
		this.editButton=(Button) findViewById(R.id.bt_recipecommit);
		
		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(Button) titleView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(RecipeEditActivity.this);
				
			}
		});
		editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Recipe rec = new Recipe();
				rec.setName(recipeName.getText().toString());
				rec.setDescription(recipeDescription.getText().toString());
				rec.setPicture(recipeImg.getText().toString());
				rec.setValue(Integer.parseInt(recipeValue.getText().toString()));
				rec.set_id(recipe.get_id());
				editRecipeByAsyncHttpClientPut(rec);
				finish();
			}
		});
		
	}
	public void editRecipeByAsyncHttpClientPut(Recipe recipe) {  
   	 AsyncHttpClient client = new AsyncHttpClient(); 
        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/receipes/"+recipe.get_id(); 

        JSONObject jsonObject = new JSONObject();
        try {
        	
			jsonObject.put("name", recipe.getName());  
		  
			jsonObject.put("picture", recipe.getPicture());  
			jsonObject.put("description", recipe.getDescription());  
			jsonObject.put("values", recipe.getValue());  
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

        client.put(RecipeEditActivity.this, url, stringEntity, "application/json",  new JsonHttpResponseHandler() {  
        
       	 @Override
       	public void onSuccess(int statusCode, Header[] headers,
       			JSONObject response) {
       		Toast.makeText(RecipeEditActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
       		mam.popOneActivity(RecipeEditActivity.this);
       		super.onSuccess(statusCode, headers, response);
       	}
           @Override
           public void onFailure(int statusCode, Header[] headers,
           		String responseString, Throwable throwable) {
        	   System.out.println("----------------"+responseString);
        	   Toast.makeText(RecipeEditActivity.this, "Edit Failure", Toast.LENGTH_SHORT).show();
           	super.onFailure(statusCode, headers, responseString, throwable);
           }   
          
        });  
      
   }  

}
