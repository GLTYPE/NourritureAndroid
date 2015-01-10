package com.gltype.nourriture.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nourriture.utils.MyActivityManager;

public class ShowAllListActivity extends FragmentActivity {
	private int which;
	
	private ViewPager vp_list;
	private FragmentPagerAdapter fragment_adapter;
	private List<Fragment> f_datas;
	
	private List<Product> mlike;
	private List<Recipe> recipes;
	private List<Moment> moments;
	
	private ImageView img_tabLine0;
	private ImageView img_tabLine1;
	private ImageView img_tabLine2;
	
	private View titleView;
	private ImageButton btn_back;
	MyActivityManager mam = MyActivityManager.getInstance();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		mam.pushOneActivity(this);
		
		which = (Integer) intent.getSerializableExtra("whichList");	
		
		mlike = (List<Product>) intent.getSerializableExtra("products");
		recipes = (List<Recipe>) intent.getSerializableExtra("recipes");
		moments = (List<Moment>) intent.getSerializableExtra("moments");
		
		setContentView(R.layout.all_item_list_switch);
		initView();
	}

	private void initView() {
		vp_list = (ViewPager) findViewById(R.id.list_viewpager);

		img_tabLine0 = (ImageView) findViewById(R.id.tabline0);
		img_tabLine1 = (ImageView) findViewById(R.id.tabline1);
		img_tabLine2 = (ImageView) findViewById(R.id.tabline2);
		
		titleView = findViewById(R.id.view_title);
		btn_back = (ImageButton) titleView.findViewById(R.id.btn_back);
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mam.popOneActivity(ShowAllListActivity.this);
				
			}
		});
		
		f_datas = new ArrayList<Fragment>();
		FragmentListLike fLike = new FragmentListLike(mlike);
		FragmentListMoments fMoments = new FragmentListMoments(moments);
		FragmentListRecipes fRecipes = new FragmentListRecipes(recipes);
		f_datas.add(fLike);
		f_datas.add(fRecipes);
		f_datas.add(fMoments);
		
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
				changeTextColor(arg0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
		});
		
	}

	protected void resetTextColor() {
		img_tabLine0.setBackgroundColor(Color.TRANSPARENT);
		img_tabLine1.setBackgroundColor(Color.TRANSPARENT);
		img_tabLine2.setBackgroundColor(Color.TRANSPARENT);
	}
	
	protected void changeTextColor(int position) {
		resetTextColor();
		switch (position) {
		case 0:
			img_tabLine0.setBackgroundColor(Color.GRAY);
			break;
		case 1:
			img_tabLine1.setBackgroundColor(Color.GRAY);
			break;
		case 2:
			img_tabLine2.setBackgroundColor(Color.GRAY);
			break;
		}
	}
	
}
