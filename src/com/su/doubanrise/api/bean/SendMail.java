package com.su.doubanrise.api.bean;

public class SendMail {
	// title 豆邮标题 必填字段
	// content 豆邮正文 必填字段
	// receiver_id 接收邮件的用户id 必填字段
	// captcha_token 系统验证码 token 选填字段
	// captcha_string 用户输入验证码 选填字段
	String title;
	String content;
	String receiver_id;
	String captcha_token;
	String captcha_string;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getCaptcha_token() {
		return captcha_token;
	}

	public void setCaptcha_token(String captcha_token) {
		this.captcha_token = captcha_token;
	}

	public String getCaptcha_string() {
		return captcha_string;
	}

	public void setCaptcha_string(String captcha_string) {
		this.captcha_string = captcha_string;
	}

}
