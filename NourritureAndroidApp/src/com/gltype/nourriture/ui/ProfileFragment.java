package com.gltype.nourriture.ui;


import com.gltype.nourriture.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gltype.nourriture.adapter.HomeProductAdapter;
import com.gltype.nourriture.adapter.ProfileMomentAdapter;
import com.gltype.nourriture.adapter.ProfileRecipeAdapter;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nourriture.model.User;
import com.gltype.nourriture.utils.RoleUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

@SuppressLint("NewApi")
public class ProfileFragment extends Fragment {
	
	public User user;
	public List<Moment> moments;
	public List<Recipe> recipes;
	public List<Product> products;
	
	private ArrayList<Fragment> fragmentList;
	
	private LinearLayout editProfile;
	private TextView tv_username,tv_userrole;
	private ImageView img_avatar;
	private Context context;
	
//	private ViewPager viewPager_list;

	private LinearLayout ll_likes, ll_recipes, ll_moments;
	private ImageView img_tabLine0, img_tabLine1, img_tabLine2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, null);
		context = getActivity();
		initView(view);
		
		if(user != null) {
	 		displayInfo();
		} else {

			getUserInfoByAsyncHttpClientGet();
		}
		
		changeTextColor(0);
		getUserlikesByAsycHttpClientGet();
		
		editProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, UserDetialActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user",user);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		

		ll_likes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				changeTextColor(0);	
//				viewPager_list.setCurrentItem(0);
				getUserlikesByAsycHttpClientGet();
			}
		});		
		ll_recipes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeTextColor(1);
//				viewPager_list.setCurrentItem(1);
				getUserRecipesByAsycHttpClientGet();
			}
		});		
		ll_moments.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeTextColor(2);
//				viewPager_list.setCurrentItem(2);
				getUserMomentsByAsycHttpClientGet();
			}
		});
		
		return view;
	}
	
	private void initView(View view) {
		editProfile = (LinearLayout) view.findViewById(R.id.edit_profile);
		tv_username = (TextView) view.findViewById(R.id.profile_username);
		tv_userrole = (TextView) view.findViewById(R.id.profile_userlevel);
		img_avatar = (ImageView) view.findViewById(R.id.profile_avatar);

		ll_likes = (LinearLayout) view.findViewById(R.id.ll_user_like);
		ll_recipes = (LinearLayout) view.findViewById(R.id.ll_user_recipes);
		ll_moments = (LinearLayout) view.findViewById(R.id.ll_user_moments);

		img_tabLine0 = (ImageView) view.findViewById(R.id.tabline0);
		img_tabLine1 = (ImageView) view.findViewById(R.id.tabline1);
		img_tabLine2 = (ImageView) view.findViewById(R.id.tabline2);
		
		fragmentList = new ArrayList<Fragment>();
		
	}
	
	public void displayInfo() {

		tv_username.setText(user.getFirstname() + " " + user.getLastname());
		tv_userrole.setText(new RoleUtil(user.getRole()).getRoleStr());
		SimpleImageLoader.showImg(img_avatar,user.getPicture());
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
	
	public void getUserInfoByAsyncHttpClientGet() {	
		AsyncHttpClient usr_client = new AsyncHttpClient();
		String usr_url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/users/token/"+LoginActivity.token;
		
		usr_client.get(usr_url,new JsonHttpResponseHandler() {		
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					
					String firstname = response.getString("firstname");
					String lastname = response.getString("lastname");
					int role = response.getInt("role");
					String email = response.getString("email");
					user = new User(email,firstname, lastname, role);
					user.setUserId(response.getString("_id"));
					user.setAbout(response.getString("about"));
					user.setPicture(response.getString("picture"));
					displayInfo();					
				} catch (JSONException e) {
					e.printStackTrace();
				}
        		super.onSuccess(statusCode, headers, response);
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	public void getUserMomentsByAsycHttpClientGet() {
		AsyncHttpClient moment_client = new AsyncHttpClient();
		
		String moment_url = null;
		moment_url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments/owner/"; 
		moment_url += HomeFragment.userId;
		
		moment_client.get(moment_url, new JsonHttpResponseHandler() {					
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {
					moments = new ArrayList<Moment>();
					JSONObject jsonObj = null;
					int len = response.length();
					
					for (int i = 0; i < len; i++) {							
						jsonObj = response.getJSONObject(i);
						String name = jsonObj.getString("name");
						String date = jsonObj.getString("date");
						String pic = jsonObj.getString("picture");
						Moment moment = new Moment(name, date);
						
						moment.setPictureurl(HomeFragment.userPicture);
						moment.setContentimg(pic);
						moment.setUsername(HomeFragment.userName);
						moment.setContent(jsonObj.getString("description"));
						moment.setOwnId(jsonObj.getString("owner_id"));
						moment.setTargetId(jsonObj.getString("target_id"));
						moment.setId(jsonObj.getString("_id"));	

						moments.add(moment);
						
					}
					FragmentListMoments f_moments = new FragmentListMoments(moments, context);
					fragmentList.add(f_moments);
					
					getFragmentManager().beginTransaction().replace(R.id.fl_list, f_moments).commit();
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	
	public void getUserRecipesByAsycHttpClientGet() {		
		AsyncHttpClient recipe_client = new AsyncHttpClient();
		
		String recipe_url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/receipes";
		//get all the recipes of user by userId
		recipe_client.get(recipe_url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {					
					recipes = new ArrayList<Recipe>();
					int len = response.length();
					JSONObject jsonObj = null;
					for(int i = 0; i < len; i++) {					
						jsonObj = response.getJSONObject(i);
						String name = jsonObj.getString("name");
						String pic = jsonObj.getString("picture");
							
						Recipe recipe = new Recipe(name, pic);
						recipe.set_id(jsonObj.getString("_id"));
						recipe.setDescription(jsonObj.getString("description"));
						recipe.set__v(jsonObj.getString("__v"));
						recipe.setOwnerId(jsonObj.getString("owner"));
						recipe.setValue(jsonObj.getInt("values"));
						recipes.add(recipe);
					}	
					FragmentListRecipes f_recipes = new FragmentListRecipes(recipes, context);
					fragmentList.add(f_recipes);
					
					getFragmentManager().beginTransaction().replace(R.id.fl_list, f_recipes).commit();
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
	
	public void getUserlikesByAsycHttpClientGet() {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/products";
		client.get(url, new JsonHttpResponseHandler() {					
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {
					products = new ArrayList<Product>();
					JSONObject jsonObj = null;
					int len = response.length();
					for (int i = 0; i < len; i++) {
						jsonObj = response.getJSONObject(i);
						String name = jsonObj.getString("name");
						String pic = jsonObj.getString("picture");
						Product pro = new Product(name);
						pro.setPicture(pic);
						pro.setDescription(jsonObj.getString("description"));
						pro.setBrand(jsonObj.getString("brand"));
						pro.setOwnerId(jsonObj.getString("owner"));
						pro.setValue(jsonObj.getString("values"));

						products.add(pro);

					}
					FragmentListProduct f_likes = new FragmentListProduct(products, context);
					fragmentList.add(f_likes);
					
					getFragmentManager().beginTransaction().replace(R.id.fl_list, f_likes).commit();
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
}
