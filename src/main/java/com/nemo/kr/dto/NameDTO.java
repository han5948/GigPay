package com.nemo.kr.dto;

import java.net.URLDecoder;

import org.apache.commons.lang.builder.ToStringBuilder;

public class NameDTO {
	
	private String seq;
	private String name;
	private String flag;
	private String label;
	private String term;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getSeq() {
		return seq;
	}
	public String getName() {
		return name;
	}
	public String getFlag() {
		return flag;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
}
