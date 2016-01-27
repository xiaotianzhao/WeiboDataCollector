package com.weibo.zxt.op;

import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.weibo.zxt.tools.WeiboPageParser;
import com.weibo.zxt.utils.GlobalValues;

/**
 * @author 赵笑天
 *
 * @time 2015年6月16日
 * 
 */
public class WeiboUserOps {
	
	public void getUserWeibo(String uid) throws Exception{
		HttpGet getMethod = new HttpGet("http://weibo.com/u/"+uid+"?is_search=0&visible=0&is_all=1&is_tag=0&profile_ftype=1&page=1#feedtop");
		HttpResponse httpResponse = GlobalValues.httpClient.execute(getMethod);
		String entity = EntityUtils.toString(httpResponse.getEntity());
		String html = WeiboPageParser.extractHtml(entity);
		WeiboMessageExtractor wme = new WeiboMessageExtractor();
		wme.extractWeiboMessage(html);
	}
	
	//获取指定对象的粉丝人数
	public void getFansNum(String uid) throws Exception{
		//<em class=\"attach S_txt1\" node-type=\"count\">36<\/em>
		HttpGet get = new HttpGet("http://d.weibo.com/230771?from=page_102803");
		
		
		HttpResponse httpResponse = GlobalValues.httpClient.execute(get);
		String entity = EntityUtils.toString(httpResponse.getEntity());
		//FileOps.writeToFile("F:\\weiboData\\"+UUID.randomUUID()+".html", entity);
		Document doc = Jsoup.parse(entity);
		//System.out.println(doc.html());
		Elements eles = doc.select("script");
		
//		FileOps.writeToFile("F:\\WeiboData\\"+UUID.randomUUID()+".txt", doc.html());
		System.out.println(eles.size());
		StringBuffer srcBuffer = new StringBuffer();
		for (Element ele : eles){
			String cleanedData = ele.html().substring(8,ele.html().length()-1);
			
			if (cleanedData.contains("\"html\"")){
				cleanedData = cleanedData.substring(cleanedData.indexOf("\"html\"")+7, cleanedData.length()-2);
				cleanedData = StringEscapeUtils.unescapeJava(cleanedData);
				
			}
			//System.out.println("=============================================");
			srcBuffer.append(cleanedData);
			//JSONObject json = JSONObject.fromObject(cleanedData);
			//System.out.println(json.get("html"));
		}
		Document doc1 = Jsoup.parse(srcBuffer.toString());
		Elements eles1 = doc1.select("div[class=WB_text W_f14]");
		for (Element ele:eles1){
			System.out.println(ele.text());
		}
//		int pos1 = entity.indexOf("他的粉丝");
//		int pos = entity.indexOf("<em class=\\\"num S_txt1\\\">", 3)+new String("<em class=\"num S_txt1\">").length();
//		System.out.println(pos+"--"+pos1);
//		String tmp = entity.substring(pos,pos+2);
//		System.out.println(tmp);
	}
}
