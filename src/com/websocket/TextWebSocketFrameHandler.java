package com.websocket;

import com.im.ChannelOperation;
import com.im.IMControlCenter;
import com.toolInterface.ChannelTools;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * TextWebSocketFrameHandler 仅作了几件事：
 * 
 * 当WebSocket 与新客户端已成功握手完成，
 * 
 * 通过写入信息到 ChannelGroup 中的 Channel 来通知所有连接的客户端，
 * 
 * 然后添加新 Channel 到 ChannelGroup 如果接收到 TextWebSocketFrame，
 * 
 * 调用 retain() ，并将其写、刷新到 ChannelGroup，使所有连接的 WebSocket Channel 都能接收到它。
 * 
 * 和以前一样，retain() 是必需的，因为当 channelRead0（）返回时，TextWebSocketFrame 的引用计数将递减。
 * 
 * 由于所有操作都是异步的，writeAndFlush() 可能会在以后完成，我们不希望它来访问无效的引用。
 * </p>
 * 
 * WebSockets 在“帧”里面来发送数据，其中每一个都代表了一个消息的一部分。一个完整的消息可以利用了多个帧。 WebSocket "Request
 * for Comments" (RFC) 定义了六中不同的 frame; Netty 给他们每个都提供了一个 POJO 实现
 * ，而我们的程序只需要使用下面4个帧类型：
 * 
 * CloseWebSocketFrame PingWebSocketFrame PongWebSocketFrame TextWebSocketFrame
 * 
 * 在这里我们只需要显示处理 TextWebSocketFrame，其他的会由 WebSocketServerProtocolHandler 自动处理。
 * 
 * @author shankes
 * @created 2016年8月7日 下午5:57:22
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	IMControlCenter imcontrolcenter=new IMControlCenter();

	/*
	 * 1. 每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel。
	 * 
	 * 其中如果你使用的是 Netty 4.x 版本时，需要把 messageReceived() 重命名为channelRead0()
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception { // (1)
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			if (channel != incoming) {
				channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
			} else {
				channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text()));
			}
		}
		imcontrolcenter.doMsgForShunt(ctx, msg);
	}

	/*
	 * 2. 每当从服务端收到新的客户端连接时，客户端的 Channel 存入 ChannelGroup 列表中，
	 * 
	 * 并通知列表中的其他客户端 Channel
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
//		Channel incoming = ctx.channel();
//		for (Channel channel : channels) {
//			channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
//		}
//		channels.add(ctx.channel());
//		System.out.println("Client:" + incoming.remoteAddress() + "加入");
	}

	/*
	 * 3. 每当从服务端收到客户端断开时，客户端的 Channel 移除 ChannelGroup 列表中，
	 * 
	 * 并通知列表中的其他客户端 Channel
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
//		Channel incoming = ctx.channel();
//		for (Channel channel : channels) {
//			channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
//		}
//		System.out.println("Client:" + incoming.remoteAddress() + "离开");
//		channels.remove(ctx.channel());
	}

	/*
	 * 4.服务端监听到客户端活动
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	/*
	 * 5.服务端监听到客户端不活动
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		ChannelOperation channelOperation=new ChannelOperation();
		channelOperation.channelRemove(ctx);
	// 	System.out.println("Client:" + incoming.remoteAddress() + "掉线");
	}

	/*
	 * 6.exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，
	 * 
	 * 即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时。
	 * 
	 * 在大部分情况下，捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
	 * 
	 * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，
	 * 
	 * 比如你可能想在关闭连接之前发送一个错误码的响应消息。
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		// System.out.println("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}
