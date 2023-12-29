package com.nemo.kr.dto;

public class CareerLogDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String log_seq;
	private String ilbo_seq;
	private String career_name;
	
	public String getLog_seq() {
		return log_seq;
	}
	public void setLog_seq(String log_seq) {
		this.log_seq = log_seq;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getCareer_name() {
		return career_name;
	}
	public void setCareer_name(String career_name) {
		this.career_name = career_name;
	}
}
