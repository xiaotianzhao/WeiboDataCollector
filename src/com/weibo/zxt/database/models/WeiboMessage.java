package com.weibo.zxt.database.models;

import java.sql.Date;

/**
 * @author 赵笑天
 *
 * @time 2016年1月26日
 * 
 */
public class WeiboMessage {
	
	private String mid;
	private String content;
	private Date publishDate;
	private int likesNum;
	private int forwardingNum;
	private int conmentsNum;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public int getLikesNum() {
		return likesNum;
	}
	public void setLikesNum(int likesNum) {
		this.likesNum = likesNum;
	}
	public int getForwardingNum() {
		return forwardingNum;
	}
	public void setForwardingNum(int forwardingNum) {
		this.forwardingNum = forwardingNum;
	}
	public int getConmentsNum() {
		return conmentsNum;
	}
	public void setConmentsNum(int conmentsNum) {
		this.conmentsNum = conmentsNum;
	}
	@Override
	public String toString() {
		return "WeiboMessage [mid=" + mid + ", content=" + content
				+ ", publishDate=" + publishDate + ", likesNum=" + likesNum
				+ ", forwardingNum=" + forwardingNum + ", conmentsNum="
				+ conmentsNum + "]";
	}
	
}
