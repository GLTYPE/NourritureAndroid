package com.gltype.nourriture.imageCache;

import com.gltype.nourriture.app.NourritureApp;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class SimpleImageLoader {
	public static void showImg(ImageView view ,String url){
		view.setTag(url);
		view.setImageBitmap(NourritureApp.imageLoader.get(url, getCallback(url,view)));
	}
	private static ImageLoaderCallback getCallback(final String url,final ImageView view){
		return new ImageLoaderCallback() {
			
			@Override
			public void refesh(String url, Bitmap bitmap) {
				
				if(url.equals(view.getTag().toString())){
					view.setImageBitmap(bitmap);
				}
			}
		};
	}

}
