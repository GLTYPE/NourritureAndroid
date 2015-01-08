package com.gltype.nourriture.adapter;

import java.util.List;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MomentAdapter extends BaseAdapter {

	private List<Moment> moments;
	private Context context;
	
	public MomentAdapter(Context contex, List<Moment> moments){
		this.moments = moments;
		this.context= contex;
	}
	@Override
	public int getCount() {
	
		return moments==null?0:moments.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return moments==null?null:moments.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return moments.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		if(null == view)
		{
			
			
			Holder holder= new Holder();
			Moment moment = this.moments.get(arg0);
			
			view  = LayoutInflater.from(context).inflate(R.layout.item_template, null);
			
			holder.txt_item_uname = (TextView) view.findViewById(R.id.txt_item_uname);
			
			holder.txt_item_content= (TextView) view.findViewById(R.id.txt_item_content);
			holder.img_item_userphoto= (ImageView) view.findViewById(R.id.img_item_userphoto);
			holder.txt_item_uname.setText(moment.getUsername());
			holder.txt_item_content.setText(moment.getContent());
			
		//	SimpleImageLoader.showImg(holder.img_item_userphoto,moment.getPictureurl());
		}
		return view;
	}
	
	private static class Holder
	{
		
		ImageView img_item_userphoto;
		
		TextView txt_item_uname;	
		
		TextView txt_item_time;
		
		TextView txt_item_content;
		
		ImageView img_item_content_pic;
		
		LinearLayout lyt_item_sublayout;
		
		TextView txt_item_subcontent;
		
		ImageView img_item_content_subpic;
		
		TextView txt_item_from;
		
		TextView txt_item_redirect;
		
		TextView txt_item_comment;
		
	}
	
	

}
