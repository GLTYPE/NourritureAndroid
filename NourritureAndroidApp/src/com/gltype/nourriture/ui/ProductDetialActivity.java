package com.gltype.nourriture.ui;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Product;
import com.gltype.nurriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
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
				finish();
			}
		});
		
	}
	
	
	
}
