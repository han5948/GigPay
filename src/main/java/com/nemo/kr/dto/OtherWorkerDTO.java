package com.nemo.kr.dto;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
 * 일가자 출역일보 관리 DTO
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-26
 */
public class OtherWorkerDTO {
  private String other_seq;                                  // 출역 순번 - PK
  private String other_worker_name;                          // 근로자명
  private String other_worker_phone;                         // 핸드폰번호
  
  
public String getOther_seq() {
	return other_seq;
}
public String getOther_worker_name() {
	return other_worker_name;
}
public String getOther_worker_phone() {
	return other_worker_phone;
}
public void setOther_seq(String other_seq) {
	this.other_seq = other_seq;
}
public void setOther_worker_name(String other_worker_name) {
	this.other_worker_name = other_worker_name;
}
public void setOther_worker_phone(String other_worker_phone) {
	this.other_worker_phone = other_worker_phone;
}
  
  

}
