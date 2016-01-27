package com.weibo.zxt.tools;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author 赵笑天
 *
 * @time 2016年1月27日
 * 
 */
public class WeiboPageParser {
	public static String extractHtml(String source){
		StringBuffer htmlBuffer = new StringBuffer();
		Document doc = Jsoup.parse(source);
		Elements eles = doc.select("script");
		
		for (Element ele : eles){
			String cleanedData = ele.html().substring(8,ele.html().length()-1);
			
			if (cleanedData.contains("\"html\"")){
				cleanedData = cleanedData.substring(cleanedData.indexOf("\"html\"")+7, cleanedData.length()-2);
				cleanedData = StringEscapeUtils.unescapeJava(cleanedData);
				
			}
			htmlBuffer.append(cleanedData);
		}
		return htmlBuffer.toString();
	}
		
}
