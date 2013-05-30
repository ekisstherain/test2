package com.gdie.vo;

import java.io.Serializable;

public class SqlParam implements Serializable{

	private String where;

	public SqlParam() {
	}

	public SqlParam(String where) {
		this.where = where;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	/**
	 * 获取sqlParam对象
	 * 
	 * @param where
	 * @return
	 */
	public static SqlParam getInstance(String where) {
		return new SqlParam(where);
	}
}
