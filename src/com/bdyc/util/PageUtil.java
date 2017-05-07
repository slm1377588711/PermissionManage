package com.bdyc.util;

/**
 * @author Administrator
 * 分页辅助类开发
 */
public class PageUtil
{
	/**
	 * 得到封装好所有分页信息的Page对象
	 * @param pageSize 	每页记录数
	 * @param totalCount 	总记录数
	 * @param currentPage 	当前页
	 * @return
	 */
	public static Page createPage(int pageSize, int totalCount, int currentPage)
	{
		pageSize = getPageSize(pageSize);
		currentPage = getCurrentPage(currentPage);
		int totalPage = getTotalPage(pageSize, totalCount);
		int beginIndex = getBeginIndex(pageSize, currentPage);
		boolean hasPrePage = getHasPrePage(currentPage);
		boolean hasNextPage = getHasNextPage(totalPage, currentPage);
		
		return new Page(pageSize, totalCount, totalPage, currentPage,
				beginIndex, hasPrePage, hasNextPage);
	}
	
	//得到每页显示记录数，默认显示10条
	public static int getPageSize(int pageSize)					
	{
		return pageSize == 0 ? 10:pageSize;
	}
	
	//获得当前页，默认为1
	public static int getCurrentPage(int currentPage)					
	{
		return currentPage == 0 ? 1:currentPage;
	}
	
	//获得总页数
	public static int getTotalPage(int pageSize, int totalCount)		
	{
		int totalPage = 0;
		if(totalCount != 0 && totalCount % pageSize == 0)
		{
			totalPage = totalCount / pageSize;
		}else
		{
			totalPage = totalCount / pageSize + 1;
		}
		return totalPage;
	}
	
	//获得起始位置
	public static int getBeginIndex(int pageSize, int currentPage)		
	{
		return (currentPage - 1) * pageSize;
	}
	
	//是否有上一页
	public static boolean getHasPrePage(int currentPage)				
	{
		return currentPage == 1 ? false:true;
	}
	
	//是否有下一页
	public static boolean getHasNextPage( int totalPage, int currentPage)
	{
		return currentPage == totalPage || totalPage == 0 ? false:true;
	}
}
