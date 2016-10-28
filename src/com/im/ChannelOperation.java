package com.im;

import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONObject;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import com.toolInterface.ChannelTools;

/**
 * 
 * <p>
 * 功能描述：websocket通道操作
 * </p>
 * <p>
 * 创建：Oct 12, 2016 by ydh
 * </p>
 * 
 */
public class ChannelOperation implements ChannelTools{
	//保存链接通道到缓存中去
		public void channelAdd(ChannelHandlerContext ctx,String senderId){
			ChannelId id = channelIdMap.get(senderId);
			if (id != null) {
				repeatLogin(ctx,senderId);
				return;
			}
			logIn(ctx);
			channelIdMap.put(senderId,ctx.channel().id());
			channels.add(ctx.channel());
			log.info("用户"+senderId+"上线");
			//用户上线，通知所有用户
		}
	//将链接通道从缓存中去除
		public void channelRemove(ChannelHandlerContext ctx){
			Set keys = channelIdMap.keySet();
			  if(keys != null) {
				  Iterator iterator = keys.iterator();
				  while(iterator.hasNext()) {
					  String key = iterator.next().toString();
					  if(channelIdMap.get(key).equals(ctx.channel().id())){
						  iterator.remove();
						  ctx.channel().close();
						  log.info("用户"+key+"下线，连接通道以关闭");
						//用户下线，通知所有用户
						  logOut(ctx);
					  }
				  }
				  
			  }
			  //移除临时通道
			  if(channelIdMapTemp.size()!=0){
				  Set keysTemp = channelIdMapTemp.keySet();
				  if(keys != null) {
					  Iterator iterator = keys.iterator();
					  while(iterator.hasNext()) {
						  String key = iterator.next().toString();
						  if(channelIdMapTemp.get(key).equals(ctx.channel().id())){
							  iterator.remove();
							  ctx.channel().close();
						  }
					  }
					  
				  }
			 }
		}
		//组装用户上线信息，通知其他用户
		public void logIn(ChannelHandlerContext ctx){
			Set keys = channelIdMap.keySet();
			  if(keys != null) {
				  Iterator iterator = keys.iterator();
				  while(iterator.hasNext()) {
					  String key = iterator.next().toString();
					  Channel channel=channels.find(channelIdMap.get(key));
					  JSONObject json=new JSONObject();
					  json.put("type", "ONLINE_FIREND");
					  json.put("SENDERID",key);
					  channel.writeAndFlush(new TextWebSocketFrame(json.toString()));
				  }
				  
			  }
		}
		//组装用户下线信息，通知其他用户
		public void logOut(ChannelHandlerContext ctx){
			Set keys = channelIdMap.keySet();
			  if(keys != null) {
				  Iterator iterator = keys.iterator();
				  while(iterator.hasNext()) {
					  String key = iterator.next().toString();
					  Channel channel=channels.find(channelIdMap.get(key));
					  JSONObject json=new JSONObject();
					  json.put("type", "OFFLINE_FIREND");
					  json.put("SENDERID",key);
					  channel.writeAndFlush(new TextWebSocketFrame(json.toString()));
				  }
				  
			  }
		}
		//用户重复登陆问题
		public void repeatLogin(ChannelHandlerContext ctx,String senderId){
			JSONObject json=new JSONObject();
			json.put("type", "REPEAT_LOGIN");
			json.put("ERR","当前用户已在线");
			ctx.channel().writeAndFlush(new TextWebSocketFrame(json.toString()));
			channels.add(ctx.channel());
			channelIdMapTemp.put(senderId,ctx.channel().id());
		}
	//处理用户重复登录,强迫在线用户下线
		public void repeatLoginHandle(ChannelHandlerContext ctx,String senderId){
		Channel chanel = channels.find(channelIdMap.get(senderId));
			JSONObject json=new JSONObject();
			json.put("type", "LOGIN_DROP");
			json.put("ERR","你已经被顶下线了");
			chanel.writeAndFlush(new TextWebSocketFrame(json.toString()));
			chanel.close();
		Set keys = channelIdMap.keySet();
		  if(keys != null) {
			  Iterator iterator = keys.iterator();
			  while(iterator.hasNext()) {
				  String key = iterator.next().toString();
				  if(key.equals(senderId)){
					  iterator.remove();
					  channelIdMap.remove(senderId);
				  }
			  }
		  }
		  logIn(ctx);
			channelIdMap.put(senderId,ctx.channel().id());
			channels.add(ctx.channel());
			log.info("用户"+senderId+"上线");
		}
		//移除临时通道
		public void tempChanelRemove(ChannelHandlerContext ctx,String senderId){
			Channel chanel = channels.find(channelIdMapTemp.get(senderId));
			chanel.close();
			Set keys = channelIdMapTemp.keySet();
			  if(keys != null) {
				  Iterator iterator = keys.iterator();
				  while(iterator.hasNext()) {
					  String key = iterator.next().toString();
					  if(key.equals(senderId)){
						  iterator.remove();
						  channelIdMapTemp.remove(senderId);
					  }
				  }
			  }
		}
}
