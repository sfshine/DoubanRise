package com.su.doubanrise.test;

import android.app.Activity;
import android.test.AndroidTestCase;

import com.su.doubanrise.api.Douban;
import com.su.doubanrise.api.MailApi;
import com.su.doubanrise.api.bean.SendMail;

public class testDoubanMail extends AndroidTestCase {

	Douban douban = new Douban((Activity) this.getContext(),
			"73f27e95b2a4a61d5bd77bc117edf257");

	public void testGetInbox() {
		douban.getInbox();

	}

	public void testgetUserList() {
		douban.getUserList();

	}

	public void testGetOutbox() {
		douban.getOutbox();

	}

	public void testSendmail() {
		SendMail sendMail = new SendMail();
		sendMail.setReceiver_id("susan ");
		sendMail.setTitle("Test");
		sendMail.setContent("第一封邮件");
		douban.sendMail(sendMail);

	}
}
