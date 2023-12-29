package com.nemo.kr.dto;

public class TransferWorkerOptionDTO extends GridDefaultDTO {

	private static final long serialVersionUID = 8929677834140930149L;

	private String transfer_seq;
	private String option_rank;
	private String company_seq;
	private String company_name;
	private String gender;
	private String job_kind;
	private String job_code;
	private String job_name;
	private String osh_yn;
	private String location_cnt;
	
	private String[] sel_transfer_seq;
	private String dest_transfer_seq;
	private String dest_option_rank;
	
	public String getDest_transfer_seq() {
		return dest_transfer_seq;
	}
	public void setDest_transfer_seq(String dest_transfer_seq) {
		this.dest_transfer_seq = dest_transfer_seq;
	}
	public String getDest_option_rank() {
		return dest_option_rank;
	}
	public void setDest_option_rank(String dest_option_rank) {
		this.dest_option_rank = dest_option_rank;
	}
	public String getTransfer_seq() {
		return transfer_seq;
	}
	public void setTransfer_seq(String transfer_seq) {
		this.transfer_seq = transfer_seq;
	}
	public String getOption_rank() {
		return option_rank;
	}
	public void setOption_rank(String option_rank) {
		this.option_rank = option_rank;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getJob_code() {
		return job_code;
	}
	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}
	public String getOsh_yn() {
		return osh_yn;
	}
	public void setOsh_yn(String osh_yn) {
		this.osh_yn = osh_yn;
	}
	public String getJob_kind() {
		return job_kind;
	}
	public void setJob_kind(String job_kind) {
		this.job_kind = job_kind;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String[] getSel_transfer_seq() {
		return sel_transfer_seq;
	}
	public void setSel_transfer_seq(String[] sel_transfer_seq) {
		this.sel_transfer_seq = sel_transfer_seq;
	}
	public String getLocation_cnt() {
		return location_cnt;
	}
	public void setLocation_cnt(String location_cnt) {
		this.location_cnt = location_cnt;
	}
	
}
