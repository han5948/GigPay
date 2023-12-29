package com.nemo.kr.dto;


public class OrderStatisticsDTO extends GridDefaultDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -694761298459961018L;
	
	private String statistics_seq;                                                                                                                        
	private String ilbo_date;		//출역일자                                                                                              
	private String company_seq;		//지점순번
	private String company_name;		//지점명
	
	private int office_count;		//사무실 개수
	private int app_count;			//app 개수
	private int share_count;		//공유 개수
	private int call_count;			//call 개수
	private int web_member_count;			//WEB회원 개수
	private int web_non_member_count;		//WEB비회원 개수
	private int mWeb_member_count;			//MWEB회원 개수
	private int mWeb_non_member_count;		//MWEB비회원 개수
	private int again_count;		//또가요 개수
	private int diff_count;			//다른분 개수
	
	private String job_id;			//배치잡아이디
	private String reg_date;			//등록일자
	
	
	public String getStatistics_seq() {
		return statistics_seq;
	}
	public void setStatistics_seq(String statistics_seq) {
		this.statistics_seq = statistics_seq;
	}
	public String getIlbo_date() {
		return ilbo_date;
	}
	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
	}
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
	public int getOffice_count() {
		return office_count;
	}
	public void setOffice_count(int office_count) {
		this.office_count = office_count;
	}
	public int getApp_count() {
		return app_count;
	}
	public void setApp_count(int app_count) {
		this.app_count = app_count;
	}
	public int getShare_count() {
		return share_count;
	}
	public void setShare_count(int share_count) {
		this.share_count = share_count;
	}
	public int getCall_count() {
		return call_count;
	}
	public void setCall_count(int call_count) {
		this.call_count = call_count;
	}
	public int getAgain_count() {
		return again_count;
	}
	public void setAgain_count(int again_count) {
		this.again_count = again_count;
	}
	public int getDiff_count() {
		return diff_count;
	}
	public void setDiff_count(int diff_count) {
		this.diff_count = diff_count;
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
	public int getWeb_member_count() {
		return web_member_count;
	}
	public void setWeb_member_count(int web_member_count) {
		this.web_member_count = web_member_count;
	}
	public int getWeb_non_member_count() {
		return web_non_member_count;
	}
	public void setWeb_non_member_count(int web_non_member_count) {
		this.web_non_member_count = web_non_member_count;
	}
	public int getmWeb_member_count() {
		return mWeb_member_count;
	}
	public void setmWeb_member_count(int mWeb_member_count) {
		this.mWeb_member_count = mWeb_member_count;
	}
	public int getmWeb_non_member_count() {
		return mWeb_non_member_count;
	}
	public void setmWeb_non_member_count(int mWeb_non_member_count) {
		this.mWeb_non_member_count = mWeb_non_member_count;
	}
}
