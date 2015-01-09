package com.gltype.nourriture.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nourriture.R;
import com.gltype.nourriture.R.color;

public class ShowAllListActivity extends FragmentActivity {
	private int which;
	
	private ViewPager vp_list;
	private FragmentPagerAdapter fragment_adapter;
	private List<Fragment> f_datas;
	
	private List<Product> mlike;
	private List<Recipe> recipes;
	private List<Moment> moments;
	
	private TextView tx_like;
	private TextView tx_recipes;
	private TextView tx_moments;
	
	private ImageView img_tabLine0;
	private ImageView img_tabLine1;
	private ImageView img_tabLine2;
	
	private int screen1third;//1/3 length of screen

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		which = (Integer) intent.getSerializableExtra("whichList");	
		
		mlike = (List<Product>) intent.getSerializableExtra("products");
		recipes = (List<Recipe>) intent.getSerializableExtra("recipes");
		moments = (List<Moment>) intent.getSerializableExtra("moments");
		
		setContentView(R.layout.all_item_list_switch);
		Toast.makeText(getApplicationContext(), String.format("which page: %d", which), Toast.LENGTH_SHORT).show();
//		initTab();
		initView();
	}

	private void initView() {
		vp_list = (ViewPager) findViewById(R.id.list_viewpager);
		
		tx_like = (TextView) findViewById(R.id.tx_like);
		tx_recipes = (TextView) findViewById(R.id.tx_recipes);
		tx_moments = (TextView) findViewById(R.id.tx_moments);

		img_tabLine0 = (ImageView) findViewById(R.id.tabline0);
		img_tabLine1 = (ImageView) findViewById(R.id.tabline1);
		img_tabLine2 = (ImageView) findViewById(R.id.tabline2);
		
		f_datas = new ArrayList<Fragment>();
		FragmentListLike like = new FragmentListLike();
		FragmentListMoments moments = new FragmentListMoments();
		FragmentListRecipes recipes = new FragmentListRecipes();
		f_datas.add(like);
		f_datas.add(recipes);
		f_datas.add(moments);
		
		fragment_adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return f_datas.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				return f_datas.get(arg0);
			}
		};
		
		vp_list.setAdapter(fragment_adapter);
		vp_list.setCurrentItem(which);
		changeTextColor(which);
		
		vp_list.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				Toast.makeText(getApplicationContext(), String.format("which page: %d", arg0), Toast.LENGTH_SHORT).show();

				changeTextColor(arg0);
//				which = arg0;
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	protected void resetTextColor() {
		img_tabLine0.setBackgroundColor(color.white);
		img_tabLine1.setBackgroundColor(color.white);
		img_tabLine2.setBackgroundColor(color.white);
	}
	
	protected void changeTextColor(int position) {
		resetTextColor();
		switch (position) {
		case 0:
			img_tabLine0.setBackgroundColor(color.blue);
			break;
		case 1:
			img_tabLine1.setBackgroundColor(color.blue);
			break;
		case 2:
			img_tabLine2.setBackgroundColor(color.blue);
			break;
		}
	}
	
}
