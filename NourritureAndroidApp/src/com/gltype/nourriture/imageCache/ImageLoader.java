package com.gltype.nourriture.imageCache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.gltype.nourriture.app.NourritureApp;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ImageLoader {

	private ImageManager imgManager = new ImageManager(NourritureApp.context);
	private BlockingQueue<String> urlQueue = new ArrayBlockingQueue<String>(50);
	private DownloadImageThread downloadImageThread=new DownloadImageThread();
	private CallbackManager callbackManager = new CallbackManager();
	
	
	
	private static final int MESSAGEID=1;
	private static final String EXTRA_IMG_URL="extra_img_url";
	private static final String EXTRA_IMG="extra_img";
	
	
	
	
	public Bitmap get(String url,ImageLoaderCallback callback){
		
		Bitmap bitmap= null;
		if(imgManager.contains(url)){
			bitmap = imgManager.getFromCache(url);
			return bitmap;
		}
		else{
		
			callbackManager.put(url, callback);
			startDownloadThread(url);
		}
		return bitmap;
	}
	
	private void startDownloadThread(String url){
		putUrlToUrlQueue(url);
		java.lang.Thread.State state = downloadImageThread.getState();
		if(state == java.lang.Thread.State.NEW){
			downloadImageThread.start();
		}
		else if(state == java.lang.Thread.State.TERMINATED){
			downloadImageThread = new DownloadImageThread();
			downloadImageThread.start();
		}
		
	}
	private void putUrlToUrlQueue(String url){
		if(!urlQueue.contains(url)){
			try {
				urlQueue.put(url);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
				case MESSAGEID:{
					Bundle bundle = msg.getData();
					String url = bundle.getString(EXTRA_IMG_URL);
					Bitmap bitmap = bundle.getParcelable(EXTRA_IMG);
					callbackManager.callback(url, bitmap);
				
				}
			}
			
			
		};
	};
	
	
	
	public class DownloadImageThread extends Thread{
		private boolean isRun = true;
		
		public void shutDown(){
			isRun = false;
		}
		@Override
		public void run() {
			try {
				while(isRun){
					String url = urlQueue.poll();
					if(null==url){
						break;
					}
					Bitmap bitmap = imgManager.safeGet(url);
				Message msg = handler.obtainMessage(MESSAGEID);
				Bundle bundle = msg.getData();
				bundle.putSerializable(EXTRA_IMG_URL, url);
				bundle.putParcelable(EXTRA_IMG, bitmap);
				handler.sendMessage(msg);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			finally{
				shutDown();
			}
		}
		
		
	}
	
}
