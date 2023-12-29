package com.nemo.kr.dto;

public class JobLimitCountDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String limit_count_seq;
	private String limit_count;
	private String limit_type;
	
	public String getLimit_count_seq() {
		return limit_count_seq;
	}
	public void setLimit_count_seq(String limit_count_seq) {
		this.limit_count_seq = limit_count_seq;
	}
	public String getLimit_count() {
		return limit_count;
	}
	public void setLimit_count(String limit_count) {
		this.limit_count = limit_count;
	}
	public String getLimit_type() {
		return limit_type;
	}
	public void setLimit_type(String limit_type) {
		this.limit_type = limit_type;
	}
}
