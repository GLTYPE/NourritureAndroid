package com.gltype.nourriture.ui;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Ingredient;
import com.gltype.nurriture.R;
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

public class IngredientEditActivity extends Activity {
	private Ingredient ingredient;
	private EditText ingredientName;

	private EditText ingredientDescription;
	private EditText ingredientValue;
	private EditText ingredientImg;
	private Button editButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingredient_edit);
		Intent intent = this.getIntent(); 
		ingredient=(Ingredient)intent.getSerializableExtra("ingredient");
		this.ingredientName = (EditText) findViewById(R.id.et_ingredientname);
		
		this.ingredientDescription = (EditText) findViewById(R.id.et_ingredientdescription);
		this.ingredientValue = (EditText) findViewById(R.id.et_ingredientvalue);
		this.ingredientImg = (EditText) findViewById(R.id.et_ingredientimg);
		
		//this.ingredientName.setText(LoginActivity.token);
		this.ingredientName.setText(ingredient.getName());
		
		this.ingredientDescription.setText(ingredient.getDescription());
		this.ingredientImg.setText(ingredient.getPicture());
		this.ingredientValue.setText(ingredient.getValue()+"");
		this.editButton=(Button) findViewById(R.id.bt_ingredientcommit);
		editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Ingredient ingredient = new Ingredient();
				ingredient.setName(ingredientName.getText().toString());
				ingredient.setDescription(ingredientDescription.getText().toString());
				ingredient.setPicture(ingredientImg.getText().toString());
				ingredient.setValue(ingredientValue.getText().toString());
				ingredient.setId(ingredient.getId());
				editingredientByAsyncHttpClientPut(ingredient);
				finish();
			}
		});
		
	}
	public void editingredientByAsyncHttpClientPut(Ingredient ingredient) {  
   	 AsyncHttpClient client = new AsyncHttpClient(); 
        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/ingredients/"+ingredient.getId(); 

        JSONObject jsonObject = new JSONObject();
        try {
        	
			jsonObject.put("name", ingredient.getName());  
		  
			jsonObject.put("picture", ingredient.getPicture());  
			jsonObject.put("description", ingredient.getDescription());  
			jsonObject.put("values", ingredient.getValue());  
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

        client.put(IngredientEditActivity.this, url, stringEntity, "application/json",  new JsonHttpResponseHandler() {  
        
       	 @Override
       	public void onSuccess(int statusCode, Header[] headers,
       			JSONObject response) {
       		Toast.makeText(IngredientEditActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
				
       		super.onSuccess(statusCode, headers, response);
       	}
           @Override
           public void onFailure(int statusCode, Header[] headers,
           		String responseString, Throwable throwable) {
        	   System.out.println("----------------"+responseString);
        	   Toast.makeText(IngredientEditActivity.this, "Edit Failure", Toast.LENGTH_SHORT).show();
           	super.onFailure(statusCode, headers, responseString, throwable);
           }   
          
        });  
      
   }  

}
