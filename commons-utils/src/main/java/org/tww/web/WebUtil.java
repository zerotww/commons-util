package org.tww.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static ServletContext getServletContext(ServletRequest request) {
		return ((HttpServletRequest) request).getSession().getServletContext();
	}

	/**
	 * 获取项目的真实路径(绝对路径)
	 * 
	 * @param request
	 * @return
	 */
	public static String getRootPath(ServletRequest request) {
		String rootPath = getServletContext(request).getRealPath("/");
		return rootPath;
	}

	/**
	 * 获取项目的路径(相对路径)
	 * 
	 * @param request
	 * @return
	 */
	public static String getWebRoot(ServletRequest request) {
		String webRoot = getServletContext(request).getContextPath();
		return webRoot;
	}

	/**
	 * 获取Web.xml文件中的全局参数
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public static String getWebXmlParamValue(HttpServletRequest request, String paramName) {
		String paramValue = getServletContext(request).getInitParameter(paramName);
		return paramValue;
	}
}
