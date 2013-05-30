package com.gdie.service;

import java.util.List;
import java.util.Map;

import com.gdie.dao.BaseMapper;
import com.gdie.vo.PageData;
import com.gdie.vo.QueryResult;

public interface IBaseService<T, K> extends BaseMapper<T, K> {
	/**
	 * 自定义查询
	 * 
	 * @param where
	 * @return
	 */
	public List<T> query(Map<String, Object> condition);

	/**
	 * 分页查询
	 * 
	 * @param condition
	 * @param page
	 * @param rows
	 * @return
	 */
	public QueryResult<T> query(Map<String, Object> condition, int page, int rows);

	/**
	 * 统计
	 * 
	 * @param condition
	 * @return
	 */
	public Long count(Map<String, Object> condition);
	
	/**
	 * 分页查询
	 * 
	 * @param condition
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageData<T> query(Map<String, Object> condition, int page);
	
	/**
	 * 根据id查数据
	 * 
	 * @param id
	 * @return
	 */
	public T findById(String id);
}
