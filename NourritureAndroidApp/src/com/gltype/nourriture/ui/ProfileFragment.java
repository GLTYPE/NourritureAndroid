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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gltype.nourriture.adapter.HomeProductAdapter;
import com.gltype.nourriture.adapter.MomentAdapter;
import com.gltype.nourriture.adapter.ProfileMomentAdapter;
import com.gltype.nourriture.adapter.ProfileRecipeAdapter;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nourriture.model.User;
import com.gltype.nourriture.utils.RoleUtil;
import com.gltype.nourriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

@SuppressLint("NewApi")
public class ProfileFragment extends Fragment {
	
	public User user;
	public List<Moment> moments;
	public List<Recipe> recipes;
	public List<Product> products;
	
	private LinearLayout editProfile;
	private TextView tv_username,tv_userrole;
	private ImageView img_avatar;
	private GridView gv_like, gv_recipe, gv_moments;
	private Context context;

	private int whichList;
	private TextView tv_like, tv_recipe, tv_moments;
	
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
		
		getUserRecipesByAsycHttpClientGet();
		getUserlikesByAsycHttpClientGet();
		getUserMomentsByAsycHttpClientGet();	
		
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
		

		tv_like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				sendMsg(0);			
			}
		});		
		tv_recipe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendMsg(1);
			}
		});		
		tv_moments.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendMsg(2);
			}
		});
		
		return view;
	}
	
	private void sendMsg(int whichPage) {
		Intent intent = new Intent(context, ShowAllListActivity.class);
		whichList = whichPage;
		Bundle bundle = new Bundle();
		bundle.putSerializable("whichList", whichList);
		bundle.putSerializable("products", (Serializable) products);
		bundle.putSerializable("recipes", (Serializable) recipes);
		bundle.putSerializable("moments", (Serializable) moments);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	private void initView(View view) {
		editProfile = (LinearLayout) view.findViewById(R.id.edit_profile);
		tv_username = (TextView) view.findViewById(R.id.profile_username);
		tv_userrole = (TextView) view.findViewById(R.id.profile_userlevel);
		img_avatar = (ImageView) view.findViewById(R.id.profile_avatar);
		gv_like = (GridView) view.findViewById(R.id.gv_user_like);
		gv_recipe = (GridView) view.findViewById(R.id.gv_recipe);
		gv_moments = (GridView) view.findViewById(R.id.gv_moments);
		tv_like = (TextView) view.findViewById(R.id.text_user_like);
		tv_recipe = (TextView) view.findViewById(R.id.text_recipe);
		tv_moments = (TextView) view.findViewById(R.id.text_moments);

		gv_like.setVerticalSpacing(30);
		gv_recipe.setVerticalSpacing(30);
		gv_moments.setVerticalSpacing(30);

	}
	
	public void displayInfo() {

		tv_username.setText(user.getFirstname() + " " + user.getLastname());
		tv_userrole.setText(new RoleUtil(user.getRole()).getRoleStr());
		if("".equals(user.getPicture())) {
			SimpleImageLoader.showImg(img_avatar,user.getPicture());
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
					int len = response.length() < 3 ? response.length() : 3;
					
					for (int i = 0; i < len; i++) {							
							//{"__v":0,"comments":[]}
						jsonObj = response.getJSONObject(i);
						String name = jsonObj.getString("name");
						String date = jsonObj.getString("date");
						String pic = jsonObj.getString("picture");
						Moment moment = new Moment(name, date);
						
						moment.setPictureurl(pic);
						moment.setContent(jsonObj.getString("description"));
						moment.setOwnId(jsonObj.getString("owner_id"));
						moment.setTargetId(jsonObj.getString("target_id"));
						moment.setId(jsonObj.getString("_id"));	

						moments.add(moment);
						
					}
					ProfileMomentAdapter mAdapter = new ProfileMomentAdapter(moments, context);
					gv_moments.setAdapter(mAdapter);	
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
					int len = response.length() < 3 ? response.length() : 3;					
					JSONObject jsonObj = null;
					for(int i = 0; i < len; i++) {					
						jsonObj = response.getJSONObject(i);
						//"ings":[],"moments":[],"rate":[]
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
					ProfileRecipeAdapter rAdapter = new ProfileRecipeAdapter(recipes, context);
					gv_recipe.setAdapter(rAdapter);	
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
					int len = response.length() < 3 ? response.length() : 3;
					for (int i = 0; i < len; i++) {
						jsonObj = response.getJSONObject(i);
						//"ings":[],"moments":[],"rate":[]
						String name = jsonObj.getString("name");
						String pic = jsonObj.getString("picture");
						Product pro = new Product(name);
						pro.setPicture(pic);
						pro.setDescription(jsonObj.getString("description"));
						pro.setBrand(jsonObj.getString("brand"));
						pro.setOwnerId(jsonObj.getString("owner"));
						pro.setValue(jsonObj.getString("values"));
//						pro.setId(jsonObj.getString("_id"));

						products.add(pro);
					}
					HomeProductAdapter lAdapter = new HomeProductAdapter(context, products);
					gv_like.setAdapter(lAdapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
}
