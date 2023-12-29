package com.nemo.kr.dto;

public class InquiryDTO extends GridDefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String inquiry_seq;
	private String company_name;
	private String work_manager_name;
	private String work_manager_phone;
	private String work_manager_mail;
	private String inquiry_content;
	private String inquiry_memo;
	private String[] arr_inquiry_seq;
	
	public String[] getArr_inquiry_seq() {
		return arr_inquiry_seq;
	}
	public void setArr_inquiry_seq(String[] arr_inquiry_seq) {
		this.arr_inquiry_seq = arr_inquiry_seq;
	}
	public String getInquiry_seq() {
		return inquiry_seq;
	}
	public void setInquiry_seq(String inquiry_seq) {
		this.inquiry_seq = inquiry_seq;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getWork_manager_name() {
		return work_manager_name;
	}
	public void setWork_manager_name(String work_manager_name) {
		this.work_manager_name = work_manager_name;
	}
	public String getWork_manager_phone() {
		return work_manager_phone;
	}
	public void setWork_manager_phone(String work_manager_phone) {
		this.work_manager_phone = work_manager_phone;
	}
	public String getWork_manager_mail() {
		return work_manager_mail;
	}
	public void setWork_manager_mail(String work_manager_mail) {
		this.work_manager_mail = work_manager_mail;
	}
	public String getInquiry_content() {
		return inquiry_content;
	}
	public void setInquiry_content(String inquiry_content) {
		this.inquiry_content = inquiry_content;
	}
	public String getInquiry_memo() {
		return inquiry_memo;
	}
	public void setInquiry_memo(String inquiry_memo) {
		this.inquiry_memo = inquiry_memo;
	}
}
