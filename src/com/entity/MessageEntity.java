package com.entity;
/**
 * 
 * <p>
 * 功能描述：websocket消息实体类
 * </p>
 * <p>
 * 创建：Oct 12, 2016 by ydh
 * </p>
 * 
 */
public class MessageEntity {
	private String senderId;
	private String recvId;
	private MsgType type;
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getRecvId() {
		return recvId;
	}
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	public MsgType getType() {
		return type;
	}
	public void setType(MsgType type) {
		this.type = type;
	}
}
