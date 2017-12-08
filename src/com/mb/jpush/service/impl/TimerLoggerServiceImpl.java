package com.mb.jpush.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.jpush.mapper.TimerLoggerMapper;
import com.mb.jpush.model.TimerLogger;
import com.mb.jpush.service.TimerLoggerService;

@Service("tlService")
public class TimerLoggerServiceImpl implements TimerLoggerService {

	@Autowired
	private TimerLoggerMapper timerLoggerMapper;

	@Override
	public List<TimerLogger> queryTimerLoggers(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return timerLoggerMapper.queryTimerLoggers(map);
	}

	@Override
	public int queryTimerLoggersCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return timerLoggerMapper.queryTimerLoggersCount(map);
	}

	@Override
	public boolean deleteTimerLogger(String... time) {
		// TODO Auto-generated method stub
		if (timerLoggerMapper.deleteTimerLogger(time) == 1)
			return true;
		return false;
	}

	@Override
	public boolean addTimerLogger(TimerLogger l) {
		// TODO Auto-generated method stub
		if (timerLoggerMapper.addTimerLogger(l) == 1)
			return true;
		return false;
	}

	@Override
	public List<TimerLogger> queryAll() {
		// TODO Auto-generated method stub
		return timerLoggerMapper.queryAll();
	}

}
