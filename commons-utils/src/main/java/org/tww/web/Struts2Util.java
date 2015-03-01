package org.tww.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class Struts2Util {

	public static ServletContext getServletContext() {
		return (ServletContext) ActionContext.getContext().get(ServletActionContext.SERVLET_CONTEXT);
	}

	public static HttpSession getSession() {
		return (HttpSession) ActionContext.getContext().get(ServletActionContext.SESSION);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	}
}
