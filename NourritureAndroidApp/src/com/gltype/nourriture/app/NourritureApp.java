package com.gltype.nourriture.app;

import com.gltype.nourriture.imageCache.ImageLoader;

import android.app.Application;
import android.content.Context;

public class NourritureApp extends Application {

	public static ImageLoader imageLoader;
	public static Context context;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		context = this.getApplicationContext();
		imageLoader =new ImageLoader();
	}
}
