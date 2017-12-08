package com.mb.jpush.model;

public class Logger {
	private int id;
	private String alert;
	private String logger_type;
	private String recipient_type;
	private boolean target_android;
	private boolean target_ios;
	private String delivery_type;
	private String delivery_time;
	private String time_to_live;
	private String status;
	private String reason;

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

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}

	public String getTime_to_live() {
		return time_to_live;
	}

	public void setTime_to_live(String time_to_live) {
		this.time_to_live = time_to_live;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLogger_type() {
		return logger_type;
	}

	public void setLogger_type(String logger_type) {
		this.logger_type = logger_type;
	}

	@Override
	public String toString() {
		return "Logger [id=" + id + ", alert=" + alert + ", recipient_type="
				+ recipient_type + ", target_android=" + target_android
				+ ", target_ios=" + target_ios + ", delivery_type="
				+ delivery_type + ", delivery_time=" + delivery_time
				+ ", time_to_live=" + time_to_live + ", status=" + status
				+ ", reason=" + reason + "]";
	}
}
