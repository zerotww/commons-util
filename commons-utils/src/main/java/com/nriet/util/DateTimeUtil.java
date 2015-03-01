/**
 * DateTimeUtil.java
 * @Author：Administrator
 * @CreateDate：2014-4-25
 * @Version 1.0
 * Copyright (C) 2013 NRIET
 */
package com.nriet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

/**
 * 功能描述：时间转换辅助类，对传进来的时间对象没有影响
 * 
 */
public class DateTimeUtil {

	private static ThreadLocal<SimpleDateFormat> sdfs = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat();
		};
	};

	/**
	 * 对传进来的Calendar对象没有影响
	 * 
	 * @param calendar
	 * @param flagsAndAmounts
	 *            按 字段 增量 字段 增量 ......的顺序作为参数，若个数不是2的倍数，将全部忽略
	 * @return
	 */
	public static Calendar toCalendar(Calendar calendar, int... flagsAndAmounts) {
		Calendar cal = (Calendar) calendar.clone();
		if (flagsAndAmounts != null && flagsAndAmounts.length % 2 == 0) {
			for (int i = 0; i < flagsAndAmounts.length / 2; i = i + 2) {
				cal.add(flagsAndAmounts[i], flagsAndAmounts[i + 1]);
			}
		}
		return cal;
	}

	public static Calendar toCalendar(Date date, int... flagsAndAmounts) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return toCalendar(calendar, flagsAndAmounts);
	}

	public static Calendar toCalendar(long time, int... flagsAndAmounts) {
		return toCalendar(new Date(time), flagsAndAmounts);
	}

	/**
	 * 字符串时间不能转换为时间，返回null
	 * 
	 * @param dateStr
	 * @param format
	 * @param flagsAndAmounts
	 * @return
	 */
	public static Calendar toCalendar(String dateStr, String format, int... flagsAndAmounts) {
		SimpleDateFormat sdf = sdfs.get();
		sdf.applyPattern(format);
		Date date;
		try {
			date = sdf.parse(dateStr);
			return toCalendar(date, flagsAndAmounts);
		} catch (ParseException e) {
			System.err.println("can not parse date string \"" + dateStr + "\" with format \"" + format + "\"");
			e.printStackTrace();
		}
		return null;
	}

	public static Date toDate(Calendar calendar, int... flagsAndAmounts) {
		return toCalendar(calendar, flagsAndAmounts).getTime();
	}

	public static Date toDate(Date date, int... flagsAndAmounts) {
		return toCalendar(date, flagsAndAmounts).getTime();
	}

	public static Date toDate(long time, int... flagsAndAmounts) {
		return toCalendar(time, flagsAndAmounts).getTime();
	}

	/**
	 * 字符串若是不能转换为时间，返回null
	 * 
	 * @param dateStr
	 * @param format
	 * @param flagsAndAmounts
	 * @return
	 */
	public static Date toDate(String dateStr, String format, int... flagsAndAmounts) {
		Calendar calendar = toCalendar(dateStr, format, flagsAndAmounts);
		if (calendar != null)
			return calendar.getTime();
		return null;
	}

	public static long toLong(Calendar calendar, int... flagsAndAmounts) {
		return toCalendar(calendar, flagsAndAmounts).getTimeInMillis();
	}

	public static long toLong(Date date, int... flagsAndAmounts) {
		return toCalendar(date, flagsAndAmounts).getTimeInMillis();
	}

	public static long toLong(long time, int... flagsAndAmounts) {
		return toCalendar(time, flagsAndAmounts).getTimeInMillis();
	}

	/**
	 * 字符串若是不能转换为时间，返回0
	 * 
	 * @param dateStr
	 * @param format
	 * @param flagsAndAmounts
	 * @return
	 */
	public static long toLong(String dateStr, String format, int... flagsAndAmounts) {
		Calendar calendar = toCalendar(dateStr, format, flagsAndAmounts);
		if (calendar != null)
			return calendar.getTimeInMillis();
		return 0;
	}

	public static String toString(Calendar calendar, String format, int... flagsAndAmounts) {
		Calendar cal = toCalendar(calendar, flagsAndAmounts);
		SimpleDateFormat sdf = sdfs.get();
		sdf.applyPattern(format);
		return sdf.format(cal.getTime());

	}

	public static String toString(Date date, String format, int... flagsAndAmounts) {
		return toString(toCalendar(date, flagsAndAmounts), format);
	}

	public static String toString(long time, String format, int... flagsAndAmounts) {
		return toString(new Date(time), format, flagsAndAmounts);
	}

	/**
	 * 时间字符串若是不能转换为时间，则返回null
	 * 
	 * @param dateStr
	 * @param format
	 * @param flagsAndAmounts
	 * @return
	 */
	public static String toString(String dateStr, String format, int... flagsAndAmounts) {
		Date date = toDate(dateStr, format, flagsAndAmounts);
		if (date != null)
			return toString(date, format);
		return null;
	}

	public static String toString(String oldDateStr, String oldDateFormat, String newDateFormat, int... flagsAndAmounts) {
		Date date = toDate(oldDateStr, oldDateFormat, flagsAndAmounts);
		if (date != null)
			return toString(date, newDateFormat);
		return null;
	}

	/**
	 * 更改Calendar各个字段的值
	 * 
	 * @param calendar
	 * @param flagsAndValues
	 */
	public static void setCalendar(Calendar calendar, int... flagsAndValues) {
		if (flagsAndValues != null) {
			for (int i = 0; i < flagsAndValues.length / 2; i = i + 2) {
				calendar.set(flagsAndValues[i], flagsAndValues[i + 1]);
			}
		}
	}

	/**
	 * 获取指定年月的最后一天<br />
	 * month从0开始,返回的day从1开始
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDayOfMonth(int year, int month) {
		int lastDay;
		int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int[] days2 = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (year % 100 == 0) {
			if (year % 4 == 0) {
				lastDay = days2[month];
			} else {
				lastDay = days[month];
			}
		} else {
			if (year % 4 == 0) {
				lastDay = days2[month];
			} else {
				lastDay = days[month];
			}
		}
		return lastDay;
	}

	/**
	 * 格式化字符串为时间,若是没有年月日则取今天的年月日
	 * 
	 * @param value
	 * @param format
	 * @return
	 */
	public DateTime getDateTime(String value, String format) {
		DateTime dateTime = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		DateTime dt = DateTime.parse(value, DateTimeFormat.forPattern(format));
		if (format.indexOf("y") < 0) {// 没有年则取今年
			dt = dt.withYear(dateTime.getYear());
		}
		if (format.indexOf("M") < 0) {// 没有月则取本月
			dt = dt.withMonthOfYear(dateTime.getMonthOfYear());
		}
		if (format.indexOf("d") < 0) {// 没有日则去今天
			dt = dt.withDayOfMonth(dateTime.getDayOfMonth());
		}
		return dt;
	}

	/**
	 * timeZoneID为GMT时为世界时
	 * 
	 * @param value
	 * @param format
	 * @param timeZoneID
	 * @return
	 */
	public static DateTime getDateTime(String value, String format, String timeZoneID) {
		DateTime dateTime = DateTime.now(DateTimeZone.forID(timeZoneID)).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		DateTime dt = DateTime.parse(value, DateTimeFormat.forPattern(format));
		if (format.indexOf("y") < 0) {
			dt = dt.withYear(dateTime.getYear());
		}
		if (format.indexOf("M") < 0) {
			dt = dt.withMonthOfYear(dateTime.getMonthOfYear());
		}
		if (format.indexOf("d") < 0) {
			dt = dt.withDayOfMonth(dateTime.getDayOfMonth());
		}
		return dt;
	}
}
