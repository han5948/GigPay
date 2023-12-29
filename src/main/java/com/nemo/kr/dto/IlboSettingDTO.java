
package com.nemo.kr.dto;

public class IlboSettingDTO {

	private static final long serialVersionUID = 2035757321256312767L;
	
	// 사용 여부 (0 : 미사용, 1 : 사용)
	// 삭제 여부 (0 : 미삭제, 1 : 삭제)
	
	private String setting_seq;							// 순번
	private String admin_seq;							// 관리자 순번
	private String ilbo_date;							// 일자
	private String ilbo_date_width;							// 일자
	private String worker_company_name;					// 구직자 소속
	private String worker_company_name_width;					// 구직자 소속
	private String worker_owner;						// 소유
	private String worker_owner_width;						// 소유
	private String ilbo_worker_view;					// 상세
	private String ilbo_worker_view_width;					// 상세
	private String worker_ilbo;							// 대장
	private String worker_ilbo_width;							// 대장
	private String ilbo_worker_name;					// 구직자
	private String ilbo_worker_name_width;					// 구직자
	private String ilbo_worker_feature;					// 특징
	private String ilbo_worker_feature_width;					// 특징
	private String ilbo_worker_memo;					// 구직자 메모
	private String ilbo_worker_memo_width;					// 구직자 메모
	private String ilbo_assign_type;					// 배정
	private String ilbo_assign_type_width;					// 배정
	private String ilbo_worker_info;					// 구직자 정보
	private String ilbo_worker_info_width;					// 구직자 정보
	private String ilbo_worker_status_info;				// 상태
	private String ilbo_worker_status_info_width;				// 상태
	private String ilbo_memo;							// 일보 메모
	private String ilbo_memo_width;							// 일보 메모
	private String work_company_name;					// 구인처 소속
	private String work_company_name_width;					// 구인처 소속
	private String work_owner;							// 소유
	private String work_owner_width;							// 소유
	private String ilbo_employer_view;					// 상세
	private String ilbo_employer_view_width;					// 상세
	private String employer_ilbo;						// 대장
	private String employer_ilbo_width;						// 대장
	private String employer_name;						// 구인처
	private String employer_name_width;						// 구인처
	private String ilbo_employer_feature;				// 특징
	private String ilbo_employer_feature_width;				// 특징
	private String addr_edit;							// 지도
	private String addr_edit_width;							// 지도
	private String addr_location;						// 위치
	private String addr_location_width;						// 위치
	private String ilbo_work_info;						// 순서
	private String ilbo_work_info_width;						// 순서
	private String ilbo_work_order_name;				// 오더 종류
	private String ilbo_work_order_name_width;				// 오더 종류
	private String ilbo_use_info;						// 공개
	private String ilbo_use_info_width;						// 공개
	private String ilbo_status_info;					// 상태
	private String ilbo_status_info_width;					// 상태
	private String ilbo_work_breakfast_yn;				// 현장 조식
	private String ilbo_work_breakfast_yn_width;				// 현장 조식
	private String ilbo_work_age_min;					// 최저 나이
	private String ilbo_work_age_min_width;					// 최저 나이
	private String ilbo_work_age;						// 최고 나이
	private String ilbo_work_age_width;						// 최고 나이
	private String ilbo_work_blood_pressure;			// 혈압
	private String ilbo_work_blood_pressure_width;			// 혈압
	private String manager_name;						// 현장 매니저명
	private String manager_name_width;						// 현장 매니저명
	private String manager_phone;						// 현장 매니저 번호
	private String manager_phone_width;						// 현장 매니저 번호
	private String ilbo_work_manager_name;				// 현장 담당
	private String ilbo_work_manager_name_width;				// 현장 담당
	private String ilbo_work_manager_phone;				// 현장 전화
	private String ilbo_work_manager_phone_width;				// 현장 전화
	private String ilbo_work_arrival;					// 도착 시간
	private String ilbo_work_arrival_width;					// 도착 시간
	private String ilbo_work_finish;					// 마감 시간
	private String ilbo_work_finish_width;					// 마감 시간
	private String work_ilbo;							// 대장
	private String work_ilbo_width;							// 대장
	private String work_name;							// 현장
	private String work_name_width;							// 현장
	private String ilbo_job_comment;					// 작업 설명
	private String ilbo_job_comment_width;					// 작업 설명
	private String ilbo_kind_name;						// 직종
	private String ilbo_kind_name_width;						// 직종
	private String ilbo_chief_memo;						// 소장 메모
	private String ilbo_chief_memo_width;						// 소장 메모
	private String ilbo_income_name;					// 노임 수령
	private String ilbo_income_name_width;					// 노임 수령
	private String ilbo_income_time;					// 수령 일자
	private String ilbo_income_time_width;					// 수령 일자
	private String ilbo_unit_price;						// 단가
	private String ilbo_unit_price_width;						// 단가
	private String ilbo_fee;							// 수수료
	private String ilbo_fee_width;							// 수수료
	private String share_fee;							// 쉐어료
	private String share_fee_width;							// 쉐어료
	private String ilbo_deduction;						// 공제금
	private String ilbo_deduction_width;						// 공제금
	private String ilbo_pay;							// 일당
	private String ilbo_pay_width;							// 일당
	private String ilbo_pay_name;						// 노임지급
	private String ilbo_pay_name_width;						// 노임지급
	private String ilbo_pay_time;						// 지급일자
	private String ilbo_pay_time_width;						// 지급일자
	private String ilbo_income_memo;					// 입출금 메모
	private String ilbo_income_memo_width;					// 입출금 메모
	private String employer_rating;							// 구인처 평가
	private String employer_rating_width;							// 구인처 평가
	private String evaluate_grade;						// 구직자 평가
	private String evaluate_grade_width;						// 구직자 평가
	private String use_yn;								// 상태
	private String use_yn_width;								// 상태
	private String reg_date;							// 등록일시
	private String reg_date_width;							// 등록일시
	private String reg_admin;							// 작성자
	private String reg_admin_width;							// 작성자
	private String owner_id;							// 소유자
	private String owner_id_width;							// 소유자
	private String setting_use_yn;						// ilbo_setting 사용 여부
	private String setting_del_yn;						// ilbo_setting 삭제 여부
	private String setting_reg_date;					// ilbo_setting 작성일
	private String setting_reg_admin;					// ilbo_setting 작성자
	private String setting_mod_admin;					// ilbo_setting 수정자
	private String setting_mod_date;					// ilbo_setting 수정일
	
	private String company_name;						// 일일대장지점
	private String company_name_width;
	private String ilbo_work_breaktime;					// 휴게시간
	private String ilbo_work_breaktime_width;
	private String ilbo_career_name;					// 경력옵션
	private String ilbo_career_name_width;
	private String receive_contract_seq;				// 대리수령 양식
	private String receive_contract_seq_width;
	private String labor_contract_seq;					// 근로계약 양식
	private String labor_contract_seq_width;
	private String work_fee;							// 구인자 소개요금
	private String work_fee_width;
	private String worker_fee;							// 구직자 소개요금
	private String worker_fee_width;
	private String fee_info;							// 소개요금 정보
	private String fee_info_width;
	private String pay_info;							// 구직자 소개요금 정보
	private String pay_info_width;
	private String manager_rating;						// 현장평가
	private String manager_rating_width;
	private String surrogate;							// 서명상태 대리수령
	private String surrogate_width;
	private String labor_contract;						// 서명상태 근로계약
	private String labor_contract_width;
	private String deductible_sum;						// 공제액 계
	private String deductible_sum_width;
	private String deductible_info;						// 상세(공제액)
	private String deductible_info_width;
	private String wages_received;						// 임금수령액
	private String wages_received_width;
	
	private String employer_detail;
	private String employer_detail_width;
	private String parking_option;
	private String parking_option_width;
	
				
	public String getEmployer_detail() {
		return employer_detail;
	}
	public void setEmployer_detail(String employer_detail) {
		this.employer_detail = employer_detail;
	}
	public String getEmployer_detail_width() {
		return employer_detail_width;
	}
	public void setEmployer_detail_width(String employer_detail_width) {
		this.employer_detail_width = employer_detail_width;
	}
	public String getParking_option() {
		return parking_option;
	}
	public void setParking_option(String parking_option) {
		this.parking_option = parking_option;
	}
	public String getParking_option_width() {
		return parking_option_width;
	}
	public void setParking_option_width(String parking_option_width) {
		this.parking_option_width = parking_option_width;
	}
	public String getIlbo_date_width() {
		return ilbo_date_width;
	}
	public void setIlbo_date_width(String ilbo_date_width) {
		this.ilbo_date_width = ilbo_date_width;
	}
	public String getWorker_company_name_width() {
		return worker_company_name_width;
	}
	public void setWorker_company_name_width(String worker_company_name_width) {
		this.worker_company_name_width = worker_company_name_width;
	}
	public String getWorker_owner_width() {
		return worker_owner_width;
	}
	public void setWorker_owner_width(String worker_owner_width) {
		this.worker_owner_width = worker_owner_width;
	}
	public String getIlbo_worker_view_width() {
		return ilbo_worker_view_width;
	}
	public void setIlbo_worker_view_width(String ilbo_worker_view_width) {
		this.ilbo_worker_view_width = ilbo_worker_view_width;
	}
	public String getWorker_ilbo_width() {
		return worker_ilbo_width;
	}
	public void setWorker_ilbo_width(String worker_ilbo_width) {
		this.worker_ilbo_width = worker_ilbo_width;
	}
	public String getIlbo_worker_name_width() {
		return ilbo_worker_name_width;
	}
	public void setIlbo_worker_name_width(String ilbo_worker_name_width) {
		this.ilbo_worker_name_width = ilbo_worker_name_width;
	}
	public String getIlbo_worker_feature_width() {
		return ilbo_worker_feature_width;
	}
	public void setIlbo_worker_feature_width(String ilbo_worker_feature_width) {
		this.ilbo_worker_feature_width = ilbo_worker_feature_width;
	}
	public String getIlbo_worker_memo_width() {
		return ilbo_worker_memo_width;
	}
	public void setIlbo_worker_memo_width(String ilbo_worker_memo_width) {
		this.ilbo_worker_memo_width = ilbo_worker_memo_width;
	}
	public String getIlbo_assign_type_width() {
		return ilbo_assign_type_width;
	}
	public void setIlbo_assign_type_width(String ilbo_assign_type_width) {
		this.ilbo_assign_type_width = ilbo_assign_type_width;
	}
	public String getIlbo_worker_info_width() {
		return ilbo_worker_info_width;
	}
	public void setIlbo_worker_info_width(String ilbo_worker_info_width) {
		this.ilbo_worker_info_width = ilbo_worker_info_width;
	}
	public String getIlbo_worker_status_info_width() {
		return ilbo_worker_status_info_width;
	}
	public void setIlbo_worker_status_info_width(String ilbo_worker_status_info_width) {
		this.ilbo_worker_status_info_width = ilbo_worker_status_info_width;
	}
	public String getIlbo_memo_width() {
		return ilbo_memo_width;
	}
	public void setIlbo_memo_width(String ilbo_memo_width) {
		this.ilbo_memo_width = ilbo_memo_width;
	}
	public String getWork_company_name_width() {
		return work_company_name_width;
	}
	public void setWork_company_name_width(String work_company_name_width) {
		this.work_company_name_width = work_company_name_width;
	}
	public String getWork_owner_width() {
		return work_owner_width;
	}
	public void setWork_owner_width(String work_owner_width) {
		this.work_owner_width = work_owner_width;
	}
	public String getIlbo_employer_view_width() {
		return ilbo_employer_view_width;
	}
	public void setIlbo_employer_view_width(String ilbo_employer_view_width) {
		this.ilbo_employer_view_width = ilbo_employer_view_width;
	}
	public String getEmployer_ilbo_width() {
		return employer_ilbo_width;
	}
	public void setEmployer_ilbo_width(String employer_ilbo_width) {
		this.employer_ilbo_width = employer_ilbo_width;
	}
	public String getEmployer_name_width() {
		return employer_name_width;
	}
	public void setEmployer_name_width(String employer_name_width) {
		this.employer_name_width = employer_name_width;
	}
	public String getIlbo_employer_feature_width() {
		return ilbo_employer_feature_width;
	}
	public void setIlbo_employer_feature_width(String ilbo_employer_feature_width) {
		this.ilbo_employer_feature_width = ilbo_employer_feature_width;
	}
	public String getAddr_edit_width() {
		return addr_edit_width;
	}
	public void setAddr_edit_width(String addr_edit_width) {
		this.addr_edit_width = addr_edit_width;
	}
	public String getAddr_location_width() {
		return addr_location_width;
	}
	public void setAddr_location_width(String addr_location_width) {
		this.addr_location_width = addr_location_width;
	}
	public String getIlbo_work_info_width() {
		return ilbo_work_info_width;
	}
	public void setIlbo_work_info_width(String ilbo_work_info_width) {
		this.ilbo_work_info_width = ilbo_work_info_width;
	}
	public String getIlbo_work_order_name_width() {
		return ilbo_work_order_name_width;
	}
	public void setIlbo_work_order_name_width(String ilbo_work_order_name_width) {
		this.ilbo_work_order_name_width = ilbo_work_order_name_width;
	}
	public String getIlbo_use_info_width() {
		return ilbo_use_info_width;
	}
	public void setIlbo_use_info_width(String ilbo_use_info_width) {
		this.ilbo_use_info_width = ilbo_use_info_width;
	}
	public String getIlbo_status_info_width() {
		return ilbo_status_info_width;
	}
	public void setIlbo_status_info_width(String ilbo_status_info_width) {
		this.ilbo_status_info_width = ilbo_status_info_width;
	}
	public String getIlbo_work_breakfast_yn_width() {
		return ilbo_work_breakfast_yn_width;
	}
	public void setIlbo_work_breakfast_yn_width(String ilbo_work_breakfast_yn_width) {
		this.ilbo_work_breakfast_yn_width = ilbo_work_breakfast_yn_width;
	}
	public String getIlbo_work_age_min_width() {
		return ilbo_work_age_min_width;
	}
	public void setIlbo_work_age_min_width(String ilbo_work_age_min_width) {
		this.ilbo_work_age_min_width = ilbo_work_age_min_width;
	}
	public String getIlbo_work_age_width() {
		return ilbo_work_age_width;
	}
	public void setIlbo_work_age_width(String ilbo_work_age_width) {
		this.ilbo_work_age_width = ilbo_work_age_width;
	}
	public String getIlbo_work_blood_pressure_width() {
		return ilbo_work_blood_pressure_width;
	}
	public void setIlbo_work_blood_pressure_width(String ilbo_work_blood_pressure_width) {
		this.ilbo_work_blood_pressure_width = ilbo_work_blood_pressure_width;
	}
	public String getManager_name_width() {
		return manager_name_width;
	}
	public void setManager_name_width(String manager_name_width) {
		this.manager_name_width = manager_name_width;
	}
	public String getManager_phone_width() {
		return manager_phone_width;
	}
	public void setManager_phone_width(String manager_phone_width) {
		this.manager_phone_width = manager_phone_width;
	}
	public String getIlbo_work_manager_name_width() {
		return ilbo_work_manager_name_width;
	}
	public void setIlbo_work_manager_name_width(String ilbo_work_manager_name_width) {
		this.ilbo_work_manager_name_width = ilbo_work_manager_name_width;
	}
	public String getIlbo_work_manager_phone_width() {
		return ilbo_work_manager_phone_width;
	}
	public void setIlbo_work_manager_phone_width(String ilbo_work_manager_phone_width) {
		this.ilbo_work_manager_phone_width = ilbo_work_manager_phone_width;
	}
	public String getIlbo_work_arrival_width() {
		return ilbo_work_arrival_width;
	}
	public void setIlbo_work_arrival_width(String ilbo_work_arrival_width) {
		this.ilbo_work_arrival_width = ilbo_work_arrival_width;
	}
	public String getIlbo_work_finish_width() {
		return ilbo_work_finish_width;
	}
	public void setIlbo_work_finish_width(String ilbo_work_finish_width) {
		this.ilbo_work_finish_width = ilbo_work_finish_width;
	}
	public String getWork_ilbo_width() {
		return work_ilbo_width;
	}
	public void setWork_ilbo_width(String work_ilbo_width) {
		this.work_ilbo_width = work_ilbo_width;
	}
	public String getWork_name_width() {
		return work_name_width;
	}
	public void setWork_name_width(String work_name_width) {
		this.work_name_width = work_name_width;
	}
	public String getIlbo_job_comment_width() {
		return ilbo_job_comment_width;
	}
	public void setIlbo_job_comment_width(String ilbo_job_comment_width) {
		this.ilbo_job_comment_width = ilbo_job_comment_width;
	}
	public String getIlbo_kind_name_width() {
		return ilbo_kind_name_width;
	}
	public void setIlbo_kind_name_width(String ilbo_kind_name_width) {
		this.ilbo_kind_name_width = ilbo_kind_name_width;
	}
	public String getIlbo_chief_memo_width() {
		return ilbo_chief_memo_width;
	}
	public void setIlbo_chief_memo_width(String ilbo_chief_memo_width) {
		this.ilbo_chief_memo_width = ilbo_chief_memo_width;
	}
	public String getIlbo_income_name_width() {
		return ilbo_income_name_width;
	}
	public void setIlbo_income_name_width(String ilbo_income_name_width) {
		this.ilbo_income_name_width = ilbo_income_name_width;
	}
	public String getIlbo_income_time_width() {
		return ilbo_income_time_width;
	}
	public void setIlbo_income_time_width(String ilbo_income_time_width) {
		this.ilbo_income_time_width = ilbo_income_time_width;
	}
	public String getIlbo_unit_price_width() {
		return ilbo_unit_price_width;
	}
	public void setIlbo_unit_price_width(String ilbo_unit_price_width) {
		this.ilbo_unit_price_width = ilbo_unit_price_width;
	}
	public String getIlbo_fee_width() {
		return ilbo_fee_width;
	}
	public void setIlbo_fee_width(String ilbo_fee_width) {
		this.ilbo_fee_width = ilbo_fee_width;
	}
	public String getShare_fee_width() {
		return share_fee_width;
	}
	public void setShare_fee_width(String share_fee_width) {
		this.share_fee_width = share_fee_width;
	}
	public String getIlbo_deduction_width() {
		return ilbo_deduction_width;
	}
	public void setIlbo_deduction_width(String ilbo_deduction_width) {
		this.ilbo_deduction_width = ilbo_deduction_width;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_name_width() {
		return company_name_width;
	}
	public void setCompany_name_width(String company_name_width) {
		this.company_name_width = company_name_width;
	}
	public String getIlbo_work_breaktime() {
		return ilbo_work_breaktime;
	}
	public void setIlbo_work_breaktime(String ilbo_work_breaktime) {
		this.ilbo_work_breaktime = ilbo_work_breaktime;
	}
	public String getIlbo_work_breaktime_width() {
		return ilbo_work_breaktime_width;
	}
	public void setIlbo_work_breaktime_width(String ilbo_work_breaktime_width) {
		this.ilbo_work_breaktime_width = ilbo_work_breaktime_width;
	}
	public String getIlbo_career_name() {
		return ilbo_career_name;
	}
	public void setIlbo_career_name(String ilbo_career_name) {
		this.ilbo_career_name = ilbo_career_name;
	}
	public String getIlbo_career_name_width() {
		return ilbo_career_name_width;
	}
	public void setIlbo_career_name_width(String ilbo_career_name_width) {
		this.ilbo_career_name_width = ilbo_career_name_width;
	}
	public String getReceive_contract_seq() {
		return receive_contract_seq;
	}
	public void setReceive_contract_seq(String receive_contract_seq) {
		this.receive_contract_seq = receive_contract_seq;
	}
	public String getReceive_contract_seq_width() {
		return receive_contract_seq_width;
	}
	public void setReceive_contract_seq_width(String receive_contract_seq_width) {
		this.receive_contract_seq_width = receive_contract_seq_width;
	}
	public String getLabor_contract_seq() {
		return labor_contract_seq;
	}
	public void setLabor_contract_seq(String labor_contract_seq) {
		this.labor_contract_seq = labor_contract_seq;
	}
	public String getLabor_contract_seq_width() {
		return labor_contract_seq_width;
	}
	public void setLabor_contract_seq_width(String labor_contract_seq_width) {
		this.labor_contract_seq_width = labor_contract_seq_width;
	}
	public String getWork_fee() {
		return work_fee;
	}
	public void setWork_fee(String work_fee) {
		this.work_fee = work_fee;
	}
	public String getWork_fee_width() {
		return work_fee_width;
	}
	public void setWork_fee_width(String work_fee_width) {
		this.work_fee_width = work_fee_width;
	}
	public String getWorker_fee() {
		return worker_fee;
	}
	public void setWorker_fee(String worker_fee) {
		this.worker_fee = worker_fee;
	}
	public String getWorker_fee_width() {
		return worker_fee_width;
	}
	public void setWorker_fee_width(String worker_fee_width) {
		this.worker_fee_width = worker_fee_width;
	}
	public String getFee_info() {
		return fee_info;
	}
	public void setFee_info(String fee_info) {
		this.fee_info = fee_info;
	}
	public String getFee_info_width() {
		return fee_info_width;
	}
	public void setFee_info_width(String fee_info_width) {
		this.fee_info_width = fee_info_width;
	}
	public String getPay_info() {
		return pay_info;
	}
	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}
	public String getPay_info_width() {
		return pay_info_width;
	}
	public void setPay_info_width(String pay_info_width) {
		this.pay_info_width = pay_info_width;
	}
	public String getManager_rating() {
		return manager_rating;
	}
	public void setManager_rating(String manager_rating) {
		this.manager_rating = manager_rating;
	}
	public String getManager_rating_width() {
		return manager_rating_width;
	}
	public void setManager_rating_width(String manager_rating_width) {
		this.manager_rating_width = manager_rating_width;
	}
	public String getSurrogate() {
		return surrogate;
	}
	public void setSurrogate(String surrogate) {
		this.surrogate = surrogate;
	}
	public String getSurrogate_width() {
		return surrogate_width;
	}
	public void setSurrogate_width(String surrogate_width) {
		this.surrogate_width = surrogate_width;
	}
	public String getLabor_contract() {
		return labor_contract;
	}
	public void setLabor_contract(String labor_contract) {
		this.labor_contract = labor_contract;
	}
	public String getLabor_contract_width() {
		return labor_contract_width;
	}
	public void setLabor_contract_width(String labor_contract_width) {
		this.labor_contract_width = labor_contract_width;
	}
	public String getDeductible_sum() {
		return deductible_sum;
	}
	public void setDeductible_sum(String deductible_sum) {
		this.deductible_sum = deductible_sum;
	}
	public String getDeductible_sum_width() {
		return deductible_sum_width;
	}
	public void setDeductible_sum_width(String deductible_sum_width) {
		this.deductible_sum_width = deductible_sum_width;
	}
	public String getDeductible_info() {
		return deductible_info;
	}
	public void setDeductible_info(String deductible_info) {
		this.deductible_info = deductible_info;
	}
	public String getDeductible_info_width() {
		return deductible_info_width;
	}
	public void setDeductible_info_width(String deductible_info_width) {
		this.deductible_info_width = deductible_info_width;
	}
	public String getWages_received() {
		return wages_received;
	}
	public void setWages_received(String wages_received) {
		this.wages_received = wages_received;
	}
	public String getWages_received_width() {
		return wages_received_width;
	}
	public void setWages_received_width(String wages_received_width) {
		this.wages_received_width = wages_received_width;
	}
	public String getIlbo_pay_width() {
		return ilbo_pay_width;
	}
	public void setIlbo_pay_width(String ilbo_pay_width) {
		this.ilbo_pay_width = ilbo_pay_width;
	}
	public String getIlbo_pay_name_width() {
		return ilbo_pay_name_width;
	}
	public void setIlbo_pay_name_width(String ilbo_pay_name_width) {
		this.ilbo_pay_name_width = ilbo_pay_name_width;
	}
	public String getIlbo_pay_time_width() {
		return ilbo_pay_time_width;
	}
	public void setIlbo_pay_time_width(String ilbo_pay_time_width) {
		this.ilbo_pay_time_width = ilbo_pay_time_width;
	}
	public String getIlbo_income_memo_width() {
		return ilbo_income_memo_width;
	}
	public void setIlbo_income_memo_width(String ilbo_income_memo_width) {
		this.ilbo_income_memo_width = ilbo_income_memo_width;
	}
	
	public String getEvaluate_grade_width() {
		return evaluate_grade_width;
	}
	public void setEvaluate_grade_width(String evaluate_grade_width) {
		this.evaluate_grade_width = evaluate_grade_width;
	}
	public String getUse_yn_width() {
		return use_yn_width;
	}
	public void setUse_yn_width(String use_yn_width) {
		this.use_yn_width = use_yn_width;
	}
	public String getReg_date_width() {
		return reg_date_width;
	}
	public void setReg_date_width(String reg_date_width) {
		this.reg_date_width = reg_date_width;
	}
	public String getReg_admin_width() {
		return reg_admin_width;
	}
	public void setReg_admin_width(String reg_admin_width) {
		this.reg_admin_width = reg_admin_width;
	}
	public String getOwner_id_width() {
		return owner_id_width;
	}
	public void setOwner_id_width(String owner_id_width) {
		this.owner_id_width = owner_id_width;
	}
	public String getSetting_seq() {
		return setting_seq;
	}
	public void setSetting_seq(String setting_seq) {
		this.setting_seq = setting_seq;
	}
	public String getAdmin_seq() {
		return admin_seq;
	}
	public void setAdmin_seq(String admin_seq) {
		this.admin_seq = admin_seq;
	}
	public String getIlbo_date() {
		return ilbo_date;
	}
	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
	}
	public String getWorker_company_name() {
		return worker_company_name;
	}
	public void setWorker_company_name(String worker_company_name) {
		this.worker_company_name = worker_company_name;
	}
	public String getWorker_owner() {
		return worker_owner;
	}
	public void setWorker_owner(String worker_owner) {
		this.worker_owner = worker_owner;
	}
	public String getIlbo_worker_view() {
		return ilbo_worker_view;
	}
	public void setIlbo_worker_view(String ilbo_worker_view) {
		this.ilbo_worker_view = ilbo_worker_view;
	}
	public String getWorker_ilbo() {
		return worker_ilbo;
	}
	public void setWorker_ilbo(String worker_ilbo) {
		this.worker_ilbo = worker_ilbo;
	}
	public String getIlbo_worker_name() {
		return ilbo_worker_name;
	}
	public void setIlbo_worker_name(String ilbo_worker_name) {
		this.ilbo_worker_name = ilbo_worker_name;
	}
	public String getIlbo_worker_feature() {
		return ilbo_worker_feature;
	}
	public void setIlbo_worker_feature(String ilbo_worker_feature) {
		this.ilbo_worker_feature = ilbo_worker_feature;
	}
	public String getIlbo_worker_memo() {
		return ilbo_worker_memo;
	}
	public void setIlbo_worker_memo(String ilbo_worker_memo) {
		this.ilbo_worker_memo = ilbo_worker_memo;
	}
	public String getIlbo_assign_type() {
		return ilbo_assign_type;
	}
	public void setIlbo_assign_type(String ilbo_assign_type) {
		this.ilbo_assign_type = ilbo_assign_type;
	}
	public String getIlbo_worker_info() {
		return ilbo_worker_info;
	}
	public void setIlbo_worker_info(String ilbo_worker_info) {
		this.ilbo_worker_info = ilbo_worker_info;
	}
	public String getIlbo_worker_status_info() {
		return ilbo_worker_status_info;
	}
	public void setIlbo_worker_status_info(String ilbo_worker_status_info) {
		this.ilbo_worker_status_info = ilbo_worker_status_info;
	}
	public String getIlbo_memo() {
		return ilbo_memo;
	}
	public void setIlbo_memo(String ilbo_memo) {
		this.ilbo_memo = ilbo_memo;
	}
	public String getWork_company_name() {
		return work_company_name;
	}
	public void setWork_company_name(String work_company_name) {
		this.work_company_name = work_company_name;
	}
	public String getWork_owner() {
		return work_owner;
	}
	public void setWork_owner(String work_owner) {
		this.work_owner = work_owner;
	}
	public String getIlbo_employer_view() {
		return ilbo_employer_view;
	}
	public void setIlbo_employer_view(String ilbo_employer_view) {
		this.ilbo_employer_view = ilbo_employer_view;
	}
	public String getEmployer_ilbo() {
		return employer_ilbo;
	}
	public void setEmployer_ilbo(String employer_ilbo) {
		this.employer_ilbo = employer_ilbo;
	}
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getIlbo_employer_feature() {
		return ilbo_employer_feature;
	}
	public void setIlbo_employer_feature(String ilbo_employer_feature) {
		this.ilbo_employer_feature = ilbo_employer_feature;
	}
	public String getAddr_edit() {
		return addr_edit;
	}
	public void setAddr_edit(String addr_edit) {
		this.addr_edit = addr_edit;
	}
	public String getAddr_location() {
		return addr_location;
	}
	public void setAddr_location(String addr_location) {
		this.addr_location = addr_location;
	}
	public String getIlbo_work_info() {
		return ilbo_work_info;
	}
	public void setIlbo_work_info(String ilbo_work_info) {
		this.ilbo_work_info = ilbo_work_info;
	}
	public String getIlbo_work_order_name() {
		return ilbo_work_order_name;
	}
	public void setIlbo_work_order_name(String ilbo_work_order_name) {
		this.ilbo_work_order_name = ilbo_work_order_name;
	}
	public String getIlbo_use_info() {
		return ilbo_use_info;
	}
	public void setIlbo_use_info(String ilbo_use_info) {
		this.ilbo_use_info = ilbo_use_info;
	}
	public String getIlbo_status_info() {
		return ilbo_status_info;
	}
	public void setIlbo_status_info(String ilbo_status_info) {
		this.ilbo_status_info = ilbo_status_info;
	}
	public String getIlbo_work_breakfast_yn() {
		return ilbo_work_breakfast_yn;
	}
	public void setIlbo_work_breakfast_yn(String ilbo_work_breakfast_yn) {
		this.ilbo_work_breakfast_yn = ilbo_work_breakfast_yn;
	}
	public String getIlbo_work_age_min() {
		return ilbo_work_age_min;
	}
	public void setIlbo_work_age_min(String ilbo_work_age_min) {
		this.ilbo_work_age_min = ilbo_work_age_min;
	}
	public String getIlbo_work_age() {
		return ilbo_work_age;
	}
	public void setIlbo_work_age(String ilbo_work_age) {
		this.ilbo_work_age = ilbo_work_age;
	}
	public String getIlbo_work_blood_pressure() {
		return ilbo_work_blood_pressure;
	}
	public void setIlbo_work_blood_pressure(String ilbo_work_blood_pressure) {
		this.ilbo_work_blood_pressure = ilbo_work_blood_pressure;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getManager_phone() {
		return manager_phone;
	}
	public void setManager_phone(String manager_phone) {
		this.manager_phone = manager_phone;
	}
	public String getIlbo_work_manager_name() {
		return ilbo_work_manager_name;
	}
	public void setIlbo_work_manager_name(String ilbo_work_manager_name) {
		this.ilbo_work_manager_name = ilbo_work_manager_name;
	}
	public String getIlbo_work_manager_phone() {
		return ilbo_work_manager_phone;
	}
	public void setIlbo_work_manager_phone(String ilbo_work_manager_phone) {
		this.ilbo_work_manager_phone = ilbo_work_manager_phone;
	}
	public String getIlbo_work_arrival() {
		return ilbo_work_arrival;
	}
	public void setIlbo_work_arrival(String ilbo_work_arrival) {
		this.ilbo_work_arrival = ilbo_work_arrival;
	}
	public String getIlbo_work_finish() {
		return ilbo_work_finish;
	}
	public void setIlbo_work_finish(String ilbo_work_finish) {
		this.ilbo_work_finish = ilbo_work_finish;
	}
	public String getWork_ilbo() {
		return work_ilbo;
	}
	public void setWork_ilbo(String work_ilbo) {
		this.work_ilbo = work_ilbo;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public String getIlbo_kind_name() {
		return ilbo_kind_name;
	}
	public void setIlbo_kind_name(String ilbo_kind_name) {
		this.ilbo_kind_name = ilbo_kind_name;
	}
	public String getIlbo_job_comment() {
		return ilbo_job_comment;
	}
	public void setIlbo_job_comment(String ilbo_job_comment) {
		this.ilbo_job_comment = ilbo_job_comment;
	}
	public String getIlbo_chief_memo() {
		return ilbo_chief_memo;
	}
	public void setIlbo_chief_memo(String ilbo_chief_memo) {
		this.ilbo_chief_memo = ilbo_chief_memo;
	}
	public String getIlbo_income_name() {
		return ilbo_income_name;
	}
	public void setIlbo_income_name(String ilbo_income_name) {
		this.ilbo_income_name = ilbo_income_name;
	}
	public String getIlbo_income_time() {
		return ilbo_income_time;
	}
	public void setIlbo_income_time(String ilbo_income_time) {
		this.ilbo_income_time = ilbo_income_time;
	}
	public String getIlbo_unit_price() {
		return ilbo_unit_price;
	}
	public void setIlbo_unit_price(String ilbo_unit_price) {
		this.ilbo_unit_price = ilbo_unit_price;
	}
	public String getIlbo_fee() {
		return ilbo_fee;
	}
	public void setIlbo_fee(String ilbo_fee) {
		this.ilbo_fee = ilbo_fee;
	}
	public String getShare_fee() {
		return share_fee;
	}
	public void setShare_fee(String share_fee) {
		this.share_fee = share_fee;
	}
	public String getIlbo_deduction() {
		return ilbo_deduction;
	}
	public void setIlbo_deduction(String ilbo_deduction) {
		this.ilbo_deduction = ilbo_deduction;
	}
	public String getIlbo_pay() {
		return ilbo_pay;
	}
	public void setIlbo_pay(String ilbo_pay) {
		this.ilbo_pay = ilbo_pay;
	}
	public String getIlbo_pay_name() {
		return ilbo_pay_name;
	}
	public void setIlbo_pay_name(String ilbo_pay_name) {
		this.ilbo_pay_name = ilbo_pay_name;
	}
	public String getIlbo_pay_time() {
		return ilbo_pay_time;
	}
	public void setIlbo_pay_time(String ilbo_pay_time) {
		this.ilbo_pay_time = ilbo_pay_time;
	}
	public String getIlbo_income_memo() {
		return ilbo_income_memo;
	}
	public void setIlbo_income_memo(String ilbo_income_memo) {
		this.ilbo_income_memo = ilbo_income_memo;
	}
	
	public String getEvaluate_grade() {
		return evaluate_grade;
	}
	public void setEvaluate_grade(String evaluate_grade) {
		this.evaluate_grade = evaluate_grade;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_admin() {
		return reg_admin;
	}
	public void setReg_admin(String reg_admin) {
		this.reg_admin = reg_admin;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getSetting_use_yn() {
		return setting_use_yn;
	}
	public void setSetting_use_yn(String setting_use_yn) {
		this.setting_use_yn = setting_use_yn;
	}
	public String getSetting_del_yn() {
		return setting_del_yn;
	}
	public void setSetting_del_yn(String setting_del_yn) {
		this.setting_del_yn = setting_del_yn;
	}
	public String getSetting_reg_date() {
		return setting_reg_date;
	}
	public void setSetting_reg_date(String setting_reg_date) {
		this.setting_reg_date = setting_reg_date;
	}
	public String getSetting_reg_admin() {
		return setting_reg_admin;
	}
	public void setSetting_reg_admin(String setting_reg_admin) {
		this.setting_reg_admin = setting_reg_admin;
	}
	public String getSetting_mod_admin() {
		return setting_mod_admin;
	}
	public void setSetting_mod_admin(String setting_mod_admin) {
		this.setting_mod_admin = setting_mod_admin;
	}
	public String getSetting_mod_date() {
		return setting_mod_date;
	}
	public void setSetting_mod_date(String setting_mod_date) {
		this.setting_mod_date = setting_mod_date;
	}
	public String getEmployer_rating() {
		return employer_rating;
	}
	public void setEmployer_rating(String employer_rating) {
		this.employer_rating = employer_rating;
	}
	public String getEmployer_rating_width() {
		return employer_rating_width;
	}
	public void setEmployer_rating_width(String employer_rating_width) {
		this.employer_rating_width = employer_rating_width;
	}
}
