package com.su.doubanrise.overall;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.su.doubanrise.api.bean.District;

public class Constant {

	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	static Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
	static String timeprefix = formatter.format(curDate);
	public static String PATH = "/sdcard/douban/";
	public static String PICPATH = PATH + timeprefix + "/pic/";
	public static String ISPATH = PATH + timeprefix + "/is/";

	// public static String PICPATH = PATH;
	// public static String ISPATH = PATH + "is/";
	/**
	 * 存放用户临时信息的变量
	 */
	public static String USERID = "";
	/**
	 * 存放行政区的数据
	 */
	public static List<District> DISTRICTS = null;
	/**
	 * 日志编辑时候 返回的变量 如果是true则刷新日志列表
	 */
	public static Boolean REFRESGDNOTE = false;
}
