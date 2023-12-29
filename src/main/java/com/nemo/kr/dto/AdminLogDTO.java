package com.nemo.kr.dto;

import java.io.Serializable;


/**
  * @FileName : AdminLogDTO.java
  * @Project : ilgaja
  * @Date : 2022. 1. 21. 
  * @작성자 : Nagiljin
  * @변경이력 :
  * @프로그램 설명 : 관리자 로그 DTO
  */
public class AdminLogDTO implements Serializable {

	private static final long serialVersionUID = -5786641609538363632L;
	
	private String log_seq;						// 로그 순번 - PK
	private String admin_seq;					// 관리자 순번
	private String admin_id;					// 관리자 아이디
	private String reg_date;					// 로그 등록 날짜
	private String menu_code;
	private String menu_name;
	private String menu_path;
	private String params;
	private String os_type;
	
	public String getLog_seq() {
		return log_seq;
	}
	public void setLog_seq(String log_seq) {
		this.log_seq = log_seq;
	}
	public String getAdmin_seq() {
		return admin_seq;
	}
	public void setAdmin_seq(String admin_seq) {
		this.admin_seq = admin_seq;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getMenu_code() {
		return menu_code;
	}
	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}
	public String getMenu_path() {
		return menu_path;
	}
	public void setMenu_path(String menu_path) {
		this.menu_path = menu_path;
	}
	public String getOs_type() {
		return os_type;
	}
	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}
	
	
}
