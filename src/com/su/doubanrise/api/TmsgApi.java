package com.su.doubanrise.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Environment;

import com.su.doubanrise.api.bean.FormFile;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.TestHttpUtil;

public class TmsgApi {

	static String HOST = "https://api.douban.com/";// 定义主机地址

	/**
	 * 获取用户关注列表 说明
	 * 
	 * 返回一个用户follow的用户列表 URL
	 * 
	 * shuo/v2/users/:id/following 支持格式 *
	 * 
	 * JSON
	 * 
	 * HTTP请求方式 *
	 * 
	 * GET
	 * 
	 * 请求参数 *
	 * 
	 * 必选 类型及范围 说明 tag false int 该tag的id 调用示例 *
	 * 
	 * shuo/v2/users/:id/following
	 * 
	 * 返回结果
	 * 
	 * 返回一个用户follow的用户列表
	 */

	public List<User> getFollowing(String uid) {
		String url = HOST + "shuo/v2/users/" + uid + "/following";
		String result = HttpUtil.get(url, null);
		List<User> users = new ArrayList<User>();
		try {
			JSONArray jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (!jsonObject.toString().contains("original_site_url")) {
					String id = jsonObject.getString("uid");
					String icon_avatar = jsonObject.getString("small_avatar");
					String name = jsonObject.getString("screen_name");
					User user = new User();
					user.setId(id);
					user.setAvatar(icon_avatar);
					user.setName(name);
					users.add(user);

				}
			}
			return users;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	/*
	 * source true string appkey text true string 广播文本内容 image false bytes 我说的图
	 * rec_title false string 推荐网址的标题 rec_url false string 推荐网址的href rec_desc
	 * false string 推荐网址的描述
	 */
	public boolean addNewTmsg(String tmsg, String imgpath, String rec_title,
			String rec_url, String rec_desc) {
		String url = HOST + "shuo/v2/statuses/";

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("source", Douban.APIKEY);
		// map.put("image", imgpath);
		map.put("text", tmsg);
		map.put("rec_title", rec_title);
		map.put("rec_url", rec_url);
		map.put("rec_desc", rec_desc);

		String result = "";
		try {

			result = HttpUtil.post(url, map, imgpath);
			MLog.e(result);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (!result.contains("ERROR")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 单纯添加一个tmsg
	 * 
	 * @param tmsg
	 */
	public boolean addNewTmsg(String tmsg) {
		String url = HOST + "shuo/v2/statuses/";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("source", Douban.APIKEY);
		map.put("text", tmsg);
		String result = HttpUtil.post(url, map);
		MLog.e(result);
		if (!result.contains("ERROR")) {
			return true;
		} else {
			return false;
		}

	}

}
