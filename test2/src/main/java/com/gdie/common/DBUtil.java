package com.gdie.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gdie.vo.Order;

/**
 * 数据库工具类
 * 
 * @author huanghaiping
 */
@SuppressWarnings("unchecked")
public class DBUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DBUtil.class);

	/**
	 * <p>
	 * 构建排序语句，如果存在order参数，则不处理orders参数，如果不存在order参数，则orders起作用
	 * </p>
	 * <p>
	 * 返回的格式: order by id asc, name desc, ...
	 * </p>
	 * 
	 * @param order
	 * @param orders
	 * @return
	 * @author huanghaiping
	 */
	public static String buildOrders(Order order, List<Order> orders) {
		String orderString = "";
		if (order != null) {
			orderString = "order by " + order.toString();
		} else if (orders != null && orders.size() > 0) {
			orderString = "order by " + orders.get(0).toString();
			for (int i = 1; i < orders.size(); i++) {
				orderString += ", " + orders.get(i).toString();
			}
		}
		return orderString + " ";
	}

	/**
	 * 构建查询条件语句: where e.attrubuteName1 = ?1 and e.conditon2 = ?2 .....
	 * 
	 * @param conditions
	 * @return
	 * @author huanghaiping
	 */
	public static String buildCondition(Map<String, Object> conditions) {
		if (MapUtil.isEmpty(conditions)) {
			return "";
		}
		StringBuffer whereBuffer = new StringBuffer(conditions.size() * 10);
		for (String key : conditions.keySet()) {
			whereBuffer.append(buildExpression(key, conditions.get(key)));
		}
		if (whereBuffer.length() > 0) {
			whereBuffer.delete(0, 4); // 去除开始的空格和and字符串,长度为4
			whereBuffer.insert(0, "where"); // 添加where
		}
		return whereBuffer.toString();
	}

	/**
	 * <p>
	 * 根据一个key,value键值对获取表达式
	 * </p>
	 * <p>
	 * key格式有：相等：eq, 类似：like, 不相等：ne, 在集合中：in, 不在集合中:nin, 大于等于：ge， 小于等于:le,
	 * 大于:gt, 小于:lt
	 * </p>
	 * <p>
	 * 返回格式：id = ?1 and name = ?1 and ...
	 * </p>
	 * 
	 * @author 黄海平
	 * @param key
	 *            属性和条件的字符串：格式：like_name, eq_id等
	 * @return
	 */
	public static String buildExpression(String key, Object value) {
		if (StringUtils.isEmpty((key))) {
			return "";
		}
		String condition = ""; // 条件
		String attribute = ""; // 属性
		final String[] els = key.split(":");
		if (els != null && els.length > 1) { // 拆分条件和属性
			condition = els[0].trim();
			attribute = els[1].trim();
		} else {
			condition = "eq"; // 默认的条件是相等
			attribute = key.trim();
		}

		String expression = " and %s %s '%s'"; // 所有值都当作string处理
		if ("eq".equals(condition)) { // 相等
			return String.format(expression, attribute, "=", value);
		}
		if ("like".equals(condition)) {// 模糊条件
			return String.format(expression, attribute, "like", "%" + value + "%");
		}
		if ("ne".equals(condition)) {// 不相等
			return String.format(expression, attribute, "<>", value);
		}
		if ("in".equals(condition)) {// 在集合中
			return String.format(expression, attribute, "in", "(" + value + ")");
		}
		if ("nin".equals(condition)) {// 不在集合中
			String.format(expression, attribute, "not in", "(" + value + ")");
		}
		if ("ge".equals(condition)) {// 大于等于
			return String.format(expression, attribute, ">=", value);
		}
		if ("le".equals(condition)) {// 小于等于
			return String.format(expression, attribute, "<=", value);
		}
		if ("gt".equals(condition)) {// 大于
			return String.format(expression, attribute, ">", value);
		}
		if ("lt".equals(condition)) {// 小于
			return String.format(expression, attribute, "<", value);
		}
		logger.debug("参数不正确：condition: " + condition + ", key: " + key);
		return "";
	}

	/**
	 * 构建分页条件
	 * 
	 * @param conditions
	 * @param page
	 * @param rows
	 * @return
	 */
	public static String buildWhere(Map<String, Object> conditions, int page, int rows) {
		logger.debug("conditions:" + conditions);
		String where = "";
		String orderString = "";
		if (conditions != null && conditions.size() > 0) {
			// 先处理排序，再处理条件
			orderString = DBUtil.buildOrders((Order) conditions.get("order"), (List<Order>) conditions.get("orders"));
			conditions.remove("order"); // 在条件处理时必须去掉排序的信息
			conditions.remove("orders");
			where = DBUtil.buildCondition(conditions); // where条件语句
		}
		StringBuffer sb = new StringBuffer();
		sb.append(where).append(" ").append(orderString);
		if (page > 0 && rows > 0) { // 参数正确就设置分页
			sb.append("limit ").append(rows).append(" offset ").append((page - 1) * rows);
		}
		return sb.toString().trim();
	}
}
