package org.tww.web;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebSpringUtil {

	/**
	 * web应用中配置Spring的org.springframework.web.context.
	 * ContextLoaderListener监听器时用来获取ApplicationContext的方法
	 * 
	 * @param context
	 * @return
	 */
	public static WebApplicationContext getWebApplicationContext(ServletContext context) {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}
}
