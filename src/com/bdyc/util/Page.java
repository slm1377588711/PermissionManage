package com.bdyc.util;

import java.util.List;

/**
 * @author Administrator
 * 分页信息类
 */
public class Page
{
	
	private int pageSize;				//每页显示记录数
	private int totalCount; 			//总记录数
	private int totalPage;				//总页数
	private int currentPage;			//当前页
	private int beginIndex;				//查询起始点
	private boolean hasPrePage;			//是否有上一页
	private boolean hasNextPage;		//是否有下一页
	
	private List<?> list;
	
	
	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	//自定义构造
	public Page(int pageSize, int totalCount, int totalPage, int currentPage,
			int beginIndex, boolean hasPrePage, boolean hasNextPage)
	{
		this.pageSize = pageSize;			//每页记录数
		this.totalCount = totalCount;		//总记录数
		this.totalPage = totalPage;			//总页数
		this.currentPage = currentPage;		//当前页
		this.beginIndex = beginIndex;		//起始页数
		this.hasPrePage = hasPrePage;		//是否有上一页
		this.hasNextPage = hasNextPage;		//是否有下一页
	}
	
	//setter和getter方法
	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getBeginIndex()
	{
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex)
	{
		this.beginIndex = beginIndex;
	}

	public boolean isHasPrePage()
	{
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage)
	{
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage()
	{
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage)
	{
		this.hasNextPage = hasNextPage;
	}

	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", currentPage=" + currentPage
				+ ", beginIndex=" + beginIndex + ", hasPrePage=" + hasPrePage
				+ ", hasNextPage=" + hasNextPage + ", list=" + list + "]";
	}
	
	
	
}
