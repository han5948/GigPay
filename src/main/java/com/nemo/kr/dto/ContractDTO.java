package com.nemo.kr.dto;

public class ContractDTO extends CategoryDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String contract_seq;
	private String contractDetail;
	private String contractName;
	private String contract_type;
	private String category_seq;
	private String jsonText;
	private String useCnt;
	private String contract_flag;
	private String contract_name;
	private String contract_default_use_yn;
	private String category_code;
	
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getContract_name() {
		return contract_name;
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}
	public String getContract_default_use_yn() {
		return contract_default_use_yn;
	}
	public void setContract_default_use_yn(String contract_default_use_yn) {
		this.contract_default_use_yn = contract_default_use_yn;
	}
	public String getContract_flag() {
		return contract_flag;
	}
	public void setContract_flag(String contract_flag) {
		this.contract_flag = contract_flag;
	}
	public String getUseCnt() {
		return useCnt;
	}
	public void setUseCnt(String useCnt) {
		this.useCnt = useCnt;
	}
	public String getContract_seq() {
		return contract_seq;
	}
	public void setContract_seq(String contract_seq) {
		this.contract_seq = contract_seq;
	}
	public String getContractDetail() {
		return contractDetail;
	}
	public void setContractDetail(String contractDetail) {
		this.contractDetail = contractDetail;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	public String getCategory_seq() {
		return category_seq;
	}
	public void setCategory_seq(String category_seq) {
		this.category_seq = category_seq;
	}
	public String getJsonText() {
		return jsonText;
	}
	public void setJsonText(String jsonText) {
		this.jsonText = jsonText;
	}
}
