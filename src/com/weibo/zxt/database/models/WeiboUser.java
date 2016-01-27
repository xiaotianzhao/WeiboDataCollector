package com.weibo.zxt.database.models;

import java.sql.Date;
import java.util.List;

/**
 * @author 赵笑天
 *
 * @time 2016年1月26日
 * 
 */
public class WeiboUser {
	private String uid;
	private String username;
	private Date birthday;
	private String location;
	private List<String> tags;
	//粉丝人数
	private int fansNum;
	//关注人数
	private int attentionNum;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public int getFansNum() {
		return fansNum;
	}
	public void setFansNum(int fansNum) {
		this.fansNum = fansNum;
	}
	public int getAttentionNum() {
		return attentionNum;
	}
	public void setAttentionNum(int attentionNum) {
		this.attentionNum = attentionNum;
	}
	@Override
	public String toString() {
		return "WeiboUser [uid=" + uid + ", username=" + username
				+ ", birthday=" + birthday + ", location=" + location
				+ ", tags=" + tags + ", fansNum=" + fansNum + ", attentionNum="
				+ attentionNum + "]";
	}
	
}
