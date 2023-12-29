package com.nemo.kr.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.nemo.kr.common.Const;

/**
 * Paging DTO
 * @author nemo
 *
 */
public class PagingDTO implements Serializable {

	private static final long serialVersionUID = -5786641609538363632L;
	
	private int rowCount;				//토탈 갯수
	private int pageSize;	
	private int pageNo;	
	private int startPage, endPage;
	private int totalPageCnt;		//페이지 갯수
	private int mtotalPageCnt;
	private int currentCnt;			//현재 갯수
	
	private int mobilePageSize;
	private int mStartPage, mEndPage;
	
	
	public PagingDTO(){
		pageSize = Const.PAGE_SIZE; //페이지당 레코드 갯수
		mobilePageSize = Const.MOBILE_PAGE_SIZE;
		
		pageNo = 1;
		endPage = pageSize;
		mEndPage = mobilePageSize;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.endPage = pageSize;
	}

	public void setMobilePageSize(int mobilePageSize) {
		this.mobilePageSize = mobilePageSize;
		this.mEndPage = mobilePageSize;;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		//System.out.println("pageNo: " + pageNo);
		this.pageNo = pageNo;
	}

	public int getStartPage() {
		startPage = (((pageNo - 1) * pageSize));
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	

	public int getmEndPage() {
		return mEndPage;
	}


	public void setmEndPage(int mEndPage) {
		this.mEndPage = mEndPage;
	}
	
	public int getTotalPageCnt() {		

		totalPageCnt = (int) Math.ceil(rowCount / (double)pageSize);
		//if(totalPageCnt == 0) totalPageCnt = 1;
		return totalPageCnt;
	}
	
	


	public int getCurrentCnt() {
		return currentCnt;
	}


	public void setCurrentCnt(int currentCnt) {
		this.currentCnt = currentCnt;
	}


	public int getMobilePageSize() {
		
		return mobilePageSize;
	}



	public int getmStartPage() {
		this.mStartPage = (((pageNo - 1) * mobilePageSize));
		return mStartPage;
	}
	

	public void setmStartPage(int mStartPage) {
		this.mStartPage = mStartPage;
	}


	public int getMtotalPageCnt() {
		
		mtotalPageCnt = (int) Math.ceil(rowCount / (double)mobilePageSize);
		//if(totalPageCnt == 0) totalPageCnt = 1;
		return mtotalPageCnt;
	
	}



}
