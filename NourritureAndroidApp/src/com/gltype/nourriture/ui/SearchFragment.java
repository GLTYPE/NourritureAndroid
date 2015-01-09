package com.gltype.nourriture.ui;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.gltype.nourriture.R;
import com.gltype.nourriture.adapter.SearchIngredientAdapter;
import com.gltype.nourriture.adapter.SearchProductAdapter;
import com.gltype.nourriture.adapter.SearchRecipeAdapter;
import com.gltype.nourriture.model.Ingredient;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchFragment extends Fragment {
	
	private EditText searchEdit;
	private ImageButton searchButton;
	private Spinner searchSpinner;
	private ListView searchListView;
	
	public List<Product> products;
	public List<Recipe> recipes;
	public List<Ingredient> ingredients;
	private int index;
	 private ArrayAdapter<String> adapter;
	 private static final String[] searchType={"Products","Recipes","Ingredients"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =inflater.inflate(R.layout.fragment_search, null);	
		this.searchButton = (ImageButton) view.findViewById(R.id.bt_search);
		this.searchEdit = (EditText) view.findViewById(R.id.et_search);
		this.searchSpinner = (Spinner) view.findViewById(R.id.sp_search);
		this.searchListView = (ListView) view.findViewById(R.id.lv_serach);
		 adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,searchType);  
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		 searchSpinner.setAdapter(adapter);
		 searchSpinner.setSelection(0);
		// refresh(0);
		 searchSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			 @Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				index=arg2;
				//refresh(index);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		 }); 
		 
		 searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				refresh(index);
				
			}
		});
		 
	
		 return view;		
	}
	
	
	public void refresh(int selectIndex){
		switch(selectIndex){
			case 0:{
				initProductsSearch();
				break;
			}
			case 1:{
				initRecipesSearch();
				break;
			}
			case 2:{
				initIngredientSearch();
				break;
			}
		}
	}
	
	public void initProductsSearch(){
		searchEdit.setText("pe");
		searchProductsByName(searchEdit.getText().toString());
		
		
		searchListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Product product = (Product) arg0.getItemAtPosition(arg2);
				 
				  Intent intent =new Intent(SearchFragment.this.getActivity(), ProductDetialActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("product",product);
				  intent.putExtras(bundle);
				  startActivity(intent);
				
			}
		});	
	}
	
	public void initRecipesSearch(){
		searchEdit.setText("si");
		searchRecipesByName(searchEdit.getText().toString());
		
		searchListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Recipe recipe = (Recipe) arg0.getItemAtPosition(arg2);
				 
				  Intent intent =new Intent(SearchFragment.this.getActivity(), RecipeDetialActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("recipe",recipe);
				  intent.putExtras(bundle);
				  startActivity(intent);
				
			}
		});	
	}
	
	public void initIngredientSearch(){
		searchEdit.setText("se");
		searchIngredientsByName(searchEdit.getText().toString());
		
		searchListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Ingredient ingredient = (Ingredient) arg0.getItemAtPosition(arg2);
				 
				  Intent intent =new Intent(SearchFragment.this.getActivity(), IngredientDetialActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putSerializable("ingredient",ingredient);
				  intent.putExtras(bundle);
				  startActivity(intent);
				
			}
		});	
	}
	
	public void searchRecipesByName(String searchName) {		
		AsyncHttpClient client = new AsyncHttpClient();
		
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/receipes/name/"+searchName;

		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {					
					recipes = new ArrayList<Recipe>();
					
					int len = response.length();					
					JSONObject jsonObj = null;
					
						for(int i = 0; i < len; i++) {					
							jsonObj = response.getJSONObject(i);
							String id = jsonObj.getString("_id");
							String name = jsonObj.getString("name");
							String desc = jsonObj.getString("description");
							String pic = jsonObj.getString("picture");
							int value = jsonObj.getInt("values");	
							Recipe recipe = new Recipe(name,value,desc,pic);
							recipe.set_id(id);
							
						
							recipes.add(recipe);
						}	
						SearchRecipeAdapter rAdapter = new SearchRecipeAdapter(getActivity(), recipes);
						searchListView.setAdapter(rAdapter);
			
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

	
	public void searchProductsByName(String searchName) {		
		AsyncHttpClient client = new AsyncHttpClient();
		
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/products/name/"+searchName;
		
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {					
					products = new ArrayList<Product>();
					
					int len = response.length();					
					JSONObject jsonObject = null;
					
						for(int i = 0; i < len; i++) {	
							jsonObject = response.getJSONObject(i);
							String productname= jsonObject.getString("name");
							String img= jsonObject.getString("picture");
							Product product = new Product(productname);
							product.setPicture(img);
							product.setProductid(jsonObject.getString("_id"));
							product.setDescription(jsonObject.getString("description"));
							product.setValue(jsonObject.getString("values"));
							product.setBrand(jsonObject.getString("brand"));
							
							products.add(product);
						}	
						SearchProductAdapter pAdapter = new SearchProductAdapter(getActivity(), products);
						searchListView.setAdapter(pAdapter);
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
	
	
	public void searchIngredientsByName(String searchName) {		
		AsyncHttpClient client = new AsyncHttpClient();
		
		String url = "http://ec2-54-77-212-173.eu-west-1.compute.amazonaws.com:4242/ingredients/name/"+searchName;
		
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				try {					
					ingredients = new ArrayList<Ingredient>();
					
					int len = response.length();					
					JSONObject jsonObj = null;
					
						for(int i = 0; i < len; i++) {					
							jsonObj = response.getJSONObject(i);
							String id = jsonObj.getString("_id");
							String name = jsonObj.getString("name");
							String desc = jsonObj.getString("description");
							String pic = jsonObj.getString("picture");
							String value = jsonObj.getString("values");	
							Ingredient ingredient = new Ingredient(id,name,value,pic,desc);
							
							ingredients.add(ingredient);
						}	
						SearchIngredientAdapter iAdapter = new SearchIngredientAdapter(getActivity(), ingredients);
						searchListView.setAdapter(iAdapter);
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
