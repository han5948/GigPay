package com.nemo.kr.dto;

import java.util.List;



/**
 * 일가자 코드 관리 DTO
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-26
 */
public class CodeLogDTO extends GridDefaultDTO {
	// nemojjang 2019. 5. 17.
	private static final long serialVersionUID = -614555688569970888L;
	
	private String log_seq;
	private String ilbo_seq;
	private String employer_seq;
	private String code_type;                           
	private String code_value;                              
	private String code_name;                           
	private String log_text;                             
	private String log_user_type;
	private String worker_seq;
	private String worker_company_seq;  
	private String work_seq;           
	private String work_company_seq;
	private String reg_admin;                  

	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	public String getLog_seq() {
		return log_seq;
	}
	public void setLog_seq(String log_seq) {
		this.log_seq = log_seq;
	}
	
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public String getCode_value() {
		return code_value;
	}
	public void setCode_value(String code_value) {
		this.code_value = code_value;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getLog_text() {
		return log_text;
	}
	public void setLog_text(String log_text) {
		this.log_text = log_text;
	}
	
	public String getReg_admin() {
		return reg_admin;
	}
	public void setReg_admin(String reg_admin) {
		this.reg_admin = reg_admin;
	}
	
	public String getLog_user_type() {
		return log_user_type;
	}
	public void setLog_user_type(String log_user_type) {
		this.log_user_type = log_user_type;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getWorker_company_seq() {
		return worker_company_seq;
	}
	public void setWorker_company_seq(String worker_company_seq) {
		this.worker_company_seq = worker_company_seq;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getWork_company_seq() {
		return work_company_seq;
	}
	public void setWork_company_seq(String work_company_seq) {
		this.work_company_seq = work_company_seq;
	}
	public String getWorker_seq() {
		return worker_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}                           


}
