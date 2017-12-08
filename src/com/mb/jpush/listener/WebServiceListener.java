package com.mb.jpush.listener;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.mb.jpush.common.Timers;
import com.mb.jpush.model.TimerLogger;
import com.mb.jpush.service.LoggerService;
import com.mb.jpush.service.TimerLoggerService;
import com.mb.jpush.util.DateFormatUtil;
import com.mb.jpush.util.JPushUtils;
import com.mb.jpush.util.StringUtil;

/**
 * 恢复定时器
 * **/
public class WebServiceListener implements ServletContextListener {
	private static Logger LOG = Logger.getLogger(WebServiceListener.class);
	private LoggerService loggerService;
	private TimerLoggerService timerLoggerService;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		LOG.info("contextDestroyed---------------------");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		LOG.info("contextInitialized---------------------");
		/**
		 * 重启服务器，恢复定时器
		 * */
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(arg0.getServletContext());
		this.timerLoggerService = (TimerLoggerService) applicationContext
				.getBean("tlService");
		this.loggerService = (LoggerService) applicationContext
				.getBean("lService");

		recoverTimer();
	}

	private void recoverTimer() {
		try {
			LOG.info("recover timer start---------------------");
			List<TimerLogger> timerloggers = timerLoggerService.queryAll();
			if (null != timerloggers && timerloggers.size() > 0) {
				for (int i = 0; i < timerloggers.size(); i++) {
					TimerLogger l = timerloggers.get(i);
					if ((StringUtil.isNullOrEmpty(l.getTimer_time()) || (l
							.getTimer_time().compareTo(
									DateFormatUtil.formatDate(
											DateFormatUtil.DATE_PATTERN_1,
											new Date())) < 0))) {
						/**
						 * 定时器已过期，删除定时器
						 * */
						timerLoggerService.deleteTimerLogger(l.getTimer_time());
						continue;
					}

					final com.mb.jpush.model.Logger logger = new com.mb.jpush.model.Logger();
					boolean isAndroidPlatform = false;
					boolean isIosPlatform = false;
					if (l.isTarget_ios()) {// ios
						logger.setTarget_ios(true);
						isIosPlatform = true;
					}

					if (l.isTarget_android()) {// android
						logger.setTarget_android(true);
						isAndroidPlatform = true;
					}
					logger.setLogger_type(l.getLogger_type());// 0-通知,1-消息
					logger.setAlert(l.getAlert());
					logger.setRecipient_type(l.getRecipient_type());
					logger.setDelivery_type("1");// 定时
					logger.setTime_to_live("86400");// 默认一天

					// ** jpush *//
					final JPushClient jpushClient = new JPushClient(
							JPushUtils.getMasterSecret(),
							JPushUtils.getAppkey());
					Builder buidler = PushPayload.newBuilder();

					if (isIosPlatform && isAndroidPlatform) {
						buidler.setPlatform(Platform.android_ios());
					} else if (isIosPlatform && !isAndroidPlatform) {
						buidler.setPlatform(Platform.ios());
					} else if (!isIosPlatform && isAndroidPlatform) {
						buidler.setPlatform(Platform.android());
					} else {
						buidler.setPlatform(Platform.all());
					}

					if (l.getRecipient_type().equals("0")) {// 广播
						buidler.setAudience(Audience.all());
					} else if (l.getRecipient_type().equals("1")) {// 标签
						String[] device_tags = l.getDevice_tags()
								.replace("[", "").replace("]", "").split(",");
						buidler.setAudience(Audience.tag(device_tags));
					} else if (l.getRecipient_type().equals("2")) {// Alias
						String[] device_aliases = l.getDevice_aliases()
								.replace("[", "").replace("]", "").split(",");
						buidler.setAudience(Audience.alias(device_aliases));
					} else if (l.getRecipient_type().equals("3")) {// Registration
																	// ID
						String[] device_registrationids = l
								.getDevice_registrationids().replace("[", "")
								.replace("]", "").split(",");
						buidler.setAudience(Audience
								.registrationId(device_registrationids));
					}

					if ("0".equals(l.getLogger_type())) {// 发通知
						buidler.setNotification(Notification.alert(l.getAlert()));
					} else if ("1".equals(l.getLogger_type())) {// 发消息
						buidler.setMessage(Message.newBuilder()
								.setMsgContent(l.getAlert()).build());
					}

					logger.setDelivery_time(l.getTimer_time());

					final PushPayload pushPayload = buidler.build();
					pushPayload.resetOptionsTimeToLive(Long.parseLong("86400"));

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								PushResult result = jpushClient
										.sendPush(pushPayload);
								LOG.info("Got result - " + result);
								if (result.isResultOK()) {
									logger.setStatus("0");
								}
								loggerService.addLogger(logger);
								timerLoggerService.deleteTimerLogger(logger
										.getDelivery_time());
								Timers.remove(logger.getDelivery_time());
							} catch (APIConnectionException e) {
								LOG.error(
										"Connection error. Should retry later. ",
										e);
								logger.setStatus("1");
								logger.setReason(e.getMessage());
								loggerService.addLogger(logger);
								timerLoggerService.deleteTimerLogger(logger
										.getDelivery_time());
								Timers.remove(logger.getDelivery_time());
							} catch (APIRequestException e) {
								LOG.error(
										"Error response from JPush server. Should review and fix it. ",
										e);
								LOG.info("HTTP Status: " + e.getStatus());
								LOG.info("Error Code: " + e.getErrorCode());
								LOG.info("Error Message: "
										+ e.getErrorMessage());
								LOG.info("Msg ID: " + e.getMsgId());
								logger.setStatus("1");
								logger.setReason(e.getErrorMessage());
								loggerService.addLogger(logger);
								timerLoggerService.deleteTimerLogger(logger
										.getDelivery_time());
								Timers.remove(logger.getDelivery_time());
							}
						}

					}, DateFormatUtil.paseDate(DateFormatUtil.DATE_PATTERN_1,
							l.getTimer_time()));

					Timers.submit(l.getTimer_time(), timer);// 提交定时器
					LOG.info("定时器提交成功。。。。。。。。");
				}
			}
			LOG.info("recover timer end--------------------->" + "定时器数量: ["
					+ Timers.getTimers().size() + "] 定时器：" + Timers.getTimers());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
