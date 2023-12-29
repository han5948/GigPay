package com.nemo.kr.dto;

import java.util.List;



/**
 * 일가자 코드 관리 DTO
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-26
 */
public class CodeDTO extends GridDefaultDTO {
  private String code_seq;                                  // 코드 순번 - PK

  private String code_type;                                 // 코드 구분
  private String code_value;                                // 코드 값
  private String code_name;                                 // 코드 명
  private String code_color;                                // 코드 명 색상
  private String code_bgcolor;                              // 코드 명 배경색
  private String code_rank;                                 // 코드 순서
  private String code_group;

  private String code_gender;								//0:남자 1: 여자 2: 기술자
  private String code_price;								// 금액
  private String app_code;
  
  private String company_seq;                               // 소속회사 순번
  private String company_name;                              // 소속회사 명


  private String[] sel_code_seq;                            // 선택된 코드 순번
  private String[] sel_code_type;                          // 선택된 코드 값

  private List<CodeDTO> list;
  
  private String code_flag;									//코드 상태 flag
  
  public String getApp_code() {
	  return app_code;
  }
  public void setApp_code(String app_code) {
	  this.app_code = app_code;
  }
  public String getCode_flag() {
	return code_flag;
}
public void setCode_flag(String code_flag) {
	this.code_flag = code_flag;
}
public String getCode_seq() {
    return code_seq;
  }
  public void setCode_seq(String code_seq) {
    this.code_seq = code_seq;
  }


  public String getCode_type() {
    return code_type;
  }
  public void setCode_type(String code_type) {
    this.code_type = code_type;
  }
  public String getCode_value() {
    return code_value;
  }
  public void setCode_value(String code_value) {
    this.code_value = code_value;
  }
  public String getCode_name() {
    return code_name;
  }
  public void setCode_name(String code_name) {
    this.code_name = code_name;
  }
  public String getCode_color() {
    return code_color;
  }
  public void setCode_color(String code_color) {
    this.code_color = code_color;
  }
  public String getCode_bgcolor() {
    return code_bgcolor;
  }
  public void setCode_bgcolor(String code_bgcolor) {
    this.code_bgcolor = code_bgcolor;
  }
  public String getCode_rank() {
    return code_rank;
  }
  public void setCode_rank(String code_rank) {
    this.code_rank = code_rank;
  }

  public String getCompany_seq() {
    return company_seq;
  }
  public void setCompany_seq(String company_seq) {
   this.company_seq = company_seq;
  }
  public String getCompany_name() {
    return company_name;
  }
  public void setCompany_name(String company_name) {
    this.company_name = company_name;
  }


  public String[] getSel_code_seq() {
    return sel_code_seq;
  }
  public void setSel_code_seq(String[] sel_code_seq) {
    this.sel_code_seq = sel_code_seq;
  }
  public String[] getSel_code_type() {
    return sel_code_type;
  }
  public void setSel_code_type(String[] sel_code_type) {
    this.sel_code_type = sel_code_type;
  }


  public List<CodeDTO> getList() {
    return list;
  }
  public void setList(List<CodeDTO> list) {
    this.list = list;
  }
public String getCode_group() {
	return code_group;
}
public void setCode_group(String code_group) {
	this.code_group = code_group;
}
public String getCode_gender() {
	return code_gender;
}
public void setCode_gender(String code_gender) {
	this.code_gender = code_gender;
}
public String getCode_price() {
	return code_price;
}
public void setCode_price(String code_price) {
	this.code_price = code_price;
}

}
