package com.mb.jpush.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

import com.mb.jpush.common.DwzAjaxDone;
import com.mb.jpush.common.Timers;
import com.mb.jpush.service.LoggerService;
import com.mb.jpush.service.TimerLoggerService;
import com.mb.jpush.util.DateFormatUtil;
import com.mb.jpush.util.JPushUtils;
import com.mb.jpush.util.StringUtil;

@Controller
@RequestMapping("/push")
public class PushController extends DwzAjaxDone {
	protected static final Logger LOG = LoggerFactory
			.getLogger(PushController.class);
	private static final String TYPE_NOTIFICATION = "0";
	private static final String TYPE_MESSAGE = "1";

	@Autowired
	private TimerLoggerService timerLoggerService;

	@Autowired
	private LoggerService loggerService;

	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public String notification() {
		return "notification";
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String message() {
		return "message";
	}

	@RequestMapping(value = "/notification", method = RequestMethod.POST)
	public String notification(HttpServletRequest request) {
		String alert = request.getParameter("alert");
		String[] aps = request.getParameterValues("aps");
		String recipient_type = request.getParameter("recipient_type");
		String[] device_tags = request.getParameter("device_tags.name").split(
				",");
		String[] device_aliases = request.getParameter("device_aliases").split(
				",");
		String[] device_registrationids = request.getParameter(
				"device_registrationids").split(",");
		String delivery_type = request.getParameter("delivery_type");
		String delivery_date = request.getParameter("delivery_date");// 定时需要
		String duration_time = request.getParameter("duration_time");

		String time_to_live = request.getParameter("time_to_live");

		boolean isCorrect = validParams(alert, aps, recipient_type,
				device_tags, device_aliases, device_registrationids,
				delivery_type, delivery_date);
		
		if (!isCorrect) {
			return ajaxForwardError("发送失败,请检查填写的数据是否有问题!", null);
		}

		if (delivery_type.equals("1")
				&& Timers.getTimers().containsKey(delivery_date)) {
			return ajaxForwardError("发送失败,这个时间已经存在定时任务了!", null);
		}
		
		boolean isSuccess = push(TYPE_NOTIFICATION, alert, aps, recipient_type,
				device_tags, device_aliases, device_registrationids,
				delivery_type, delivery_date, duration_time, time_to_live);
		if (!isSuccess) {
			/**
			 * rel,uri,tilte,logger_type
			 * **/
			return ajaxForwardError("发送失败！", new String[] { "loggerList",
					"logger/list", "推送历史", "0" });
		}

		if (delivery_type.equals("0")) {// 及时消息打开推送历史列表
			return ajaxForwardSuccess("发送成功！", new String[] { "loggerList",
					"logger/list", "推送历史", "0" });
		} else if (delivery_type.equals("1")) {// 定时消息打开定时列表
			return ajaxForwardSuccess("发送成功！", new String[] {
					"timerloggerList", "timerlogger/list", "定时消息", "0" });
		}

		return ajaxForwardSuccess("发送成功！", null);
	}

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public String message(HttpServletRequest request) {
		String alert = request.getParameter("m_alert");
		String[] aps = request.getParameterValues("m_aps");
		String recipient_type = request.getParameter("m_recipient_type");
		String[] device_tags = request.getParameter("m_device_tags.name")
				.split(",");
		String[] device_aliases = request.getParameter("m_device_aliases")
				.split(",");
		String[] device_registrationids = request.getParameter(
				"m_device_registrationids").split(",");
		String delivery_type = request.getParameter("m_delivery_type");
		String delivery_date = request.getParameter("m_delivery_date");// 定时需要
		String duration_time = request.getParameter("m_duration_time");
		
		String time_to_live = request.getParameter("m_time_to_live");

		boolean isCorrect = validParams(alert, aps, recipient_type,
				device_tags, device_aliases, device_registrationids,
				delivery_type, delivery_date);
		
		if (!isCorrect) {
			return ajaxForwardError("发送失败,请检查填写的数据是否有问题!", null);
		}

		if (delivery_type.equals("1")
				&& Timers.getTimers().containsKey(delivery_date)) {
			return ajaxForwardError("发送失败,这个时间已经存在定时任务了!", null);
		}
		
		boolean isSuccess = push(TYPE_MESSAGE, alert, aps, recipient_type,
				device_tags, device_aliases, device_registrationids,
				delivery_type, delivery_date, duration_time, time_to_live);

		if (!isSuccess) {
			/**
			 * rel,uri,tilte,logger_type
			 * **/
			return ajaxForwardError("发送失败！", new String[] { "loggerList",
					"logger/list", "推送历史", "1" });
		}

		if (delivery_type.equals("0")) {// 及时消息打开推送历史列表
			return ajaxForwardSuccess("发送成功！", new String[] { "loggerList",
					"logger/list", "推送历史", "1" });
		} else if (delivery_type.equals("1")) {// 定时消息打开定时列表
			return ajaxForwardSuccess("发送成功！", new String[] {
					"timerloggerList", "timerlogger/list", "定时消息", "1" });
		}

		return ajaxForwardSuccess("发送成功！", null);
	}

	private boolean push(String logger_type, String alert, String[] aps,
			String recipient_type, String[] device_tags, String[] device_alias,
			String[] device_registrationids, String delivery_type,
			String delivery_date, String duration_time, String time_to_live) {

		try {
			final com.mb.jpush.model.Logger logger = new com.mb.jpush.model.Logger();
			boolean isAndroidPlatform = false;
			boolean isIosPlatform = false;
			if (aps != null) {
				if (Arrays.toString(aps).contains("0")
						|| Arrays.toString(aps).contains("1")) {// ios
					logger.setTarget_ios(true);
					isIosPlatform = true;
				}

				if (Arrays.toString(aps).contains("2")) {// android
					logger.setTarget_android(true);
					isAndroidPlatform = true;
				}
			}
			logger.setLogger_type(logger_type);// 0-通知,1-消息
			logger.setAlert(alert);
			logger.setRecipient_type(recipient_type);
			logger.setDelivery_type(delivery_type);
			logger.setTime_to_live(time_to_live);

			// ** jpush *//
			final JPushClient jpushClient = new JPushClient(
					JPushUtils.getMasterSecret(), JPushUtils.getAppkey());
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
			if (null != recipient_type) {
				if (recipient_type.equals("0")) {// 广播
					buidler.setAudience(Audience.all());
				} else if (recipient_type.equals("1")) {// 标签
					buidler.setAudience(Audience.tag(device_tags));
				} else if (recipient_type.equals("2")) {// Alias
					buidler.setAudience(Audience.alias(device_alias));
				} else if (recipient_type.equals("3")) {// Registration ID
					buidler.setAudience(Audience
							.registrationId(device_registrationids));
				}
			}

			if (TYPE_NOTIFICATION.equals(logger_type)) {// 发通知
				buidler.setNotification(Notification.alert(alert));
			} else if (TYPE_MESSAGE.equals(logger_type)) {// 发消息
				buidler.setMessage(Message.newBuilder().setMsgContent(alert)
						.build());
			}

			final PushPayload pushPayload = buidler.build();
			pushPayload.resetOptionsTimeToLive(Long.parseLong(time_to_live));

			// 发送时间处理
			if (null != delivery_type) {
				if (delivery_type.equals("0")) {// 立即发送
					logger.setDelivery_time(DateFormatUtil.formatDate(
							DateFormatUtil.DATE_PATTERN_1, new Date()));
					try {
						PushResult result = jpushClient.sendPush(pushPayload);
						LOG.info("Got result - " + result);
						if (result.isResultOK()) {
							logger.setStatus("0");
						}
						loggerService.addLogger(logger);
					} catch (APIConnectionException e) {
						LOG.error("Connection error. Should retry later. ", e);
						logger.setStatus("1");
						logger.setReason(e.getMessage());
						loggerService.addLogger(logger);
						return false;
					} catch (APIRequestException e) {
						LOG.error(
								"Error response from JPush server. Should review and fix it. ",
								e);
						LOG.info("HTTP Status: " + e.getStatus());
						LOG.info("Error Code: " + e.getErrorCode());
						LOG.info("Error Message: " + e.getErrorMessage());
						LOG.info("Msg ID: " + e.getMsgId());
						logger.setStatus("1");
						logger.setReason(e.getErrorMessage());
						loggerService.addLogger(logger);
						return false;
					}

				} else if (delivery_type.equals("1")) {// 定时
					// 如果时间小于当前时间,或者没有设置时间,则立即推送
					if (StringUtil.isNullOrEmpty(delivery_date)
							|| (delivery_date.compareTo(DateFormatUtil
									.formatDate(DateFormatUtil.DATE_PATTERN_1,
											new Date())) < 0)) {
						delivery_date = DateFormatUtil.formatDate(
								DateFormatUtil.DATE_PATTERN_1, new Date());
					}

					logger.setDelivery_time(delivery_date);
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
								LOG.error("定时器集合：" + Timers.getTimers());
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
								LOG.error("定时器集合：" + Timers.getTimers());
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
								LOG.error("定时器集合：" + Timers.getTimers());
							}
						}

					}, DateFormatUtil.paseDate(DateFormatUtil.DATE_PATTERN_1,
							delivery_date));

					Timers.submit(delivery_date, timer);// 提交定时器
					LOG.info("定时器提交成功。。。。。。。。");
					LOG.info("定时器集合：" + Timers.getTimers());
					// 记录定时信息
					com.mb.jpush.model.TimerLogger timerLogger = new com.mb.jpush.model.TimerLogger();
					timerLogger.setAlert(logger.getAlert());
					timerLogger.setLogger_type(logger.getLogger_type());
					timerLogger.setRecipient_type(logger.getRecipient_type());
					timerLogger.setTarget_android(logger.isTarget_android());
					timerLogger.setTarget_ios(logger.isTarget_ios());
					timerLogger.setTimer_time(delivery_date);

					timerLogger.setDevice_tags(Arrays.toString(device_tags));
					timerLogger
							.setDevice_aliases(Arrays.toString(device_alias));
					timerLogger.setDevice_registrationids(Arrays
							.toString(device_registrationids));

					timerLoggerService.addTimerLogger(timerLogger);
					LOG.info("定时任务信息记录数据库成功。。。。。。。。");

				} else if (delivery_type.equals("2")) {// 定速推送

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean validParams(String alert, String[] aps,
			String recipient_type, String[] device_tags,
			String[] device_aliases, String[] device_registrationids,
			String delivery_type, String delivery_date) {
		if (StringUtil.isNullOrEmpty(alert) || aps == null || aps.length == 0
				|| null == recipient_type) {
			return false;
		}
		if (recipient_type.equals("1")
				&& (device_tags == null || device_tags.length == 0)) {// 标签
			return false;
		} else if (recipient_type.equals("2")
				&& (device_aliases == null || device_aliases.length == 0)) {// Alias
			return false;
		} else if (recipient_type.equals("3")
				&& (device_registrationids == null || device_registrationids.length == 0)) {// Registration
																							// ID
			return false;
		}
		if (delivery_type.equals("1")
				&& (StringUtil.isNullOrEmpty(delivery_date) || (delivery_date
						.compareTo(DateFormatUtil.formatDate(
								DateFormatUtil.DATE_PATTERN_1, new Date())) < 0))) {
			return false;
		}
		return true;
	}
}
