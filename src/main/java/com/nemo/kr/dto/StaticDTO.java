package com.nemo.kr.dto;

/**
  * @FileName : StaticDTO.java
  * @Project : ilgaja
  * @Date : 2021. 1. 27. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 통계DTO
  */
public class StaticDTO extends GridDefaultDTO {
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6139503819718430953L;
	
	private String staticMode = "SALES";		// 통계 S: 매출 통계
	private String searchMode;					// MD이번달일별 D 일별 Y:년별 DD 일별기간    
	private String company_seq;                // 회사 순번
	private String work_company_seq;
	private String worker_company_seq;
	private String company_name;
		
	private String ilbo_date;
	private long unitPriceSum;
	private long feeSum;
	private long shareSum;
	private long deducationSum;
	private long paySum;
	private long counselorSum;
	private String admin_id;
	private String output_cnt;
	
	private long companySum;
	private long openSum;
	private long jnpSum;
	
	public String getOutput_cnt() {
		return output_cnt;
	}

	public void setOutput_cnt(String output_cnt) {
		this.output_cnt = output_cnt;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getCompany_seq() {
		return company_seq;
	}

	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}

	public String getStaticMode() {
		return staticMode;
	}

	public void setStaticMode(String staticMode) {
		this.staticMode = staticMode;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getIlbo_date() {
		return ilbo_date;
	}

	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
	}


	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public long getUnitPriceSum() {
		return unitPriceSum;
	}

	public void setUnitPriceSum(long unitPriceSum) {
		this.unitPriceSum = unitPriceSum;
	}

	public long getFeeSum() {
		return feeSum;
	}

	public void setFeeSum(long feeSum) {
		this.feeSum = feeSum;
	}

	public long getShareSum() {
		return shareSum;
	}

	public void setShareSum(long shareSum) {
		this.shareSum = shareSum;
	}

	public long getDeducationSum() {
		return deducationSum;
	}

	public void setDeducationSum(long deducationSum) {
		this.deducationSum = deducationSum;
	}

	public long getPaySum() {
		return paySum;
	}

	public void setPaySum(long paySum) {
		this.paySum = paySum;
	}

	public long getCounselorSum() {
		return counselorSum;
	}

	public void setCounselorSum(long counselorSum) {
		this.counselorSum = counselorSum;
	}

	public String getWork_company_seq() {
		return work_company_seq;
	}

	public void setWork_company_seq(String work_company_seq) {
		this.work_company_seq = work_company_seq;
	}

	public String getWorker_company_seq() {
		return worker_company_seq;
	}

	public void setWorker_company_seq(String worker_company_seq) {
		this.worker_company_seq = worker_company_seq;
	}

	public long getCompanySum() {
		return companySum;
	}

	public void setCompanySum(long companySum) {
		this.companySum = companySum;
	}

	public long getOpenSum() {
		return openSum;
	}

	public void setOpenSum(long openSum) {
		this.openSum = openSum;
	}

	public long getJnpSum() {
		return jnpSum;
	}

	public void setJnpSum(long jnpSum) {
		this.jnpSum = jnpSum;
	}


	
	 
}
