package com.su.doubanrise.util;

import android.content.Context;
import android.widget.Toast;

public class UIutil {
	static public void toast(Context context, String msg) {
		Toast.makeText(context, msg, 10).show();
	}
}
