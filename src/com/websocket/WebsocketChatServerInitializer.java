package com.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 由于 Netty 处理了其余大部分功能，
 * 
 * 唯一剩下的我们现在要做的是初始化 ChannelPipeline 给每一个创建的新的 Channel 。
 * 
 * @author shankes
 * @created 2016年8月7日 下午6:05:12
 */
public class WebsocketChatServerInitializer extends ChannelInitializer<SocketChannel> { // 1.扩展
																						// ChannelInitializer

	/*
	 * 2.添加 ChannelHandler 到 ChannelPipeline
	 * 
	 * 设置 ChannelPipeline 中所有新注册的 Channel,安装所有需要的 ChannelHandler
	 */
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpRequestHandler("/ws"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipeline.addLast(new TextWebSocketFrameHandler());

	}
}
