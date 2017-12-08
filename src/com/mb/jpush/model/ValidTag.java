package com.mb.jpush.model;

/**
 * 有效标签
 * */

public class ValidTag {
	private int id;
	private String tag_;// 标签,
	private String registration_id;// 终端唯一标示

	public ValidTag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidTag(String tag_, String registration_id) {
		super();
		this.tag_ = tag_;
		this.registration_id = registration_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag_() {
		return tag_;
	}

	public void setTag_(String tag_) {
		this.tag_ = tag_;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag_=" + tag_ + ", registration_id="
				+ registration_id + "]";
	}
}
