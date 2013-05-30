package com.gdie.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gdie.common.DBUtil;
import com.gdie.common.ReflectUtil;
import com.gdie.dao.BaseMapper;
import com.gdie.service.IBaseService;
import com.gdie.vo.PageData;
import com.gdie.vo.QueryResult;
import com.gdie.vo.SqlParam;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseService<T, K> implements IBaseService<T, K> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseService.class);

	protected BaseMapper mapper;

	@Override
	public int deleteByPrimaryKey(K id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(T record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public T selectByPrimaryKey(K id) {
		// TODO Auto-generated method stub
		return (T) mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public List<T> query(SqlParam sqlParam) {
		// TODO Auto-generated method stub
		return mapper.query(sqlParam);
	}

	@Override
	public List<T> query(Map<String, Object> condition) {
		SqlParam param = SqlParam.getInstance(DBUtil.buildWhere(condition, 0, 0));
		return mapper.query(param);
	}

	@Override
	public QueryResult<T> query(Map<String, Object> condition, int page, int rows) {
		// TODO Auto-generated method stub
		SqlParam sqlParam = SqlParam.getInstance(DBUtil.buildWhere(condition, page, rows));
		QueryResult<T> result = new QueryResult<T>();
		result.setData(mapper.query(sqlParam));

		// 去除分页信息 语句
		String sql = sqlParam.getWhere();
		int startIndex = sql.indexOf("limit");
		if (startIndex > -1) {
			sql = sql.substring(0, startIndex);
		}
		
		// 去除排序
		startIndex = sql.indexOf("order");
		if (startIndex > -1) {
			sql = sql.substring(0, startIndex);
		}
		
		sqlParam.setWhere(sql);
		result.setTotal(count(sqlParam));

		logger.debug("total sql:" + sqlParam.getWhere());
		return result;
	}

	@Override
	public Long count(SqlParam sqlParam) {
		// TODO Auto-generated method stub
		return mapper.count(sqlParam);
	}

	@Override
	public Long count(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return mapper.count(SqlParam.getInstance(DBUtil.buildWhere(condition, 0, 0)));
	}

	@Override
	public PageData<T> query(Map<String, Object> condition, int page) {
		// TODO Auto-generated method stub
		PageData<T> pageData = new PageData<T>(page);
		logger.debug("pageSize:" + pageData.getPageSize());
		pageData.initPageData(query(condition, page, pageData.getPageSize())); // 设置页面属性
		return pageData;
	}

	@Override
	public T findById(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<T> list = query(map);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return (T) ReflectUtil.getGenericInstance(getClass());
		}
	}
}
