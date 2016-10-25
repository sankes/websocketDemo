package com.heartbeat;

import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.toolInterface.ChannelTools;

public class HeartBeatThread extends Thread implements ChannelTools {
	public HeartBeatThread() {
		start();
	}

	@Override
	public void run() {
		try {
			sleep(15 * 1000);
			executeOffline(heartList, channels, channelIdMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}

	// 心跳检测关闭未反馈信息的通道
	public void executeOffline(List list, ChannelGroup channels,
			Map<String, ChannelId> channelIdMap) {
		log.info("开始检测.......");
		Set keys = channelIdMap.keySet();
		List<String> offLine = new ArrayList<String>();
		List<String> onLine = new ArrayList<String>();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next().toString();
				onLine.add(key);
			}
		}
		for (int i = 0; i < onLine.size(); i++) {
			if (list.contains(onLine.get(i))) {
				continue;
			} else {
				offLine.add(onLine.get(i));
			}
		}
		for (int i = 0; i < offLine.size(); i++) {
			channels.find(channelIdMap.get(offLine.get(i))).close();
			channelIdMap.remove(offLine.get(i));
			log.info("用户" + offLine.get(i) + "已被移除下线，连接通道以关闭............");
		}
	}
}
