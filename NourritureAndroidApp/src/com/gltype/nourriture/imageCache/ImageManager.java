package com.gltype.nourriture.imageCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import org.apache.http.HttpException;

import com.gltype.nourriture.utils.MD5Util;
import com.gltype.nurriture.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageManager {
	
	Map<String, SoftReference<Bitmap>> imgCache ;
	Context context;
	
	public static Bitmap userDefaultHead;
	
	public ImageManager(Context context){
		imgCache = new HashMap<String, SoftReference<Bitmap>>();
		this.context= context;
		this.userDefaultHead = drawableToBitmap(context.getResources().getDrawable(R.drawable.usericon));
	}
	public Boolean contains(String url){
		return imgCache.containsKey(url);
	}
	
	public Bitmap getFromCache(String url){//r
		Bitmap bitmap= null;
		bitmap = this.getFromMapCache(url);
		if(null == bitmap){
			bitmap = getFromFile(url);
		}
		return bitmap;
	}
	public Bitmap getFromFile(String url){//r
		String fileName = this.getMd5(url);
		FileInputStream is =null;
		try {
			is = context.openFileInput(fileName);
			return BitmapFactory.decodeStream(is);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return null;
		}
		finally{
			if (null!= is){
				try{
					is.close();
				}catch(Exception e){};
			}
		}
	}
	public Bitmap getFromMapCache(String url){//r
		Bitmap bitmap= null;
		SoftReference<Bitmap> ref =null;
		synchronized (this) {
			ref = imgCache.get(url);
		}
		if(null!=ref){
			bitmap = ref.get();
		}
		return bitmap;
	}
	
	public Bitmap safeGet(String url) throws HttpException{//r
		Bitmap bitmap = this.getFromFile(url);
		if(null!=bitmap){
			synchronized (this) {
				imgCache.put(url, new SoftReference<Bitmap>(bitmap));
			}
			return bitmap;
		}
		
		return downloadImg(url);
	}
	
	
	
	public Bitmap downloadImg(String urlStr)throws HttpException { //r
		try {
			URL url =  new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			String fileName=writeToFile(urlStr,conn.getInputStream());
			return BitmapFactory.decodeFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String writeToFile(String fileName,InputStream is){//r
		BufferedInputStream bis =null;
		BufferedOutputStream bos = null;
		fileName = this.getMd5(fileName);
		try{
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
		byte[] buffer =new byte[1024];
		int length;
		while((length= bis.read(buffer))!= -1){
			bos.write(buffer, 0, length);
		}
		
		
		}catch(Exception e){
			
		}
		finally{
			try {
				if(null != bis){
					bis.close();
				}
				if(null != bos){
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return context.getFilesDir()+"/"+fileName;
	}
	
	private String getMd5(String src){
		return MD5Util.getMD5String(src);
	}
	
	private Bitmap drawableToBitmap(Drawable drawable){
		BitmapDrawable tempDrawable = (BitmapDrawable)drawable;
		return tempDrawable.getBitmap();
	}

}
