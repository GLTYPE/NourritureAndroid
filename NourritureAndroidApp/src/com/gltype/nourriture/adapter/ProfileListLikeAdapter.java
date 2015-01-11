package com.gltype.nourriture.adapter;

import java.util.List;
import java.util.zip.Inflater;

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

public class ProfileListLikeAdapter extends BaseAdapter {

	private List<Product> listPros;
	private Context context;
	public ProfileListLikeAdapter(List<Product> listPros, Context context) {
		this.listPros = listPros;
		this.context = context;
	}
	@Override
	public int getCount() {
		return listPros == null ? 0 : listPros.size();
	}

	@Override
	public Object getItem(int position) {
		return listPros == null ? null : listPros.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listPros == null ? 0 : listPros.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view  = convertView;
		
		if(view == null) {
			Product pro = listPros.get(position);
			ProfileHolder holder = new ProfileHolder();
			
			view  = LayoutInflater.from(context).inflate(R.layout.profile_list_item_template, null);
			
			holder.list_item_img = (ImageView) view.findViewById(R.id.item_img);
			holder.list_item_tv_description = (TextView) view.findViewById(R.id.item_desc);
			holder.list_item_tv_name = (TextView) view.findViewById(R.id.item_name);
			
			if (pro.getPicture() != null)
				SimpleImageLoader.showImg(holder.list_item_img, pro.getPicture());
			holder.list_item_tv_description.setText(pro.getDescription());
			holder.list_item_tv_name.setText(pro.getName());
			
		}
		return view;
	}

}
