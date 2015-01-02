package com.gltype.nourriture.ui;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Product;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductEditActivity extends Activity {
	private Product product;
	private EditText productName;
	private EditText productBrand;
	private EditText productDescription;
	private EditText productValue;
	private EditText productImg;
	private Button editButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_edit);
		Intent intent = this.getIntent(); 
		product=(Product)intent.getSerializableExtra("product");
		this.productName = (EditText) findViewById(R.id.et_productname);
		this.productBrand = (EditText) findViewById(R.id.et_productbrand);
		this.productDescription = (EditText) findViewById(R.id.et_productdescription);
		this.productValue = (EditText) findViewById(R.id.et_productvalue);
		this.productImg = (EditText) findViewById(R.id.et_productimg);
		
		//this.productName.setText(LoginActivity.token);
		this.productName.setText(product.getName());
		this.productBrand.setText(product.getBrand());
		this.productDescription.setText(product.getDescription());
		this.productImg.setText(product.getPicture());
		this.productValue.setText(product.getValue());
		this.editButton=(Button) findViewById(R.id.bt_productcommit);
		editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Product pro = new Product(productName.getText().toString());
				pro.setBrand(productBrand.getText().toString());
				pro.setDescription(productDescription.getText().toString());
				pro.setPicture(productImg.getText().toString());
				pro.setValue(productValue.getText().toString());
				pro.setProductid(product.getProductid());
				editProductByAsyncHttpClientPut(pro);
				finish();
			}
		});
		
	}
	public void editProductByAsyncHttpClientPut(Product product) {  
   	 AsyncHttpClient client = new AsyncHttpClient(); 
        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/products/"+product.getProductid(); 

        JSONObject jsonObject = new JSONObject();
        try {
        	
			jsonObject.put("name", product.getName());  
			jsonObject.put("brand", product.getBrand());  
			jsonObject.put("picture", product.getPicture());  
			jsonObject.put("description", product.getDescription());  
			jsonObject.put("value", product.getValue());  
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

        client.put(ProductEditActivity.this, url, stringEntity, "application/json",  new JsonHttpResponseHandler() {  
        
       	 @Override
       	public void onSuccess(int statusCode, Header[] headers,
       			JSONObject response) {
       		Toast.makeText(ProductEditActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
				
       		super.onSuccess(statusCode, headers, response);
       	}
           @Override
           public void onFailure(int statusCode, Header[] headers,
           		String responseString, Throwable throwable) {
        	   Toast.makeText(ProductEditActivity.this, "Edit Failure", Toast.LENGTH_SHORT).show();
           	super.onFailure(statusCode, headers, responseString, throwable);
           }   
          
        });  
      
   }  

}
