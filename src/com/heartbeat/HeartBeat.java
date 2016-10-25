package com.heartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Iterator;
import java.util.Set;
import java.util.Timer;

import java.util.TimerTask;

import net.sf.json.JSONObject;

import com.toolInterface.ChannelTools;

public class HeartBeat implements ChannelTools {
	public static void heartBeat(){
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				// @Override
				public void run() {
					Set keys = channelIdMap.keySet();
					if (keys != null) {
						Iterator iterator = keys.iterator();
						while (iterator.hasNext()) {
							String key = iterator.next().toString();
							Channel channel = channels.find(channelIdMap.get(key));
							JSONObject json = new JSONObject();
							json.put("type", "HEAER_BEAT");
							json.put("INFO", "心跳检测");
							channel.writeAndFlush(new TextWebSocketFrame(json
									.toString()));
						}
					}
					new HeartBeatThread();
				}
			};
			 timer.schedule(task, 0, 20000L);
	}
}
