package com.toolInterface;

import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.im.IMControlCenter;
import com.logger.LogInfo;

public interface ChannelTools {
		//所有的消息通道
		public static ChannelGroup channels = IMControlCenter.channels;
		//所有在线用户
		public static Map<String,ChannelId> channelIdMap= IMControlCenter.channelIdMap;
		//所有临时通道
		public static Map<String,ChannelId> channelIdMapTemp= IMControlCenter.channelIdMapTemp;
		// 日志处理
		Logger logs = Logger.getLogger(IMControlCenter.class);
		LogInfo log = new LogInfo();
		//心跳数组
		public static ArrayList<String> heartList=IMControlCenter.onlinList;
}
