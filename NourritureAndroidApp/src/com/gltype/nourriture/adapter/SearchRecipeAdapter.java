package com.gltype.nourriture.adapter;

import java.util.List;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nurriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchRecipeAdapter extends BaseAdapter {
	
	private List<Recipe> recipes;
	private Context context;
	
	public SearchRecipeAdapter(Context context,List<Recipe> recipes) {
		this.recipes = recipes;
		this.context = context;
	}

	@Override
	public int getCount() {
		if(recipes == null) return 0;
		return recipes.size();
	}

	@Override
	public Object getItem(int position) {
		if(recipes == null) return null;
		return recipes.get(position);
	}

	@Override
	public long getItemId(int position) {
		if(recipes == null) return 0;
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		SearchHolder holder ;
		if(view == null) {
			 holder = new SearchHolder();
			
			view  = LayoutInflater.from(context).inflate(R.layout.search_item_template, null);
			
			holder.tv_search_item_name = (TextView) view.findViewById(R.id.tv_item_name);
			holder.tv_search_item_value = (TextView) view.findViewById(R.id.tv_item_value);
			holder.tv_search_item_description = (TextView) view.findViewById(R.id.tv_item_description);		
			holder.img_search_item= (ImageView) view.findViewById(R.id.img_item_pic);
			view.setTag(holder);
			
		}else{
			holder = (SearchHolder)view.getTag();
		}
		Recipe recipe = recipes.get(position);
		holder.tv_search_item_name.setText(recipe.getName());
		holder.tv_search_item_value.setText(recipe.getValue()+"");
		holder.tv_search_item_description.setText(recipe.getDescription());
		if(""!=recipe.getPicture()){
		
			SimpleImageLoader.showImg(holder.img_search_item,recipe.getPicture());
		}
		return view;
	}

}
