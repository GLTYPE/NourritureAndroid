package com.gltype.nourriture.tests;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.gltype.nourriture.R;
import com.gltype.nourriture.ui.*;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentTabHost;
import android.test.ActivityInstrumentationTestCase2;

@SuppressLint("NewApi")
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	
	private FragmentTabHost mTabHost;
	
	private int tabId[] = {R.string.Tab_HOME,R.string.Tab_MOMENTS,
			R.string.Tab_SEARCH,R.string.Tab_PROFILE};
	private int tabSrcId[] = {R.drawable.tab_icon_home,R.drawable.tab_icon_moments,
			R.drawable.tab_icon_search,R.drawable.tab_icon_profile};	
	private String tabSpecs[] = {"HOME","MOMENTS","SEARCH","PROFILE"};
	private Class FClassArray[] = {HomeFragment.class,MomentFragment.class,
			SearchFragment.class,ProfileFragment.class};
	
	public MainActivityTest(Class<MainActivity> activityClass) {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		setActivityInitialTouchMode(false);
		
		mActivity = getActivity();
		
		mTabHost = (FragmentTabHost)mActivity.findViewById
				(com.gltype.nourriture.R.id.startup_tabHost);
		
		
		
	}
	
	public void testTabHost() {
		
		mActivity.runOnUiThread(
				new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mTabHost.requestFocus();
						
					}
				});
		
	}
	

	public void testDemo() throws UiObjectNotFoundException{
//		getUiDevice().pressHome();
//		UiObject allAppsButton = new UiObject(new UiSelector().description("nourriture"));
//		allAppsButton.clickAndWaitForNewWindow();
//		UiObject appsTab = new UiObject(new UiSelector().text("Apps"));
//		appsTab.click();
//		UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
//		appViews.setAsHorizontalList();
//		UiObject settingsApp = appViews.getChildByText(new UiSelector()
//			.className(android.widget.TextView.class.getName()),"Settings");
//		settingsApp.clickAndWaitForNewWindow();
//		UiObject settingsValidation = new UiObject(new UiSelector().packageName("com.android.settings"));
//		assertTrue("Unable to detect Settings",settingsValidation.exists());
	}


}
