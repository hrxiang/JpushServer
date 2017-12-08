package com.mb.jpush.mapper;

import java.util.List;
import java.util.Map;

import com.mb.jpush.model.Logger;

public interface LoggerMapper {

	List<Logger> queryLoggers(Map<String,Object> map);
	
	int queryLoggersCount(Map<String,Object> map);
	
//	int deleteLogger(Logger l);
	
	int deleteLogger(String ...ids);
	
	int addLogger(Logger l);
}
