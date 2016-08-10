package com.gcl.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtil {
	
	private Context ctx;
	private static PreferenceUtil util;
	private static SharedPreferences preferences;
	
	public static synchronized PreferenceUtil With(Context ctx){
		if(util == null ){
			util = new PreferenceUtil(ctx);
		}
		return util;
	}
	
	private PreferenceUtil(Context ctx){
		this.ctx = ctx;
		preferences = ctx.getSharedPreferences("library", ctx.MODE_PRIVATE);
	}
	
	/**
	 * 从SharedPreference中取数据，找不到返回null
	 */
	public String getItem(String nameString){
		return preferences.getString(nameString, null);
	}
	
	/**
	 * 设置item
	 */
	public void setItem(String name, String value) {
		Editor editor = preferences.edit();
		editor.putString(name, value);
		editor.commit();
	}
}
