package org.tww.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * JSON/对象转换工具类
 * 
 * @author tangweiwei
 * 
 */
public class GsonUtil {

	private static GsonBuilder builder = new GsonBuilder();

	private static Gson gson = builder.create();

	/**
	 * 设置字符串的时间格式，需要调用转换方法前设置
	 * 
	 * @param dateFormat
	 */
	public static void setDateFormat(String dateFormat) {
		builder.setDateFormat(dateFormat);
		gson = builder.create();
	}

	/**
	 * 对象转成json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	/**
	 * json字符串转成对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	/**
	 * json字符串转为对象，主要用于具有泛型的对象
	 * 
	 * @param json
	 * @param token
	 *            提供TypeToken的空子类即可,比如new TypeToken<Map<String,String>>(){}
	 * @return
	 */
	public static <T> T toObject(String json, TypeToken<T> token) {
		return gson.fromJson(json, token.getType());
	}
}
