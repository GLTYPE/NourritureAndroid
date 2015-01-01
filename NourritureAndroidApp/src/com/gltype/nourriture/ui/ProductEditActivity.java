package com.gltype.nourriture.ui;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Product;
import com.gltype.nurriture.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
		setContentView(R.layout.product_detial);
		Intent intent = this.getIntent(); 
		product=(Product)intent.getSerializableExtra("product");
		this.productName = (EditText) findViewById(R.id.et_productname);
		this.productBrand = (EditText) findViewById(R.id.et_productbrand);
		this.productDescription = (EditText) findViewById(R.id.et_productdescription);
		this.productValue = (EditText) findViewById(R.id.et_productvalue);
		this.productImg = (EditText) findViewById(R.id.et_productimg);
		
		this.productName.setText(HomeFragment.token);
		//this.productName.setText(product.getName());
		this.productBrand.setText(product.getBrand());
		this.productDescription.setText(product.getDescription());
		this.productImg.setText(product.getPicture());
		this.productValue.setText(product.getValue());
		this.editButton=(Button) findViewById(R.id.bt_productcommit);
		editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				
				
				//finish();
			}
		});
		
	}
	

}
