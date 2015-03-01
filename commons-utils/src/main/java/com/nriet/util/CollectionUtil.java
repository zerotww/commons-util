package com.nriet.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class CollectionUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CollectionUtil.class);

	public static <K, V> void printMap(Map<K, V> map, String seperator) {
		for (Entry<K, V> entry : map.entrySet()) {
			System.out.println(entry.getKey() + seperator + entry.getValue());
		}
	}

	public static <K, V> String mapToString(Map<K, V> map, String seperator) {
		StringBuffer sb = new StringBuffer();
		for (Entry<K, V> entry : map.entrySet()) {
			sb.append(entry.getKey() + seperator + entry.getValue() + "\n");
		}
		return sb.toString();
	}

	public static <K, V> void logMap(Map<K, V> map, String seperator) {
		for (Entry<K, V> entry : map.entrySet()) {
			logger.info(entry.getKey() + seperator + entry.getValue());
		}
	}

	public static <K, V> Map<K, V> createMap(K[] ks, V[] vs) {
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < ks.length; i++) {
			map.put(ks[i], vs[i]);
		}
		return map;
	}
}
