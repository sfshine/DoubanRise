package com.su.doubanrise.api.bean;

import java.io.Serializable;
import java.util.HashMap;

public class DNote implements Serializable {
	/*
	 * { "update_time": "2012-08-20 15:55:20", "publish_time":
	 * "2012-08-19 01:20:09", "photos": { "2" :
	 * "http://img1.douban.com/view/note/large/public/p231928725-2.jpg", "3" :
	 * "http://img1.douban.com/view/note/large/public/p231928725-3.jpg", "4" :
	 * "http://img1.douban.com/view/note/large/public/p231928725-4.jpg", },
	 * "comments_count": 19, "liked_count": 67 "recs_count": 72, "id":
	 * "231928725", "alt": "http://www.douban.com/note/231928725", "can_reply":
	 * true, "title": "《EVA》中现代人的救赎之路 ———论《EVA》中的诺斯替和现代性元素及其他（未完待续）", "privacy":
	 * "public", "summary": "《EVA》中现代人的救赎之路 ———论《EVA》中的诺斯替和现代性元素及其他   这个...",
	 * "content":
	 * "临近20C，当尼采这个狂人宣告上帝已死的时候，现代性才跃出了启蒙思想家的思想图纸，犹如鬼魅一般真正进入了大众民众的日常思想谱系，于是，形式繁多的现代性学说主宰了20C的大众意识形态，无                 论是虚无主义，存在主义，还是马克思主义等等都是现代性语境下的产物。但随着现代性在意识形态的统治地位不断扩张，现代性所引发的病理在21C也愈发彰显。[img=2:C]救赎之路[/img]于是现代性问题成为20C思想界的重要议题，但有两位独树一帜的思想家却引发了我的兴趣，他俩就是约纳斯和沃格林。[img=4:L][/img]沃格林首开先河的提出，基督教早期的异端诺斯替主义像幽灵一样附着于现代性的各种学说之中，并从思想史的角度出发来阐释诺斯替主义和现代性千丝万缕的关系。约纳斯不仅把诺斯替主义看作历史上的一场精神运动，而且进一步把它视为对人类处境的一种独特类型的回应，认为它的思想原则与精神态度普遍地存在于历史的各个阶段。古代的诺斯替宗教..  [url=http://www.douban.com][/url]"
	 * }
	 */

	String update_time;
	String publish_time;
	String comments_count;
	String liked_count;
	String recs_count;
	String id;
	String title;
	String privacy;
	String summary;
	String content;
	String can_reply;
	String alt;
	String[] pics;

	public DNote(String title, String privacy, String content, String can_reply) {
		super();
		this.title = title;
		this.privacy = privacy;
		this.content = content;
		this.can_reply = can_reply;
	}

	public DNote(String update_time, String publish_time,
			String comments_count, String liked_count, String recs_count,
			String id, String title, String privacy, String summary,
			String content, String can_reply, String alt, String[] pics) {
		super();
		this.update_time = update_time;
		this.publish_time = publish_time;
		this.comments_count = comments_count;
		this.liked_count = liked_count;
		this.recs_count = recs_count;
		this.id = id;
		this.title = title;
		this.privacy = privacy;
		this.summary = summary;
		this.content = content;
		this.can_reply = can_reply;
		this.alt = alt;
		this.pics = pics;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public String getComments_count() {
		return comments_count;
	}

	public void setComments_count(String comments_count) {
		this.comments_count = comments_count;
	}

	public String getLiked_count() {
		return liked_count;
	}

	public void setLiked_count(String liked_count) {
		this.liked_count = liked_count;
	}

	public String getRecs_count() {
		return recs_count;
	}

	public void setRecs_count(String recs_count) {
		this.recs_count = recs_count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCan_reply() {
		return can_reply;
	}

	public void setCan_reply(String can_reply) {
		this.can_reply = can_reply;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String[] getMap() {
		return pics;
	}

	public void setMap(String[] pics) {
		this.pics = pics;
	}

}
