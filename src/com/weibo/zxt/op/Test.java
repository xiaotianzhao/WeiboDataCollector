package com.weibo.zxt.op;

import java.util.Date;

import com.weibo.zxt.utils.GlobalValues;
import com.weibo.zxt.utils.MessageInfo;

/**
 * @author 赵笑天
 *
 * @time 2015年6月16日
 * 
 */
public class Test {
	public static void main(String[] args){
		WeiboLogin wl = new WeiboLogin();
		try {
			wl.Login("393504144@qq.com", "393504144");
			WeiboUserOps wuo = new WeiboUserOps();
			//解析微博内容			
			//wuo.getUserWeibo("5591519974");
			
			//解析关注的人的有关信息
			wuo.getUserFans("2010089325");
			
			/*WeiboMessageOps wmo = new WeiboMessageOps();
			//WeiboUserOps wuo = new WeiboUserOps();
			//wuo.getFansNum("3820652804");
			//发表验证
			MessageInfo mi = wmo.sendMessage("正在测试的苦逼程序猿..."+new Date().getTime());
			//wmo.addComment("hehhehe", mi.getUid(), mi.getMid());
			Thread.sleep(1000*10);
			wmo.giveHeart(mi.getMid());
			//评论验证
			wmo.transferMessage(mi.getMid(), "这个不错");
			wmo.addComment("哈哈",mi.getUid() , mi.getMid());
			
			//Thread.sleep(1000*30);
			//wmo.transferMessage(mi.getMid(), "hahahh");
			Thread.sleep(1000*10);

			
			//Thread.sleep(1000*60);
			//删除验证
			wmo.deleteMessage(mi.getMid());
			
			*/
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
