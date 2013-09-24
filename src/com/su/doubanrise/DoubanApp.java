package com.su.doubanrise;

import com.su.doubanrise.util.MyCrashHandler;

import android.app.Application;
import android.content.Context;

public class DoubanApp extends Application {
	static Context context;

	// 在整个应用第一次被创建出来的时候 执行
	// 在应用程序对应的进程 第一次创建出来的时候执行
	@Override
	public void onCreate() {
		super.onCreate();
		context = this.getApplicationContext();
		// // 把自定义的异常处理类设置 给主线程
		// MyCrashHandler myCrashHandler = MyCrashHandler.getInstance();
		// myCrashHandler.init(getApplicationContext());
		// Thread.currentThread().setUncaughtExceptionHandler(myCrashHandler);
	}

	static public Context getContext() {
		return context;

	}
}