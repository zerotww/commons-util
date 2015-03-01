package com.nriet.util;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import sun.misc.Launcher;

public class ClassLoaderUtil {

	private static Field classes;

	private static Method addURL;
	static {
		try {
			classes = ClassLoader.class.getDeclaredField("classes");
			addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		} catch (Exception e) {
			e.printStackTrace();
		}
		classes.setAccessible(true);
		addURL.setAccessible(true);
	}

	private static URLClassLoader system = (URLClassLoader) getSystemClassLoader();

	private static URLClassLoader ext = (URLClassLoader) getExtClassLoader();

	public static ClassLoader getSystemClassLoader() {
		return ClassLoader.getSystemClassLoader();
	}

	public static ClassLoader getExtClassLoader() {
		return getSystemClassLoader().getParent();
	}

	/**
	 * 获得加载的类
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getClassesLoadedBySystemClassLoader() {
		return getClassesLoadedByClassLoader(getSystemClassLoader());
	}

	@SuppressWarnings("rawtypes")
	public static List getClassesLoadedByExtClassLoader() {
		return getClassesLoadedByClassLoader(getExtClassLoader());
	}

	@SuppressWarnings("rawtypes")
	public static List getClassesLoadedByClassLoader(ClassLoader cl) {
		try {
			return (List) classes.get(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static URL[] getSystemURLs() {
		return system.getURLs();
	}

	private static void list(PrintStream ps, URL[] classPath) {
		for (int i = 0; i < classPath.length; i++) {
			ps.println(classPath[i]);
		}
	}

	public static void listBootstrapClassPath() {
		listBootstrapClassPath(System.out);
	}

	public static void listBootstrapClassPath(PrintStream ps) {
		ps.println("BootstrapClassPath:");
		list(ps, getBootstrapClassPath());
	}

	public static void listSystemClassPath() {
		listSystemClassPath(System.out);
	}

	public static void listSystemClassPath(PrintStream ps) {
		ps.println("SystemClassPath:");
		list(ps, getSystemClassPath());
	}

	public static void listExtClassPath() {
		listExtClassPath(System.out);
	}

	public static void listExtClassPath(PrintStream ps) {
		ps.println("ExtClassPath:");
		list(ps, getExtClassPath());
	}

	public static URL[] getBootstrapClassPath() {
		return getBootstrapURLs();
	}

	/**
	 * jre/目录下的核心库下的jar包,这个方法调用可能需要使用JDK1.6
	 * 
	 * @return
	 */
	public static URL[] getBootstrapURLs() {
		return Launcher.getBootstrapClassPath().getURLs();
	}

	public static void main(String[] args) {
		URL[] urls = getBootstrapURLs();
		for (URL url : urls) {
			System.out.println(url);
		}
	}

	/**
	 * 加载CLASSPATH路径下的包
	 * 
	 * @return
	 */
	public static URL[] getSystemClassPath() {
		return getSystemURLs();
	}

	/**
	 * 加载/jre/lib/ext/目录下的扩展包
	 * 
	 * @return
	 */
	public static URL[] getExtClassPath() {
		return getExtURLs();
	}

	public static URL[] getExtURLs() {
		return ext.getURLs();
	}

	public static void addURL2SystemClassLoader(URL url) {
		try {
			addURL.invoke(system, new Object[] { url });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addURL2ExtClassLoader(URL url) {
		try {
			addURL.invoke(ext, new Object[] { url });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 如果是jar包则给定jar包文件路径，如果是class文件则给定目录
	 * 
	 * @param path
	 */
	public static void addClassPath(String path) {
		addClassPath(new File(path));
	}

	/**
	 * 如果是jar包则给定jar包文件路径，如果是class文件则给定目录
	 * 
	 * @param path
	 */
	public static void addExtClassPath(String path) {
		addExtClassPath(new File(path));
	}

	/**
	 * 如果是jar包则给定jar包文件路径，如果是class文件则给定目录
	 * 
	 * @param jarOrDir
	 */
	public static void addClassPath(File jarOrDir) {
		try {
			addURL2SystemClassLoader(jarOrDir.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 如果是jar包则给定jar包文件路径，如果是class文件则给定目录
	 * 
	 * @param jarOrDir
	 */
	public static void addExtClassPath(File jarOrDir) {
		try {
			addURL2ExtClassLoader(jarOrDir.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
