package com.weibo.zxt.op;

import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
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
	
	/*获取微博内容*/
	public void getUserWeibo(String uid) throws Exception{
		//获取页面源码
		HttpGet getMethod = new HttpGet("http://weibo.com/u/"+uid+"?is_search=0&visible=0&is_all=1&is_tag=0&profile_ftype=1&page=1#feedtop");
		HttpResponse httpResponse = GlobalValues.httpClient.execute(getMethod);
		String entity = EntityUtils.toString(httpResponse.getEntity());
		//将页面源码转变为html格式
		String html = WeiboPageParser.extractHtml(entity);
		//创建信息抽取器抽取信息，这里抽取的是微博内容
		InfoExtractor ie = new InfoExtractor();
		ie.extractWeiboMessage(html);
	}
	
	/*获取用户粉丝列表*/
	public void getUserFans(String uid) throws Exception{
		//获取页面源码
		HttpGet getMethod = new HttpGet("http://weibo.com/p/100505"+uid+"/follow?relate=fans&page=2#Pl_Official_HisRelation__65");
		HttpResponse httpResponse = GlobalValues.httpClient.execute(getMethod);
		String entity = EntityUtils.toString(httpResponse.getEntity());
		//将页面源码转变为html格式
		String html = WeiboPageParser.extractHtml(entity);
		//创建信息抽取器抽取信息，这里抽取的是用户粉丝列表
		InfoExtractor ie = new InfoExtractor();
		ie.extractUserFans(html);
	}
	
	/*获取用户关注列表*/
	public void getUserFocus(String uid) throws Exception{
		//获取页面源码
		HttpGet getMethod = new HttpGet("http://weibo.com/p/100505"+uid+"/follow?page=2#Pl_Official_HisRelation__65");
		HttpResponse httpResponse = GlobalValues.httpClient.execute(getMethod);
		String entity = EntityUtils.toString(httpResponse.getEntity());
		//将页面源码转变为html格式
		String html = WeiboPageParser.extractHtml(entity);
		//创建信息抽取器抽取信息，这里抽取的是用户关注列表
		InfoExtractor ie = new InfoExtractor();
		ie.extractUserFans(html);
	}
	
	/*获取用户的粉丝数和关注数*/
	public void getUserConnnect(String uid) throws Exception{
		JSONObject connect = new JSONObject();
		
		HttpGet getMethod = new HttpGet("http://weibo.com/p/100505"+uid+"/follow?relate=fans&from=100505&wvr=6&mod=headfans#place");
		HttpResponse httpResponse = GlobalValues.httpClient.execute(getMethod);
		String entity = EntityUtils.toString(httpResponse.getEntity());
		
		String html = WeiboPageParser.extractHtml(entity);
		
		InfoExtractor ie = new InfoExtractor();
		ie.extractUserConnect(html);
		
	}
}
