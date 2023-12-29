package com.nemo.kr.dto;

public class TaxbillDTO extends GridDefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String tax_seq;
	
	//공급자 정보
	private String company_seq;					//지점 순번
	private String business_name;				//지점 사업자 상호
	private String company_num;					//지점 사업자 등록번호
	private String company_name;				//지점 명
	private String company_owner;				//지점 대표자
	private String company_addr;				//지점 주소
	private String company_business;			//지점 업태
	private String company_sector;				//지점 업종
	private String company_email;				//지점 이메일
	
	//공급받는 자 정보
	private String employer_seq;				//구인처 순번
	private String employer_num;				//구인처 사업자 등록번호
	private String employer_name;				//구인처 명
	private String employer_owner;				//구인처 대표자
	private String employer_addr;				//구인처 주소
	private String employer_business;			//구인처 업태
	private String employer_sector;				//구인처 업종
	private String employer_email;				//구인처 이메일
	private String employer_corp_scan_yn;		//구인처 사업자등록증 스캔 여부
	
	//공통
	private String write_date;					//작성일자
	private String subject;						//품목
	private String claim;						//영수/청구
	private String supply_price;				//공급가액
	private String tax_status;					//발행상태 0:발행 예정, 1:발행 완료, 2:발행 취소
	private String tax_memo;
	
	private String[] sel_seq_arr;
	
	public String getTax_seq() {
		return tax_seq;
	}
	public void setTax_seq(String tax_seq) {
		this.tax_seq = tax_seq;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getCompany_num() {
		return company_num;
	}
	public void setCompany_num(String company_num) {
		this.company_num = company_num;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_owner() {
		return company_owner;
	}
	public void setCompany_owner(String company_owner) {
		this.company_owner = company_owner;
	}
	public String getCompany_business() {
		return company_business;
	}
	public void setCompany_business(String company_business) {
		this.company_business = company_business;
	}
	public String getCompany_sector() {
		return company_sector;
	}
	public void setCompany_sector(String company_sector) {
		this.company_sector = company_sector;
	}
	public String getCompany_email() {
		return company_email;
	}
	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	public String getEmployer_num() {
		return employer_num;
	}
	public void setEmployer_num(String employer_num) {
		this.employer_num = employer_num;
	}
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getEmployer_owner() {
		return employer_owner;
	}
	public void setEmployer_owner(String employer_owner) {
		this.employer_owner = employer_owner;
	}
	public String getEmployer_business() {
		return employer_business;
	}
	public void setEmployer_business(String employer_business) {
		this.employer_business = employer_business;
	}
	public String getEmployer_sector() {
		return employer_sector;
	}
	public void setEmployer_sector(String employer_sector) {
		this.employer_sector = employer_sector;
	}
	public String getEmployer_email() {
		return employer_email;
	}
	public void setEmployer_email(String employer_email) {
		this.employer_email = employer_email;
	}
	public String getSupply_price() {
		return supply_price;
	}
	public void setSupply_price(String supply_price) {
		this.supply_price = supply_price;
	}
	public String getTax_status() {
		return tax_status;
	}
	public void setTax_status(String tax_status) {
		this.tax_status = tax_status;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getClaim() {
		return claim;
	}
	public void setClaim(String claim) {
		this.claim = claim;
	}
	public String getEmployer_addr() {
		return employer_addr;
	}
	public void setEmployer_addr(String employer_addr) {
		this.employer_addr = employer_addr;
	}
	public String getCompany_addr() {
		return company_addr;
	}
	public void setCompany_addr(String company_addr) {
		this.company_addr = company_addr;
	}
	public String getEmployer_corp_scan_yn() {
		return employer_corp_scan_yn;
	}
	public void setEmployer_corp_scan_yn(String employer_corp_scan_yn) {
		this.employer_corp_scan_yn = employer_corp_scan_yn;
	}
	public String[] getSel_seq_arr() {
		return sel_seq_arr;
	}
	public void setSel_seq_arr(String[] sel_seq_arr) {
		this.sel_seq_arr = sel_seq_arr;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getTax_memo() {
		return tax_memo;
	}
	public void setTax_memo(String tax_memo) {
		this.tax_memo = tax_memo;
	}
}
