package com.nemo.kr.dto;

/**
  * @FileName : PushLogDTO.java
  * @Date : 2020. 11. 3. 
  * @작성자 : Park YunSoo
  * @프로그램 설명 :
  */
public class PushLogDTO {
	private String push_log_seq;
	private String push_type;
	private String total_cnt;
	private String success_cnt;
	private String fail_cnt;
	private String push_time;
	private String reg_date;
	private String status_code;
	private String contents_seq;
	
	private String receive_push_log;
	private String receive_seq;
	private String receive_type;
	private String push_result;
	private String result_msg;
	private String read_yn;
	private String read_yn_time;
	private String mod_date;
	
	public String getPush_log_seq() {
		return push_log_seq;
	}
	public void setPush_log_seq(String push_log_seq) {
		this.push_log_seq = push_log_seq;
	}
	public String getPush_type() {
		return push_type;
	}
	public void setPush_type(String push_type) {
		this.push_type = push_type;
	}
	public String getTotal_cnt() {
		return total_cnt;
	}
	public void setTotal_cnt(String total_cnt) {
		this.total_cnt = total_cnt;
	}
	public String getSuccess_cnt() {
		return success_cnt;
	}
	public void setSuccess_cnt(String success_cnt) {
		this.success_cnt = success_cnt;
	}
	public String getFail_cnt() {
		return fail_cnt;
	}
	public void setFail_cnt(String fail_cnt) {
		this.fail_cnt = fail_cnt;
	}
	public String getPush_time() {
		return push_time;
	}
	public void setPush_time(String push_time) {
		this.push_time = push_time;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getReceive_push_log() {
		return receive_push_log;
	}
	public void setReceive_push_log(String receive_push_log) {
		this.receive_push_log = receive_push_log;
	}
	public String getReceive_seq() {
		return receive_seq;
	}
	public void setReceive_seq(String receive_seq) {
		this.receive_seq = receive_seq;
	}
	public String getReceive_type() {
		return receive_type;
	}
	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}
	public String getPush_result() {
		return push_result;
	}
	public void setPush_result(String push_result) {
		this.push_result = push_result;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getRead_yn() {
		return read_yn;
	}
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}
	public String getRead_yn_time() {
		return read_yn_time;
	}
	public void setRead_yn_time(String read_yn_time) {
		this.read_yn_time = read_yn_time;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	public String getContents_seq() {
		return contents_seq;
	}
	public void setContents_seq(String contents_seq) {
		this.contents_seq = contents_seq;
	}
}
