package com.weibo.zxt.utils;

import java.util.Date;

/**
 * @author 赵笑天
 *
 * @time 2015年6月16日
 * 
 */
public class MessageInfo {
	//用户id
	private String uid;
	//微博消息id
	private String mid;
	//发布时间
	private String date;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
