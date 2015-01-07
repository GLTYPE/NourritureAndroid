package com.gltype.nourriture.ui;

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

import com.gltype.nourriture.adapter.MomentAdapter;
import com.gltype.nourriture.adapter.ProfileMomentAdapter;
import com.gltype.nourriture.adapter.ProfileRecipeAdapter;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nourriture.model.User;
import com.gltype.nourriture.utils.RoleUtil;
import com.gltype.nurriture.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

@SuppressLint("NewApi")
public class ProfileFragment extends Fragment {
	
	public User user = null;
	public List<Moment> moments;
	public List<Recipe> recipes;
	
	private LinearLayout editProfile;
	private TextView tv_username,tv_userrole;
	private ImageView img_avatar;
	private GridView gv_like, gv_recipe, gv_moments;
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, null);
		context = getActivity();
		
		editProfile = (LinearLayout) view.findViewById(R.id.edit_profile);
		tv_username = (TextView) view.findViewById(R.id.profile_username);
		tv_userrole = (TextView) view.findViewById(R.id.profile_userlevel);
		img_avatar = (ImageView) view.findViewById(R.id.profile_avatar);
		gv_like = (GridView) view.findViewById(R.id.gv_user_like);
		gv_recipe = (GridView) view.findViewById(R.id.gv_recipe);
		gv_moments = (GridView) view.findViewById(R.id.gv_moments);

		gv_like.setVerticalSpacing(30);
		gv_recipe.setVerticalSpacing(30);
		gv_moments.setVerticalSpacing(30);
		
		if(user != null) {
			displayInfo();
		} else {

			getUserInfoByAsyncHttpClientGet();
		}
		getUserRecipesByAsycHttpClientGet();
		
		ProfileRecipeAdapter rAdapter = new ProfileRecipeAdapter(recipes, context);
		gv_recipe.setAdapter(rAdapter);	
		gv_like.setAdapter(rAdapter);	
		
		getUserMomentsByAsycHttpClientGet();
		ProfileMomentAdapter mAdapter = new ProfileMomentAdapter(moments, context);
		gv_moments.setAdapter(mAdapter);	
		
		
		
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
		return view;
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
		//get information of user by email
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
					if(len < 3) {
						for(int i = 0; i < len; i++) {					
							jsonObj = response.getJSONObject(i);
							String id = jsonObj.getString("_id");
							String name = jsonObj.getString("name");
							String desc = jsonObj.getString("description");
							String pic = jsonObj.getString("picture");
								
							Recipe recipe = new Recipe(name, pic);
							recipe.set_id(id);
							recipe.setDescription(desc);
							//recipe.setIngs(jsonObj.getString("ings"));
							recipes.add(recipe);
						}	
					} else {
						for(int i = 0; i < 3; i++) {	
							
							jsonObj = response.getJSONObject(i);
							String name = jsonObj.getString("name");
							String pic = jsonObj.getString("picture");	
							Recipe recipe = new Recipe(name, pic);	
							recipe.set_id(jsonObj.getString("_id"));
							recipe.setDescription(jsonObj.getString("description"));
							recipes.add(recipe);
							
						}
					}
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
	
	public void getUserMomentsByAsycHttpClientGet() {
		AsyncHttpClient moment_client = new AsyncHttpClient();
//		String moment_url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/moments/owner/";
		//test
		String moment_url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/products";
		//moment_client.get(moment_url + user.getUserId(), new JsonHttpResponseHandler() {
		moment_client.get(moment_url, new JsonHttpResponseHandler() {					
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {
					moments = new ArrayList<Moment>();
					JSONObject jsonObj = null;
					int len = response.length();
					if (len < 3) {
						for (int i = 0; i < len; i++) {
							jsonObj = response.getJSONObject(i);
							String username = user.getFirstname();
							String content = jsonObj.getString("description");
							String pic = jsonObj.getString("picture");
							Moment moment = new Moment(username, content, pic);

							moments.add(moment);
						}
					} else {
						for (int i = 0; i < 3; i++) {
							jsonObj = response.getJSONObject(i);
							String username = "";
							String content = jsonObj.getString("description");
							String pic = jsonObj.getString("picture");
							Moment moment = new Moment(username, content, pic);
	
							moments.add(moment);
						}
					}
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
