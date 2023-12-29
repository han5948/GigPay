package com.nemo.kr.dto;

public class ManagerSubInduceDTO extends ManagerInduceDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String manager_sub_seq;
	private String manager_induce_seq;
	private String worker_seq;
	private String worker_name;
	private String manager_sub_memo;
	private String flag;
	private String reg_worker;
	private String worker_company_seq;
	private String worker_company_name;
	
	public String getWorker_name() {
		return worker_name;
	}
	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
	}
	public String getManager_induce_seq() {
		return manager_induce_seq;
	}
	public void setManager_induce_seq(String manager_induce_seq) {
		this.manager_induce_seq = manager_induce_seq;
	}
	public String getWorker_company_seq() {
		return worker_company_seq;
	}
	public void setWorker_company_seq(String worker_company_seq) {
		this.worker_company_seq = worker_company_seq;
	}
	public String getWorker_company_name() {
		return worker_company_name;
	}
	public void setWorker_company_name(String worker_company_name) {
		this.worker_company_name = worker_company_name;
	}
	public String getWorker_seq() {
		return worker_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}
	public String getManager_sub_seq() {
		return manager_sub_seq;
	}
	public void setManager_sub_seq(String manager_sub_seq) {
		this.manager_sub_seq = manager_sub_seq;
	}
	public String getManager_sub_memo() {
		return manager_sub_memo;
	}
	public void setManager_sub_memo(String manager_sub_memo) {
		this.manager_sub_memo = manager_sub_memo;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getReg_worker() {
		return reg_worker;
	}
	public void setReg_worker(String reg_worker) {
		this.reg_worker = reg_worker;
	}
}
