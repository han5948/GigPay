package com.nemo.kr.dto;

import java.util.List;

import com.nemo.kr.dto.GridDefaultDTO;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
  * @FileName : AdminDTO.java
  * @Project : ilgaja
  * @Date : 2021. 1. 18. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 관리자 DTO
  */
public class AdminDTO extends GridDefaultDTO {

	private String admin_seq;					// 관리자 순번 - PK
	private String company_seq;				// 소속회사 순번

	private String admin_name;					// 관리자 이름
	private String admin_id;						// 관리자 아이디
	private String admin_pass;					// 관리자 패스워드
	private String admin_phone;					// 관리자 폰번호
	private String admin_email;					// 관리자 이메일
	private String admin_memo;					// 관리자 메모, auth_level 0 인 최고 관리자만 작성 및 보기 가능
	private String recommendation;				// 추천 키워드
	private String recommendationDecrypt;
	private String auth_level;						// 권한 - 0:전체최고관리자 1:회사최고관리자 2: 회사직원 3:상담사 4:매니져
	private String company_name;				// 소속회사 명
	private String[] sel_admin_seq;			// 선택된 관리자 순번
	private List<AdminDTO> list;
	private String worker_menu;					//구직자 관리 권한
	private String work_menu;					//구인자 관리 권한
	private String worker_app;
	private String work_app;
	private String share_yn;
	private String work_app_sms;				//app 오더시 SMS 발송 유무
	private String push_token;
	private String web_token;					// Web token
	private String app_push_use_yn;
	private String order_use_yn;
	private String ilbo_use_yn;
	private String static_use_yn;
	private String setting_use_yn;
	private String counselor_rate;				// 상담사 수익율
	
	private String minimumReservesFlag = "0"; 	// 최소 적립금 유지유무 ( 로그인 할때만 사용 0: 충족, 1: 미충족 )
	
	private String auto_company_use_yn;
	
	private String admin_comment;
	
	public String getAuto_company_use_yn() {
		return auto_company_use_yn;
	}

	public void setAuto_company_use_yn(String auto_company_use_yn) {
		this.auto_company_use_yn = auto_company_use_yn;
	}

	public String getRecommendationDecrypt() {
		return recommendationDecrypt;
	}

	public void setRecommendationDecrypt(String recommendationDecrypt) {
		this.recommendationDecrypt = recommendationDecrypt;
	}

	public String getOrder_use_yn() {
		return order_use_yn;
	}

	public void setOrder_use_yn(String order_use_yn) {
		this.order_use_yn = order_use_yn;
	}

	public String getIlbo_use_yn() {
		return ilbo_use_yn;
	}

	public void setIlbo_use_yn(String ilbo_use_yn) {
		this.ilbo_use_yn = ilbo_use_yn;
	}

	public String getStatic_use_yn() {
		return static_use_yn;
	}

	public void setStatic_use_yn(String static_use_yn) {
		this.static_use_yn = static_use_yn;
	}

	public String getSetting_use_yn() {
		return setting_use_yn;
	}

	public void setSetting_use_yn(String setting_use_yn) {
		this.setting_use_yn = setting_use_yn;
	}

	public String getApp_push_use_yn() {
		return app_push_use_yn;
	}
	
	public void setApp_push_use_yn(String app_push_use_yn) {
		this.app_push_use_yn = app_push_use_yn;
	}
	
	public String getWeb_push_use_yn() {
		return web_push_use_yn;
	}
	public void setWeb_push_use_yn(String web_push_use_yn) {
		this.web_push_use_yn = web_push_use_yn;
	}
	private String web_push_use_yn;							// Web Push 사용 유무

	public String getWeb_token() {
		return web_token;
	}
	public void setWeb_token(String web_token) {
		this.web_token = web_token;
	}
	public String getAdmin_seq() {
		return admin_seq;
	}
	public void setAdmin_seq(String admin_seq) {
		this.admin_seq = admin_seq;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}


	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_pass() {
		return admin_pass;
	}
	public void setAdmin_pass(String admin_pass) {
		this.admin_pass = admin_pass;
	}
	public String getAdmin_phone() {
		return StringUtil.numberFormat(admin_phone);
	}
	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}
	public String getAdmin_email() {
		return admin_email;
	}
	public void setAdmin_email(String admin_email) {
		this.admin_email = admin_email;
	}
	public String getAdmin_memo() {
		return admin_memo;
	}
	public void setAdmin_memo(String admin_memo) {
		this.admin_memo = admin_memo;
	}

	public String getAuth_level() {
		return auth_level;
	}
	public void setAuth_level(String auth_level) {
		this.auth_level = auth_level;
	}

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}


	public String[] getSel_admin_seq() {
		return sel_admin_seq;
	}
	public void setSel_admin_seq(String[] sel_admin_seq) {
		this.sel_admin_seq = sel_admin_seq;
	}


	public List<AdminDTO> getList() {
		return list;
	}
	public void setList(List<AdminDTO> list) {
		this.list = list;
	}
	public String getWorker_menu() {
		return worker_menu;
	}
	public void setWorker_menu(String worker_menu) {
		this.worker_menu = worker_menu;
	}
	public String getWork_menu() {
		return work_menu;
	}
	public void setWork_menu(String work_menu) {
		this.work_menu = work_menu;
	}
	public String getWorker_app() {
		return worker_app;
	}
	public void setWorker_app(String worker_app) {
		this.worker_app = worker_app;
	}
	public String getWork_app() {
		return work_app;
	}
	public void setWork_app(String work_app) {
		this.work_app = work_app;
	}
	public String getShare_yn() {
		return share_yn;
	}
	public void setShare_yn(String share_yn) {
		this.share_yn = share_yn;
	}
	public String getWork_app_sms() {
		return work_app_sms;
	}
	public void setWork_app_sms(String work_app_sms) {
		this.work_app_sms = work_app_sms;
	}
	public String getPush_token() {
		return push_token;
	}
	public void setPush_token(String push_token) {
		this.push_token = push_token;
	}

	public String getCounselor_rate() {
		return counselor_rate;
	}

	public void setCounselor_rate(String counselor_rate) {
		this.counselor_rate = counselor_rate;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getMinimumReservesFlag() {
		return minimumReservesFlag;
	}

	public void setMinimumReservesFlag(String minimumReservesFlag) {
		this.minimumReservesFlag = minimumReservesFlag;
	}

	public String getAdmin_comment() {
		return admin_comment;
	}

	public void setAdmin_comment(String admin_comment) {
		this.admin_comment = admin_comment;
	}

}
