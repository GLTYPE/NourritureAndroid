package com.gltype.nourriture.ui;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.http.AsyncHttpClient;
import com.gltype.nourriture.http.MyHandler;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.utils.MyActivityManager;
import com.gltype.nurriture.R;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetialActivity extends Activity {
	private Product product;
	private TextView productName;
	private TextView productBrand;
	private TextView productDescription;
	private TextView productValue;
	private ImageView productImg;
	private Button editButton;
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private Button btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detial);
		Intent intent = this.getIntent(); 
		product=(Product)intent.getSerializableExtra("product");
		this.productName = (TextView) findViewById(R.id.tv_productname);
		this.productBrand = (TextView) findViewById(R.id.tv_productbrand);
		this.productDescription = (TextView) findViewById(R.id.tv_productdescription);
		this.productValue = (TextView) findViewById(R.id.tv_productvalue);
		this.productImg = (ImageView) findViewById(R.id.img_product);
		
		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(Button) titleView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(ProductDetialActivity.this);
				
			}
		});
		
		this.productName.setText(product.getName());
		this.productBrand.setText(product.getBrand());
		this.productDescription.setText(product.getDescription());
		SimpleImageLoader.showImg(this.productImg, product.getPicture());
		this.productValue.setText(product.getValue());
		this.editButton=(Button) findViewById(R.id.bt_productedit);
		editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				 Intent intent =new Intent(ProductDetialActivity.this, ProductEditActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("product",product);
				  intent.putExtras(bundle);
				  startActivity(intent);
				  mam.popOneActivity(ProductDetialActivity.this);
			}
		});
//		editButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				deleteMoment(product.getProductid());
//				
//			}
//		});
		
		
	}
	
	
	public void deleteProduct(String procuctId) {  
	   	 AsyncHttpClient client = new AsyncHttpClient(); 
	        String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/products/"+procuctId; 
	        JSONObject jsonObject = new JSONObject();
	        try {
				jsonObject.put("token",LoginActivity.token);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
	        client.delete(url, jsonObject, new MyHandler(){
	        	@Override
	        	public void onSuccess(String content, int status) {
	        		productDescription.setText(status+" "+content);
	        	};
	        	@Override
	        	public void onFailure(String content) {
	        		productDescription.setText(content);
	        		super.onFailure(content);
	        	}
	        });
	      
	   }  
	
	
}
