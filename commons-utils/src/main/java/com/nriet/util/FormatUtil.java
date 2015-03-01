package com.nriet.util;

public class FormatUtil {

	/**
	 * SimpleDateFormat#format()所用的格式转换为String#format()所用的格式,不会对原格式产生影响
	 * 
	 * @param dateFormat
	 * @param index
	 * @return
	 */
	public static String dateFormatToStringFormat(String dateFormat, int index) {
		dateFormat = dateFormat.replaceAll("yyyy", "%" + index + "\\$tY");
		dateFormat = dateFormat.replaceAll("MM", "%" + index + "\\$tm");
		dateFormat = dateFormat.replaceAll("dd", "%" + index + "\\$td");
		dateFormat = dateFormat.replaceAll("HH", "%" + index + "\\$tH");
		dateFormat = dateFormat.replaceAll("mm", "%" + index + "\\$tM");
		dateFormat = dateFormat.replaceAll("ss", "%" + index + "\\$tS");
		return dateFormat.replaceAll("SSS", "%" + index + "$tL");
	}

	public static void main(String[] args) {
		String dateFormat = "NAFP_GFS_yyyyMMddHHmmsss_%2$03d.grib2";
		String result = dateFormatToStringFormat(dateFormat, 1);
		System.out.println(result);
	}
}
