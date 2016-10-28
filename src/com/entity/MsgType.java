package com.entity;

public enum MsgType {
	LOGIN,
	MESSAGE_TO_ONE,
	LOGOUT,
	REPEAT_LOGIN_HANDLE,
	LGONIN_ANGRY,
	TEMP_DROP,
	MSG_OTHER;
	public static MsgType valueOfString(String type) {
		MsgType result=MSG_OTHER;
		if(type.equalsIgnoreCase("LOGIN")){
			result=LOGIN;
		}else if(type.equalsIgnoreCase("MESSAGE_TO_ONE")){
			result=MESSAGE_TO_ONE;
		}else if(type.equalsIgnoreCase("LOGINOUT")){
			result=LOGOUT;
		}else if(type.equalsIgnoreCase("REPEAT_LOGIN_HANDLE")){
			result=REPEAT_LOGIN_HANDLE;
		}
		else if(type.equalsIgnoreCase("LGONIN_ANGRY")){
			result=LGONIN_ANGRY;
		}
		else if(type.equalsIgnoreCase("TEMP_DROP")){
			result=TEMP_DROP;
		}
		return result;
	}
	
}

