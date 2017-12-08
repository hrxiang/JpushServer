package com.mb.jpush.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtils {
	private static String appkey;
	private static String masterSecret;

	private JPushUtils() {
	};

	static {
		ResourceBundle rs = ResourceBundle.getBundle("jpush");
		Enumeration<String> e = rs.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			String value = rs.getString(key);
			if ("appKey".equals(key)) {
				appkey = value;
			}
			if ("masterSecret".equals(key)) {
				masterSecret = value;
			}
		}
	}

	public static String getAppkey() {
		return appkey;
	}

	public static String getMasterSecret() {
		return masterSecret;
	}

	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.alertAll(alert);
	}

	public static PushPayload buildPushObject_all_alias_alert(String alert,
			String... alias) {
		return PushPayload.newBuilder().setPlatform(Platform.all())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(alert)).build();
	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle(
			String alert, String title, String... tagValue) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.tag(tagValue))
				.setNotification(Notification.android(alert, title, null))
				.build();
	}

	public static PushPayload buildPushObject_android_and_ios(String alert,
			String title, String extra_key, String extra_value,
			String... tagValue) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.tag(tagValue))
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(alert)
								.addPlatformNotification(
										AndroidNotification.newBuilder()
												.setTitle(title).build())
								.addPlatformNotification(
										IosNotification
												.newBuilder()
												.incrBadge(1)
												.addExtra(extra_key,
														extra_value).build())
								.build()).build();
	}

}
