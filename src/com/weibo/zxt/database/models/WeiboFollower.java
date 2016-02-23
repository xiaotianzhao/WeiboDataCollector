package com.weibo.zxt.database.models;

/**
 * @author 赵笑天
 *
 * @time 2016年2月22日
 * 
 */
public class WeiboFollower {
	private String username;
	private String uid;
	private String gender;
	private int followerNum;
	private int focusNum;
	private int weiboNum;
	private String location;
	private String intro;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getFollowerNum() {
		return followerNum;
	}
	public void setFollowerNum(int followerNum) {
		this.followerNum = followerNum;
	}
	public int getFocusNum() {
		return focusNum;
	}
	public void setFocusNum(int focusNum) {
		this.focusNum = focusNum;
	}
	public int getWeiboNum() {
		return weiboNum;
	}
	public void setWeiboNum(int weiboNum) {
		this.weiboNum = weiboNum;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	@Override
	public String toString() {
		return "WeiboFollower [username=" + username + ", uid=" + uid
				+ ", gender=" + gender + ", followerNum=" + followerNum
				+ ", focusNum=" + focusNum + ", weiboNum=" + weiboNum
				+ ", location=" + location + ", intro=" + intro + "]";
	}
}
