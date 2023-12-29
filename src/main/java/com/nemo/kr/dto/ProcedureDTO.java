package com.nemo.kr.dto;

import java.io.Serializable;

public class ProcedureDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String log_seq;
	private String proc_name;
	private String r_count;
	private String reg_date;
	
	private String sub_day = "10";
	private PagingDTO paging = new PagingDTO();

	public String getLog_seq() {
		return log_seq;
	}
	public void setLog_seq(String log_seq) {
		this.log_seq = log_seq;
	}
	public String getProc_name() {
		return proc_name;
	}
	public void setProc_name(String proc_name) {
		this.proc_name = proc_name;
	}
	public String getR_count() {
		return r_count;
	}
	public void setR_count(String r_count) {
		this.r_count = r_count;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public PagingDTO getPaging() {
		return paging;
	}
	public void setPaging(PagingDTO paging) {
		this.paging = paging;
	}
	public String getSub_day() {
		return sub_day;
	}
	public void setSub_day(String sub_day) {
		this.sub_day = sub_day;
	}
}
