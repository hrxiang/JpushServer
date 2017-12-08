package com.mb.jpush.mapper;

import java.util.List;
import java.util.Map;

import com.mb.jpush.model.TimerLogger;

public interface TimerLoggerMapper {

	List<TimerLogger> queryTimerLoggers(Map<String, Object> map);

	int queryTimerLoggersCount(Map<String, Object> map);

	int deleteTimerLogger(String... time);

	int addTimerLogger(TimerLogger l);
	
	/**定时器恢复接口*/
	List<TimerLogger> queryAll();
}
