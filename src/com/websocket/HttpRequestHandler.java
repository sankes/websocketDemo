package com.websocket;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

/**
 * HttpRequestHandler 鍋氫簡涓嬮潰鍑犱欢浜嬶紝
 * </p>
 * 濡傛灉璇�HTTP 璇锋眰琚彂閫佸埌URI 鈥�ws鈥濓紝璋冪敤 FullHttpRequest 涓婄殑 retain()锛屽苟閫氳繃璋冪敤
 * fireChannelRead(msg) 杞彂鍒颁笅涓�釜 ChannelInboundHandler銆俽etain() 鏄繀瑕佺殑锛屽洜涓�
 * channelRead() 瀹屾垚鍚庯紝瀹冧細璋冪敤 FullHttpRequest 涓婄殑 release() 鏉ラ噴鏀惧叾璧勬簮銆�锛堣鍙傝�鎴戜滑鍏堝墠鐨�
 * SimpleChannelInboundHandler 鍦ㄧ6绔犱腑璁ㄨ锛�
 * </p>
 * 濡傛灉瀹㈡埛绔彂閫佺殑 HTTP 1.1 澶存槸鈥淓xpect: 100-continue鈥�锛屽皢鍙戦�鈥�00 Continue鈥濈殑鍝嶅簲銆�
 * </p>
 * 鍦�澶磋璁剧疆鍚庯紝鍐欎竴涓�HttpResponse 杩斿洖缁欏鎴风銆傛敞鎰忥紝杩欐槸涓嶆槸 FullHttpResponse锛屽敮涓�殑鍙嶅簲鐨勭涓�儴鍒嗐�姝ゅ锛屾垜浠笉浣跨敤
 * writeAndFlush() 鍦ㄨ繖閲�- 杩欎釜鏄湪鏈�悗瀹屾垚銆�
 * </p>
 * 濡傛灉娌℃湁鍔犲瘑涔熶笉鍘嬬缉锛岃杈惧埌鏈�ぇ鐨勬晥鐜囧彲浠ユ槸閫氳繃瀛樺偍 index.html 鐨勫唴瀹瑰湪涓�釜 DefaultFileRegion
 * 瀹炵幇銆傝繖灏嗗埄鐢ㄩ浂鎷疯礉鏉ユ墽琛屼紶杈撱�鍑轰簬杩欎釜鍘熷洜锛屾垜浠鏌ワ紝鐪嬬湅鏄惁鏈変竴涓�SslHandler 鍦�ChannelPipeline 涓�鍙﹀锛屾垜浠娇鐢�
 * ChunkedNioFile銆�
 * </p>
 * 鍐�LastHttpContent 鏉ユ爣璁板搷搴旂殑缁撴潫锛屽苟缁堟瀹�
 * </p>
 * 濡傛灉涓嶈姹�keepalive 锛屾坊鍔�ChannelFutureListener 鍒�ChannelFuture
 * 瀵硅薄鐨勬渶鍚庡啓鍏ワ紝骞跺叧闂繛鎺ャ�娉ㄦ剰锛岃繖閲屾垜浠皟鐢�writeAndFlush() 鏉ュ埛鏂版墍鏈変互鍓嶅啓鐨勪俊鎭�
 * 
 * 鎵╁睍 SimpleChannelInboundHandler 鐢ㄤ簬澶勭悊 FullHttpRequest淇℃伅
 * 
 * @author shankes
 * @created 2016骞�鏈�鏃�涓嬪崍5:47:36
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {// 1.鎵╁睍
																						// SimpleChannelInboundHandler
																						// 鐢ㄤ簬澶勭悊
																						// FullHttpRequest淇℃伅
	private final String wsUri;
	private static final File INDEX;

	static {
		URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			String path = location.toURI() + "WebsocketChatClient.html";
			path = !path.contains("file:") ? path : path.substring(5);
			INDEX = new File(path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException("Unable to locate WebsocketChatClient.html", e);
		}
	}

	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (wsUri.equalsIgnoreCase(request.getUri())) {
			// 2.濡傛灉璇锋眰鏄�WebSocket 鍗囩骇锛岄�澧炲紩鐢ㄨ鏁板櫒锛堜繚鐣欙級
			// 骞朵笖灏嗗畠浼犻�缁欏湪 ChannelPipeline 涓殑涓嬩釜 ChannelInboundHandler
			ctx.fireChannelRead(request.retain());
		} else {
			if (HttpHeaders.is100ContinueExpected(request)) {
				// 3.澶勭悊绗﹀悎 HTTP 1.1鐨�"100 Continue" 璇锋眰
				send100Continue(ctx);
			}
			// 4.璇诲彇榛樿鐨�WebsocketChatClient.html 椤甸潰
			RandomAccessFile file = new RandomAccessFile(INDEX, "r");

			HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

			boolean keepAlive = HttpHeaders.isKeepAlive(request);
			// 5.鍒ゆ柇 keepalive 鏄惁鍦ㄨ姹傚ご閲岄潰
			if (keepAlive) {
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
				response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			}
			// 6.鍐�HttpResponse 鍒板鎴风
			ctx.write(response);
			// 7.鍐�index.html 鍒板鎴风锛屽垽鏂�SslHandler 鏄惁鍦�ChannelPipeline 鏉ュ喅瀹氭槸浣跨敤
			// DefaultFileRegion 杩樻槸 ChunkedNioFile
			if (ctx.pipeline().get(SslHandler.class) == null) {
				ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
			} else {
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			// 8.鍐欏苟鍒锋柊 LastHttpContent 鍒板鎴风锛屾爣璁板搷搴斿畬鎴�
			ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			if (!keepAlive) {
				// 9.濡傛灉 keepalive 娌℃湁瑕佹眰锛屽綋鍐欏畬鎴愭椂锛屽叧闂�Channel
				future.addListener(ChannelFutureListener.CLOSE);
			}

			file.close();
		}
	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "寮傚父");
		// 褰撳嚭鐜板紓甯稿氨鍏抽棴杩炴帴
		cause.printStackTrace();
		ctx.close();
	}

	
}
