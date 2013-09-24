package com.su.doubanrise.api.bean;

import java.io.Serializable;

import android.R.string;

public class User implements Serializable {
	// {
	// "id": "1000001",
	// "uid": "ahbei",
	// "name": "阿北",
	// "avatar": "http://img3.douban.com/icon/u1000001-28.jpg", //头像小图
	// "alt": "http://www.douban.com/people/ahbei/",
	// "relation": "contact", //和当前登录用户的关系，friend或contact
	// "created": "2006-01-09 21:12:47", //注册时间
	// "loc_id": "108288", //城市id
	// "loc_name": "北京", //所在地全称
	// "desc": " 现在多数时间在忙忙碌碌地为豆瓣添砖加瓦。坐在马桶上看书，算是一天中最放松的时间。
	//
	// 我不但喜欢读书、旅行和音乐电影，还曾经是一个乐此不疲的实践者，有一墙碟、两墙书、三大洲的车船票为记。现在自己游荡差>不多够了，开始懂得分享和回馈。豆瓣是一个开始，希望它对你同样有用。
	//
	// (因为时间和数量的原因，豆邮和"@阿北"不能保证看到。有豆瓣的问题请email联系help@douban.com。)"
	// }
	String id;
	String uid;
	String name;
	String avatar;
	String alt;
	String relation;
	String created;
	String loc_id;
	String loc_name;
	String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", uid=" + uid + ", name=" + name
				+ ", avatar=" + avatar + ", alt=" + alt + ", relation="
				+ relation + ", created=" + created + ", loc_id=" + loc_id
				+ ", loc_name=" + loc_name + ", desc=" + desc + "]";
	}

}
