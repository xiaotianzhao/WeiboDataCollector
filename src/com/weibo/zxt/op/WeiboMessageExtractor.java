package com.weibo.zxt.op;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.weibo.zxt.database.models.WeiboMessage;

/**
 * @author 赵笑天
 *
 * @time 2016年1月27日
 * 
 */
public class WeiboMessageExtractor {
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

			
			System.out.println("===============================");
		}

		return weiboMessages;
	}
	
}
