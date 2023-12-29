package com.nemo.kr.dto;

public class SignManagementDTO extends IlboDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String management_seq;
	private String ilbo_seq;
	private String category_seq;
	private String category_code;
	private String category_name;
	private String doc_number;
	private String company_sign_flag;
	private String worker_sign_flag;
	private String work_sign_flag;
	private String company_phone;
	private String company_owner;
	private String employer_name;
	private String sign_company_name;
	private String complete_date;
	
	public String getComplete_date() {
		return complete_date;
	}
	public void setComplete_date(String complete_date) {
		this.complete_date = complete_date;
	}
	public String getSign_company_name() {
		return sign_company_name;
	}
	public void setSign_company_name(String sign_company_name) {
		this.sign_company_name = sign_company_name;
	}
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getCompany_phone() {
		return company_phone;
	}
	public void setCompany_phone(String company_phone) {
		this.company_phone = company_phone;
	}
	public String getCompany_owner() {
		return company_owner;
	}
	public void setCompany_owner(String company_owner) {
		this.company_owner = company_owner;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getManagement_seq() {
		return management_seq;
	}
	public void setManagement_seq(String management_seq) {
		this.management_seq = management_seq;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getCategory_seq() {
		return category_seq;
	}
	public void setCategory_seq(String category_seq) {
		this.category_seq = category_seq;
	}
	public String getDoc_number() {
		return doc_number;
	}
	public void setDoc_number(String doc_number) {
		this.doc_number = doc_number;
	}
	public String getCompany_sign_flag() {
		return company_sign_flag;
	}
	public void setCompany_sign_flag(String company_sign_flag) {
		this.company_sign_flag = company_sign_flag;
	}
	public String getWorker_sign_flag() {
		return worker_sign_flag;
	}
	public void setWorker_sign_flag(String worker_sign_flag) {
		this.worker_sign_flag = worker_sign_flag;
	}
	public String getWork_sign_flag() {
		return work_sign_flag;
	}
	public void setWork_sign_flag(String work_sign_flag) {
		this.work_sign_flag = work_sign_flag;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	
}
