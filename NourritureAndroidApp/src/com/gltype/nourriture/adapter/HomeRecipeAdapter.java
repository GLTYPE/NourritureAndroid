package com.gltype.nourriture.adapter;

import java.util.List;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Recipe;
import com.gltype.nurriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeRecipeAdapter extends BaseAdapter {

	private List<Recipe> recipes;
	private Context context;
	
	public HomeRecipeAdapter(Context context, List<Recipe> recipes){
		this.recipes = recipes;
		this.context= context;
	}
	@Override
	public int getCount() {
	
		return recipes==null?0:recipes.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return recipes==null?null:recipes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return 0;
	}
 
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		if(null == view)
		{
			
			
			Holder holder= new Holder();
			Recipe recipe = this.recipes.get(arg0);
			
			view  = LayoutInflater.from(context).inflate(R.layout.recipe_gridview_item, null);
			
			holder.txt_item_name = (TextView) view.findViewById(R.id.tv_item_name);
			
			
			holder.img_item= (ImageView) view.findViewById(R.id.img_item);
			holder.txt_item_name.setText(recipe.getName());
			if(""!=recipe.getPicture()){
			
				SimpleImageLoader.showImg(holder.img_item,recipe.getPicture());
			}
		}
		return view;
	}
	
	
	

}
