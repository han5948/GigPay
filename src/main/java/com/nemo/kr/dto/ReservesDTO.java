package com.nemo.kr.dto;

public class ReservesDTO extends GridDefaultDTO {
	 private String reserves_seq;
	 private String company_seq;
	 private String dest_company_seq;
	 private String reserves_date;
	 private String reserves_price;
	 private String reserves_req_admin;
	 private String reserves_req_memo;
	 private String reserves_process_status;
	 private String reserves_res_memo;
	 private String reserves_res_admin;
	 private String reserves_res_date;
	 private String reserves_type;
	 private String use_yn;
	 private String del_yn;
	 private String reg_admin;
	 private String reg_date;
	 private String mod_date;
	 private String mod_admin;
	 
	public String getDest_company_seq() {
		return dest_company_seq;
	}
	public void setDest_company_seq(String dest_company_seq) {
		this.dest_company_seq = dest_company_seq;
	}
	public String getReserves_req_admin() {
		return reserves_req_admin;
	}
	public void setReserves_req_admin(String reserves_req_admin) {
		this.reserves_req_admin = reserves_req_admin;
	}
	public String getReserves_req_memo() {
		return reserves_req_memo;
	}
	public void setReserves_req_memo(String reserves_req_memo) {
		this.reserves_req_memo = reserves_req_memo;
	}
	public String getReserves_res_memo() {
		return reserves_res_memo;
	}
	public void setReserves_res_memo(String reserves_res_memo) {
		this.reserves_res_memo = reserves_res_memo;
	}
	public String getReserves_res_admin() {
		return reserves_res_admin;
	}
	public void setReserves_res_admin(String reserves_res_admin) {
		this.reserves_res_admin = reserves_res_admin;
	}
	public String getReserves_res_date() {
		return reserves_res_date;
	}
	public void setReserves_res_date(String reserves_res_date) {
		this.reserves_res_date = reserves_res_date;
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
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	public String getMod_admin() {
		return mod_admin;
	}
	public void setMod_admin(String mod_admin) {
		this.mod_admin = mod_admin;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getReserves_seq() {
		return reserves_seq;
	}
	public void setReserves_seq(String reserves_seq) {
		this.reserves_seq = reserves_seq;
	}
	public String getReserves_date() {
		return reserves_date;
	}
	public void setReserves_date(String reserves_date) {
		this.reserves_date = reserves_date;
	}
	public String getReserves_price() {
		return reserves_price;
	}
	public void setReserves_price(String reserves_price) {
		this.reserves_price = reserves_price;
	}
	public String getReserves_process_status() {
		return reserves_process_status;
	}
	public void setReserves_process_status(String reserves_process_status) {
		this.reserves_process_status = reserves_process_status;
	}
	public String getReserves_type() {
		return reserves_type;
	}
	public void setReserves_type(String reserves_type) {
		this.reserves_type = reserves_type;
	}
}
