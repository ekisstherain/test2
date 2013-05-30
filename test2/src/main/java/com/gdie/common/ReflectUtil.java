package com.gdie.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 反射工具类
 * 
 * @author huanghaiping
 */
public class ReflectUtil {

	/**
	 * 日志对象
	 */
	protected final static Log log = LogFactory.getLog(ReflectUtil.class);

	/**
	 * <p>把object对象非collection类型的属性转换成string输出 </p>
	 * <p>返回格式为：name1:value1， name2:value2,剔除value值为空和null的情况</p>
	 * @param object
	 * @return 
	 * @author huanghaiping
	 */
	public static String convertFieldToString(Object object) {
		StringBuffer stringBuffer = new StringBuffer();
		Field[] fields = object.getClass().getDeclaredFields();
		if (fields.length > 0) {
			Field field = fields[0];
			if (!Collection.class.isAssignableFrom(field.getType())) {
				Object value = getValue(field, object);
				// 剔除是空值的属性
				if (isNeedToConvert(value)) {
					stringBuffer.append(field.getName()).append(":").append(getValue(field, object)); // 处理第一个field
				}
			}

			// 处理剩余的field
			for (int i = 1; i < fields.length; i++) {
				field = fields[i];
				// 剔除那些是数组的属性
				if (!Collection.class.isAssignableFrom(field.getType())) {
					Object value = getValue(field, object);
					// 剔除是空值的属性
					if (isNeedToConvert(value)) {
						stringBuffer.append(", ").append(field.getName()).append(":").append(value);
					}
				}
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 判断 value是否必须转换
	 * 
	 * @param value
	 * @return 
	 * @author huanghaiping
	 */
	public static Boolean isNeedToConvert(Object value) {
		if (value instanceof String && value.toString().length() > 0) {
			return true;
		}
		if (value instanceof Double) {
			return true;
		}
		if (value instanceof Long) {
			return true;
		}
		if (value instanceof Integer) {
			return true;
		}
		if (value instanceof Boolean) {
			return true;
		}
		if (value instanceof Date) {
			return true;
		}
		return false;
	}

	/**
	 * 获取object的某一个field的值
	 * 
	 * @param field 要获取的属性
	 * @param object 被获取属性值的对象
	 * @return 属性值
	 * @author huanghaiping
	 */
	protected static Object getValue(Field field, Object object) {
		field.setAccessible(true); // 设置私有属性可访问
		try {
			return field.get(object);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 收集类的所有字段
	 * 
	 * @param clazz 要搜集的类
	 * @return 
	 * @author huanghaiping
	 */
	public static List<Field> getFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		do {
			for (Field field : clazz.getDeclaredFields()) {
				fields.add(field);
			}
			clazz = clazz.getSuperclass();

		} while (clazz.getSuperclass() == Object.class);

		return fields;
	}

	/**
	 * 收集类的所有字段名称
	 * 
	 * @param clazz 要搜集的类
	 * @return 
	 */
	public static List<String> getFieldNameList(Class clazz) {
		List<Field> fields = getFields(clazz);
		List<String> fieldNames = new ArrayList<String>();
		for (Field field : fields) {
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}

	/**
	 * 调用对象的方法, 返回-1表示出现错误
	 * 
	 * @param object 被调用对象
	 * @param methodName 调用方法名
	 * @param args 调用方法需要的参数
	 * @return 返回调用结果
	 * @author huanghaiping
	 */
	public static Object invokeMethod(Object object, String methodName, Object... args) {
		Class clazz = object.getClass();
		Method method = null;
		// 有参数方法
		if (args != null && args.length > 0) {
			Class[] argsClass = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argsClass[i] = args[i].getClass();
			}
			try {
				method = clazz.getMethod(methodName, argsClass);
				return method.invoke(object, args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		} else {
			try {
				method = clazz.getMethod(methodName, new Class[0]);
				return method.invoke(object, new Object[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
	}

	/**
	 * 获得子类的泛型父类参数的实质类型
	 * 
	 * @param clazz 子类
	 * @param index 父类泛型参数索引
	 * @return 实质类型
	 * @author huanghaiping
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass(); // 得到泛型父类
		// 如果没有实现ParameterizedType, 即不支持泛型，直接返回object的class
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("你输入的索引:" + (index < 0 ? "不能小于0!" : "超出了参数总数"));
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
	 * 
	 * @param field field字段
	 * @param index 泛型参数索引
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 * @author huanghaiping
	 */
	@SuppressWarnings("unchecked")
	public static Class getFieldGenericType(Field field, int index) {
		Type genericFieldType = field.getGenericType();
		if (genericFieldType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericFieldType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			if (index >= fieldArgTypes.length || index < 0) {
				throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class) fieldArgTypes[index];
		}
		return Object.class;
	}
	
	/**
	 * 获取泛型对象参数实例, 如果clazz不支持泛型，则返回null，比如aaa<T>, 获取的是T示例，参数是aaa对象的class
	 * 
	 * @param clazz 具体继承泛型类的子类
	 * @return 
	 * @author huanghaiping
	 */
	public static Object getGenericInstance(Class clazz) {
		Type genType = clazz.getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			try {
				return ((Class) params[0]).newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.debug("实例化泛型T失败!");
				e.printStackTrace();
				return null;
			}
		}
		return null; // 不支持泛型，返回null
	}
}
