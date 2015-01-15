package com.gltype.nourriture.adapter;

import java.util.List;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Ingredient;
import com.gltype.nourriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchIngredientAdapter extends BaseAdapter {

	private List<Ingredient> ingredients;
	private Context context;

	public SearchIngredientAdapter(Context context, List<Ingredient> ingredients) {
		this.ingredients = ingredients;
		this.context = context;
	}

	@Override
	public int getCount() {
		if (ingredients == null)
			return 0;
		return ingredients.size();
	}

	@Override
	public Object getItem(int position) {
		if (ingredients == null)
			return null;
		return ingredients.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (ingredients == null)
			return 0;

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		SearchHolder holder;
		if (view == null) {
			holder = new SearchHolder();

			view = LayoutInflater.from(context).inflate(
					R.layout.search_item_template, null);

			holder.tv_search_item_name = (TextView) view
					.findViewById(R.id.tv_item_name);
			holder.tv_search_item_value = (TextView) view
					.findViewById(R.id.tv_item_value);
			holder.tv_search_item_description = (TextView) view
					.findViewById(R.id.tv_item_description);
			holder.img_search_item = (ImageView) view
					.findViewById(R.id.img_item_pic);
			view.setTag(holder);

		} else {
			holder = (SearchHolder) view.getTag();
		}
		Ingredient ingredient = ingredients.get(position);
		holder.tv_search_item_name.setText(ingredient.getName());
		holder.tv_search_item_value
				.setText(ingredient.getValue() + " calories");
		if ("".equals(ingredient.getDescription())) {
			holder.tv_search_item_description.setText("No description~");
		} else {
			holder.tv_search_item_description.setText(ingredient
					.getDescription());
		}
		if ("".equals(ingredient.getPicture()))
			holder.img_search_item.setImageResource(R.drawable.avatar_tomato);
		else
			SimpleImageLoader.showImg(holder.img_search_item, ingredient.getPicture());
		return view;
	}

}
