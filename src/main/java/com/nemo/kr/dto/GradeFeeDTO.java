package com.nemo.kr.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GradeFeeDTO implements Serializable {
	
	private static final long serialVersionUID = 6641422294511790920L;
	
	private String seq;
	private String grade_flag;
	private String grade;
	private String contr_min_price;
	private String contr_max_price;
	private String non_contr_min_price;
	private String non_contr_max_price;
	private String minus_fee;
	private String comment;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getGrade_flag() {
		return grade_flag;
	}
	public void setGrade_flag(String grade_flag) {
		this.grade_flag = grade_flag;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getContr_min_price() {
		return contr_min_price;
	}
	public void setContr_min_price(String contr_min_price) {
		this.contr_min_price = contr_min_price;
	}
	public String getContr_max_price() {
		return contr_max_price;
	}
	public void setContr_max_price(String contr_max_price) {
		this.contr_max_price = contr_max_price;
	}
	public String getNon_contr_min_price() {
		return non_contr_min_price;
	}
	public void setNon_contr_min_price(String non_contr_min_price) {
		this.non_contr_min_price = non_contr_min_price;
	}
	public String getNon_contr_max_price() {
		return non_contr_max_price;
	}
	public void setNon_contr_max_price(String non_contr_max_price) {
		this.non_contr_max_price = non_contr_max_price;
	}
	public String getMinus_fee() {
		return minus_fee;
	}
	public void setMinus_fee(String minus_fee) {
		this.minus_fee = minus_fee;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
