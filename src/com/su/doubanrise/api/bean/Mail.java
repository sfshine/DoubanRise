package com.su.doubanrise.api.bean;

import java.io.Serializable;

public class Mail implements Serializable {
	// {
	// "status": "R",
	// "id: "256444525",
	// "sender": User,
	// "receiver": User,
	// "title": "你好，我们是电影《二次曝光》，【二次曝光】豆瓣官方小站期待您的关注，时刻都有惊喜带给您！",
	// "published": "2012-07-31 12:40:04",
	// "content": "　　你是否曾深爱过一个人？
	// 你是否有过一段深爱的恋情？
	// 你是否有个爱恨交织难舍难分的挚友？
	// 你是否……
	// 这是我们的故事，是电影《二次曝光》的故事，也是你的故事。
	// "
	// }
	String status;
	String id;
	String title;
	User sender;
	User receiver;
	String published;
	String content;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
