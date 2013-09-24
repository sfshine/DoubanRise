package com.su.doubanrise.test;

import android.app.Activity;
import android.test.AndroidTestCase;

import com.su.doubanrise.api.Douban;
import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.util.HttpUtil;

public class testDNoteApi extends BaseTest {
	public void testGetDnotes() {
		//HttpUtil.get("http://m.baidu.com");
		 douban.getDNotes("52441987");
	}

	// public void testWriteNote() {
	// DNote dNote = new DNote("豆瓣测试", "打火机哦", "额发是否", "46465");
	// douban.writeDNote(dNote);
	// }
}
