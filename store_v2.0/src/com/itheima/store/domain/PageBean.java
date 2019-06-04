package com.itheima.store.domain;

import java.util.List;

public class PageBean<T> {
	
	private Integer currPage; //当前页
	private Integer pageSize; //一页的条数
	private Integer totalCount; //总共有多少条
	private Integer totalPage; //总共有多少页
	private List<T> list; //查询出的数据对象 用泛型，传入哪个对象就查哪个
	public Integer getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageBean [currPage=" + currPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", list=" + list + "]";
	}
	
}
