package com.nemo.kr.dto;

import java.util.List;




public class PushSendDTO {
  
	private String send_seq;			// 순번
	private String ilbo_seq;			// 일보순번
	private String work_seq;			// 구인순번
	private String worker_seq;		// 구직자순번
	private String send_status;		// 보낸 상태	0:실패 1:성공
	private String push_type;			//1: 수동푸쉬 2:예약푸쉬
	private String reserve_seq;		// 예약순번
	private String send_time;			// 보낸 시간
	private String push_confirm;		//푸쉬 확인 유무
	private String login_key;
	private String send_result;			//push 응답
	
	private String fcm_result;				// fcm 응답 json
	private String fcm_success;			// fcm 성공유무 0:실패 1:성공
	private String fcm_message;		// fcm 실패 이유.
	
	
	public String getSend_seq() {
		return send_seq;
	}
	
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public String getWorker_seq() {
		return worker_seq;
	}
	
	public String getSend_status() {
		return send_status;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_seq(String send_seq) {
		this.send_seq = send_seq;
	}
	
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}
	
	public void setSend_status(String send_status) {
		this.send_status = send_status;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getPush_type() {
		return push_type;
	}
	public String getReserve_seq() {
		return reserve_seq;
	}
	public void setPush_type(String push_type) {
		this.push_type = push_type;
	}
	public void setReserve_seq(String reserve_seq) {
		this.reserve_seq = reserve_seq;
	}

	public String getPush_confirm() {
		return push_confirm;
	}

	public void setPush_confirm(String push_confirm) {
		this.push_confirm = push_confirm;
	}

	public String getLogin_key() {
		return login_key;
	}

	public void setLogin_key(String login_key) {
		this.login_key = login_key;
	}

	public String getSend_result() {
		return send_result;
	}

	public void setSend_result(String send_result) {
		this.send_result = send_result;
	}

	public String getFcm_result() {
		return fcm_result;
	}

	public void setFcm_result(String fcm_result) {
		this.fcm_result = fcm_result;
	}

	

	public String getFcm_success() {
		return fcm_success;
	}

	public void setFcm_success(String fcm_success) {
		this.fcm_success = fcm_success;
	}

	public String getFcm_message() {
		return fcm_message;
	}

	public void setFcm_message(String fcm_message) {
		this.fcm_message = fcm_message;
	}
	
	
		

}
