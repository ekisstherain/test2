package com.gdie.vo;

import java.io.Serializable;

/**
 * 分页条件
 * 
 * @author HHP
 * 
 */
public class PageCondition implements Serializable{

	/**
	 * 条件
	 */
	private String where;

	/**
	 * 开始索引
	 */
	private int firstResult;

	/**
	 * 最大结果集
	 */
	private int maxResult;

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
}
