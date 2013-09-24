package com.su.doubanrise.test;

import android.app.Activity;
import android.test.AndroidTestCase;

import com.su.doubanrise.api.Douban;

public class testTmsg extends AndroidTestCase {
	Douban douban = new Douban((Activity) this.getContext(),
			"73f27e95b2a4a61d5bd77bc117edf257");

	public void testAddImg() {
		douban.addNewTmsg("总算做好了", "/sdcard/1.png", "", "", "");
	}

	// public void testAddMsg() {
	// douban.addNewTmsg("测试图片");
	// }
}
