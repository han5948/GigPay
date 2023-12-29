package com.nemo.kr.dto;

import java.util.List;

/**
  * @FileName : TransferWorkerLocationDTO.java
  * @Date : 2023. 2. 2. 
  * @작성자 : Na GilJin
  * @프로그램 설명 : 구직자이관 지역설정
  */
public class TransferWorkerLocationDTO extends DefaultDTO {

	private static final long serialVersionUID = 8929677834140930149L;

	private String location_seq;
	private String transfer_seq;
	private String sido_code;
	private String sido_name;
	private String sigungu_code;
	private String sigungu_name;
	private String eupmyeondong_code;
	private String eupmyeondong_name;
	
	private String[] del_location_list;
	private String[] sel_transfer_seq;
	private List<TransferWorkerLocationDTO> add_location_list;
	
	
	public String[] getDel_location_list() {
		return del_location_list;
	}
	public void setDel_location_list(String[] del_location_list) {
		this.del_location_list = del_location_list;
	}
	public List<TransferWorkerLocationDTO> getAdd_location_list() {
		return add_location_list;
	}
	public void setAdd_location_list(List<TransferWorkerLocationDTO> add_location_list) {
		this.add_location_list = add_location_list;
	}
	public String getTransfer_seq() {
		return transfer_seq;
	}
	public void setTransfer_seq(String transfer_seq) {
		this.transfer_seq = transfer_seq;
	}
	public String getSido_code() {
		return sido_code;
	}
	public void setSido_code(String sido_code) {
		this.sido_code = sido_code;
	}
	public String getSido_name() {
		return sido_name;
	}
	public void setSido_name(String sido_name) {
		this.sido_name = sido_name;
	}
	public String getSigungu_code() {
		return sigungu_code;
	}
	public void setSigungu_code(String sigungu_code) {
		this.sigungu_code = sigungu_code;
	}
	public String getSigungu_name() {
		return sigungu_name;
	}
	public void setSigungu_name(String sigungu_name) {
		this.sigungu_name = sigungu_name;
	}
	public String getEupmyeondong_code() {
		return eupmyeondong_code;
	}
	public void setEupmyeondong_code(String eupmyeondong_code) {
		this.eupmyeondong_code = eupmyeondong_code;
	}
	public String getEupmyeondong_name() {
		return eupmyeondong_name;
	}
	public void setEupmyeondong_name(String eupmyeondong_name) {
		this.eupmyeondong_name = eupmyeondong_name;
	}
	public String getLocation_seq() {
		return location_seq;
	}
	public void setLocation_seq(String location_seq) {
		this.location_seq = location_seq;
	}
	public String[] getSel_transfer_seq() {
		return sel_transfer_seq;
	}
	public void setSel_transfer_seq(String[] sel_transfer_seq) {
		this.sel_transfer_seq = sel_transfer_seq;
	}
	
}
