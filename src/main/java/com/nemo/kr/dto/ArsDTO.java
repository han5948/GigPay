package com.nemo.kr.dto;

public class ArsDTO extends DefaultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6641422294511790920L;
	
	private String seq;
	private String user_cid;
	private String return_type;
	private String company_seq;
	private String receive_phone;
	private String result_code;
	private String memo;
	private String type_seq;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUser_cid() {
		return user_cid;
	}
	public void setUser_cid(String user_cid) {
		this.user_cid = user_cid;
	}
	public String getReturn_type() {
		return return_type;
	}
	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getReceive_phone() {
		return receive_phone;
	}
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getType_seq() {
		return type_seq;
	}
	public void setType_seq(String type_seq) {
		this.type_seq = type_seq;
	}
	
	


}
