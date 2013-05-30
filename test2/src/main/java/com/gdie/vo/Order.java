package com.gdie.vo;

import java.io.Serializable;

/**
 * 属性排序定义
 * 
 * @author HHP
 * 
 */
@SuppressWarnings("serial")
public class Order implements Serializable {

	private boolean ascending;
	private String propertyName;

	public String toString() {
		return propertyName + ' ' + (ascending ? "asc" : "desc");
	}

	/**
	 * Constructor for Order.
	 */
	protected Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	/**
	 * Ascending order
	 * 
	 * @param propertyName
	 * @return Order
	 */
	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	/**
	 * Descending order
	 * 
	 * @param propertyName
	 * @return Order
	 */
	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}
}
