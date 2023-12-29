package com.nemo.kr.dto;

public class ManagerInduceDTO extends GridDefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String manager_induce_seq;
	private String manager_seq;
	private String manager_type;
	private String manager_company_seq;
	private String manager_company_name;
	private String manager_name;
	private String manager_phone;
	private String manager_use_status;
	private String declaration_cnt;
	private String manager_induce_memo;
	private String reg_worker;
	private String last_declaration_date;
	private String[] mainGridArr;
	private String[] subGridArr;
	
	public String[] getMainGridArr() {
		return mainGridArr;
	}
	public void setMainGridArr(String[] mainGridArr) {
		this.mainGridArr = mainGridArr;
	}
	public String[] getSubGridArr() {
		return subGridArr;
	}
	public void setSubGridArr(String[] subGridArr) {
		this.subGridArr = subGridArr;
	}
	public String getLast_declaration_date() {
		return last_declaration_date;
	}
	public void setLast_declaration_date(String last_declaration_date) {
		this.last_declaration_date = last_declaration_date;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getManager_type() {
		return manager_type;
	}
	public void setManager_type(String manager_type) {
		this.manager_type = manager_type;
	}
	public String getManager_company_seq() {
		return manager_company_seq;
	}
	public void setManager_company_seq(String manager_company_seq) {
		this.manager_company_seq = manager_company_seq;
	}
	public String getManager_company_name() {
		return manager_company_name;
	}
	public void setManager_company_name(String manager_company_name) {
		this.manager_company_name = manager_company_name;
	}
	public String getManager_phone() {
		return manager_phone;
	}
	public void setManager_phone(String manager_phone) {
		this.manager_phone = manager_phone;
	}
	public String getManager_use_status() {
		return manager_use_status;
	}
	public void setManager_use_status(String manager_use_status) {
		this.manager_use_status = manager_use_status;
	}
	public String getManager_induce_seq() {
		return manager_induce_seq;
	}
	public void setManager_induce_seq(String manager_induce_seq) {
		this.manager_induce_seq = manager_induce_seq;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	public String getDeclaration_cnt() {
		return declaration_cnt;
	}
	public void setDeclaration_cnt(String declaration_cnt) {
		this.declaration_cnt = declaration_cnt;
	}
	public String getManager_induce_memo() {
		return manager_induce_memo;
	}
	public void setManager_induce_memo(String manager_induce_memo) {
		this.manager_induce_memo = manager_induce_memo;
	}
	public String getReg_worker() {
		return reg_worker;
	}
	public void setReg_worker(String reg_worker) {
		this.reg_worker = reg_worker;
	}
	
	
}
