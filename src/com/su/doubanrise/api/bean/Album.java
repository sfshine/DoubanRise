package com.su.doubanrise.api.bean;

public class Album {
	// <li class="clearfix">
	// <div class="img"><a onclick="moreurl(this, {from:'new'})"
	// href="http://music.douban.com/subject/20434006/"><img width="100%"
	// src="http://img3.douban.com/spic/s24479613.jpg"/></a></div>
	// <div class="intro">
	// <h3>1. <a onclick="moreurl(this, {from:'new'})"
	// href="http://music.douban.com/subject/20434006/">十二新作</a></h3>
	//
	// 周杰伦<br/>
	// <div class="star clearfix"><span class="allstar40"></span>7.7</div>
	// </div>
	// </li>
	String image;
	String id;
	String name;
	float ratting;
	String singer;

	public Album(String image, String id, String name, float ratting,
			String singer) {
		super();
		this.image = image;
		this.id = id;
		this.name = name;
		this.ratting = ratting;
		this.singer = singer;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getRatting() {
		return ratting;
	}

	public void setRatting(float ratting) {
		this.ratting = ratting;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

}
