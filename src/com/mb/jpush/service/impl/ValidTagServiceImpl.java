package com.mb.jpush.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.jpush.mapper.ValidTagMapper;
import com.mb.jpush.model.ValidTag;
import com.mb.jpush.service.ValidTagService;

@Service
public class ValidTagServiceImpl implements ValidTagService {

	@Autowired
	private ValidTagMapper tagMapper;

	public boolean addTags(List<ValidTag> validTags) {
		if (tagMapper.addTags(validTags) == 1)
			return true;
		return false;
	}

	@Override
	public boolean deleteTagByRegistration_id(String registration_id) {
		// TODO Auto-generated method stub
		if (tagMapper.deleteTagByRegistration_id(registration_id) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteTagByTag_(String tag_) {
		// TODO Auto-generated method stub
		if (tagMapper.deleteTagByTag_(tag_) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> queryTag_s(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return tagMapper.queryTag_s(map);
	}

	@Override
	public int queryTag_sCount() {
		// TODO Auto-generated method stub
		return tagMapper.queryTag_sCount();
	}
}
