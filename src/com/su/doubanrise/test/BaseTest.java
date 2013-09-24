package com.su.doubanrise.test;

import android.app.Activity;
import android.content.Context;
import android.test.AndroidTestCase;

import com.su.doubanrise.api.Douban;
import com.su.doubanrise.util.HttpUtil;

//{"access_token":"5a6848c38d75b02f0f38da4aa861108d","douban_user_id":"52441987","expires_in":604800,"refresh_token":"131cd5414cdc9b2739567fc3e695d175"}

public class BaseTest extends AndroidTestCase {
	Context context = getContext();
	Douban douban = new Douban((Activity) this.getContext(),
			"66dc31e3f85a34a0349495abe90e9de0");

	// Douban douban = new Douban((Activity) this.getContext(),
	// "73f27e95b2a4a61d5bd77bc117edf257");

	public void testDoubanBase() {
		HttpUtil.get("https://api.douban.com/v2/user/~me");
	}

}
