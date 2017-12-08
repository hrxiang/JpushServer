package com.mb.jpush.model;

import java.io.Serializable;

public class Error extends Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8326253043676677247L;
	private Value error;

	public Error() {
	}

	public Error(String reason) {
		this.error = new Value(FAIL, reason);
	}

	public Value getError() {
		return error;
	}

	public void setError(Value error) {
		this.error = error;
	}
}
