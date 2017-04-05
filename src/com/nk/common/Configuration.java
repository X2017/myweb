package com.nk.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

public class Configuration{
	public static Map<String,String> propMap = new LinkedHashMap<String,String>();
	static{
		try{
			InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("configuration.properties");
			Properties prop = new Properties();
			prop.load(inputStream);
			Iterator<Entry<Object,Object>> it = prop.entrySet().iterator();
			while(it.hasNext()){
				Entry<Object,Object> entry = it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				propMap.put(key.toString(),value.toString());
			}
		}catch(FileNotFoundException e){
			System.out.println("文件没有找到！");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
