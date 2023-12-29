package com.nemo.kr.dto;


public class UseStatisticsDTO extends GridDefaultDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -694761298459961018L;
	
	private String use_statistics_seq;       
	private String ilbo_date;		//출역일자               
	private String company_seq;
	private String company_name;
	
	private String worker_company_seq;		//구직자 지점 순번
	private String worker_company_name;
	
	private int undisclosed_count;		//사무실 개수
	private int all_count;		//app 개수
	private int company_count;		//공유 개수
	
	private String job_id;			//배치잡아이디
	private String reg_date;			//등록일자
	
	private String company_flag;    //0: 구직자 소속 지점 기준 1: 구인처 소속 지점 기준
	
	
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getUse_statistics_seq() {
		return use_statistics_seq;
	}
	public void setUse_statistics_seq(String use_statistics_seq) {
		this.use_statistics_seq = use_statistics_seq;
	}
	public String getIlbo_date() {
		return ilbo_date;
	}
	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
	}
	public String getWorker_company_seq() {
		return worker_company_seq;
	}
	public void setWorker_company_seq(String worker_company_seq) {
		this.worker_company_seq = worker_company_seq;
	}
	public int getUndisclosed_count() {
		return undisclosed_count;
	}
	public void setUndisclosed_count(int undisclosed_count) {
		this.undisclosed_count = undisclosed_count;
	}
	public int getAll_count() {
		return all_count;
	}
	public void setAll_count(int all_count) {
		this.all_count = all_count;
	}
	public int getCompany_count() {
		return company_count;
	}
	public void setCompany_count(int company_count) {
		this.company_count = company_count;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getWorker_company_name() {
		return worker_company_name;
	}
	public void setWorker_company_name(String worker_company_name) {
		this.worker_company_name = worker_company_name;
	}
	public String getCompany_flag() {
		return company_flag;
	}
	public void setCompany_flag(String company_flag) {
		this.company_flag = company_flag;
	}
	
	
	
	

	
}
