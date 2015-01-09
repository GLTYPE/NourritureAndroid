package com.gltype.nourriture.adapter;

import java.util.List;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;
import com.gltype.nourriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchProductAdapter extends BaseAdapter {

	private List<Product> products;
	private Context context;
	
	public SearchProductAdapter(Context contex, List<Product> products){
		this.products = products;
		this.context= contex;
	}
	@Override
	public int getCount() {
	
		return products==null?0:products.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return products==null?null:products.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return products.get(arg0).getId();
	}
 
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		SearchHolder holder;
		if(null == view)
		{
			
			
			 holder= new SearchHolder();
			
			
			view  = LayoutInflater.from(context).inflate(R.layout.search_item_template, null);
			
			holder.tv_search_item_name = (TextView) view.findViewById(R.id.tv_item_name);
			holder.tv_search_item_value = (TextView) view.findViewById(R.id.tv_item_value);
			holder.tv_search_item_description = (TextView) view.findViewById(R.id.tv_item_description);		
			holder.img_search_item= (ImageView) view.findViewById(R.id.img_item_pic);
			
			view.setTag(holder);
			
		}else {
			holder = (SearchHolder)view.getTag();
		}
		Product product = this.products.get(arg0);
		holder.tv_search_item_name.setText(product.getName());
		holder.tv_search_item_value.setText(product.getValue());
		holder.tv_search_item_description.setText(product.getDescription());
		if(""!=product.getPicture()){
		
			SimpleImageLoader.showImg(holder.img_search_item,product.getPicture());
		}
		return view;
	}
	
	
	

}
