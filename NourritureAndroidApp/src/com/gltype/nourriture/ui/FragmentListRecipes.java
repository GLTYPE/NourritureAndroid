package com.gltype.nourriture.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.adapter.ProfileListRecipeAdapter;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Recipe;

public class FragmentListRecipes extends Fragment {

	private List<Recipe> recipes;
	private ListView lv_recipes;
	private ProfileListRecipeAdapter adapter;
	
	public FragmentListRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.profile_fragment_recipes, container, false);
		
		lv_recipes = (ListView) view.findViewById(R.id.list_recipes);
		adapter = new ProfileListRecipeAdapter(recipes, getActivity());
		lv_recipes.setAdapter(adapter);
		
		lv_recipes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long arg3) {
				Recipe recipe = (Recipe) list.getItemAtPosition(position);
				Intent intent = new Intent(FragmentListRecipes.this.getActivity(), RecipeDetialActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("recipe", recipe);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		return view;
	}
}
