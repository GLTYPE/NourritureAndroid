package com.gltype.nourriture.adapter;

import java.util.List;


import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Comment;

import com.gltype.nourriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private List<Comment> comments;
	private Context context;
	
	public CommentAdapter(Context contex, List<Comment> comments){
		this.comments = comments;
		this.context= contex;
	}
	@Override
	public int getCount() {
	
		return comments==null?0:comments.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return comments==null?null:comments.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getCommentId(int arg0) {
		
		return comments.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if(null == view)
		{
			view  = LayoutInflater.from(context).inflate(R.layout.comment_item_template, null);
			
			holder= new Holder(view);
			view.setTag(holder);
			
		}else{
			holder= (Holder)view.getTag();
			
		}
		Comment comment = this.comments.get(arg0);
		holder.txt_item_uname.setText(comment.getUsername());
		holder.txt_item_content.setText(comment.getContent());
		holder.txt_item_time.setText(comment.getDate());
		if("".equals(comment.getPictureurl())||comment.getPictureurl()==null){
			holder.img_item_userphoto.setImageResource(R.drawable.usericon);
		}else{
			SimpleImageLoader.showImg(holder.img_item_userphoto,comment.getPictureurl());
			
		}
		return view;
	}
	
	private static class Holder
	{
		
		ImageView img_item_userphoto;
		
		TextView txt_item_uname;	
		
		TextView txt_item_time;
		
		TextView txt_item_content;
		
		public Holder(View view){
			txt_item_uname = (TextView) view.findViewById(R.id.txt_item_uname);
			txt_item_time = (TextView) view.findViewById(R.id.txt_item_time);
			txt_item_content= (TextView) view.findViewById(R.id.txt_item_content);
			img_item_userphoto= (ImageView) view.findViewById(R.id.img_item_userphoto);
		}
		
		
	}
	
	

}
