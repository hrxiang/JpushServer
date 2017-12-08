package com.mb.jpush.model;

import java.io.Serializable;

public class Success extends Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3086745789144550186L;
	private Value success;

	public Success() {
	}

	public Success(String reason) {
		this.success = new Value(SUCCESS, reason);
	}

	public Value getSuccess() {
		return success;
	}

	public void setSuccess(Value success) {
		this.success = success;
	}
}
