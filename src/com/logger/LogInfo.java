package com.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class LogInfo {
private static Logger log;
	
	static
	{
		log = Logger.getLogger(LogInfo.class);
	}
	
	/**
	 *  
	 * @title getTrace
	 * @description 输出异常详细信息
	 * @param t
	 * @return String 
	 * @author  fivestar
	 * @time 2013-11-26 下午7:52:09
	 */
	public  String getTrace(Throwable t)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return "抛出异常详细:"+buffer.toString();
	}
	/**
	 * 输出标准日志
	 */
	public String outputFinalLog (String startStr, String expStr,String endStr)
	{
		StringBuffer logStr = new StringBuffer();
		logStr.append(startStr);
		logStr.append("\r\n");
		if (expStr != null)
		{
			logStr.append(expStr);
			logStr.append("\r\n");
		}
		logStr.append(endStr).append("记录日志结束时间：").append(getNowTime()+"。").append("\t\n\t\n");
		//log.info(logStr.toString());
		return logStr.toString();
	}
	public void info(String str)
	{
		log.info(str);
	}
	public void info(Exception e)
	{
		log.info(e);
	}
	/**
	 * 获取服务器的当前时间
	 * @return
	 */
	public static String getNowTime()
	{
		Date date = new Date();
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	    Calendar canlandar = Calendar.getInstance();
	    String now_time = df.format(date);
	    System.out.println(now_time);
	    return now_time;
	}
}
