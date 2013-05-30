package com.gdie.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>快速获取spring容器的对象bean,增加的bean对象可以在这里注册 </p>
 * <p>要是该类起作用，必须在spring配置文件中注册该bean或者纳入bean的扫描范围内才有效</p>
 * 
 * @author huanghaiping
 */
public final class SpringUtil implements ApplicationContextAware {
	/**
	 * spring应用程序上下文
	 */
	private static ApplicationContext context;

	/**
	 * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (context == null) {
			context = applicationContext;
		}
	}

	/**
	 * 这是一个便利的方法，帮助我们快速得到一个BEAN, 推荐使用getBean(class)方法
	 * 
	 * @param beanName 需要获取的bean名称
	 * @return 
	 * @author huanghaiping
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 使用泛型获取bean， 该bean对象必须在spring中注册
	 * 
	 * @param <T> 泛型类型定义
	 * @param clazz 需要获取bean的类
	 * @return 
	 * @author huanghaiping
	 */
	public static <T> T getBean(Class<T> clazz) {
		return (T) getBean(StringUtils.uncapitalize(clazz.getSimpleName()));
	}
}
