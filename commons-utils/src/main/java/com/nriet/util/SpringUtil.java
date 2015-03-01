package com.nriet.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @文件名: SpringUtil.java
 * @作者:tangweiwei
 * @日期:2014-9-24
 * @版本信息:
 * @版权:
 * @描述:spring工具类
 */
public class SpringUtil {

	public static String filename = "spring.xml";

	private static Map<String, ApplicationContext> contexts = new HashMap<String, ApplicationContext>(1);

	/**
	 * 获取spring的ApplicationContext对象
	 * 
	 * @param fileName
	 *            spring配置文件路径
	 * @return
	 */
	public static ApplicationContext getApplicationContext(String fileName) {
		if (contexts.containsKey(fileName)) {
			return contexts.get(fileName);
		} else {
			ApplicationContext ac = new ClassPathXmlApplicationContext(fileName);
			contexts.put(fileName, ac);
			return ac;
		}
	}

	/**
	 * 获取bean
	 * 
	 * @param fileName
	 *            spring配置文件
	 * @param beanName
	 *            bean名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String fileName, String beanName) {
		return (T) getApplicationContext(fileName).getBean(beanName);
	}

	/**
	 * 获取bean
	 * 
	 * @param fileName
	 *            spring配置文件
	 * @param clazz
	 *            要取的bean的类型
	 * @return
	 */
	public static <T> T getBean(String fileName, Class<T> clazz) {
		return getApplicationContext(fileName).getBean(clazz);
	}

	/**
	 * 若是设置了filename属性,可以直接获取bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static <T> T getBean(String beanName) {
		return getBean(filename, beanName);
	}

	/**
	 * 若是设置了filename属性,可以直接获取bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getBean(filename, clazz);
	}

	/**
	 * 获取单个bean,用在提供的class有子类实例的时候
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getSingleBean(Class<T> clazz) {
		Map<String, T> beanMap = getApplicationContext(filename).getBeansOfType(clazz);
		for (T t : beanMap.values()) {
			if (t.getClass() == clazz) {
				return t;
			}
		}
		return null;
	}

	/**
	 * 获取指定类型的所有bean,包括该类型的子类或者实现类的bean
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> getBeans(Class<T> clazz) {
		Map<String, T> beanMap = getApplicationContext(filename).getBeansOfType(clazz);
		List<T> result = new ArrayList<T>();
		for (T t : beanMap.values()) {
			result.add(t);
		}
		return result;
	}
}
