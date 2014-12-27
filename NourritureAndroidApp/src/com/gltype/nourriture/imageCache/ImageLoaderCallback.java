package com.gltype.nourriture.imageCache;

import android.graphics.Bitmap;

public interface ImageLoaderCallback {
	void refesh(String url,Bitmap bitmap);
}
