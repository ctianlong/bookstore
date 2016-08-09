package com.ctl.bookstore.web;

import java.util.List;

public class Page<T> {
	
	//当前页码
	private int pageNo;
	//当前页内容
	private List<T> list;
	//每一页显示数目，定义为静态
	private static int pageSize = 4;
	//总条目数
	private long totalItemNumber;
	
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}

	//需要校验
	public int getPageNo() {
//		if(totalItemNumber == 0){
//			pageNo = 1;
//		}else
		
		if(pageNo < 1){
			pageNo = 1;
		}else if(pageNo > getTotalPageNumber()){
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}
	
	public static int getPageSize() {
		return pageSize;
	}
	
	public static void setPageSize(int pageSize) {
		Page.pageSize = pageSize;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}
	
	//获取总页数
	public int getTotalPageNumber() {
		if(totalItemNumber % pageSize == 0){
			return (int) (totalItemNumber / pageSize);
		}else{
			return (int) (totalItemNumber / pageSize + 1);
		}
	}
	
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	
	public boolean isHasNext(){
		return getPageNo() != getTotalPageNumber();
	}
	
	public boolean isHasPrev(){
		return getPageNo() != 1;
	}
	
	public int getPrePage(){
		return (isHasPrev() ? (getPageNo() - 1) : getPageNo());
	}
	
	public int getNextPage(){
		return (isHasNext() ? (getPageNo() + 1) : getPageNo());
	}
	
}
