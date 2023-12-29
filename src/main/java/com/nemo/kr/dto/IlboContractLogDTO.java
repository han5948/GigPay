package com.nemo.kr.dto;

public class IlboContractLogDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String log_seq;
	private String ilbo_seq;
	private String before_contract_seq;
	private String after_contract_seq;
	
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
	public String getBefore_contract_seq() {
		return before_contract_seq;
	}
	public void setBefore_contract_seq(String before_contract_seq) {
		this.before_contract_seq = before_contract_seq;
	}
	public String getAfter_contract_seq() {
		return after_contract_seq;
	}
	public void setAfter_contract_seq(String after_contract_seq) {
		this.after_contract_seq = after_contract_seq;
	}
}
