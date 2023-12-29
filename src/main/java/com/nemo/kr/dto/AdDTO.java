package com.nemo.kr.dto;

public class AdDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String ad_seq;
	private long ad_justnonamt;
	private long ad_justamt;
	private String ad_date;
	private String ad_device;
	private String ad_networktype;
	private long ad_nonamt;
	private String ad_prductcode;
	private long ad_amt;
	private String reg_date;
	private String reg_admin;
	private String totalCnt;
	private String query;
	private String srh_networkType;
	private String srh_device;
	private String srh_date;
	
	public String getSrh_networkType() {
		return srh_networkType;
	}
	public void setSrh_networkType(String srh_networkType) {
		this.srh_networkType = srh_networkType;
	}
	public String getSrh_device() {
		return srh_device;
	}
	public void setSrh_device(String srh_device) {
		this.srh_device = srh_device;
	}
	public String getSrh_date() {
		return srh_date;
	}
	public void setSrh_date(String srh_date) {
		this.srh_date = srh_date;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getAd_seq() {
		return ad_seq;
	}
	public void setAd_seq(String ad_seq) {
		this.ad_seq = ad_seq;
	}
	public long getAd_justnonamt() {
		return ad_justnonamt;
	}
	public void setAd_justnonamt(long ad_justnonamt) {
		this.ad_justnonamt = ad_justnonamt;
	}
	public long getAd_justamt() {
		return ad_justamt;
	}
	public void setAd_justamt(long ad_justamt) {
		this.ad_justamt = ad_justamt;
	}
	public String getAd_date() {
		return ad_date;
	}
	public void setAd_date(String ad_date) {
		this.ad_date = ad_date;
	}
	public String getAd_device() {
		return ad_device;
	}
	public void setAd_device(String ad_device) {
		this.ad_device = ad_device;
	}
	public String getAd_networktype() {
		return ad_networktype;
	}
	public void setAd_networktype(String ad_networktype) {
		this.ad_networktype = ad_networktype;
	}
	public long getAd_nonamt() {
		return ad_nonamt;
	}
	public void setAd_nonamt(long ad_nonamt) {
		this.ad_nonamt = ad_nonamt;
	}
	public String getAd_prductcode() {
		return ad_prductcode;
	}
	public void setAd_prductcode(String ad_prductcode) {
		this.ad_prductcode = ad_prductcode;
	}
	public long getAd_amt() {
		return ad_amt;
	}
	public void setAd_amt(long ad_amt) {
		this.ad_amt = ad_amt;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_admin() {
		return reg_admin;
	}
	public void setReg_admin(String reg_admin) {
		this.reg_admin = reg_admin;
	}
	public String getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}
}
