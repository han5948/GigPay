package com.nemo.kr.common;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Config {
	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	private static final String GLOBAL_PROPERTIES_PATH =  "app-config.properties";
	
	private static CompositeConfiguration config;

	private Config() {
		
	}

	/**
	 * global.properties에 정의된 VALUE를 읽어온다.
	 * 
	 * @param prop	 *            정의된 문자열	 * @throws ConfigurationException
	 */
	public static String getProperty(String prop) throws RuntimeException {
		if (config == null) {
			try {
				System.out.println("GLOBAL_PROPERTIES_PATH: " + GLOBAL_PROPERTIES_PATH);
				config = new CompositeConfiguration();
				config.addConfiguration(new PropertiesConfiguration(GLOBAL_PROPERTIES_PATH));
			} catch (Exception e) {
				logger.error("Exception :: ", e);
			}
		}
		return config.getString(prop);
	}

	/**
	 * path{*.properties}에 정의된 VALUE를 읽어온다.
	 * 
	 * @param prop
	 *            정의된 문자열
	 * @param path
	 *            * .properties
	 */
	public static String getProperty(String prop, String path) {
		String retValue = null;
		try {
			CompositeConfiguration config = new CompositeConfiguration();
			config.addConfiguration(new PropertiesConfiguration(path));
			retValue = config.getString(prop);
			config = null;
		} catch (Exception e) {
			logger.error("Exception :: ", e);
		}
		return retValue;
	}
}
