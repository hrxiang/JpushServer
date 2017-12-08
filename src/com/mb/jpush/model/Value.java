package com.mb.jpush.model;

import java.io.Serializable;

public class Value implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7366192970872985446L;
	private int code;
	private String reason;

	public Value(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
