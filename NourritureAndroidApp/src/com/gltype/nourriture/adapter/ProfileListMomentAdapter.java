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
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;

public class ProfileListMomentAdapter extends BaseAdapter {

	private List<Moment> listMoms;
	private Context context;
	public ProfileListMomentAdapter(List<Moment> listMoms, Context context) {
		this.listMoms = listMoms;
		this.context = context;
	}
	@Override
	public int getCount() {
		return listMoms == null ? 0 : listMoms.size();
	}

	@Override
	public Object getItem(int position) {
		return listMoms == null ? null : listMoms.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view  = convertView;
		
		if(view == null) {
			Moment moment = listMoms.get(position);
			ProfileHolder holder = new ProfileHolder();
			
			view  = LayoutInflater.from(context).inflate(R.layout.profile_list_item_template, null);
			
			holder.list_item_img = (ImageView) view.findViewById(R.id.item_img);
			holder.list_item_tv_description = (TextView) view.findViewById(R.id.item_desc);
			holder.list_item_tv_name = (TextView) view.findViewById(R.id.item_name);
			
			if (moment.getPictureurl() != null)
				SimpleImageLoader.showImg(holder.list_item_img, moment.getPictureurl());
			holder.list_item_tv_description.setText(moment.getContent());
			holder.list_item_tv_name.setText(moment.getName());
			
		}
		return view;
	}

}
