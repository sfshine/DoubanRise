package com.su.doubanrise.api;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.su.doubanrise.MainActivity;
import com.su.doubanrise.util.DataStoreUtil;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.UIutil;

public class Auth {
	/**
	 * 授权成功后保存到配置文件
	 */
	private Context context;

	public Auth(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 检查用户是否进行过授权 true表示授权过
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkToken(Context context) {
		if (DataStoreUtil.getString(context, "refresh_token").length() > 3) {
			return true;// 授权过返回true
		} else {
			return false;
		}
	}

	/**
	 * 拼接获取code的url
	 * 
	 * @return
	 */
	public String getCodeURL() {
		StringBuilder stringBuilder = new StringBuilder(Douban.AUTHURL);
		stringBuilder.append(Douban.APIKEY);
		stringBuilder.append("&redirect_uri=");
		stringBuilder.append(Douban.REDIRECT_URI);
		stringBuilder
				.append("&response_type=code&scope=music_basic_r,douban_basic_common,community_advanced_doumail_r,community_advanced_doumail_w,douban_basic_common,movie_basic_r,movie_basic_w,community_basic_note,community_basic_user,book_basic_r,book_basic_w,shuo_basic_r,shuo_basic_w,event_basic_r,event_basic_w");
		return stringBuilder.toString();

	}

	/**
	 * 获取token
	 * 
	 * @param code
	 */

	public void getToken(final String code) {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("client_id", Douban.APIKEY);
				map.put("client_secret", Douban.SECRET);
				map.put("redirect_uri", Douban.REDIRECT_URI);
				map.put("grant_type", "authorization_code");
				map.put("code", code);
				String result = HttpUtil.post(Douban.TOKENURL, map);

				return result;
			}

			protected void onPostExecute(String result) {
				Intent intent;
				if (saveInfo(result)) {
					UIutil.toast(context, "授权成功");
					intent = new Intent(context, MainActivity.class);

					context.startActivity(intent);
				} else {
					UIutil.toast(context, "授权失败，请重试");

				}

				((Activity) context).finish();
			};
		}.execute();

	}

	/**
	 * 刷新token
	 * 
	 * @return
	 */
	public boolean refreshToken() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("client_id", Douban.APIKEY);
		map.put("client_secret", Douban.SECRET);
		map.put("redirect_uri", Douban.REDIRECT_URI);
		map.put("refresh_token",
				DataStoreUtil.getString(context, "refresh_token"));

		String result = HttpUtil.post(Douban.TOKENURL, map);
		MLog.e(result);
		saveInfo(result);
		// if (saveInfo(result)) {
		// UIutil.toast(context, "刷新授权成功");
		//
		// } else {
		// UIutil.toast(context, "刷新授权失败，建议重新启动应用");
		//
		// }
		return false;

	}

	/**
	 * 保存用户信息
	 * 
	 * @param result
	 * @return
	 */
	private boolean saveInfo(String result) {
		try {

			JSONObject jsonObject = new JSONObject(result);
			String access_token = jsonObject.getString("access_token");
			String expires_in = jsonObject.getString("expires_in");
			String refresh_token = jsonObject.getString("refresh_token");
			String douban_user_id = jsonObject.getString("douban_user_id");

			DataStoreUtil.put(context, "access_token", access_token);
			DataStoreUtil.put(context, "expires_in", expires_in);
			DataStoreUtil.put(context, "refresh_token", refresh_token);
			DataStoreUtil.put(context, "douban_user_id", douban_user_id);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
