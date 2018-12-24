package com.bw.day16.utils;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 对Page<E>结果进行包装
 * <p/>
 * 新增分页的多项属性，主要参考:http://bbs.csdn.net/topics/360010907
 *
 * @author pankun
 * @version 3.3.0
 * @since 3.2.2 项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PageInfo<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	// 当前页
	private int pageNum;
	// 每页的数量
	private int pageSize;
	// 当前页的数量
	private int size;
	// 由于startRow和endRow不常用，这里说个具体的用法
	// 可以在页面中"显示startRow到endRow 共size条数据"

	// 当前页面第一个元素在数据库中的行号
	private int startRow;
	// 当前页面最后一个元素在数据库中的行号
	private int endRow;
	// 总记录数
	private long total;
	// 总页数
	private int pages;
	// 结果集
	private List<T> list;

	// 第一页
	private int firstPage;
	// 前一页
	private int prePage;
	// 下一页
	private int nextPage;
	// 最后一页
	private int lastPage;

	// 是否为第一页
	private boolean isFirstPage = false;
	// 是否为最后一页
	private boolean isLastPage = false;
	// 是否有前一页
	private boolean hasPreviousPage = false;
	// 是否有下一页
	private boolean hasNextPage = false;
	// 导航页码数
	private int navigatePages;
	// 所有导航页号
	private int[] navigatepageNums;

	// 显示在页面的分页栏
	//第一种
	private String toolBarOne;
	//第二种
	private String toolBarTwo;
	//第三种
	private String toolBarTree;
	//第四种
	private String toolBarFore;
	
	//页面路径
	private String url;

	public PageInfo() {
	}

	/**
	 * 包装Page对象
	 *
	 * @param list
	 */
	public PageInfo(List<T> list, String url) {
		this(list, 10);
		this.url=url;
	}

	/**
	 * 包装Page对象
	 *
	 * @param list
	 *            page结果
	 * @param navigatePages
	 *            页码数量
	 */
	public PageInfo(List<T> list, int navigatePages) {
		if (list instanceof Page) {
			Page page = (Page) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();

			this.total = page.getTotal();
			this.pages = page.getPages();
			this.list = page;
			this.size = page.size();
			// 由于结果是>startRow的，所以实际的需要+1
			if (this.size == 0) {
				this.startRow = 0;
				this.endRow = 0;
			} else {
				this.startRow = page.getStartRow() + 1;
				// 计算实际的endRow（最后一页的时候特殊）
				this.endRow = this.startRow - 1 + this.size;
			}
			this.navigatePages = navigatePages;
			// 计算导航页
			calcNavigatepageNums();
			// 计算前后页，第一页，最后一页
			calcPage();
			// 判断页面边界
			judgePageBoudary();
		}
	}

	/**
	 * 计算导航页
	 */
	private void calcNavigatepageNums() {
		// 当总页数小于或等于导航页码数时
		if (pages <= navigatePages) {
			navigatepageNums = new int[pages];
			for (int i = 0; i < pages; i++) {
				navigatepageNums[i] = i + 1;
			}
		} else { // 当总页数大于导航页码数时
			navigatepageNums = new int[navigatePages];
			int startNum = pageNum - navigatePages / 2;
			int endNum = pageNum + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				// (最前navigatePages页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			} else if (endNum > pages) {
				endNum = pages;
				// 最后navigatePages页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatepageNums[i] = endNum--;
				}
			} else {
				// 所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			}
		}
	}

	/**
	 * 计算前后页，第一页，最后一页
	 */
	private void calcPage() {
		if (navigatepageNums != null && navigatepageNums.length > 0) {
			firstPage = navigatepageNums[0];
			lastPage = navigatepageNums[navigatepageNums.length - 1];
			if (pageNum > 1) {
				prePage = pageNum - 1;
			}
			if (pageNum < pages) {
				nextPage = pageNum + 1;
			}
		}
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNum == 1;
		isLastPage = pageNum == pages;
		hasPreviousPage = pageNum > 1;
		hasNextPage = pageNum < pages;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public boolean isIsFirstPage() {
		return isFirstPage;
	}

	public void setIsFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}
    
	/***
	 * 为了方便页面的上显示，在原来的基础之上加了这步
	 * 第一种分页
	 * @return
	 */
	public String getToolBarOne() {
		StringBuffer sb = new StringBuffer();
		sb.append("总记录数&nbsp;&nbsp;"+this.total+"&nbsp;&nbsp;");
		sb.append("总页数&nbsp;&nbsp;"+this.pages+"&nbsp;&nbsp;");
		//首页
		if (this.pageNum ==1) {
			sb.append("首页&nbsp;&nbsp;");
		} else {
			sb.append("<a href=\"./" + url +"?page=1\">首页&nbsp;&nbsp;</a>");
			//sb.append("<a href=\"javascript:(0);" + "onclick=oClick('"+url +"?page=1"+"')\">首页</a>");
		}
		//上一页
		if(this.pageNum==this.firstPage) {
			sb.append("上一页&nbsp;&nbsp;");
		}else {
			sb.append("<a href='./"+this.url+"?page="+(this.pageNum-1)+"'>上一页&nbsp;&nbsp;</a>");
			//sb.append("<a href=\"javascript:(0);"+"onclick=oClick('"+this.url+"?page="+(this.pageNum-1)+"')"+"\">上一页&nbsp;&nbsp;</a>");
		}
		//下一页
		if(this.pageNum!=this.lastPage) {
			sb.append("<a href='./"+this.url+"?page="+(this.pageNum+1)+"'>下一页&nbsp;&nbsp;</a>");
			//sb.append("<a href=\"javascript:(0);"+"onclick=oClick('"+this.url+"?page="+(this.pageNum+1)+"')"+"\">下一页&nbsp;&nbsp;</a>");
		}else {
			sb.append("下一页&nbsp;&nbsp;");
		}
		//末页
		if (this.pageNum !=this.pages) {
			sb.append("<a href='./"+this.url+"?page="+(this.pages)+"'>末页&nbsp;&nbsp;</a>");
			//sb.append("<a href=\"javascript:(0);"+"onclick=oClick('"+this.url+"?page="+(this.pages)+"')"+"\">末页&nbsp;&nbsp;</a>");
		} else {
			sb.append("末页&nbsp;&nbsp;");
		}
		toolBarOne=sb.toString();
		return toolBarOne;
	}


	/**
	 * 第二种分页（带页码数）
	 * @return
	 */
	public String getToolBarTwo() {
		StringBuffer sb = new StringBuffer();
		//总页数
		sb.append("总页数&nbsp;&nbsp;"+this.pages+"&nbsp;&nbsp;");
		//首页
		if (this.pageNum ==1) {
			sb.append("首页&nbsp;&nbsp;");
		} else {
			sb.append("<a href=\"./" + url +"?page=1\">首页&nbsp;&nbsp;</a>");
		}
		//上一页
		if(this.pageNum==this.firstPage) {
			sb.append("上一页&nbsp;&nbsp;");
		}else {
			sb.append("<a href='./"+this.url+"?page="+(this.pageNum-1)+"'>上一页&nbsp;&nbsp;</a>");
		}
		
		//带页码数
		for(int i=1;i<=this.pages;i++) {
			if(i==this.pageNum) {
			  sb.append(i);
			}else {
			  sb.append("<a href='./"+this.url+"?page="+i+"'>"+i+"</a>&nbsp;");
			}
		}
		//下一页
		if(this.pageNum!=this.lastPage) {
			sb.append("<a href='./"+this.url+"?page="+(this.pageNum+1)+"'>下一页&nbsp;&nbsp;</a>");
		}else {
			sb.append("下一页&nbsp;&nbsp;");
		}
		//末页
		if (this.pageNum !=this.pages) {
			sb.append("<a href='./"+this.url+"?page="+(this.pages)+"'>末页&nbsp;&nbsp;</a>");
		} else {
			sb.append("末页&nbsp;&nbsp;");
		}
		toolBarTwo=sb.toString();
		return toolBarTwo;
	}

	public void setToolBarTwo(String toolBarTwo) {
		this.toolBarTwo = toolBarTwo;
	}

	/**
	 * 第三种分页（带跳转页）
	 * @return
	 */
	public String getToolBarTree() {
		StringBuffer sb = new StringBuffer();
		//总页数
		sb.append("总页数&nbsp;&nbsp;"+this.pages+"&nbsp;&nbsp;");
		//首页
		if (this.pageNum ==1) {
			sb.append("首页&nbsp;&nbsp;");
		} else {
			sb.append("<a href=\"./" + url +"?page=1\">首页&nbsp;&nbsp;</a>");
		}
		//上一页
		if(this.pageNum==this.firstPage) {
			sb.append("上一页&nbsp;&nbsp;");
		}else {
			sb.append("<a href='./"+this.url+"?page="+(this.pageNum-1)+"'>上一页&nbsp;&nbsp;</a>");
		}
		
		//下一页
		if(this.pageNum!=this.lastPage) {
			sb.append("<a href='./"+this.url+"?page="+(this.pageNum+1)+"'>下一页&nbsp;&nbsp;</a>");
		}else {
			sb.append("下一页&nbsp;&nbsp;");
		}
		//末页
		if (this.pageNum !=this.pages) {
			sb.append("<a href='./"+this.url+"?page="+(this.pages)+"'>末页&nbsp;&nbsp;</a>");
		} else {
			sb.append("末页&nbsp;&nbsp;");
		}
		//一条显示的记录数
		sb.append("一页显示 &nbsp; <select onchange=\"reset(this.value)\" style=\"width:48px\"> ");
		for(int i=5;i<=30;i=i+5) {
			 sb.append("<option "+selectRows(i)+">"+i+"</option>");
		}
		sb.append(" </select>\n");
		sb.append("条记录&nbsp;");
		
	    //跳转页
		sb.append("跳转到&nbsp; <select onchange=\"goTO(this.value)\" style=\"width:48px\"> ");
		for(int i=1;i<=this.pages;i++) {
			  sb.append("<option "+checkSelect(i)+">"+i+"</option>");
		}
		sb.append(" </select>\n");
	  
		sb.append("<script type=\"text/javascript\">\n");
		//页面跳转
		sb.append("function goTO(id){\n");
		sb.append("location='./"+this.url+"?page='+id;");
		sb.append("\n}");
		//页面记录数重置
		sb.append("function reset(size){\n");
		sb.append("location='./"+this.url+"?rows='+size;");
		sb.append("\n}");
		sb.append("\n</script>");
		toolBarTree=sb.toString();
		return toolBarTree;
	}
	//判断选择的记录数的是否和选中相等
	public String selectRows(int rows){
		if(this.pageSize == rows) return "selected";
		return "";
	}
	//判断跳转的页是否和选中页数相等
	public String checkSelect(int pageSize){
		if(this.pageNum == pageSize) return "selected";
		return "";
	}
	public void setToolBarTree(String toolBarTree) {
		this.toolBarTree = toolBarTree;
	}

	public String getToolBarFore() {
		return toolBarFore;
	}

	public void setToolBarFore(String toolBarFore) {
		this.toolBarFore = toolBarFore;
	}

	public void setToolBarOne(String pageToolBar) {
		this.toolBarOne = pageToolBar;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PageInfo{");
		sb.append("pageNum=").append(pageNum);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", size=").append(size);
		sb.append(", startRow=").append(startRow);
		sb.append(", endRow=").append(endRow);
		sb.append(", total=").append(total);
		sb.append(", pages=").append(pages);
		sb.append(", list=").append(list);
		sb.append(", firstPage=").append(firstPage);
		sb.append(", prePage=").append(prePage);
		sb.append(", nextPage=").append(nextPage);
		sb.append(", lastPage=").append(lastPage);
		sb.append(", isFirstPage=").append(isFirstPage);
		sb.append(", isLastPage=").append(isLastPage);
		sb.append(", hasPreviousPage=").append(hasPreviousPage);
		sb.append(", hasNextPage=").append(hasNextPage);
		sb.append(", navigatePages=").append(navigatePages);
		sb.append(", navigatepageNums=");
		if (navigatepageNums == null)
			sb.append("null");
		else {
			sb.append('[');
			for (int i = 0; i < navigatepageNums.length; ++i)
				sb.append(i == 0 ? "" : ", ").append(navigatepageNums[i]);
			sb.append(']');
		}
		sb.append('}');
		return sb.toString();
	}
	
	

}
