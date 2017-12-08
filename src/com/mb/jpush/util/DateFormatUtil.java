package com.mb.jpush.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {
	/**
	 * 日期格式
	 * */
	public static final String DATE_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN_2 = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_PATTERN_3 = "yyyy-MM-dd";
	public static final String DATE_PATTERN_4 = "yyyy/MM/dd";
	public static final String DATE_PATTERN_5 = "HH:mm:ss";

	/***
	 * 根据指定格式，获取当前时间字符串
	 * 
	 * @return pattern time
	 * */
	public static String getCurrDate(String pattern) {
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		return fmt.format(date);
	}

	/**
	 * 将字符串根据格式解析为时间
	 * 
	 * @param pattern
	 *            yyyy-MM-dd hh:mm:ss
	 * @param st
	 *            "2014-03-06 13:06:22"
	 * @return date
	 * **/
	public static Date paseDate(String pattern, String st) {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(pattern);
			return fmt.parse(st);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 将日期格式化指定字符串形式
	 * 
	 * @return string
	 * */
	public static String formatDate(String pattern, Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		return fmt.format(date);
	}

	/**
	 * 返回年月：201304
	 * 
	 * @return
	 */
	public static String getCurrYM() {
		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		return year + "" + (String) (month < 10 ? ("0" + month) : month);
	}

	/**
	 * 得到指定日期
	 * */
	public static String getYMD(int amount) {
		Calendar cal = Calendar.getInstance();// 使用日历类
		cal.add(Calendar.DAY_OF_MONTH, amount);// 得到前5
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return year + "-" + (String) (month < 10 ? ("0" + month) : month + "") +
				"-" + (String) (day < 10 ? ("0" + day) : day + "");
	}
}
