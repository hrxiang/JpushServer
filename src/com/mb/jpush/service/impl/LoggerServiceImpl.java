package com.mb.jpush.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.jpush.mapper.LoggerMapper;
import com.mb.jpush.model.Logger;
import com.mb.jpush.service.LoggerService;

@Service("lService")
public class LoggerServiceImpl implements LoggerService {

	@Autowired
	private LoggerMapper loggerMapper;

	@Override
	public List<Logger> queryLoggers(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return loggerMapper.queryLoggers(map);
	}

	@Override
	public int queryLoggersCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return loggerMapper.queryLoggersCount(map);
	}

	// @Override
	// public boolean deleteLogger(Logger l) {
	// // TODO Auto-generated method stub
	// if (loggerMapper.deleteLogger(l) == 1)
	// return true;
	// return false;
	// }

	@Override
	public boolean addLogger(Logger l) {
		// TODO Auto-generated method stub
		if (loggerMapper.addLogger(l) == 1)
			return true;
		return false;
	}

	@Override
	public boolean deleteLogger(String... ids) {
		// TODO Auto-generated method stub
		if (loggerMapper.deleteLogger(ids) == 1)
			return true;
		return false;
	}

}
