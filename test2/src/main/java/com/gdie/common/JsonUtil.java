package com.gdie.common;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

	/**
	 * 格式化时间输出对象
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString2(Object object) {
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
	}
}
