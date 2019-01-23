package org.alibaba.common;

import java.util.List;

/**
 * 分页数据
 * @author sky
 *
 */
public class PageResult {
	private Integer currentPage; //当前页
	private Integer pageLimit; //每页记录数
	private Integer totalRows; //总记录数
	private Integer totalPage; //总页数
	private List<?> list;
	public PageResult(Integer currentPage, Integer pageLimit, Integer totalRows, List<?> list) {
		this.currentPage = currentPage;
		this.pageLimit = pageLimit;
		this.totalRows = totalRows;
		this.list = list;
		totalPage = (int) Math.ceil((double) totalRows/ (double) pageLimit);
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
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	

	
}
