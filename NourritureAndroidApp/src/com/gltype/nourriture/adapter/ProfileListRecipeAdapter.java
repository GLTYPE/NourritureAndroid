package com.gltype.nourriture.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.model.Recipe;

public class ProfileListRecipeAdapter extends BaseAdapter {

	private List<Recipe> listRcps;
	private Context context;
	public ProfileListRecipeAdapter(List<Recipe> listPros, Context context) {
		this.listRcps = listPros;
		this.context = context;
	}
	@Override
	public int getCount() {
		return listRcps == null ? 0 : listRcps.size();
	}

	@Override
	public Object getItem(int position) {
		return listRcps == null ? null : listRcps.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view  = convertView;
		
		if(view == null) {
			Recipe recipe = listRcps.get(position);
			ProfileHolder holder = new ProfileHolder();
			
			view  = LayoutInflater.from(context).inflate(R.layout.profile_list_item_template, null);
			
			holder.list_item_img = (ImageView) view.findViewById(R.id.item_img);
			holder.list_item_tv_description = (TextView) view.findViewById(R.id.item_desc);
			holder.list_item_tv_name = (TextView) view.findViewById(R.id.item_name);
			
			if (recipe.getPicture() != null)
				SimpleImageLoader.showImg(holder.list_item_img, recipe.getPicture());
			holder.list_item_tv_description.setText(recipe.getDescription());
			holder.list_item_tv_name.setText(recipe.getName());
			
		}
		return view;
	}

}
