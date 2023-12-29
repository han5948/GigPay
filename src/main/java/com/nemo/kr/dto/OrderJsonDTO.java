package com.nemo.kr.dto;

@SuppressWarnings("serial")
public class OrderJsonDTO {
	private String json_seq;
	private String work_seq;
	private String work_date;
	private String kind_code;
	private String third_code;
	private String[] fourth_code;
	private String json_data;
	
	public String getJson_seq() {
		return json_seq;
	}
	public void setJson_seq(String json_seq) {
		this.json_seq = json_seq;
	}
	public String getJson_data() {
		return json_data;
	}
	public void setJson_data(String json_data) {
		this.json_data = json_data;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public String getKind_code() {
		return kind_code;
	}
	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}
	public String getThird_code() {
		return third_code;
	}
	public void setThird_code(String third_code) {
		this.third_code = third_code;
	}
	public String[] getFourth_code() {
		return fourth_code;
	}
	public void setFourth_code(String[] fourth_code) {
		this.fourth_code = fourth_code;
	}
	
}
