package com.gdie.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>配置文件config.properties获取属性值公共类</p>
 * <p>config.properties位于classpath根目录下面</p>
 * @author zhouwei
 *
 */
public class ConfigUtil {

	/**
	 * 加载配置文件对象
	 */
	private final static Properties prop = loadProperties();
	/**
	 * 需要加载的配置文件名称。如：/config.properties
	 */
	private final static String propFileName = "/config.properties";
	
	/**
	 * <b>功能</b>：加载config.properties配置文件，将配置文件加载到Properties对象。
	 * @return 
	 * @author chenchunrong
	 */
	private static Properties loadProperties(){
		InputStream input = null;
		Properties prop=null;
		try {
			input = ConfigUtil.class.getResourceAsStream(propFileName);
			prop = new Properties();
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e){
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * <p>获取配置文件的属性值</p>
	 * <p>如果配置文件不存在，则返回null</p>
	 * @param property
	 * @return
	 */
	public static String getProperty(String property){
		if(prop==null) return null;
		return prop.getProperty(property);
	}
}
