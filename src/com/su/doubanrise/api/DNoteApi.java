package com.su.doubanrise.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.api.bean.Note;
import com.su.doubanrise.util.HttpUtil;

public class DNoteApi {

	static DNoteApi noteApi;

	public DNoteApi() {
	}

	static public DNoteApi getDNoteApi() {
		if (noteApi == null) {
			noteApi = new DNoteApi();
		}
		return noteApi;
	}

	public String deleteDNote(DNote dNote) {
		String url = "https://api.douban.com/v2/note/" + dNote.getId();
		return HttpUtil.customrequestget(url, null, "DELETE");

	}

	public String modDNote(DNote dNote) {
		String url = "https://api.douban.com/v2/note/" + dNote.getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", dNote.getTitle());
		map.put("privacy", dNote.getPrivacy());
		map.put("can_reply", dNote.getCan_reply());
		map.put("content", dNote.getContent());
		return HttpUtil.customrequest(url, map, "PUT");

	}

	public String writeDNote(DNote dNote) {
		// title 日记标题 必传，不能为空
		// privacy 隐私控制 为public，friend，private，分布对应公开，朋友可见，仅自己可见
		// can_reply 是否允许回复 必传, true或者false
		// content 日记内容, 如果含图片，使用“<图片p_pid>”伪tag引用图片,
		// 如果含链接，使用html的链接标签格式，或者直接使用网址 必传 日记正文<图片p_2>正文<图片p_3>
		// pids 上传的图片pid本地编号，使用前缀"p_" 用逗号分隔，最多一次传3张图片 p_2,p_3,p_4
		// layout_pid 对应pid的排版 有L, C, R 分别对应居左，居中，居右3种排版
		// desc_pid 对应pid的图注 可以为空
		// image_pid 对应pid的图片内容
		String url = "https://api.douban.com/v2/notes";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", dNote.getTitle());
		map.put("privacy", dNote.getPrivacy());
		map.put("can_reply", dNote.getCan_reply());
		map.put("content", dNote.getContent());
		return HttpUtil.post(url, map);

	}

	// https://api.douban.com/v2/note/user_created/:id
	public List<DNote> getDNotes(String id, String format) {

		String url = "https://api.douban.com/v2/note/user_created/" + id;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("format", "html_full");
		String result = HttpUtil.get(url, map);
		List<DNote> dNotes = new ArrayList<DNote>();
		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("notes");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				DNote dNote = parseDnote(jsonObject.toString());
				dNotes.add(dNote);

			}
			return dNotes;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public DNote parseDnote(String json) {
		try {

			JSONObject jsonObject = new JSONObject(json);

			// {
			// "update_time": "2012-12-14 15:14:56",
			// "publish_time": "2012-12-14 15:14:47",
			// "photos": {
			// "2" :
			// "http://img1.douban.com/view/note/large/public/p231928725-2.jpg",
			// "3" :
			// "http://img1.douban.com/view/note/large/public/p231928725-3.jpg",
			// "4" :
			// "http://img1.douban.com/view/note/large/public/p231928725-4.jpg",
			// },
			// "recs_count": 0,
			// "alt": "http://www.douban.com/note/252590920/",
			// "id": "252590920",
			// "can_reply": true,
			// "title": "测试",
			// "privacy": "public",
			// "summary": "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试",
			// "content": "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试",
			// "comments_count": 0,
			// "liked_count": 0
			// }
			String update_time = jsonObject.optString("update_time");
			String publish_time = jsonObject.optString("publish_time");
			String comments_count = jsonObject.optString("comments_count");
			String liked_count = jsonObject.optString("liked_count");
			String recs_count = jsonObject.optString("recs_count");
			String id = jsonObject.optString("id");
			String title = jsonObject.optString("title");
			String privacy = jsonObject.optString("privacy");
			String summary = jsonObject.optString("summary");
			String content = jsonObject.optString("content");
			String can_reply = jsonObject.optString("can_reply");
			String alt = jsonObject.optString("alt");
			jsonObject = jsonObject.optJSONObject("photos");

			String[] pics = new String[] {};

			DNote dNote = new DNote(update_time, publish_time, comments_count,
					liked_count, recs_count, id, title, privacy, summary,
					content, can_reply, alt, pics);
			return dNote;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}
}
