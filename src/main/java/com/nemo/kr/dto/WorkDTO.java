package com.nemo.kr.dto;

import java.util.List;



/**
 * 일가자 현장 관리 DTO
 *
 */
public class WorkDTO extends GridDefaultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4376617349476937808L;

	private String work_seq;                                  // 현장 순번 - PK
	private String company_seq;                               // 소속회사 순번
	private String employer_seq;                              // 구인처 순번

	private String work_name;                                 // 현장명
	private String work_arrival;                              // 현장도착시간
	private String work_finish;
	private String work_addr;                                 // 현장주소
	private String work_sido;
	private String work_sigugun;
	private String work_dongmyun;
	private String work_rest;
	private String work_latlng;                               // 현장좌표
	private String work_addr_comment;
	private String work_breakfast_yn;                         // 조식유무 - 1:유 0:무
	private String work_age_min;							// 최저 나이
	private String work_age;                                  // 나이제한 - 0: 제한없음
	private String work_blood_pressure;                       // 혈압제한 - 0:제한없음
	private String work_manager_name;                         // 현장담당자 이름
	private String work_manager_fax;                          // 현장 팩스
	private String work_manager_phone;                       // 폰번호
	private String work_manager_email;                        // 현장담장자 이메일
	private String work_memo;                                 // 현장설명
	private String work_comment;                              // 현장설명

	private String company_name;                              // 소속회사 명
	private String employer_name;                             // 구인처 명
	private String employer_num;                             // 구인처 사업자등록번호 or 주민번호

	private String ilbo_output_count;                         // 출역 횟수
	private String ilbo_total_count;                          // 일보 횟수

	private String out_count;									// 출역수
	private String total_out_count;							// 총출역수
	private String day_count;									// 출역일수
	
	private String complete_count;							  // 완료 횟수
	private String ilbo_last_date;							  // 마지막 출역 날짜

	private String[] sel_work_seq;                            // 선택된 현장 순번

	private List<WorkDTO> list;

	// 오더 담당자.
	private String manager_seq;
	private String manager_name;
	private String manager_phone;
	private String visit_type;

	private String wm_seq;
	private int tmp_manager_seq = 0;
	
	private String is_worker_info;
	private String manager_overlap_flag;				// 매니저 중복 flag
	
	private String employer_feature;
	private String orderFlag;
	
	private String source_work_seq;
	
	private String accident_use_yn;
	
	private String labor_contract_seq;
	private String labor_contract_name;
	private String receive_contract_seq;
	private String receive_contract_name;
	
	public String getLabor_contract_seq() {
		return labor_contract_seq;
	}
	public void setLabor_contract_seq(String labor_contract_seq) {
		this.labor_contract_seq = labor_contract_seq;
	}
	public String getLabor_contract_name() {
		return labor_contract_name;
	}
	public void setLabor_contract_name(String labor_contract_name) {
		this.labor_contract_name = labor_contract_name;
	}
	public String getReceive_contract_seq() {
		return receive_contract_seq;
	}
	public void setReceive_contract_seq(String receive_contract_seq) {
		this.receive_contract_seq = receive_contract_seq;
	}
	public String getReceive_contract_name() {
		return receive_contract_name;
	}
	public void setReceive_contract_name(String receive_contract_name) {
		this.receive_contract_name = receive_contract_name;
	}
	public String getAccident_use_yn() {
		return accident_use_yn;
	}
	public void setAccident_use_yn(String accident_use_yn) {
		this.accident_use_yn = accident_use_yn;
	}
	public String getSource_work_seq() {
		return source_work_seq;
	}
	public void setSource_work_seq(String source_work_seq) {
		this.source_work_seq = source_work_seq;
	}
	public String getWork_sido() {
		return work_sido;
	}
	public void setWork_sido(String work_sido) {
		this.work_sido = work_sido;
	}
	public String getWork_sigugun() {
		return work_sigugun;
	}
	public void setWork_sigugun(String work_sigugun) {
		this.work_sigugun = work_sigugun;
	}
	public String getWork_dongmyun() {
		return work_dongmyun;
	}
	public void setWork_dongmyun(String work_dongmyun) {
		this.work_dongmyun = work_dongmyun;
	}
	public String getWork_rest() {
		return work_rest;
	}
	public void setWork_rest(String work_rest) {
		this.work_rest = work_rest;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getManager_overlap_flag() {
		return manager_overlap_flag;
	}
	public void setManager_overlap_flag(String manager_overlap_flag) {
		this.manager_overlap_flag = manager_overlap_flag;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}


	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public String getWork_arrival() {
		return work_arrival;
	}
	public void setWork_arrival(String work_arrival) {
		this.work_arrival = work_arrival.replaceAll(":", "");
	}
	public String getWork_addr() {
		return work_addr;
	}
	public void setWork_addr(String work_addr) {
		this.work_addr = work_addr;
	}
	public String getWork_latlng() {
		return work_latlng;
	}
	public void setWork_latlng(String work_latlng) {
		this.work_latlng = work_latlng;
	}
	public String getWork_breakfast_yn() {
		return work_breakfast_yn;
	}
	public void setWork_breakfast_yn(String work_breakfast_yn) {
		this.work_breakfast_yn = work_breakfast_yn;
	}
	public String getWork_age() {
		return work_age;
	}
	public void setWork_age(String work_age) {
		this.work_age = work_age;
	}
	public String getWork_blood_pressure() {
		return work_blood_pressure;
	}
	public void setWork_blood_pressure(String work_blood_pressure) {
		this.work_blood_pressure = work_blood_pressure;
	}
	public String getWork_manager_name() {
		return work_manager_name;
	}
	public void setWork_manager_name(String work_manager_name) {
		this.work_manager_name = work_manager_name;
	}
	public String getWork_manager_fax() {
		return work_manager_fax;
	}
	public void setWork_manager_fax(String work_manager_fax) {
		this.work_manager_fax = work_manager_fax;
	}
	public String getWork_manager_phone() {
		return work_manager_phone;
	}
	public void setWork_manager_phone(String work_manager_phone) {

		this.work_manager_phone = work_manager_phone.replaceAll("-", "");
	}
	public String getWork_manager_email() {
		return work_manager_email;
	}
	public void setWork_manager_email(String work_manager_email) {
		this.work_manager_email = work_manager_email;
	}
	public String getWork_memo() {
		return work_memo;
	}
	public void setWork_memo(String work_memo) {
		this.work_memo = work_memo;
	}
	public String getWork_comment() {
		return work_comment;
	}
	public void setWork_comment(String work_comment) {
		this.work_comment = work_comment;
	}


	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getIlbo_output_count() {
		return ilbo_output_count;
	}
	public void setIlbo_output_count(String ilbo_output_count) {
		this.ilbo_output_count = ilbo_output_count;
	}
	public String getIlbo_total_count() {
		return ilbo_total_count;
	}
	public void setIlbo_total_count(String ilbo_total_count) {
		this.ilbo_total_count = ilbo_total_count;
	}


	public String[] getSel_work_seq() {
		return sel_work_seq;
	}
	public void setSel_work_seq(String[] sel_work_seq) {
		this.sel_work_seq = sel_work_seq;
	}


	public List<WorkDTO> getList() {
		return list;
	}
	public void setList(List<WorkDTO> list) {
		this.list = list;
	}
	public String getWork_addr_comment() {
		if("null".equals(work_addr_comment))
			return "";
		else
			return work_addr_comment;
	}
	public void setWork_addr_comment(String work_addr_comment) {
		this.work_addr_comment = work_addr_comment;
	}
	public String getWork_finish() {
		return work_finish;
	}
	public void setWork_finish(String work_finish) {
		this.work_finish = work_finish.replaceAll(":", "");
	}
	public String getOut_count() {
		return out_count;
	}
	public String getTotal_out_count() {
		return total_out_count;
	}
	public String getDay_count() {
		return day_count;
	}
	public void setOut_count(String out_count) {
		this.out_count = out_count;
	}
	public void setTotal_out_count(String total_out_count) {
		this.total_out_count = total_out_count;
	}
	public void setDay_count(String day_count) {
		this.day_count = day_count;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	public String getManager_phone() {
		return manager_phone;
	}
	public void setManager_phone(String manager_phone) {
		this.manager_phone = manager_phone.replaceAll("-", "");
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getVisit_type() {
		return visit_type;
	}
	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}
	public String getIs_worker_info() {
		return is_worker_info;
	}
	public void setIs_worker_info(String is_worker_info) {
		this.is_worker_info = is_worker_info;
	}
	public String getWork_age_min() {
		return work_age_min;
	}
	public void setWork_age_min(String work_age_min) {
		this.work_age_min = work_age_min;
	}
	public String getComplete_count() {
		return complete_count;
	}
	public void setComplete_count(String complete_count) {
		this.complete_count = complete_count;
	}
	public String getIlbo_last_date() {
		return ilbo_last_date;
	}
	public void setIlbo_last_date(String ilbo_last_date) {
		this.ilbo_last_date = ilbo_last_date;
	}
	public int getTmp_manager_seq() {
		return tmp_manager_seq;
	}
	public void setTmp_manager_seq(int tmp_manager_seq) {
		this.tmp_manager_seq = tmp_manager_seq;
	}
	public String getWm_seq() {
		return wm_seq;
	}
	public void setWm_seq(String wm_seq) {
		this.wm_seq = wm_seq;
	}
	public String getEmployer_feature() {
		return employer_feature;
	}
	public void setEmployer_feature(String employer_feature) {
		this.employer_feature = employer_feature;
	}

	public String getEmployer_num() {
		return this.employer_num;
	}
	public void setEmployer_num(String employer_num) {
		this.employer_num = employer_num;
	}
}
