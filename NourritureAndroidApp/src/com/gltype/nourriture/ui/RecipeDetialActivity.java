package com.gltype.nourriture.ui;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nourriture.utils.MyActivityManager;
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

public class RecipeDetialActivity extends Activity {
	private Recipe recipe;
	private TextView recipeName;

	private TextView recipeDescription;
	private TextView recipeValue;
	private ImageView recipeImg;
	private Button editButton;
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private Button btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_detial);
		Intent intent = this.getIntent(); 
		recipe=(Recipe)intent.getSerializableExtra("recipe");
		this.recipeName = (TextView) findViewById(R.id.tv_recipename);
	
		this.recipeDescription = (TextView) findViewById(R.id.tv_recipedescription);
		this.recipeValue = (TextView) findViewById(R.id.tv_recipevalue);
		this.recipeImg = (ImageView) findViewById(R.id.img_recipe);
		
		
		this.recipeName.setText(recipe.getName());
		
		this.recipeDescription.setText(recipe.getDescription());
		SimpleImageLoader.showImg(this.recipeImg, recipe.getPicture());
		this.recipeValue.setText(recipe.getValue()+"");
		this.editButton=(Button) findViewById(R.id.bt_recipeedit);
		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(Button) titleView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(RecipeDetialActivity.this);
				
			}
		});
		
		editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				 Intent intent =new Intent(RecipeDetialActivity.this, RecipeEditActivity.class);
				 Bundle bundle = new Bundle();
				  bundle.putSerializable("recipe",recipe);
				  intent.putExtras(bundle);
				  startActivity(intent);
				  mam.popOneActivity(RecipeDetialActivity.this);
			}
		});
		
	}
	
	
	
}
