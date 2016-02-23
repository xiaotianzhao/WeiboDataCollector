package com.weibo.zxt.op;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.weibo.zxt.database.models.WeiboFollower;
import com.weibo.zxt.database.models.WeiboMessage;
import com.weibo.zxt.database.models.WeiboUser;

/**
 * @author 赵笑天
 *
 * @time 2016年1月27日
 * 
 */
public class InfoExtractor {
	public List<WeiboMessage> extractWeiboMessage(String source){
		//System.out.println(source);
		List<WeiboMessage> weiboMessages = new ArrayList<WeiboMessage>();
		Document doc = Jsoup.parse(source);
			
		Elements eles = doc.select("div[class=WB_cardwrap WB_feed_type S_bg2]");
		for (Element ele:eles){
			String weiboHtml = ele.html();
			Document docWeibo = Jsoup.parse(weiboHtml);
			//解析微博内容
			Elements elesContent = docWeibo.select("div[class=WB_text W_f14]");
			if (elesContent != null && elesContent.size() == 1){
				System.out.println("Content:"+elesContent.get(0).text());
			}
			
			//解析时间来源
			Elements elesTimeAndSource = docWeibo.select("div[class=WB_from S_txt2]");
			if (elesTimeAndSource != null && elesTimeAndSource.size() == 1){
				System.out.println("Time And Source:"+elesTimeAndSource.get(0).text());
			}else{
				System.out.println("Origin Time And Source:"+elesTimeAndSource.get(0).text());
				System.out.println("Time And Source:"+elesTimeAndSource.get(1).text());
			}
			
			//解析有关点赞转发数据
			Elements elesStatusList = docWeibo.select("ul[class=WB_row_line WB_row_r4 clearfix S_line2]");
			Elements lis = elesStatusList.get(0).getElementsByTag("li");
			
			//解析收藏数
			if (lis.get(0).text().replace("收藏", "").trim() != ""){
				System.out.println("收藏数:"+ lis.get(0).text().replace("收藏", "").trim());
			}else{
				System.out.println("收藏数:0");
			}
			
			//解析转发数
			if (lis.get(1).text().replace("转发", "").trim() != ""){
				System.out.println("转发数:"+ lis.get(1).text().replace("转发", "").trim());
			}else{
				System.out.println("转发数:0");
			}
			
			//解析评论数
			if (lis.get(2).text().replace("评论", "").trim() != ""){
				System.out.println("评论数:"+ lis.get(2).text().replace("评论", "").trim());
			}else{
				System.out.println("评论数:0");
			}
			
			//解析点赞数
			if (lis.get(3).text().trim() != ""){
				System.out.println("点赞数:"+ lis.get(3).text().trim());
			}else{
				System.out.println("点赞数:0");
			}
			
			//解析转发微博的原作者
			Elements eleOriginAuthor = docWeibo.select("div[class=WB_feed_expand] div[class=WB_expand S_bg1] div[class=WB_info]");
			if (eleOriginAuthor != null && eleOriginAuthor.size()!=0){
				System.out.println("Origin Author:"+eleOriginAuthor.get(0).text());
			}
			
			//解析转发微博的内容
			Elements eleOriginContent = docWeibo.select("div[class=WB_feed_expand] div[class=WB_expand S_bg1] div[class=WB_text]");
			if (eleOriginContent != null && eleOriginContent.size() != 0){
				System.out.println("Origin Content:"+eleOriginContent.get(0).text());
			}
			
			//解析转发微博的转发，评论，点赞数
			Elements eleOriginStatusList = docWeibo.select("div[class=WB_feed_expand] div[class=WB_expand S_bg1] div[class=WB_func clearfix] div[class=WB_handle W_fr] ul[class = clearfix]");
			if (eleOriginStatusList != null && eleOriginStatusList.size() > 0){
				Elements originLis = eleOriginStatusList.get(0).getElementsByTag("li");
				
				//解析转发数
				if (originLis.get(0).text().replace("转发", "").trim() != ""){
					System.out.println("Origin 转发数:"+ originLis.get(0).text().replace("转发", "").trim());
				}else{
					System.out.println("Origin 转发数:0");
				}
				
				//解析评论数
				if (originLis.get(1).text().replace("评论", "").trim() != ""){
					System.out.println("Origin 评论数:"+ originLis.get(1).text().replace("评论", "").trim());
				}else{
					System.out.println("Origin 评论数:0");
				}
				
				//解析点赞数
				if (originLis.get(2).text().trim() != ""){
					System.out.println("Origin 点赞数:"+ originLis.get(2).text().trim());
				}else{
					System.out.println("Origin 点赞数:0");
				}
			}
			System.out.println("===============================");
		}

		return weiboMessages;
	}
	
	public List<WeiboUser> extractUserFans(String source){
		List<WeiboUser> fans = new ArrayList<WeiboUser>();
		Document doc = Jsoup.parse(source);
		//获取包含粉丝列表的ul标签
		Elements eles = doc.select("div[class=follow_inner] ul[class=follow_list]");
		if (eles != null && eles.size() > 0){
			for (Element eleUl : eles){
				//获取粉丝列表
				Elements fansLis = eleUl.select("li[class=follow_item S_line2]");
				
				for (Element fan : fansLis){
					WeiboFollower weiboFollower = new WeiboFollower();
					String fanHtml = fan.html();
					Document fanDoc = Jsoup.parse(fanHtml);
					//解析用户名
					Elements elesUserName = fanDoc.select("dl[class=clearfix] dd[class=mod_info S_line1] div[class=info_name W_fb W_f14] a[class=S_txt1]");
					if (elesUserName != null && elesUserName.size() > 0){
//						System.out.println("UserName:"+elesUserName.get(0).text());
						weiboFollower.setUsername(elesUserName.get(0).text());
					}
					
					//解析用户uid
					if (elesUserName != null && elesUserName.size() > 0){
//						System.out.println("Uid:"+elesUserName.get(0).attr("usercard").substring(3,13));
						weiboFollower.setUid(elesUserName.get(0).attr("usercard").substring(3,13));
					}
					
					//解析用户性别
					Elements elesUserGenderFemale = fanDoc.select("dl[class=clearfix] dd[class=mod_info S_line1] div[class=info_name W_fb W_f14] a i[class=W_icon icon_female]");
					Elements elesUserGenderMale = fanDoc.select("dl[class=clearfix] dd[class=mod_info S_line1] div[class=info_name W_fb W_f14] a i[class=W_icon icon_male]");					
					if (elesUserGenderFemale != null && elesUserGenderFemale.size() > 0){
						weiboFollower.setGender("女");
					}else if (elesUserGenderMale != null && elesUserGenderMale.size() > 0){
						weiboFollower.setGender("男");
					}else{
						weiboFollower.setGender("未知");
					}
					
					//解析用户关联信息，关注数，粉丝数，微博数
					Elements elesConnect = fanDoc.select("dl[class=clearfix] dd[class=mod_info S_line1] div[class=info_connect] span em a");
					if (elesConnect != null && elesConnect.size() == 3){
						
						weiboFollower.setFocusNum(Integer.parseInt(elesConnect.get(0).text()));
						weiboFollower.setFollowerNum(Integer.parseInt(elesConnect.get(1).text()));
						weiboFollower.setWeiboNum(Integer.parseInt(elesConnect.get(2).text()));
					}
					
					//解析用户地址
					Elements eleLocation = fanDoc.select("dl[class=clearfix] dd[class=mod_info S_line1] div[class=info_add] span");
					
					if (eleLocation != null && eleLocation.size() == 1){
						weiboFollower.setLocation(eleLocation.get(0).text());
					}
					
					//解析用户简介
					Elements eleIntro = fanDoc.select("dl[class=clearfix] dd[class=mod_info S_line1] div[class=info_intro] span");
					
					if (eleIntro != null && eleIntro.size() == 1){
						weiboFollower.setIntro(eleIntro.get(0).text());
					}
					
					System.out.println(weiboFollower);
					System.out.println("===============================");
				}
				
			}
		}
		return fans;
	}
	
	public void extractUserConnect(String html) {
		// TODO Auto-generated method stub
		
	}
}
