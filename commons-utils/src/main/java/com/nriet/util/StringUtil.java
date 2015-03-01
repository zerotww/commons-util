package com.nriet.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String initCaps(String str) {
		char[] chars = str.toCharArray();
		chars[0] = (char) (chars[0] & 0xDF);
		return new String(chars);
	}

	/**
	 * 拼接字符串
	 * 
	 * @param str1
	 * @param other
	 * @return
	 */
	public static String concat(String str1, String... other) {
		String str = str1;
		for (String s : other) {
			str += s;
		}
		return str;
	}

	public static String decode(String str) throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(str, "utf-8");
	}

	public static boolean containsIngoreCase(List<String> list, String str) {
		for (String s : list)
			if (s.equalsIgnoreCase(str))
				return true;
		return false;
	}

	/**
	 * list转成String
	 * 
	 * @param list
	 * @param seperator
	 *            每个元素之间的分隔符
	 * @return
	 */
	public static String listToString(List<?> list, String seperator) {
		StringBuffer sb = new StringBuffer();
		for (Object object : list) {
			sb.append(object.toString() + seperator);
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public static String arrayToString(String[] arr, String seperator) {
		String result = "";
		for (String string : arr) {
			result += string + seperator;
		}
		return result.substring(0, result.length() - seperator.length());
	}

	/**
	 * 获取固定长度的字符串，长度过程则截取前面部分，长度不够则后面补指定的字符
	 * 
	 * @param source
	 * @param length
	 * @param symbol
	 * @return
	 */
	public static String getFixedString(String source, int length, String symbol) {
		if (source.length() > length)
			return source.substring(0, length);
		if (source.length() < length) {
			int srcLength = source.length();
			for (int i = 0; i < length - srcLength; i++) {
				source += symbol;
			}
		}
		return source;
	}

	/**
	 * 去除小数部分末尾的零
	 * 
	 * @param num
	 * @return
	 */
	public static String removeZero(String num) {
		if (num.indexOf(".") > 0) {
			char[] chars = num.toCharArray();
			for (int i = chars.length - 1; i >= 0; i--) {
				if (chars[i] != '0') {
					num = num.substring(0, i + 1);
					if (num.endsWith("."))
						num = num.substring(0, num.length() - 1);
					break;
				}
			}
		}
		return num;
	}

	/**
	 * 获取指定的精度，未做四舍五入处理
	 * 
	 * @param num
	 * @param precision
	 * @return
	 */
	public static String getFixedPrecision(Number num, int precision) {
		String result = num.toString();
		if (num instanceof Float || num instanceof Double) {
			String[] arr = result.split("\\.");
			if (arr.length <= 1) {// 原始数据没有小数部分
				if (precision > 0) {// 输出要小数部分
					result += ".";
					for (int i = 0; i < precision; i++) {
						result += "0";
					}
				}
			} else {// 原始数据有小数部分
				if (precision == 0) {// 输出不要小数部分
					return arr[0];
				} else if (precision > 0 && arr[1].length() > precision) {// 输出部分要小数但是原始数据的小数部分精度比输出的要高
					result = arr[0] + "." + arr[1].substring(0, precision);
				} else if (precision > 0 && arr[1].length() <= precision) {// 输出部分要小数但是原始数据的小数部分精度比输出的要低
					result = arr[0] + "." + arr[1];
					for (int i = 0; i < precision - arr[1].length(); i++) {
						result += "0";
					}
				}
			}
		}
		return result;
	}

	/**
	 * 批量格式化字符串
	 * 
	 * @param template
	 * @param values
	 * @return
	 */
	public static List<String> fillTemplate(String template, List<String> values) {
		List<String> result = new ArrayList<String>();
		for (String value : values)
			result.add(String.format(template, value));
		return result;
	}

	/**
	 * 判断两个字符串是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1, String str2) {
		if (str1 == null || str2 == null)
			return false;
		return str1.equals(str2);
	}

	/**
	 * 判断两个字符串是否相等，忽略大小写
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqualsIgnoreCase(String str1, String str2) {
		if (str1 == null || str2 == null)
			return false;
		return str1.equalsIgnoreCase(str2);
	}

}
