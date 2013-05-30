package com.gdie.dao;

import java.util.List;

import com.gdie.vo.SqlParam;

/**
 * 公共dao
 * 
 * @author HHP
 * 
 * @param <T>
 * @param <K>
 */
public interface BaseMapper<T, K> {
	int deleteByPrimaryKey(K id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(K id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

	public List<T> query(SqlParam sqlParam);
	
	public Long count(SqlParam sqlParam);
}
