package com.ffzx.cas.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesLoader extends PropertyPlaceholderConfigurer {

	private final static Map<String, String> propMaps = new HashMap<>();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {

		super.processProperties(beanFactoryToProcess, props);

		for (Entry<Object, Object> entry : props.entrySet()) {
			String stringKey = String.valueOf(entry.getKey());
			String stringValue = String.valueOf(entry.getValue());
			propMaps.put(stringKey, stringValue);
		}

	}
	
	public static String getProperty(String key){
		return propMaps.get(key);
	}
}
