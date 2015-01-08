package com.gltype.nourriture.utils;

import java.util.Stack;

import android.app.Activity;
import android.util.Log;

public class MyActivityManager {
private static MyActivityManager instance;
private Stack<Activity> activityStack;//activity栈
private MyActivityManager() {
}

public static MyActivityManager getInstance() {
    if (instance == null) {
        instance = new MyActivityManager();
    }
    return instance;
}

public void pushOneActivity(Activity actvity) {
    if (activityStack == null) {
        activityStack = new Stack<Activity>();
    }
    activityStack.add(actvity);
    Log.d("MyActivityManager ", "size = " + activityStack.size());
}

public Activity getLastActivity() {
    return activityStack.lastElement();
}

public void popOneActivity(Activity activity) {
    if (activityStack != null && activityStack.size() > 0) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }
}

public void finishAllActivity() {
    if (activityStack != null) {
        while (activityStack.size() > 0) {
            Activity activity = getLastActivity();
            if (activity == null) break;
            popOneActivity(activity);
        }
    }
}}
