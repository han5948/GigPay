package com.nemo.kr.dto;

import java.util.List;



/**
 * 오더 DTO
 * @author  : nemojjang
 * @date    : 2019. 7. 4.
 * @desc    : 
 *
 */
public class OrderLogDTO extends GridDefaultDTO {
	
	// nemojjang 2019. 7. 4.
	private static final long serialVersionUID = 914061109831884706L;
	
	private String log_seq;
	private String log_type;
	private String order_seq;
	private String prarent_seq;
	private String prev_data;
	private String change_data;
	
	
	public String getLog_seq() {
		return log_seq;
	}
	public void setLog_seq(String log_seq) {
		this.log_seq = log_seq;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}
	public String getOrder_seq() {
		return order_seq;
	}
	public void setOrder_seq(String order_seq) {
		this.order_seq = order_seq;
	}
	public String getPrarent_seq() {
		return prarent_seq;
	}
	public void setPrarent_seq(String prarent_seq) {
		this.prarent_seq = prarent_seq;
	}
	public String getPrev_data() {
		return prev_data;
	}
	public void setPrev_data(String prev_data) {
		this.prev_data = prev_data;
	}
	public String getChange_data() {
		return change_data;
	}
	public void setChange_data(String chang_data) {
		this.change_data = chang_data;
	}
	  
	
 }
