package com.nemo.kr.dto;

/**
 * 
  * @FileName : SmsTemplateDTO.java
  * @Date : 2020. 6. 23. 
  * @작성자 : Park YunSoo
  * @프로그램 설명 : SMS 템플릿 DTO
 */
public class SmsTemplateDTO extends DefaultDTO {
	private static final long serialVersionUID = -2231950057788485849L;
	
	private String template_seq;					//템플릿 순서
	private String template_title;					//템플릿 제목
	private String template_content;					//템플릿 내용
	private String company_seq;					//템플릿 보여지는 회사
	private String company_name;				//템플릿 보여지는 회사명
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getTemplate_seq() {
		return template_seq;
	}
	public void setTemplate_seq(String template_seq) {
		this.template_seq = template_seq;
	}
	public String getTemplate_title() {
		return template_title;
	}
	public void setTemplate_title(String template_title) {
		this.template_title = template_title;
	}
	public String getTemplate_content() {
		return template_content;
	}
	public void setTemplate_content(String template_content) {
		this.template_content = template_content;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
}
