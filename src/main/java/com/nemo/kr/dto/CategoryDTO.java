package com.nemo.kr.dto;

public class CategoryDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String category_seq;
	private String category_name;
	private String category_code;
	
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getCategory_seq() {
		return category_seq;
	}
	public void setCategory_seq(String category_seq) {
		this.category_seq = category_seq;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
}
