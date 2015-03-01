package com.nriet.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class ConfigurationUtil {

	public static XMLConfiguration configuration;

	static {
		try {
			configuration = new XMLConfiguration(ClassPathUtil.getFileFromClassPath("config.xml"));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

}
