package com.mb.jpush.model;

import java.io.Serializable;

public class TimerLogger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1476815782885677012L;
	private int id;
	private String timer_time;
	private String alert;
	private String logger_type;
	private String recipient_type;
	private boolean target_android;
	private boolean target_ios;
	private String device_tags;
	private String device_aliases;
	private String device_registrationids;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getTimer_time() {
		return timer_time;
	}

	public void setTimer_time(String timer_time) {
		this.timer_time = timer_time;
	}

	public String getRecipient_type() {
		return recipient_type;
	}

	public void setRecipient_type(String recipient_type) {
		this.recipient_type = recipient_type;
	}

	public boolean isTarget_android() {
		return target_android;
	}

	public void setTarget_android(boolean target_android) {
		this.target_android = target_android;
	}

	public boolean isTarget_ios() {
		return target_ios;
	}

	public void setTarget_ios(boolean target_ios) {
		this.target_ios = target_ios;
	}

	public String getLogger_type() {
		return logger_type;
	}

	public void setLogger_type(String logger_type) {
		this.logger_type = logger_type;
	}

	public String getDevice_tags() {
		return device_tags;
	}

	public void setDevice_tags(String device_tags) {
		this.device_tags = device_tags;
	}

	public String getDevice_aliases() {
		return device_aliases;
	}

	public void setDevice_aliases(String device_aliases) {
		this.device_aliases = device_aliases;
	}

	public String getDevice_registrationids() {
		return device_registrationids;
	}

	public void setDevice_registrationids(String device_registrationids) {
		this.device_registrationids = device_registrationids;
	}

	@Override
	public String toString() {
		return "TimerLogger [id=" + id + ", timer_time=" + timer_time
				+ ", alert=" + alert + ", logger_type=" + logger_type
				+ ", recipient_type=" + recipient_type + ", target_android="
				+ target_android + ", target_ios=" + target_ios
				+ ", device_tags=" + device_tags + ", device_aliases="
				+ device_aliases + ", device_registrationids="
				+ device_registrationids + "]";
	}
}
