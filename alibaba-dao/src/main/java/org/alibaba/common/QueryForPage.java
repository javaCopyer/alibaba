package org.alibaba.common;

import org.alibaba.common.xss.SQLFilter;

import com.github.pagehelper.StringUtil;

/**
 * 分页参数
 * @author sky
 *
 */
public class QueryForPage {
	private Integer currentPage = new Integer(1); //当前页
	private Integer pageLimit = new Integer(10); //每页记录数
	private String orderByStr = null;
	private Object obj = new Object();  //查询条件
	
	public QueryForPage(Integer currentPage, Integer pageLimit, String orderByStr, Object obj) {
		super();
		this.currentPage = currentPage;
		this.pageLimit = pageLimit;
		this.orderByStr = orderByStr;
		this.obj = obj;
	}
	public String getOrderBy() {
		if (StringUtil.isNotEmpty(orderByStr)) {
			orderByStr = SQLFilter.sqlInject(orderByStr); //防止sql注入
			return orderByStr;
		}	
		return "";
	}
	/**
	 * 拼接mysql分页 limit sql
	 * @return
	 */
	public String getPageCondition() {
		int begin = pageLimit * (currentPage - 1);
		return " limit " + begin + "," + pageLimit;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}
	public String getOrderByStr() {
		return orderByStr;
	}
	public void setOrderByStr(String orderByStr) {
		this.orderByStr = orderByStr;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
