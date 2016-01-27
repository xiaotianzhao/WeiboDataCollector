package com.weibo.zxt.op;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.weibo.zxt.utils.GlobalValues;
import com.weibo.zxt.utils.MessageInfo;

/**
 * @author 赵笑天
 *
 * @time 2015年6月16日
 * 
 */
public class WeiboMessageOps {
	/**
	 * @author 赵笑天
	 *
	 * @time 2015年6月16日
	 * 
	 */
	private Logger logger = Logger.getLogger(WeiboMessageOps.class);
	@SuppressWarnings("deprecation")
	public WeiboMessageOps(){
				//返回的数据是经过gzip压缩的，这里需要对HttpClient进行设置，使之能够支持对gzip进行解压，否则获取到的数据会出现乱码
				GlobalValues.httpClient.addRequestInterceptor(new HttpRequestInterceptorImplementation());
				
				GlobalValues.httpClient.addResponseInterceptor(new HttpResponseInterceptor(){
					public void process(HttpResponse response, HttpContext context)
							throws HttpException, IOException {
						
						HttpEntity entity = response.getEntity();  
			               Header ceheader = entity.getContentEncoding();  
			               if (ceheader != null) {  
			                   HeaderElement[] codecs = ceheader.getElements();  
			                   for (int i = 0; i < codecs.length; i++) {  
			                       if (codecs[i].getName().equalsIgnoreCase("gzip")) {  
			                           response.setEntity(  
			                                   new GzipDecompressingEntity(response.getEntity()));   
			                           return;  
			                       }  
			                   }  
			               }  
					}
					
				});
	}
	
	private static class HttpRequestInterceptorImplementation implements
			HttpRequestInterceptor {
		public void process(HttpRequest request, HttpContext context)
				throws HttpException, IOException {
			if (!request.containsHeader("Accept-Encoding")) {  
		           request.addHeader("Accept-Encoding", "gzip");  
		       }  
			
		}
	}

	//发布微博操作
	@SuppressWarnings("deprecation")
	public MessageInfo sendMessage(String content) throws ClientProtocolException, IOException, ParseException{
		MessageInfo mi = new MessageInfo();
		Date date = new Date();
		Long time = date.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mi.setDate(sdf.format(date.getTime()));
		
		HttpPost post = new HttpPost("http://weibo.com/aj/mblog/add?ajwvr=6&__rnd="+time);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		nvps.add(new BasicNameValuePair("location","v6_content_home"));
		nvps.add(new BasicNameValuePair("appkey",""));
		nvps.add(new BasicNameValuePair("style_type","1"));
		nvps.add(new BasicNameValuePair("pic_id",""));
		nvps.add(new BasicNameValuePair("text",content));
		nvps.add(new BasicNameValuePair("pdetail",""));
		nvps.add(new BasicNameValuePair("rank","0"));
		nvps.add(new BasicNameValuePair("rankid",""));
		nvps.add(new BasicNameValuePair("module","stissue"));
		nvps.add(new BasicNameValuePair("pub_type","dialog"));
		nvps.add(new BasicNameValuePair("_t","0"));
		
		post.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
		
		post.addHeader("Host","weibo.com");
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("X-Requested-With","XMLHttpRequest");
		post.addHeader("Referer", "http://weibo.com/u/"+GlobalValues.uid+"/home?wvr=5");
		//post.addHeader("Content-Length","123");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Pragma", "no-cache");
		post.addHeader("Cache-Control", "no-cache");
		
		HttpResponse httpResponse = GlobalValues.httpClient.execute(post);
		
		//获取发表微博的信息
		String entity = EntityUtils.toString(httpResponse.getEntity());
		//logger.info(entity);
		JSONObject jsonObj = new JSONObject(entity);
		String html =((JSONObject) jsonObj.get("data")).getString("html");
		
		mi.setMid(html.substring(html.indexOf("&mid")+5,html.indexOf("&mid")+21));
		mi.setUid(html.substring(html.indexOf("&uid")+5,html.indexOf("&uid")+15));
		
		if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
			
			logger.info("发表成功");
			post.abort();
			return mi;
		}else{
			logger.info(httpResponse.getStatusLine().getStatusCode());
			logger.info("发表失败");
			post.abort();
			return null;
		}
	}
	
	//删除微博操作
	public  void deleteMessage(String mid) throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost("http://weibo.com/aj/mblog/del?ajwvr=6");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("mid",mid));
		post.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));

		post.addHeader("Host","weibo.com");
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("X-Requested-With","XMLHttpRequest");
		post.addHeader("Referer", "http://weibo.com/u/"+GlobalValues.uid+"/home?topnav=1&wvr=6");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Pragma", "no-cache");
		post.addHeader("Cache-Control", "no-cache");
		
		HttpResponse httpResponse = GlobalValues.httpClient.execute(post);
		if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
			logger.info("删除成功！");
		}else{
			logger.info("错误代码"+httpResponse.getStatusLine().getStatusCode());
			logger.info("删除失败");
		}
		
		post.abort();
	}
	
	//发表评论
	public void addComment(String comment,String uid,String mid) throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost("http://weibo.com/aj/v6/comment/add?ajwvr=6&__rnd="+new Date().getTime());
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		nvps.add(new BasicNameValuePair("act","post"));
		nvps.add(new BasicNameValuePair("uid",uid));
		nvps.add(new BasicNameValuePair("mid",mid));
		nvps.add(new BasicNameValuePair("forward","0"));
		nvps.add(new BasicNameValuePair("isroot","0"));
		nvps.add(new BasicNameValuePair("content",comment));
		nvps.add(new BasicNameValuePair("location","v6_content_home"));
		nvps.add(new BasicNameValuePair("module","scommlist"));
		nvps.add(new BasicNameValuePair("group_source",""));
		nvps.add(new BasicNameValuePair("pdetail",""));
		nvps.add(new BasicNameValuePair("_t","0"));
		
		post.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));

		post.addHeader("Host","weibo.com");
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("X-Requested-With","XMLHttpRequest");
		post.addHeader("Referer", "http://weibo.com/u/"+GlobalValues.uid+"/home?topnav=1&wvr=6");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Pragma", "no-cache");
		post.addHeader("Cache-Control", "no-cache");
		
		HttpResponse httpResponse = GlobalValues.httpClient.execute(post);
		System.out.println(EntityUtils.toString(httpResponse.getEntity()));
		if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
			logger.info("添加评论成功！");
		}else{
			logger.info("错误代码"+httpResponse.getStatusLine().getStatusCode());
			logger.info("添加评论失败");
		}
		post.abort();
	}
	
	//微博点赞
	public void giveHeart(String mid) throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost("http://weibo.com/aj/v6/like/add?ajwvr=6");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		nvps.add(new BasicNameValuePair("version","mini"));
		nvps.add(new BasicNameValuePair("qid","heart"));
		nvps.add(new BasicNameValuePair("mid",mid));
		nvps.add(new BasicNameValuePair("like_src","1"));
		nvps.add(new BasicNameValuePair("location","v6_content_home"));
		nvps.add(new BasicNameValuePair("group_source",""));
		
		post.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));

		post.addHeader("Host","weibo.com");
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("X-Requested-With","XMLHttpRequest");
		post.addHeader("Referer", "http://weibo.com/u/"+GlobalValues.uid+"/home?wvr=5&c=spr_sinamktbd_bd_baidub_weibo_t001&sudaref=www.baidu.com");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Pragma", "no-cache");
		post.addHeader("Cache-Control", "no-cache");
		
		HttpResponse httpResponse = GlobalValues.httpClient.execute(post);
		//logger.info(EntityUtils.toString(httpResponse.getEntity()));
		if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
			post.abort();
			
			HttpGet get = new HttpGet("http://weibo.com/aj/v6/like/small?ajwvr=6&version=mini&qid=heart&mid="+mid+"&like_src=1&location=v6_content_home&__rnd="+new Date().getTime());
			
			HttpResponse httpResponse1 = GlobalValues.httpClient.execute(get);
			
			if (HttpStatus.SC_OK == httpResponse1.getStatusLine().getStatusCode()){
				logger.info("点赞成功！");
				
				//logger.info(EntityUtils.toString(httpResponse1.getEntity()));
			}else{
				logger.info("错误代码"+httpResponse.getStatusLine().getStatusCode());
				logger.info("点赞失败！");
			}
			get.abort();
			
		}else{
			post.abort();
			logger.info("错误代码"+httpResponse.getStatusLine().getStatusCode());
			logger.info("点赞失败");
		}
		
	}
	//微博转发
	public void transferMessage(String mid,String reason) throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost("http://weibo.com/aj/v6/mblog/forward?ajwvr=6&domain="+GlobalValues.uid+"&__rnd="+new Date().getTime());
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("pic_src",""));
		nvps.add(new BasicNameValuePair("appkey",""));
		nvps.add(new BasicNameValuePair("mid",mid));
		nvps.add(new BasicNameValuePair("style_type","1"));
		nvps.add(new BasicNameValuePair("mark",""));
		nvps.add(new BasicNameValuePair("reason",reason));
		nvps.add(new BasicNameValuePair("location","v6_content_home"));
		nvps.add(new BasicNameValuePair("pdetail",""));
		nvps.add(new BasicNameValuePair("module",""));
		nvps.add(new BasicNameValuePair("page_module_id",""));
		nvps.add(new BasicNameValuePair("refer_sort",""));
		nvps.add(new BasicNameValuePair("rank","0"));
		nvps.add(new BasicNameValuePair("rankid",""));
		nvps.add(new BasicNameValuePair("group_source","group_all"));
		nvps.add(new BasicNameValuePair("rid","6_0_1_2598638214208887812"));
																					
		nvps.add(new BasicNameValuePair("_t","0"));
		post.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));

		post.addHeader("Host","weibo.com");
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
		post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("X-Requested-With","XMLHttpRequest");
		post.addHeader("Referer", "http://weibo.com/u/"+GlobalValues.uid+"/home?topnav=1&wvr=6");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Pragma", "no-cache");
		post.addHeader("Cache-Control", "no-cache");
		
		HttpResponse httpResponse = GlobalValues.httpClient.execute(post);
		if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()){
			logger.info("转发微博成功！");
			//logger.info(EntityUtils.toString(httpResponse.getEntity()));
		}else{
			logger.info("错误代码"+httpResponse.getStatusLine().getStatusCode());
			logger.info("转发微博失败");
		}
		
		post.abort();
	}
}
