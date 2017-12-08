package com.mb.jpush.mapper;

import java.util.List;
import java.util.Map;

import com.mb.jpush.model.ValidTag;
public interface ValidTagMapper {
	int addTags(List<ValidTag> validTags);
	
	int deleteTagByRegistration_id(String registration_id);
	
	int deleteTagByTag_(String tag_);
	
	List<String> queryTag_s(Map<String,Object> map);
	
	int queryTag_sCount();
}
