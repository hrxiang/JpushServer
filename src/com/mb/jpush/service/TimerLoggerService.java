package com.mb.jpush.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mb.jpush.model.TimerLogger;

@Service
public interface TimerLoggerService {

	List<TimerLogger> queryTimerLoggers(Map<String, Object> map);

	int queryTimerLoggersCount(Map<String, Object> map);

	// boolean deleteLogger(Logger l);
	boolean deleteTimerLogger(String... time);

	boolean addTimerLogger(TimerLogger l);
	
	/**定时器恢复接口*/
	List<TimerLogger> queryAll();
}
