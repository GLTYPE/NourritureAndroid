package com.gltype.nourriture.adapter;

import java.util.List;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nurriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileMomentAdapter extends BaseAdapter {
	
	private List<Moment> itemList;
	private Context context;
	
	public ProfileMomentAdapter(List<Moment> itemList, Context context) {
		this.itemList = itemList;
		this.context = context;
	}

	@Override
	public int getCount() {
		if(itemList == null) return 0;
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		if(itemList == null) return null;
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if(itemList == null) return 0;
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;

		if(view == null) {
			Holder holder = new Holder();
			Moment moment = itemList.get(position);
			
			view = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
			holder.img_item = (ImageView) view.findViewById(R.id.img_item);
			holder.txt_item_name = (TextView) view.findViewById(R.id.tv_item_name);
			
			holder.txt_item_name.setText(moment.getContent());
			if(holder.img_item != null) {
				SimpleImageLoader.showImg(holder.img_item, moment.getPictureurl());
			}
		}
		
		return view;
	}

}
