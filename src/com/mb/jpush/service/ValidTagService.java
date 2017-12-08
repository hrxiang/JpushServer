package com.mb.jpush.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mb.jpush.model.ValidTag;

@Service
public interface ValidTagService {
	boolean addTags(List<ValidTag> validTags);

	boolean deleteTagByRegistration_id(String registration_id);

	boolean deleteTagByTag_(String tag_);

	List<String> queryTag_s(Map<String, Object> map);

	int queryTag_sCount();
}
