package com.nemo.kr.common;


import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CommonConfig {

	@Bean(name = "common")
	public PropertiesFactoryBean propertiesFactoryBean() {
		String profile = System.getProperty("spring.profiles.active");
		
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		ClassPathResource classPathResource = new ClassPathResource("properties/" + profile + "/common-config.properties");
		propertiesFactoryBean.setLocation(classPathResource);
		
		return propertiesFactoryBean;
	}
}
