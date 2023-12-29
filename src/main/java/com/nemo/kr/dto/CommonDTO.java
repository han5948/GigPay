package com.nemo.kr.dto;

import java.util.List;



/**
  * @FileName : CommonDTO.java
  * @Project : ilgaja
  * @Date : 2021. 5. 4. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 :
  */
public class CommonDTO extends GridDefaultDTO {
  
  private String company_seq;
  private String parent_company_seq;

  private String service_type;
  private String service_code;
  private String service_seq;

  private String company_name;
  private List<CompanyDTO> list;
  
  private String manager_phone;
  private String employer_num;

  private String recommendation;
  private String name;
  private String phone;
  
  public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getCompany_seq() {
    return company_seq;
  }
  public void setCompany_seq(String company_seq) {
    this.company_seq = company_seq;
  }

  public String getService_type() {
    return service_type;
  }
  public void setService_type(String service_type) {
    this.service_type = service_type;
  }
  public String getService_code() {
    return service_code;
  }
  public void setService_code(String service_code) {
    this.service_code = service_code;
  }
  public String getService_seq() {
    return service_seq;
  }
  public void setService_seq(String service_seq) {
    this.service_seq = service_seq;
  }

  public String getCompany_name() {
    return company_name;
  }
  public void setCompany_name(String company_name) {
    this.company_name = company_name;
  }


  public List<CompanyDTO> getList() {
    return list;
  }
  public void setList(List<CompanyDTO> list) {
    this.list = list;
  }
public String getParent_company_seq() {
	return parent_company_seq;
}
public void setParent_company_seq(String parent_company_seq) {
	this.parent_company_seq = parent_company_seq;
}
public String getManager_phone() {
	return manager_phone;
}
public void setManager_phone(String manager_phone) {
	this.manager_phone = manager_phone;
}
public String getEmployer_num() {
	return employer_num;
}
public void setEmployer_num(String employer_num) {
	this.employer_num = employer_num;
}
public String getRecommendation() {
	return recommendation;
}
public void setRecommendation(String recommendation) {
	this.recommendation = recommendation;
}

}
