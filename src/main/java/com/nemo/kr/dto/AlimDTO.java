package com.nemo.kr.dto;

import java.util.ArrayList;
import java.util.List;

public class AlimDTO extends DefaultDTO {
	private static final long serialVersionUID = -2231950057788485849L;
	
	private String alim_seq;		//알림 순번
	private String alim_title;		//알림 제목
	private String alim_content;	//알림 내용
	private String alim_writer;		//알림 작성자
	private String company_seq; 	//알림 작성자 회사
	private String writer_auth;		//알림 작성자 auth
	private String worker_name;		//알림 받는 구직자
	private String main_view_yn;				//메인 노출 여부 (0 : 미노출, 1 : 노출)
	private String flag; // W : 구직자, M : 매니저, B : 소장
	private String manager_name;
	private String work_seq;		//현장순번
	private String alim_count;
	
	private List<AlimReceiveDTO> alimReceiveList = new ArrayList<AlimReceiveDTO>();

	public String getAlim_count() {
		return alim_count;
	}
	public void setAlim_count(String alim_count) {
		this.alim_count = alim_count;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<AlimReceiveDTO> getAlimReceiveList() {
		return alimReceiveList;
	}
	public void setAlimReceiveList(List<AlimReceiveDTO> alimReceiveList) {
		this.alimReceiveList = alimReceiveList;
	}
	public String getMain_view_yn() {
		return main_view_yn;
	}
	public void setMain_view_yn(String main_view_yn) {
		this.main_view_yn = main_view_yn;
	}
	public String getWriter_auth() {
		return writer_auth;
	}
	public void setWriter_auth(String writer_auth) {
		this.writer_auth = writer_auth;
	}
	public String getWorker_name() {
		return worker_name;
	}
	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	
	public String getAlim_seq() {
		return alim_seq;
	}
	public void setAlim_seq(String alim_seq) {
		this.alim_seq = alim_seq;
	}
	public String getAlim_title() {
		return alim_title;
	}
	public void setAlim_title(String alim_title) {
		this.alim_title = alim_title;
	}
	public String getAlim_content() {
		return alim_content;
	}
	public void setAlim_content(String alim_content) {
		this.alim_content = alim_content;
	}
	public String getAlim_writer() {
		return alim_writer;
	}
	public void setAlim_writer(String alim_writer) {
		this.alim_writer = alim_writer;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	
}
