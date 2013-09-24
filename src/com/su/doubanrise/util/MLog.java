package com.su.doubanrise.util;

public class MLog {
	static public void e(String msg) {
		android.util.Log.e("=======ERROR======", msg);
	}

	static public void i(String msg) {
		android.util.Log.i("=======INFO======", msg);
	}
}
