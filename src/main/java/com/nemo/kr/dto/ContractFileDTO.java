package com.nemo.kr.dto;

public class ContractFileDTO extends ContractDTO {
	private static final long serialVersionUID = 3944304017057338288L;

	private String contract_file_seq;
	private String fileName;
	private String fileUrl;
	private String orgFileName;
	private String contract_seq;
	
	public String getContract_file_seq() {
		return contract_file_seq;
	}
	public void setContract_file_seq(String contract_file_seq) {
		this.contract_file_seq = contract_file_seq;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	public String getContract_seq() {
		return contract_seq;
	}
	public void setContract_seq(String contract_seq) {
		this.contract_seq = contract_seq;
	}
}
