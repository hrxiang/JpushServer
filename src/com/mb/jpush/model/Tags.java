package com.mb.jpush.model;

import java.io.Serializable;
import java.util.List;

public class Tags implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2585568234150958082L;
	private String registration_id;
	private List<String> tags;
	public String getRegistration_id() {
		return registration_id;
	}
	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "Tags [registration_id=" + registration_id + ", tags=" + tags
				+ "]";
	}
}
