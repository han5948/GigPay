package com.nemo.kr.dto;

public class InsuranceDTO extends DefaultDTO {
	private static final long serialVersionUID = 1L;
	
	private String insurance_log_seq;
	private String insurance_log_type;
	private String employer_seq;
	private String seq;
	private String user_type;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getInsurance_log_seq() {
		return insurance_log_seq;
	}
	public void setInsurance_log_seq(String insurance_log_seq) {
		this.insurance_log_seq = insurance_log_seq;
	}
	public String getInsurance_log_type() {
		return insurance_log_type;
	}
	public void setInsurance_log_type(String insurance_log_type) {
		this.insurance_log_type = insurance_log_type;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	
}
