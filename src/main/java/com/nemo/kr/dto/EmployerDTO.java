package com.nemo.kr.dto;

import java.util.List;



/**
 * 일가자 구인처 관리 DTO
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-14
 */
public class EmployerDTO extends GridDefaultDTO {
	// nemojjang 2019. 6. 22.
	private static final long serialVersionUID = 1072960641402786561L;

	private String employer_seq;                               // 구인처 순번 - PK
	private String company_seq;                               // 소속회사 순번

	private String employer_num;                              // 구인처 사업자번호
	private String employer_pass;
	private String employer_name;                             	// 구인처 명
	private String employer_owner;                            	// 구인처 대표자
	private String employer_business;                         	// 구인처 업태
	private String employer_sector;                           	// 구인처 업종
	private String employer_tel;                              	// 구인처 전화번호
	private String employer_fax;                              	// 구인처 팩스
	private String employer_phone;                            	// 구인처 폰번호
	private String employer_phone1;                           	// 구인처 폰번호
	private String employer_phone2;                           	// 구인처 폰번호
	private String employer_phone3;                           	// 구인처 폰번호
	private String employer_phone4;                           // 구인처 폰번호
	private String employer_email;                            	// 구인처 이메일
	private String employer_addr;                             	// 구인처 주소
	private String employer_feature;                          	// 구인처 특징
	private String employer_memo;                             // 구인처 메모

	private String company_name;                              // 소속회사 명
	private String manager_name;                              // 본사매니저 명

	private String[] sel_employer_seq;                        // 선택된 구인처 순번

	private String out_count;									// 출역수
	private String total_out_count;							// 총출역수
	private String work_count;								// 현장수

	private String employer_corp_scan_yn;
	private String employer_etc_scan_yn;

	private String is_tax;
	private String tax_name;
	private String tax_phone;

	private String visit_date; 			//최근 접속날짜
	private String visit_count;		//접속 카운트
	private String visit_type;			//'M:모바일 W:web',

	private List<EmployerDTO> list;
	private String login_type;
	
	private String company_tel;
	private String partner_tel;
	
	private String os_type;
	private String push_token;
	private String app_version;
	private String manager_seq;
	
	private String source_employer_seq;
	private String ilbo_last_date;
	private String limit_count;
	
	private String employer_insurance;
	private String accident_insurance;
	private String national_pension;
	private String health_insurance;
	
	private String payment; //0:직불-직접지급;1:직불-간편송금;2:기성-간편결제;3:기성-대불
	private String wage; //0:없음;1:1주 수요일;2:2주 수요일;3:익월 5일;4:익월 10일;5:익월 15일;6:익월 20일;7:익월 25일;8:익월 말일
	
	private String labor_contract_seq;
	private String labor_contract_name;
	private String receive_contract_seq;
	private String receive_contract_name;
	
	private String sign_manager_type;
	private String sign_manager_seq;
	private String sign_manager_name;
	
	private String employer_detail_memo;
	
	private String employer_income_code;
	private String employer_income_name;
	private String employer_pay_code;
	private String employer_pay_name;
	
	private String insurance_check_date;
	
	public String getInsurance_check_date() {
		return insurance_check_date;
	}
	public void setInsurance_check_date(String insurance_check_date) {
		this.insurance_check_date = insurance_check_date;
	}
	public String getEmployer_income_name() {
		return employer_income_name;
	}
	public void setEmployer_income_name(String employer_income_name) {
		this.employer_income_name = employer_income_name;
	}
	public String getEmployer_pay_name() {
		return employer_pay_name;
	}
	public void setEmployer_pay_name(String employer_pay_name) {
		this.employer_pay_name = employer_pay_name;
	}
	public String getEmployer_income_code() {
		return employer_income_code;
	}
	public void setEmployer_income_code(String employer_income_code) {
		this.employer_income_code = employer_income_code;
	}
	public String getEmployer_pay_code() {
		return employer_pay_code;
	}
	public void setEmployer_pay_code(String employer_pay_code) {
		this.employer_pay_code = employer_pay_code;
	}
	public String getEmployer_detail_memo() {
		return employer_detail_memo;
	}
	public void setEmployer_detail_memo(String employer_detail_memo) {
		this.employer_detail_memo = employer_detail_memo;
	}
	public String getSign_manager_name() {
		return sign_manager_name;
	}
	public void setSign_manager_name(String sign_manager_name) {
		this.sign_manager_name = sign_manager_name;
	}
	public String getSign_manager_type() {
		return sign_manager_type;
	}
	public void setSign_manager_type(String sign_manager_type) {
		this.sign_manager_type = sign_manager_type;
	}
	public String getSign_manager_seq() {
		return sign_manager_seq;
	}
	public void setSign_manager_seq(String sign_manager_seq) {
		this.sign_manager_seq = sign_manager_seq;
	}
	public String getLabor_contract_name() {
		return labor_contract_name;
	}
	public void setLabor_contract_name(String labor_contract_name) {
		this.labor_contract_name = labor_contract_name;
	}
	public String getReceive_contract_name() {
		return receive_contract_name;
	}
	public void setReceive_contract_name(String receive_contract_name) {
		this.receive_contract_name = receive_contract_name;
	}
	public String getLabor_contract_seq() {
		return labor_contract_seq;
	}
	public void setLabor_contract_seq(String labor_contract_seq) {
		this.labor_contract_seq = labor_contract_seq;
	}
	public String getReceive_contract_seq() {
		return receive_contract_seq;
	}
	public void setReceive_contract_seq(String receive_contract_seq) {
		this.receive_contract_seq = receive_contract_seq;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getWage() {
		return wage;
	}
	public void setWage(String wage) {
		this.wage = wage;
	}
	public String getEmployer_insurance() {
		return employer_insurance;
	}
	public void setEmployer_insurance(String employer_insurance) {
		this.employer_insurance = employer_insurance;
	}
	public String getAccident_insurance() {
		return accident_insurance;
	}
	public void setAccident_insurance(String accident_insurance) {
		this.accident_insurance = accident_insurance;
	}
	public String getNational_pension() {
		return national_pension;
	}
	public void setNational_pension(String national_pension) {
		this.national_pension = national_pension;
	}
	public String getHealth_insurance() {
		return health_insurance;
	}
	public void setHealth_insurance(String health_insurance) {
		this.health_insurance = health_insurance;
	}
	public String getLimit_count() {
		return limit_count;
	}
	public void setLimit_count(String limit_count) {
		this.limit_count = limit_count;
	}
	public String getIlbo_last_date() {
		return ilbo_last_date;
	}
	public void setIlbo_last_date(String ilbo_last_date) {
		this.ilbo_last_date = ilbo_last_date;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}


	public String getEmployer_num() {
		return employer_num;
	}
	public void setEmployer_num(String employer_num) {
		this.employer_num = employer_num.replaceAll("-", ""); 
	}
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getEmployer_owner() {
		return employer_owner;
	}
	public void setEmployer_owner(String employer_owner) {
		this.employer_owner = employer_owner;
	}
	public String getEmployer_business() {
		return employer_business;
	}
	public void setEmployer_business(String employer_business) {
		this.employer_business = employer_business;
	}
	public String getEmployer_sector() {
		return employer_sector;
	}
	public void setEmployer_sector(String employer_sector) {
		this.employer_sector = employer_sector;
	}
	public String getEmployer_tel() {
		return employer_tel;
	}
	public void setEmployer_tel(String employer_tel) {
		this.employer_tel = employer_tel;
	}
	public String getEmployer_fax() {
		return employer_fax;
	}
	public void setEmployer_fax(String employer_fax) {
		this.employer_fax = employer_fax;
	}
	public String getEmployer_phone() {
		return employer_phone;
	}
	public void setEmployer_phone(String employer_phone) {
		this.employer_phone = employer_phone;
	}
	public String getEmployer_phone1() {
		return employer_phone1;
	}
	public void setEmployer_phone1(String employer_phone1) {
		this.employer_phone1 = employer_phone1;
	}
	public String getEmployer_email() {
		return employer_email;
	}
	public String getEmployer_phone2() {
		return employer_phone2;
	}
	public void setEmployer_phone2(String employer_phone2) {
		this.employer_phone2 = employer_phone2;
	}
	public String getEmployer_phone3() {
		return employer_phone3;
	}
	public void setEmployer_phone3(String employer_phone3) {
		this.employer_phone3 = employer_phone3;
	}
	public String getEmployer_phone4() {
		return employer_phone4;
	}
	public void setEmployer_phone4(String employer_phone4) {
		this.employer_phone4 = employer_phone4;
	}
	public void setEmployer_email(String employer_email) {
		this.employer_email = employer_email;
	}
	public String getEmployer_addr() {
		return employer_addr;
	}
	public void setEmployer_addr(String employer_addr) {
		this.employer_addr = employer_addr;
	}
	public String getEmployer_feature() {
		return employer_feature;
	}
	public void setEmployer_feature(String employer_feature) {
		this.employer_feature = employer_feature;
	}
	public String getEmployer_memo() {
		return employer_memo;
	}
	public void setEmployer_memo(String employer_memo) {
		this.employer_memo = employer_memo;
	}

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}


	public String[] getSel_employer_seq() {
		return sel_employer_seq;
	}
	public void setSel_employer_seq(String[] sel_employer_seq) {
		this.sel_employer_seq = sel_employer_seq;
	}


	public List<EmployerDTO> getList() {
		return list;
	}
	public void setList(List<EmployerDTO> list) {
		this.list = list;
	}
	public String getOut_count() {
		return out_count;
	}
	public void setOut_count(String out_count) {
		this.out_count = out_count;
	}
	public String getTotal_out_count() {
		return total_out_count;
	}
	public void setTotal_out_count(String total_out_count) {
		this.total_out_count = total_out_count;
	}
	public String getWork_count() {
		return work_count;
	}
	public void setWork_count(String work_count) {
		this.work_count = work_count;
	}
	public String getEmployer_corp_scan_yn() {
		return employer_corp_scan_yn;
	}
	public void setEmployer_corp_scan_yn(String employer_corp_scan_yn) {
		this.employer_corp_scan_yn = employer_corp_scan_yn;
	}
	public String getEmployer_etc_scan_yn() {
		return employer_etc_scan_yn;
	}
	public void setEmployer_etc_scan_yn(String employer_etc_scan_yn) {
		this.employer_etc_scan_yn = employer_etc_scan_yn;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getEmployer_pass() {
		return employer_pass;
	}
	public void setEmployer_pass(String employer_pass) {
		this.employer_pass = employer_pass;
	}
	public String getIs_tax() {
		return is_tax;
	}
	public void setIs_tax(String is_tax) {
		this.is_tax = is_tax;
	}
	public String getTax_name() {
		return tax_name;
	}
	public void setTax_name(String tax_name) {
		this.tax_name = tax_name;
	}
	public String getTax_phone() {
		return tax_phone;
	}
	public void setTax_phone(String tax_phone) {
		this.tax_phone = tax_phone;
	}
	public String getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}
	public String getVisit_count() {
		return visit_count;
	}
	public void setVisit_count(String visit_count) {
		this.visit_count = visit_count;
	}
	public String getVisit_type() {
		return visit_type;
	}
	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public String getCompany_tel() {
		return company_tel;
	}
	public void setCompany_tel(String company_tel) {
		this.company_tel = company_tel;
	}
	public String getPartner_tel() {
		return partner_tel;
	}
	public void setPartner_tel(String partner_tel) {
		this.partner_tel = partner_tel;
	}
	public String getOs_type() {
		return os_type;
	}
	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}
	public String getPush_token() {
		return push_token;
	}
	public void setPush_token(String push_token) {
		this.push_token = push_token;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	public String getSource_employer_seq() {
		return source_employer_seq;
	}
	public void setSource_employer_seq(String source_employer_seq) {
		this.source_employer_seq = source_employer_seq;
	}

}
