package com.nemo.kr.dto;

public class JobAlimDTO extends DefaultDTO {
	private static final long serialVersionUID = -2231950057788485849L;
	
	private String jobAlim_seq;		//일자리 알림 순번
	private String jobAlim_content;	//일자리 알림 내용
	private String jobAlim_writer;	//일자리 알림 작성자
	private String company_seq; 	//일자리 알림 작성자 회사
	private String worker_name;		//일자리 알림 받는 구직자
	private String worker_length;	//일자리 알림 받는 구직자 수
	private String queryFlag;		//일자리 알림 조회 테이블  flag (I : ilbo테이블 조회, W : worker테이블 조회)
	private String workFlag;
	private String main_view_yn;				//메인 노출 여부 (0 : 미노출, 1 : 노출)
	private String employer_seq;
	private String employer_name;
	private String work_seq;
	private String work_name;
	private String jobAlim_title;
	private String work_sido;
	private String work_sigugun;
	private String work_dongmyun;
	private String work_manager;
	
	public String getWork_manager() {
		return work_manager;
	}
	public void setWork_manager(String work_manager) {
		this.work_manager = work_manager;
	}
	public String getWork_sido() {
		return work_sido;
	}
	public void setWork_sido(String work_sido) {
		this.work_sido = work_sido;
	}
	public String getWork_sigugun() {
		return work_sigugun;
	}
	public void setWork_sigugun(String work_sigugun) {
		this.work_sigugun = work_sigugun;
	}
	public String getWork_dongmyun() {
		return work_dongmyun;
	}
	public void setWork_dongmyun(String work_dongmyun) {
		this.work_dongmyun = work_dongmyun;
	}
	public String getJobAlim_title() {
		return jobAlim_title;
	}
	public void setJobAlim_title(String jobAlim_title) {
		this.jobAlim_title = jobAlim_title;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public String getWorkFlag() {
		return workFlag;
	}
	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}
	public String getMain_view_yn() {
		return main_view_yn;
	}
	public void setMain_view_yn(String main_view_yn) {
		this.main_view_yn = main_view_yn;
	}
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	public String getWorker_length() {
		return worker_length;
	}
	public void setWorker_length(String worker_length) {
		this.worker_length = worker_length;
	}
	public String getJobAlim_seq() {
		return jobAlim_seq;
	}
	public void setJobAlim_seq(String jobAlim_seq) {
		this.jobAlim_seq = jobAlim_seq;
	}
	public String getJobAlim_content() {
		return jobAlim_content;
	}
	public void setJobAlim_content(String jobAlim_content) {
		this.jobAlim_content = jobAlim_content;
	}
	public String getJobAlim_writer() {
		return jobAlim_writer;
	}
	public void setJobAlim_writer(String jobAlim_writer) {
		this.jobAlim_writer = jobAlim_writer;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getWorker_name() {
		return worker_name;
	}
	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
	}
}
