package com.nemo.kr.dto;

public class NotificationLogDTO extends DefaultDTO {
	private String notification_log_seq;
	private String notification_seq;
	private String admin_seq;
	private String notification_type;
	private String company_seq;
	private String read_yn;
	private String reg_date;
	private String read_date;
	
	
	
	public String getNotification_seq() {
		return notification_seq;
	}
	public void setNotification_seq(String notification_seq) {
		this.notification_seq = notification_seq;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getNotification_type() {
		return notification_type;
	}
	public void setNotification_type(String notification_type) {
		this.notification_type = notification_type;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getNotification_log_seq() {
		return notification_log_seq;
	}
	public void setNotification_log_seq(String notification_log_seq) {
		this.notification_log_seq = notification_log_seq;
	}
	public String getAdmin_seq() {
		return admin_seq;
	}
	public void setAdmin_seq(String admin_seq) {
		this.admin_seq = admin_seq;
	}
	public String getRead_yn() {
		return read_yn;
	}
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}
	public String getRead_date() {
		return read_date;
	}
	public void setRead_date(String read_date) {
		this.read_date = read_date;
	}
	
}
