package com.gdie.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具
 * 
 * @author huanghaiping
 */
public class DateUtil {

	/**
	 * 日期格式: yyyy-MM-dd
	 */
	public static final String DATE = "yyyy-MM-dd";

	/**
	 * 时间格式: yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 默认时区（中国时区）
	 */
	private static Locale defaultLocale = Locale.CHINA;

	/**
	 * 改变默认时区
	 * 
	 * @param defaultLocale
	 */
	public static void setDefaultLocale(Locale defaultLocale) {
		DateUtil.defaultLocale = defaultLocale;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return 
	 * @author huanghaiping
	 */
	public static java.util.Date now() {
		Calendar cal = Calendar.getInstance(defaultLocale);
		return cal.getTime();
	}

	/**
	 * 从现在起n年后的时间
	 * 
	 * @param n 年数
	 * @return 
	 * @author huanghaiping
	 */
	public static java.util.Date nextYear(int n) {
		Calendar cal = Calendar.getInstance(defaultLocale);
		cal.add(Calendar.YEAR, n);
		return cal.getTime();
	}

	/**
	 * 从现在起n天后的时间
	 * 
	 * @param 天数
	 * @return 
	 * @author huanghaiping
	 */
	public static java.util.Date nextDay(int n) {
		Calendar cal = Calendar.getInstance(defaultLocale);
		cal.add(Calendar.DAY_OF_MONTH, n);
		return cal.getTime();
	}

	/**
	 * 从某个时候起n年后的时间
	 * 
	 * @param value 某个时候
	 * @param n 年数
	 * @return 
	 * @author huanghaiping
	 */
	public static java.util.Date nextYear(java.util.Date value, int n) {
		Calendar cal = Calendar.getInstance(defaultLocale);
		cal.setTime(value);
		cal.add(Calendar.YEAR, n);
		return cal.getTime();
	}

	/**
	 * 从某个时候起n天后的时间
	 * 
	 * @param value 某个时候
	 * @param n  天数
	 * @return 
	 * @author huanghaiping
	 */
	public static java.util.Date nextDay(java.util.Date value, int n) {
		Calendar cal = Calendar.getInstance(defaultLocale);
		cal.setTime(value);
		cal.add(Calendar.DAY_OF_MONTH, n);
		return cal.getTime();
	}

	/**
	 * 现在，日期字符串，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 返回当前时间的字符串
	 * @author huanghaiping
	 */
	public static java.lang.String nowString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", defaultLocale);
		return sdf.format(now());
	}

	/**
	 * 现在
	 * 
	 * @param pattern 日期字符串格式
	 * @return 日期字符串
	 * @author huanghaiping
	 */
	public static java.lang.String nowString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, defaultLocale);
		return sdf.format(now());
	}

	/**
	 * 把现在日期转换为字符串，格式为 yyyy-MM-dd
	 * 
	 * @return
	 * @author huanghaiping
	 */
	public static java.lang.String dateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", defaultLocale);
		return sdf.format(now());
	}

	/**
	 * 获取当前日期的年月，格式为:yyyy-MM
	 * 
	 * @return
	 * @author huanghaiping
	 */
	public static java.lang.String yearMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", defaultLocale);
		return sdf.format(now());
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param value 某个时候
	 * @param pattern 日期字符串格式
	 * @return 日期字符串
	 * @author huanghaiping
	 */
	public static java.lang.String dateString(java.util.Date value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, defaultLocale);
		return sdf.format(value);
	}

	/**
	 * 把日期转换为字符串,默认格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param value 日期
	 * @return
	 * @author huanghaiping
	 */
	public static java.lang.String dateString(java.util.Date value) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME, defaultLocale);
		return sdf.format(value);
	}

	/**
	 * 把日期转换为字符串,默认格式：yyyy-MM-dd
	 * 
	 * @param value 日期
	 * @return
	 * @author huanghaiping
	 */
	public static java.lang.String dateString2(java.util.Date value) {
		if(value == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE, defaultLocale);
		return sdf.format(value);
	}

	/**
	 * 把字符串转换为日期
	 * 
	 * @param value 日期字符串
	 * @param pattern 日期字符串格式
	 * @return java.util.Date
	 * @author huanghaiping
	 */
	public static java.util.Date stringDate(String value, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, defaultLocale);
			return sdf.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 把日期的时分秒毫秒截掉，只保留年月日
	 * 
	 * @param value 日期
	 * @return 只保留年月日的日期
	 * @author huanghaiping
	 */
	public static java.util.Date trimHMS(java.util.Date value) {
		Calendar c = Calendar.getInstance(defaultLocale);
		c.setTime(value);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 返回当前日期：yyyy-MM-dd
	 * 
	 * @return
	 * @author huanghaiping
	 */
	public static Date currentDate() {
		Calendar c = Calendar.getInstance(defaultLocale);
		c.setTime(now());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 返回当前时间：yyyy-MM-dd HH:mm:ss, 去掉了微秒
	 * 
	 * @return
	 * @author huanghaiping
	 */
	public static Date currentTime() {
		Calendar c = Calendar.getInstance(defaultLocale);
		c.setTime(now());
		c.set(Calendar.MILLISECOND, 0); // 去掉微秒
		return c.getTime();
	}
	
	/**
	 * 获取当前年份
	 * 
	 * @author hhp
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar c = Calendar.getInstance(defaultLocale);
		return c.get(Calendar.YEAR);
	}
}
