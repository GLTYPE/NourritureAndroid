package com.gltype.nourriture.adapter;

import java.util.List;

import com.gltype.nourriture.imageCache.SimpleImageLoader;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;
import com.gltype.nurriture.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeProductAdapter extends BaseAdapter {

	private List<Product> products;
	private Context context;
	
	public HomeProductAdapter(Context contex, List<Product> products){
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
		if(null == view)
		{
			
			
			Holder holder= new Holder();
			Product product = this.products.get(arg0);
			
			view  = LayoutInflater.from(context).inflate(R.layout.home_gridview_item, null);
			
			holder.txt_item_productname = (TextView) view.findViewById(R.id.tv_home_productname);
			
			
			holder.img_item_productimg= (ImageView) view.findViewById(R.id.img_home_product);
			holder.txt_item_productname.setText(product.getName());
			if(""!=product.getPicture()){
			
				SimpleImageLoader.showImg(holder.img_item_productimg,product.getPicture());
			}
		}
		return view;
	}
	
	private static class Holder
	{
		
		ImageView img_item_productimg;
		
		TextView txt_item_productname;	
		
	}
	
	

}
