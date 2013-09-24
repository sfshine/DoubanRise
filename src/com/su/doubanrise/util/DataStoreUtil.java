package com.su.doubanrise.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 简单的xml数据操作类
 * 
 * @author sfshine
 * 
 */

public class DataStoreUtil {
	public static String datastore = "doubandata";

	public static void put(Context context, String key, String value) {

		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);

		editor.commit();
	}

	public static String getString(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);

		String arg = settings.getString(key, "0");
		return arg;
	}

	public static void clear(Context context) {
		SharedPreferences settings = context.getSharedPreferences(datastore,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
}