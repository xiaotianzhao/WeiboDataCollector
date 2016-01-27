package com.weibo.zxt.utils;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author 赵笑天
 *
 * @time 2015年6月16日
 * 
 */
public class GlobalValues {
	public static DefaultHttpClient httpClient = new DefaultHttpClient();
	public static String uid;
	public static CookieStore cookie;
}
