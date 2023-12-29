package com.nemo.kr.dto;

import java.util.List;

import com.nemo.kr.util.StringUtil;

public class IlboDeductibleDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String deduction_seq;
	private String ilbo_seq;
	private String deduction_status;
	private String deduction_order;
	private String reg_date;
	private List<IlboDTO> list;
	private String income_tax_price;
	private String local_tax_price;
	private String employer_insurance_price;
	private String national_pension_price;
	private String health_insurance_price;
	private String care_insurance_price;
	
	public String getDeducatible_sum() {
		return	StringUtil.isNullToInt(income_tax_price) + StringUtil.isNullToInt(local_tax_price) 
			+ StringUtil.isNullToInt(employer_insurance_price) + StringUtil.isNullToInt(national_pension_price) 
			+ StringUtil.isNullToInt(health_insurance_price) + StringUtil.isNullToInt(care_insurance_price) + "";
	}
	public String getIncome_tax_price() {
		return income_tax_price;
	}
	public void setIncome_tax_price(String income_tax_price) {
		this.income_tax_price = income_tax_price;
	}
	public String getLocal_tax_price() {
		return local_tax_price;
	}
	public void setLocal_tax_price(String local_tax_price) {
		this.local_tax_price = local_tax_price;
	}
	public String getEmployer_insurance_price() {
		return employer_insurance_price;
	}
	public void setEmployer_insurance_price(String employer_insurance_price) {
		this.employer_insurance_price = employer_insurance_price;
	}
	public String getNational_pension_price() {
		return national_pension_price;
	}
	public void setNational_pension_price(String national_pension_price) {
		this.national_pension_price = national_pension_price;
	}
	public String getHealth_insurance_price() {
		return health_insurance_price;
	}
	public void setHealth_insurance_price(String health_insurance_price) {
		this.health_insurance_price = health_insurance_price;
	}
	public String getCare_insurance_price() {
		return care_insurance_price;
	}
	public void setCare_insurance_price(String care_insurance_price) {
		this.care_insurance_price = care_insurance_price;
	}
	public String getDeduction_order() {
		return deduction_order;
	}
	public void setDeduction_order(String deduction_order) {
		this.deduction_order = deduction_order;
	}
	public List<IlboDTO> getList() {
		return list;
	}
	public void setList(List<IlboDTO> list) {
		this.list = list;
	}
	public String getDeduction_seq() {
		return deduction_seq;
	}
	public void setDeduction_seq(String deduction_seq) {
		this.deduction_seq = deduction_seq;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getDeduction_status() {
		return deduction_status;
	}
	public void setDeduction_status(String deduction_status) {
		this.deduction_status = deduction_status;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
}
