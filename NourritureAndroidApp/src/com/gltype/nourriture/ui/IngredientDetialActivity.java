package com.gltype.nourriture.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Ingredient;
import com.gltype.nourriture.utils.MyActivityManager;

public class IngredientDetialActivity extends Activity {
	private Ingredient ingredient;
	private TextView ingredientName;

	private TextView ingredientDescription;
	private TextView ingredientValue;
	private ImageView ingredientImg;
	private Button editButton;
	MyActivityManager mam = MyActivityManager.getInstance();
	private View titleView;
	private ImageButton btn_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingredient_detial);
		Intent intent = this.getIntent(); 
		ingredient=(Ingredient)intent.getSerializableExtra("ingredient");
		this.ingredientName = (TextView) findViewById(R.id.tv_ingredientname);
	
		this.ingredientDescription = (TextView) findViewById(R.id.tv_ingredientdescription);
		this.ingredientValue = (TextView) findViewById(R.id.tv_ingredientvalue);
		this.ingredientImg = (ImageView) findViewById(R.id.img_ingredient);
		titleView = findViewById(R.id.layout_title_bar);
		mam.pushOneActivity(this);
		btn_back=(ImageButton) titleView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(IngredientDetialActivity.this);
				
			}
		});
		
		this.ingredientName.setText(ingredient.getName());
		
		this.ingredientDescription.setText(ingredient.getDescription());
		SimpleImageLoader.showImg(this.ingredientImg, ingredient.getPicture());
		this.ingredientValue.setText(ingredient.getValue()+"");
		this.editButton=(Button) findViewById(R.id.bt_ingredientedit);
		editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				 Intent intent =new Intent(IngredientDetialActivity.this, IngredientEditActivity.class);
				 Bundle bundle = new Bundle();
				  bundle.putSerializable("ingredient",ingredient);
				  intent.putExtras(bundle);
				  startActivity(intent);
					mam.popOneActivity(IngredientDetialActivity.this);
			}
		});
		
	}
	
	
	
}
