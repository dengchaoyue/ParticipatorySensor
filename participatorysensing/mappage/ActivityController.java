/**
 * class name: 
 * class description: 
 * author: dengchaoyue 
 * version: 1.0
 */
package com.example.participatorysensing.mappage;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {
	public static List<Activity> activities = new ArrayList<Activity>();
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	public static void removeActivity(Activity activity){
		activities.remove(activity);
	}
	public static void finishAll(){
		for(Activity activity:activities){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
	
	public ActivityController() {
		// TODO Auto-generated constructor stub
		
	}

}
