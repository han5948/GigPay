package com.nemo.kr.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaxRateDTO implements Serializable{
	
	private static final long serialVersionUID = -4743470319615629998L;
	
	private String tax_seq; 
	private String deduction_price;
	private String income_tax_rate;
	private String employer_insurance_rate ; 
	private String national_pension_rate;
	private String health_insurance_rate; 
	private String care_insurance_rate; 
	private String use_yn;
	private String effective_date;
	private String reg_admin; 
	private String reg_date;

	private String job_kind;
	
	private String[] del_tax_seq_arr;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getTax_seq() {
		return tax_seq;
	}
	public void setTax_seq(String tax_seq) {
		this.tax_seq = tax_seq;
	}
	public String getDeduction_price() {
		return deduction_price;
	}
	public void setDeduction_price(String deduction_price) {
		this.deduction_price = deduction_price;
	}
	public String getIncome_tax_rate() {
		return income_tax_rate;
	}
	public void setIncome_tax_rate(String income_tax_rate) {
		this.income_tax_rate = income_tax_rate;
	}
	public String getEmployer_insurance_rate() {
		return employer_insurance_rate;
	}
	public void setEmployer_insurance_rate(String employer_insurance_rate) {
		this.employer_insurance_rate = employer_insurance_rate;
	}
	public String getNational_pension_rate() {
		return national_pension_rate;
	}
	public void setNational_pension_rate(String national_pension_rate) {
		this.national_pension_rate = national_pension_rate;
	}
	public String getHealth_insurance_rate() {
		return health_insurance_rate;
	}
	public void setHealth_insurance_rate(String health_insurance_rate) {
		this.health_insurance_rate = health_insurance_rate;
	}
	public String getCare_insurance_rate() {
		return care_insurance_rate;
	}
	public void setCare_insurance_rate(String care_insurance_rate) {
		this.care_insurance_rate = care_insurance_rate;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getEffective_date() {
		return effective_date;
	}
	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
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

	public String[] getDel_tax_seq_arr() {
		return del_tax_seq_arr;
	}

	public void setDel_tax_seq_arr(String[] del_tax_seq_arr) {
		this.del_tax_seq_arr = del_tax_seq_arr;
	}


	public String getJob_kind() {
		return job_kind;
	}
	public void setJob_kind(String job_kind) {
		this.job_kind = job_kind;
	}
	
}
