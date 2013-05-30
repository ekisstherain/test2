package com.gdie.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果集
 * 
 * @author hhp
 * 
 */
public class QueryResult<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1903147401423674502L;

	/**
	 * 查询返回的数据
	 */
	private List<T> data;

	/**
	 * 总记录数
	 */
	private Long total;

	/**
	 * 获取 查询返回的数据
	 */

	public List<T> getData() {
		return data;
	}

	/**
	 * 设置 查询返回的数据
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 获取 总记录数
	 */

	public Long getTotal() {
		return total;
	}

	/**
	 * 设置 总记录数
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
}
