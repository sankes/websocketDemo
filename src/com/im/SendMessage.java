package com.im;

import java.util.Map;

import com.toolInterface.ChannelTools;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.sf.json.JSONObject;
/**
 * 
 * <p>
 * 功能描述：websocket发送消息
 * </p>
 * <p>
 * 创建：Oct 12, 2016 by ydh
 * </p>
 * 
 */
public class SendMessage implements ChannelTools {
	public static void sendMessage(JSONObject obj, String recvId) {
		ChannelId id = channelIdMap.get(recvId);
		if (id != null) {
			Channel channel = channels.find(id);
			channel.writeAndFlush(new TextWebSocketFrame(obj.toString()));
		}
	}

	
}
