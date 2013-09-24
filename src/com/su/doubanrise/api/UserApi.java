package com.su.doubanrise.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;

public class UserApi {
	static String HOST = "https://api.douban.com/v2/";// 定义主机地址

	Gson gson = new Gson();

	/**
	 * 获取我的信息
	 * 
	 * @return
	 */
	public User getMyinfo() {
		String url = HOST + "user/~me";
		String result = HttpUtil.get(url, null);
		try {

			User user = gson.fromJson(result, User.class);
			return user;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 通过uid获取信息
	 * 
	 * @param name
	 *            uid
	 * @return
	 */
	public User getUserByName(String name) {
		String url = HOST + "user/" + name;
		String result = HttpUtil.get(url, null);

		try {

			User user = gson.fromJson(result, User.class);
			return user;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	/**
	 * 
	 * @param q
	 *            全文检索的关键词
	 * @param start
	 *            起始元素
	 * @param count
	 *            返回结果的数量
	 */

	public List<User> searchUser(String q, int start, int count) {
		String url = HOST + "user";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("q", q);
		map.put("start", start + "");
		map.put("count", count + "");
		String result = HttpUtil.get(url, map);
		try {

			JSONObject jsonObject = new JSONObject(result);
			String json = jsonObject.getString("users");

			Type listType = new TypeToken<List<User>>() {
			}.getType();

			List<User> users = gson.fromJson(json, listType);
			// User user = gson.fromJson(result, User.class);
			MLog.e(users.get(29).getAvatar());
			return users;

		} catch (Exception e) {

		}
		return null;

	}
}
