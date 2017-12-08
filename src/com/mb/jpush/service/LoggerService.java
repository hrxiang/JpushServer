package com.mb.jpush.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mb.jpush.model.Logger;

@Service
public interface LoggerService {
	
	List<Logger> queryLoggers(Map<String,Object> map);
	
	int queryLoggersCount(Map<String,Object> map);
	
//	boolean deleteLogger(Logger l);
	boolean deleteLogger(String ...ids);
	
	boolean addLogger(Logger l);
}
