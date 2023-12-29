package com.nemo.kr.dto;

/**
 * 
  * @FileName : GroupCodeDTO.java
  * @Date : 2020. 6. 16. 
  * @작성자 : Park YunSoo
  * @프로그램 설명 : 공통 코드 그룹 DTO
 */
public class GroupCodeDTO extends DefaultDTO {
	private static final long serialVersionUID = -2231950057788485849L;
	
	private String group_seq;					//그룹 순번
	private String group_name;					//그룹 이름
	private String group_code;					//그룹 코드
	private String before_code;
	
	public String getBefore_code() {
		return before_code;
	}
	public void setBefore_code(String before_code) {
		this.before_code = before_code;
	}
	public String getGroup_seq() {
		return group_seq;
	}
	public void setGroup_seq(String group_seq) {
		this.group_seq = group_seq;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
}
