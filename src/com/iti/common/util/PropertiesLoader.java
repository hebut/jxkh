package com.iti.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class PropertiesLoader {
	
	private static Map<String,Properties>properties=new HashMap<String,Properties>();
	
	public static Properties loader(String name,String path){
		
		if(properties==null){
			properties=new HashMap<String,Properties>();
		}
		
		ClassPathResource resource=new ClassPathResource(path);
		Properties pros=new Properties();
		try{
			pros.load(resource.getInputStream());
			properties.put(name, pros);
			return pros;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ȡ���� Properties�����ļ�Map
	 * @return
	 */
	public static Map<String, Properties> getProperties() {
		return properties;
	}
	/**
	 * ��ȡ�����ļ�
	 * @param fileName
	 * �ļ����ƣ���������չ��
	 * @return
	 */
	public static Properties getPropertiesByName(String fileName){
		return properties.get(fileName);
	}
	public static String getPropertiesValue(String key,String fileName ){
		return properties.get(fileName).getProperty(key);
	}
}
