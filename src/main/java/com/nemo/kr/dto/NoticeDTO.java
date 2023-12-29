package com.nemo.kr.dto;

import java.util.Date;

import com.nemo.kr.util.DateUtil;

public class NoticeDTO extends DefaultDTO {
	private static final long serialVersionUID = -2231950057788485849L;
	
	private String notice_seq;			//공지사항 번호
	private String auth_view;				//보여지는 권한(0 : 전체, 1 : 구직자, 2: 구인처)
	private String notice_title;			//공지사항 제목
	private String notice_contents;		//공지사항 내용
	private String view_cnt;				//공지사항 조회수
	private String notice_writer;			//공지사항 작성자
	private String writer_auth;				//작성자 AUTH_LEVEL
	private int reply_cnt;					//공지사항 댓글 카운트
	private String company_seq;		//공지사항 작성 회사 순번
	private String adminAuth;			//로그인 한 유저의 권한 정보
	private String auth_company_seq;	//로그인 한 유저의 회사 정보
	private String auth_company_name;	//보여지는 회사
	private String main_view_yn;				//메인 노출 여부 (0 : 미노출, 1 : 노출)
	private String main_start_date;		//메인 노출 시작 날짜
	private String main_end_date;		//메인 노출 종료 날짜
	
	public String getMain_view_yn() {
		return main_view_yn;
	}
	public void setMain_view_yn(String main_view_yn) {
		this.main_view_yn = main_view_yn;
	}
	public String getMain_start_date() {
		return main_start_date;
	}
	public String getMain_end_date() {
		return main_end_date;
	}
	public void setMain_start_date(String main_start_date) {
		this.main_start_date = main_start_date;
	}
	public void setMain_end_date(String main_end_date) {
		this.main_end_date = main_end_date;
	}
	public String getWriter_auth() {
		return writer_auth;
	}
	public void setWriter_auth(String writer_auth) {
		this.writer_auth = writer_auth;
	}
	public String getAuth_company_name() {
		return auth_company_name;
	}
	public void setAuth_company_name(String auth_company_name) {
		this.auth_company_name = auth_company_name;
	}
	private String auth_seq;			
	
	public String getAuth_seq() {
		return auth_seq;
	}
	public void setAuth_seq(String auth_seq) {
		this.auth_seq = auth_seq;
	}
	public String getAuth_company_seq() {
		return auth_company_seq;
	}
	public void setAuth_company_seq(String auth_company_seq) {
		this.auth_company_seq = auth_company_seq;
	}
	public String getAdminAuth() {
		return adminAuth;
	}
	public void setAdminAuth(String adminAuth) {
		this.adminAuth = adminAuth;
	}
	
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getNotice_writer() {
		return notice_writer;
	}
	public void setNotice_writer(String notce_writer) {
		this.notice_writer = notce_writer;
	}
	
	public String getNotice_seq() {
		return notice_seq;
	}
	public void setNotice_seq(String notice_seq) {
		this.notice_seq = notice_seq;
	}
	public String getAuth_view() {
		return auth_view;
	}
	public void setAuth_view(String auth_view) {
		this.auth_view = auth_view;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_contents() {
		return notice_contents;
	}
	public void setNotice_contents(String notice_contents) {
		this.notice_contents = notice_contents;
	}
	public String getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(String view_cnt) {
		this.view_cnt = view_cnt;
	}
	public int getReply_cnt() {
		return reply_cnt;
	}
	public void setReply_cnt(int reply_cnt) {
		this.reply_cnt = reply_cnt;
	}
	
	
	
	
	
}
