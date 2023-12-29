package com.nemo.kr.dto;

public class AccountDTO extends CompanyDTO {
	private String account_seq;
	private String account_date;
	private String company_seq;
	private String dest_company_seq;
	private String account_type;
	private String worker_fee;
	private String account_price;
	private String account_flag;
	private String account_balance;
	private String totalAcc;
	private String use_yn;
	private String del_yn;
	private String reg_admin;
	private String reg_date;
	private String start_date;
	private String end_date;
	 
	private String dest_company_num;
	private String dest_business_name;
	private String dest_company_owner;
	private String dest_company_addr;
	private String dest_company_business;
	private String dest_company_sector;
	private String dest_company_email;
	
	private String srh_tax_type;
	private String srh_tax_type_kr;
	private String tax_amount;

	public String getDest_company_addr() {
		return dest_company_addr;
	}
	public void setDest_company_addr(String dest_company_addr) {
		this.dest_company_addr = dest_company_addr;
	}
	public String getDest_company_business() {
		return dest_company_business;
	}
	public void setDest_company_business(String dest_company_business) {
		this.dest_company_business = dest_company_business;
	}
	public String getDest_company_sector() {
		return dest_company_sector;
	}
	public void setDest_company_sector(String dest_company_sector) {
		this.dest_company_sector = dest_company_sector;
	}
	public String getDest_company_email() {
		return dest_company_email;
	}
	public void setDest_company_email(String dest_company_email) {
		this.dest_company_email = dest_company_email;
	}
	public String getDest_company_num() {
		return dest_company_num;
	}
	public void setDest_company_num(String dest_company_num) {
		this.dest_company_num = dest_company_num;
	}
	public String getDest_business_name() {
		return dest_business_name;
	}
	public void setDest_business_name(String dest_business_name) {
		this.dest_business_name = dest_business_name;
	}
	public String getDest_company_owner() {
		return dest_company_owner;
	}
	public void setDest_company_owner(String dest_company_owner) {
		this.dest_company_owner = dest_company_owner;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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
	private String mod_date;
	 private String mod_admin;
	 
	public String getTotalAcc() {
		return totalAcc;
	}
	public void setTotalAcc(String totalAcc) {
		this.totalAcc = totalAcc;
	}
	public String getAccount_seq() {
		return account_seq;
	}
	public void setAccount_seq(String account_seq) {
		this.account_seq = account_seq;
	}
	public String getAccount_date() {
		return account_date;
	}
	public void setAccount_date(String account_date) {
		this.account_date = account_date;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getDest_company_seq() {
		return dest_company_seq;
	}
	public void setDest_company_seq(String dest_company_seq) {
		this.dest_company_seq = dest_company_seq;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getAccount_price() {
		return account_price;
	}
	public void setAccount_price(String account_price) {
		this.account_price = account_price;
	}
	public String getAccount_flag() {
		return account_flag;
	}
	public void setAccount_flag(String account_flag) {
		this.account_flag = account_flag;
	}
	public String getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(String account_balance) {
		this.account_balance = account_balance;
	}
	public String getSrh_tax_type() {
		return srh_tax_type;
	}
	public void setSrh_tax_type(String srh_tax_type) {
		this.srh_tax_type = srh_tax_type;
	}
	public String getTax_amount() {
		return tax_amount;
	}
	public void setTax_amount(String tax_amount) {
		this.tax_amount = tax_amount;
	}
	public String getSrh_tax_type_kr() {
		
		if( this.srh_tax_type == null || this.srh_tax_type.isEmpty() ) {
			return "";
		}
		
		if( this.srh_tax_type.equals("systemFee") ) {
			return "시스템이용료";
		}else if( this.srh_tax_type.equals("runningRoyalty") ) {
			return "로얄티";
		}
		
		return "";
	}
	public String getWorker_fee() {
		return worker_fee;
	}
	public void setWorker_fee(String worker_fee) {
		this.worker_fee = worker_fee;
	}
}
