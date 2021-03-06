package com.gltype.nourriture.ui;


import com.gltype.nourriture.R;
import com.gltype.nourriture.utils.MyActivityManager;




import android.annotation.SuppressLint;



import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends FragmentActivity{	

	private FragmentTabHost mTabHost;
	
	private LayoutInflater layoutInflater;
	
	private int tabId[] = {R.string.Tab_HOME,R.string.Tab_MOMENTS,
			R.string.Tab_SEARCH,R.string.Tab_PROFILE};
	
	private int tabSrcId[] = {R.drawable.tab_icon_home,R.drawable.tab_icon_moments,
			R.drawable.tab_icon_search,R.drawable.tab_icon_profile};	
	
	private String tabSpecs[] = {"HOME","MOMENTS","SEARCH","PROFILE"};
	
	private Class<?> FClassArray[] = {HomeFragment.class,MomentFragment.class,
			SearchFragment.class,ProfileFragment.class};
	
	MyActivityManager mam = MyActivityManager.getInstance();
	//private View titleView;
	//private Button btn_back;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_startup);
        mam.pushOneActivity(this);
        Intent intent = getIntent();
       
        initView();
    }
	 	
	private void initView(){
		
		layoutInflater = LayoutInflater.from(this);	
	
		mTabHost = (FragmentTabHost)findViewById(R.id.startup_tabHost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.fragment_content);	

		int count = FClassArray.length;	
				
		for(int i = 0; i < count; ++i) {
    		mTabHost.addTab(mTabHost.newTabSpec(tabSpecs[i]).setIndicator(
            		getTabIndicator(mTabHost.getContext(), tabId[i], tabSrcId[i])),
            		FClassArray[i], null);
    	}
	}
	
	private View getTabIndicator(Context context, int title, int icon) {
    	View view = layoutInflater.inflate(R.layout.layout_tabs, null);
    	
    	ImageView imgView = (ImageView) view.findViewById(R.id.tab_image);
    	imgView.setImageResource(icon);
    	TextView tView = (TextView) view.findViewById(R.id.tab_text);
    	tView.setText(title);
    	   	
    	return view;
	}
}
