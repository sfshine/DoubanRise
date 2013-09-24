package com.su.doubanrise.api.bean;

import java.io.Serializable;

public class Note implements Serializable {
	// "privacy": 2,
	// "abstract_photo": "",
	// "abstract": "时，他已经22 岁了。为了实现童年的梦想，斯皮尔伯格毅然……把自己的出生年份从1946 年改成了1947 年。
	// 2 牛事：别迷恋哥，哥只是传说
	// 李小龙出手速度奇快，出拳再收拳，只要0.4 秒。洪金宝年轻时不服，曾找李...",
	// "content":
	// "时，他已经22 岁了。为了实现童年的梦想，斯皮尔伯格毅然……把自己的出生年份从1946 年改成了1947 年。<div style=\"padding-bottom:1em;\"></div>2 牛事：别迷恋哥，哥只是传说<div style=\"padding-bottom:1em;\"></div>李小龙出手速度奇快，出拳再收拳，只要0.4 秒。洪金宝年轻时不服，曾找李小龙挑战，一拳刚出来，李小龙的脚已经停在他的脸边了。洪金宝从此五体投地，在李小龙的电影里数次跑龙套，被李小龙狂扁，绝无怨言。<div style=\"padding-bottom:1em;\"></div>3 囧事：你所认为的必然，其实都是阴差阳错",
	// "photos": { },
	// "last_photo": 0,
	// "comments_count": 0,
	// "hasmath": false,
	// "book_id": "20383605",
	// "time": "2012-12-16 10:42:27",
	// "author_id": "52441987",
	// "id": "23201111",
	// "page_no": 222
	String chapter;
	String author_name;
	String abstract_;
	String time;
	String content;
	String id;
	String page_no;

	public Note(String chapter, String author_name, String abstract_,
			String time, String content, String id, String page_no) {
		super();
		this.chapter = chapter;
		this.author_name = author_name;
		this.abstract_ = abstract_;
		this.time = time;
		this.content = content;
		this.id = id;
		this.page_no = page_no;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAbstract_() {
		return abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
