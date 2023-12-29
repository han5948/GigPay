package com.nemo.kr.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.jqGridUtil;

/**
 * Grid 공통 DTO
 * @author nemo
 *
 */
public class GridDefaultDTO extends DefaultDTO implements Serializable {

	private static final long serialVersionUID = -5786641609538363632L;

	private String id;

	private int page;   		//몇번째 페이지를 요청했는지 private int pageNo;
	private int rows;			//rows : 페이지 당 몇개의 행이 보여질건지  private int pageNo;		

	private String sidx;		//sidx : 소팅하는 기준이 되는 인덱스
	private String sord;		//sord : 내림차순 또는 오름차순

	private String filters;
	private String where = "";
	private String search;
	private String oper;
	private String term;
	
	private String cellname;

	// 검색조건
	private String srh_company_seq;                         // 소속회사 순번 검색

	private String start_reg_date;                            // 등록일자 기간 검색
	private String end_reg_date;

	private String srh_use_yn;
	private String srh_del_yn;

	private String srh_type;
	private String srh_text;
	

	private String srh_distinct_yn;                           // 검색시 중복 허용 여부 - 0: 허용 1: 허용하지 않음

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
		this.getPaging().setPageNo(page);
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
		this.getPaging().setPageSize(rows);
	}


	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}


	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
		if(filters !=null){
			this.where = jqGridUtil.filersToWhere( filters );
		}
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}


	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	


	public String getSrh_company_seq() {
		return srh_company_seq;
	}
	public void setSrh_company_seq(String srh_company_seq) {
		this.srh_company_seq = srh_company_seq;
	}
	public String getStart_reg_date() {
		return start_reg_date;
	}
	public void setStart_reg_date(String start_reg_date) {
		this.start_reg_date = start_reg_date;
	}
	public String getEnd_reg_date() {
		return end_reg_date;
	}
	public void setEnd_reg_date(String end_reg_date) {
		this.end_reg_date = end_reg_date;
	}


	public String getSrh_use_yn() {
		return srh_use_yn;
	}
	public void setSrh_use_yn(String srh_use_yn) {
		this.srh_use_yn = srh_use_yn;
	}


	public String getSrh_type() {
		return srh_type;
	}
	public void setSrh_type(String srh_type) {
		this.srh_type = srh_type;
	}
	public String getSrh_text() {
		return srh_text;
	}
	public void setSrh_text(String srh_text) {
		this.srh_text = srh_text;
	}


	public String getSrh_distinct_yn() {
		return srh_distinct_yn;
	}
	public void setSrh_distinct_yn(String srh_distinct_yn) {
		this.srh_distinct_yn = srh_distinct_yn;
	}

	public String getCellname() {
		return cellname;
	}

	public void setCellname(String cellname) {
		this.cellname = cellname;
	}

	public String getSrh_del_yn() {
		return srh_del_yn;
	}

	public void setSrh_del_yn(String srh_del_yn) {
		this.srh_del_yn = srh_del_yn;
	}

}
