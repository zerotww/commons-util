package com.nriet.util;

import java.lang.reflect.Field;

public class ObjectUtils {

	public static boolean isEqual(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return obj1.equals(obj2);
	}

	public static void printProperty(Object obj) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				System.out.println(field.getName() + ":" + field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkPropertyNull(Object obj) {
		
	}	
}
