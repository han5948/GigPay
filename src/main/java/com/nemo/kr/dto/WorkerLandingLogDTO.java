package com.nemo.kr.dto;

public class WorkerLandingLogDTO {
	
	private static final long serialVersionUID = -5294743880954844412L;
	
	private String worker_landing_seq;
	private String connection_ip;
	private String os_type;
	private String reg_date;
	
	public String getWorker_landing_seq() {
		return worker_landing_seq;
	}
	public void setWorker_landing_seq(String worker_landing_seq) {
		this.worker_landing_seq = worker_landing_seq;
	}
	public String getConnection_ip() {
		return connection_ip;
	}
	public void setConnection_ip(String connection_ip) {
		this.connection_ip = connection_ip;
	}
	public String getOs_type() {
		return os_type;
	}
	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
}
