package com.gdie.common;

import java.util.Map;

/**
 * map集合工具类
 * 
 * @author 黄海平
 */
@SuppressWarnings("rawtypes")
public class MapUtil {

	/**
	 * 判断是否为空, 为空返回true
	 * 
	 * @author 黄海平
	 * @createDate 2012-11-5 上午11:12:08
	 * @param map
	 * @return
	 */
	public static Boolean isEmpty(Map map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 判断是否有数据, 有返回true
	 * 
	 * @author 黄海平
	 * @createDate 2012-11-5 上午11:12:08
	 * @param map
	 * @return
	 */
	public static Boolean hasSize(Map map) {
		return !isEmpty(map);
	}
}
