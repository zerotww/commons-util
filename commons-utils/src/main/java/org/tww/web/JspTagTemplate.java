package org.tww.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 还需要编写TLD文件才可以在JSP文件中使用
 * 
 * @author Administrator
 *
 */
public class JspTagTemplate extends SimpleTagSupport {

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void doTag() throws JspException, IOException {
		this.getJspContext().getOut().print(this.username + ":" + this.password);
		super.doTag();
	}
}
