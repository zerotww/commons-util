package org.tww.json;

import net.sf.json.JSONObject;

public class NetsfJsonUtil {

	public static String toJson(Object object) {
		return JSONObject.fromObject(object).toString();
	}

	public static String toObject(String json) {
		return null;
	}

	public static void main(String[] args) {
	}
}
