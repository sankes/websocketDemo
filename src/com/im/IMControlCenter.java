package com.im;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.base.util.EasyApplicationContextUtils;
import com.business.IMDao;
import com.entity.MessageEntity;
import com.entity.MsgType;
import com.heartbeat.HeartBeat;
import com.logger.LogInfo;

public class IMControlCenter {
	
	// 日志处理
	Logger logs = Logger.getLogger(IMControlCenter.class);
	LogInfo log = new LogInfo();
	// 所有的消息通道
	public static ChannelGroup channels = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);
	// 所有在线用户
	public static Map<String, ChannelId> channelIdMap = new HashMap<String, ChannelId>();
	//临时通道
	public static Map<String, ChannelId> channelIdMapTemp = new HashMap<String, ChannelId>();
	// 通道操作对象
	ChannelOperation channelOperation = new ChannelOperation();
	// 发送消息对象
	SendMessage sendMessage = new SendMessage();
	//后台对象
	private IMDao iMDao = EasyApplicationContextUtils.getBeanByType(IMDao.class);
	//心跳检测启动标识当flag_heart为0时，启动心跳检测
	private int flag_heart = 0;
	//存放心跳检测在线人员数组
	public static ArrayList<String> onlinList=new ArrayList<String>();
	// 消息分类处理
	public void doMsgForShunt(ChannelHandlerContext ctx, TextWebSocketFrame msg){
		log.info("终端上传参数"+msg.text());
//		if(flag_heart == 0){
//			 HeartBeat.heartBeat();
//			 log.info("开启心跳检测");
//			 flag_heart++;
//		}
		JSONObject json = JSONObject.fromObject(msg.text());
		MessageEntity obj=(MessageEntity) JSONObject.toBean(json,MessageEntity.class);
		String senderId = json.getString("senderId");
		switch(MsgType.valueOfString(obj.getType())){
		case LOGIN:
			channelOperation.channelAdd(ctx, senderId);
			break;
		case MESSAGE_TO_ONE:
			String recvId = json.getString("recvId");
			sendMessage.sendMessage(json, recvId);
			break;
		case LOGOUT:
			channelOperation.channelRemove(ctx);
			break;
		case REPEAT_LOGIN_HANDLE:
			channelOperation.repeatLoginHandle(ctx, senderId);
			break;
		case TEMP_DROP:
			channelOperation.tempChanelRemove(ctx, senderId);
			break;
		default:
			break;
		}
//		if(obj.getType().equalsIgnoreCase("LOGIN")) {
//			channelOperation.channelAdd(ctx, senderId);
//		}else if(obj.getType().equalsIgnoreCase("MessageToOne")){
//			String recvId = json.getString("recvId");
//			sendMessage.sendMessage(json, recvId);
//		}else if(obj.getType().equalsIgnoreCase("LOGINOUT")){
//			channelOperation.channelRemove(ctx);
//		}else if(obj.getType().equalsIgnoreCase("HEAER_BEAT")){
//			onlinList.add(obj.getSenderId());
//		}
		
	}
}
