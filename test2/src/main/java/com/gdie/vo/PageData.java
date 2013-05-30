package com.gdie.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.gdie.common.ConfigUtil;

/**
 * 分页工具条数据
 * 
 * @author hhp
 * 
 * @param <T>
 */
public class PageData<T> implements Serializable {
	/**
	 * 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(PageData.class);

	/**
	 * 构造函数，设置当前页
	 * 
	 * @param currentPage
	 */
	public PageData(Integer currentPage) {
		this.currentPage = (currentPage == null || currentPage < 1 ) ? 1 : currentPage; // 保证当前页大于等于1
	}

	/**
	 * 构造函数，设置每页显示的记录数，当前页
	 * 
	 * @param pageSize
	 * @param currentPage
	 */
	public PageData(int pageSize, int currentPage) {
		this.pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize; // 保证有记录
		this.currentPage = currentPage < 1 ? 1 : currentPage; // 保证当前页大于等于1
	}

	/**
	 * 初始化分页数据
	 * 
	 * @author 黄海平
	 * @createDate 2012-11-9 下午5:28:31
	 * @param queryResult
	 */
	public void initPageData(QueryResult<T> queryResult) {
		if (queryResult != null) {
			this.total = queryResult.getTotal();
			this.data = queryResult.getData();
		}
		// 计算总页数
		if (queryResult.getTotal() != null){
			int tp =  (int) (this.total + this.pageSize - 1) / this.pageSize;
			setTotalPage(tp < 1? 1 : tp); // 总页数不能小于1
		}
	}

	/**
	 * 默认每页记录数
	 */
	public final static int DEFAULT_PAGE_SIZE = new Integer(ConfigUtil.getProperty("DEFAULT_PAGE_SIZE"));

	/**
	 * 结果集合
	 */
	private List<T> data;

	/**
	 * 总记录数
	 */
	private Long total;

	/**
	 * 每页显示的记录数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 当前页
	 */
	private int currentPage;

	// get/set方法------------------------------------------------------------

	/**
	 * 获取 结果集合
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 获取 总记录数
	 * @return
	 */
	public Long getTotal() {
		return total;
	}
	
	/**
	 * 设置  总记录数
	 * @param total
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * 设置 结果集合
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 获取当前页
	 * 
	 * @return currentPage 当前页
	 */

	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 *            当前页
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取每页显示的记录数
	 * 
	 * @return pageSize 每页显示的记录数
	 */

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示的记录数
	 * 
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取总页数
	 * 
	 * @return totalPage 总页数
	 */

	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 设置总页数
	 * 
	 * @param totalPage
	 *            总页数
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 获取当前页
	 * 
	 * @return currentpage 当前页
	 */

	public int getCurrentpage() {
		return currentPage;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentpage
	 *            当前页
	 */
	public void setCurrentpage(int currentpage) {
		this.currentPage = currentpage;
	}
}
