package com.mb.jpush.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Timers {
	/**
	 * 定时器，根据时间取消对应的定时器  
	 * key---->时间
	 * */
	private static Map<String, Timer> timers = new HashMap<String, Timer>();

	/**
	 * 提交定时器
	 * */
	public static Timer submit(String time, Timer timer) {
		return timers.put(time, timer);
	}

	/**
	 * 取消定时器
	 * */
	public static void cancel(String time) {
		if (timers.containsKey(time))
			timers.remove(time).cancel();
	}

	/**
	 * 移除
	 * */
	public static Timer remove(String time) {
		if (timers.containsKey(time))
			return timers.remove(time);
		return null;
	}
	
	public static Map<String, Timer> getTimers(){
		return timers;
	}
}
