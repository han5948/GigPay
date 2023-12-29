package com.nemo.kr.dto;

public class LaborContractDTO extends DefaultDTO {
	private static final long serialVersionUID = 1L;
	
	private String labor_contract_seq;
	private String labor_contract_use_yn;
	private String labor_contract_manager_type;
	private String labor_contract_manager_seq;
	
	public String getLabor_contract_seq() {
		return labor_contract_seq;
	}
	public void setLabor_contract_seq(String labor_contract_seq) {
		this.labor_contract_seq = labor_contract_seq;
	}
	public String getLabor_contract_use_yn() {
		return labor_contract_use_yn;
	}
	public void setLabor_contract_use_yn(String labor_contract_use_yn) {
		this.labor_contract_use_yn = labor_contract_use_yn;
	}
	public String getLabor_contract_manager_type() {
		return labor_contract_manager_type;
	}
	public void setLabor_contract_manager_type(String labor_contract_manager_type) {
		this.labor_contract_manager_type = labor_contract_manager_type;
	}
	public String getLabor_contract_manager_seq() {
		return labor_contract_manager_seq;
	}
	public void setLabor_contract_manager_seq(String labor_contract_manager_seq) {
		this.labor_contract_manager_seq = labor_contract_manager_seq;
	}
	
}
