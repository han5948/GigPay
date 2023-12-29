package com.nemo.kr.dto;

public class IssueDTO extends DefaultDTO {
	private static final long serialVersionUID = -5866656098823000167L;
	
	private String issue_seq;
	private String company_seq;
	private String company_name;
	private String duty_type;
	private String issue_date;
	private String issue_text;
	private String use_yn;
	private String del_yn;
	private String reg_admin;
	private String reg_date;
	private String mod_admin;
	private String mod_date;
	private String admin_seq;
	private String auth_level;
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getAuth_level() {
		return auth_level;
	}
	public void setAuth_level(String auth_level) {
		this.auth_level = auth_level;
	}
	public String getAdmin_seq() {
		return admin_seq;
	}
	public void setAdmin_seq(String admin_seq) {
		this.admin_seq = admin_seq;
	}
	public String getIssue_seq() {
		return issue_seq;
	}
	public void setIssue_seq(String issue_seq) {
		this.issue_seq = issue_seq;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getDuty_type() {
		return duty_type;
	}
	public void setDuty_type(String duty_type) {
		this.duty_type = duty_type;
	}
	public String getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}
	public String getIssue_text() {
		return issue_text;
	}
	public void setIssue_text(String issue_text) {
		this.issue_text = issue_text;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getReg_admin() {
		return reg_admin;
	}
	public void setReg_admin(String reg_admin) {
		this.reg_admin = reg_admin;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getMod_admin() {
		return mod_admin;
	}
	public void setMod_admin(String mod_admin) {
		this.mod_admin = mod_admin;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
}
