package com.nemo.kr.dto;

public class AccountingDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2035757321256312767L;
	
	
	private String company_seq;                 // 회사 순번
	private String company_name;
	
	private String admin_id;
	private String admin_name;
	
	private String ilbo_date;
	private String ilbo_unit_price;
	private String ilbo_fee;
	private String share_fee;
	private String counselor_fee;
	private String out_count;
	
	private String startDate;
	private String endDate;
	
	private String work_company_seq;
	private String worker_company_seq;
	private String sort_flag = "1";

	private String worker_fees;
	private String work_fees;
	
	private String day_of_week;
	private String total_sales;
	private String ad_price;
	
	private String work_income;					// 구인제공 수입금
	private String work_payment;				// 구인제공 지급금
	private String work_payment_worker_fee;		// 구인제공 (직수,페이 일 때 빼는 구직자 소개요금)
	private String worker_income;				// 구직제공 수입금
	private String worker_payment;				// 구직제공 지급금
	private String worker_income_worker_fee;	// 구직제공 (직수,페이 일 때 빼는 구직자 소개요금)
	
	private String work_fee_rate;				// 구인정산 수수료 비율
	private String worker_fee_rate;				// 구직정산 수수료 비율
	
	
	public String getWorker_fees() {
		return worker_fees;
	}
	public void setWorker_fees(String worker_fees) {
		this.worker_fees = worker_fees;
	}
	public String getWork_fees() {
		return work_fees;
	}
	public void setWork_fees(String work_fees) {
		this.work_fees = work_fees;
	}
	public String getSort_flag() {
		return sort_flag;
	}
	public void setSort_flag(String sort_flag) {
		this.sort_flag = sort_flag;
	}
	public String getWorker_company_seq() {
		return worker_company_seq;
	}
	public void setWorker_company_seq(String worker_company_seq) {
		this.worker_company_seq = worker_company_seq;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getIlbo_unit_price() {
		return ilbo_unit_price;
	}
	public void setIlbo_unit_price(String ilbo_unit_price) {
		this.ilbo_unit_price = ilbo_unit_price;
	}
	public String getIlbo_fee() {
		return ilbo_fee;
	}
	public void setIlbo_fee(String ilbo_fee) {
		this.ilbo_fee = ilbo_fee;
	}
	public String getShare_fee() {
		return share_fee;
	}
	public void setShare_fee(String share_fee) {
		this.share_fee = share_fee;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIlbo_date() {
		return ilbo_date;
	}
	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getDay_of_week() {
		return day_of_week;
	}
	public void setDay_of_week(String day_of_week) {
		this.day_of_week = day_of_week;
	}
	public String getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(String total_sales) {
		this.total_sales = total_sales;
	}
	public String getAd_price() {
		return ad_price;
	}
	public void setAd_price(String ad_price) {
		this.ad_price = ad_price;
	}
	public String getCounselor_fee() {
		return counselor_fee;
	}
	public void setCounselor_fee(String counselor_fee) {
		this.counselor_fee = counselor_fee;
	}
	public String getWork_company_seq() {
		return work_company_seq;
	}
	public void setWork_company_seq(String work_company_seq) {
		this.work_company_seq = work_company_seq;
	}
	public String getOut_count() {
		return out_count;
	}
	public void setOut_count(String out_count) {
		this.out_count = out_count;
	}
	public String getWork_income() {
		return work_income;
	}
	public void setWork_income(String work_income) {
		this.work_income = work_income;
	}
	public String getWork_payment() {
		return work_payment;
	}
	public void setWork_payment(String work_payment) {
		this.work_payment = work_payment;
	}
	public String getWorker_income() {
		return worker_income;
	}
	public void setWorker_income(String worker_income) {
		this.worker_income = worker_income;
	}
	public String getWorker_payment() {
		return worker_payment;
	}
	public void setWorker_payment(String worker_payment) {
		this.worker_payment = worker_payment;
	}
	public String getWork_payment_worker_fee() {
		return work_payment_worker_fee;
	}
	public void setWork_payment_worker_fee(String work_payment_worker_fee) {
		this.work_payment_worker_fee = work_payment_worker_fee;
	}
	public String getWorker_income_worker_fee() {
		return worker_income_worker_fee;
	}
	public void setWorker_income_worker_fee(String worker_income_worker_fee) {
		this.worker_income_worker_fee = worker_income_worker_fee;
	}
	public String getWork_fee_rate() {
		return work_fee_rate;
	}
	public void setWork_fee_rate(String work_fee_rate) {
		this.work_fee_rate = work_fee_rate;
	}
	public String getWorker_fee_rate() {
		return worker_fee_rate;
	}
	public void setWorker_fee_rate(String worker_fee_rate) {
		this.worker_fee_rate = worker_fee_rate;
	}

}
