package com.nemo.kr.dto;

public class OnOffDTO {

	private String onoff_seq;
	private String function_type;
	private String function_name;
	private String use_yn;
	
	public String getOnoff_seq() {
		return onoff_seq;
	}
	public void setOnoff_seq(String onoff_seq) {
		this.onoff_seq = onoff_seq;
	}
	public String getFunction_type() {
		return function_type;
	}
	public void setFunction_type(String function_type) {
		this.function_type = function_type;
	}
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
}
