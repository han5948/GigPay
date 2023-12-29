package com.nemo.kr.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.column.LimitCount;


public class IlboDTO extends GridDefaultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6096171221838659082L;
	
	private String ilbo_seq;                                  // 출역 순번 - PK
	private String ilbo_date;                                 // 출역날짜
	private String ilbo_assign_type;									// 일보 배정 타입 일보 배정 타입 O:사무실 A: 앱자동 W:앱수동
	private String company_seq;                               // 소속회사 순번
	private String employer_seq;                              // 구인처 순번
	private String work_seq;                                  // 현장 순번
	private String worker_seq;                                // 구직자 순번
	private String order_work_seq;						// tbl_order_work 테이블의 순번

	private String ilbo_work_name;                            // 현장명
	private String ilbo_work_arrival;                         	// 현장도착시간
	private String ilbo_work_finish;                         	// 현장마감시간
	private String ilbo_work_breaktime;
	private String ilbo_work_addr;                            	// 현장주소
	private String work_sido;
	private String work_sigugun;
	private String work_dongmyun;
	private String work_rest;
	private String ilbo_work_latlng;                          	// 현장좌표
	private String ilbo_work_addr_comment;				// 주소설명
	private String ilbo_work_breakfast_yn;                  // 조식유무 - 1:유 0:무
	private String ilbo_work_age_min;
	private String ilbo_work_age;                             	// 나이제한 - 0: 제한없음
	private String ilbo_work_blood_pressure;               // 혈압제한 - 0:제한없음
	private String ilbo_work_manager_name;               // 현장담당자 이름
	private String ilbo_work_manager_fax;                  // 현장 팩스
	private String ilbo_work_manager_phone;              // 폰번호
	private String ilbo_work_manager_email;               // 현장담장자 이메일
	private String ilbo_order_code;                           // 타입 코드 - 1차 2차등 노출우선순위 ( 기본값을 고민하자)
	private String ilbo_order_name;                           // 타입 명 - 1차 2차 등 ( 기본값을 고민하자)
	private String ilbo_work_order_code;
	private String ilbo_work_order_name;
	
	private String ilbo_use_code;							// 공개여부 코드
	private String ilbo_use_name;							// 공개여부명
	private String ilbo_status_code;						// 상태 코드
	private String ilbo_status_name;						// 상태명

	private String ilbo_kind_code;                            	// 직종 코드 ( 기본값을 고민하자)
	private String ilbo_kind_name;                            	// 직종 명 ( 기본값을 고민하자)
	private String ilbo_job_detail_code;
	private String ilbo_job_detail_name;
	private String ilbo_job_add_code;
	private String ilbo_job_add_name;
	
	private String ilbo_job_comment;                         // 작업내용
	private String ilbo_chief_memo;
	private String ilbo_worker_name;                          // 근로자명
	private String ilbo_worker_phone;                         // 핸드폰번호
	private String ilbo_worker_addr;                          // 주소
	private String ilbo_worker_latlng;                        	// 일하러 가는 주소좌표
	private String ilbo_worker_ilgaja_addr;                   // 주소 (모바일)
	private String ilbo_worker_ilgaja_latlng;                 // 좌표 (모바일)
	private String ilbo_worker_jumin;                         // 주민번호
	private String ilbo_worker_job_code;                      // 전문분야 코드
	private String ilbo_worker_job_name;                      // 전문분야 명
	private String ilbo_worker_barcode;                       // 구직자 바코드
	private String ilbo_worker_memo;                          // 메모
	private String ilbo_worker_bank_code;                     // 은행코드 순번
	private String ilbo_worker_bank_name;                     // 은행명
	private String ilbo_worker_bank_account;                  // 계좌번호
	private String ilbo_worker_bank_owner;                    // 예금주
	private String ilbo_worker_feature;                       // 특징
	private String ilbo_worker_blood_pressure;                // 혈압 - 0:혈압없음 0> 혈압 수치
	private String ilbo_worker_OSH_yn;                        // 건설업 기초안전보건교육 이수 여부 - 0:미이수 1:이수
	private String ilbo_worker_jumin_scan_yn;                 // 주민등록증 scan 여부
	private String ilbo_worker_bankbook_scan_yn;              // 통장사본 scan 여부
	private String ilbo_worker_OSH_scan_yn;                   // 이수증 scan 여부
	private String ilbo_worker_certificate_scan_yn;           // 증명서 scan 여부
	private String ilbo_worker_app_status;                        // 일가자 탈퇴 유무 - 0:승인대기 1:승인완료 3:강제중지 4:본인중지 5:컨설팅 신청
	private String ilbo_worker_reserve_push_status;                          // 일가자 작업 예약 상태 0:본인중지 1:예약받음 2:관리자가 중지 시킴
	private String ilbo_worker_app_use_status;                            // 앱사용유무  -1:미사용 0:승인대기 1:사용 2:관리자중지 3:사용자중지
	private String ilbo_unit_price;                           // 단가
	private String ilbo_gongsu;                               // 공수
	private String ilbo_fee;                                  // 수수료
	private String fee_info;								  // 수수료 정보
	private String ilbo_extra_pay;                            // 추가지불
	private String ilbo_deduction;                            // 공제금
	private String ilbo_pay;                                  // 지불금
	private String pay_info;								  // 구직자 임금 정보
	private String ilbo_pay_code;                             // 지불타입 코드
	private String ilbo_pay_name;                             // 지불타입 명
	private String ilbo_pay_time;                             // 지불처리 시간
	private String ilbo_pay_memo;                             // 지불메모
	private String ilbo_income_code;                          // 입금 타입( 기본값을 고민하자)
	private String ilbo_income_name;                          // 입금 타입( 기본값을 고민하자)
	private String ilbo_income_time;                          // 입금 시간
	private String ilbo_income_memo;                          // 입금메모
	private String ilbo_confirm;                              	// 승인 - 0:미승인 1:승인  기본값0
	private String ilbo_memo;                                 // 일보메모
	private String ilbo_status;                               	// 매칭상태 0: 미지정 1:관리자 지정 2:푸쉬예약 3: 푸쉬지정 
	private String output_status_code;                     // 출역상태 코드 (code.code_type = 100) 
	private String output_status_name;                    // 출역상태 명 (code.code_type = 100) 
	private String ilbo_worker_status_code;                     // 구직자 상태 코드 (code.code_type = WSC) 
	
	private String ilbo_worker_status_name;                    // 구직자 상태명 (code.code_type = WSC) 
	private String employer_rating;                                	// 구인처 평가
	private String manager_rating;								// 구인자 평가

	private String company_name;                              // 소속회사 명
	private String employer_name;                             // 구인처 명
	private String work_name;                                 // 현장 명

	private String ilbo_employer_feature;                     // 구인처 특징

	private String ilbo_worker_age = "";                      // 구직자 나이
	private String ilbo_worker_sex = "";                      // 구직자 성별

	// 검색조건
	private String start_ilbo_date;                           // 시작일자
	private String end_ilbo_date;                             // 종료일자

	private String srh_ilbo_worker_reserve_push_status;                      // 일가자 작업 예약 상태 0:본인중지 1:예약받음 2:관리자가 중지 시킴
	private String srh_ilbo_worker_app_use_status;                        // 앱사용유무  -1:미사용 0:승인대기 1:사용 2:관리자중지 3:사용자중지
	private String srh_ilbo_status;                           // 매칭상태 0: 미지정 1:관리자 지정 2:푸쉬예약 3: 푸쉬지정 
	private String srh_worker_request;						  // 구직상태 : 구직신청자 포함

	private String srh_seq;                                   // 구직/구인대장 검색 seq 

	private String[] sel_ilbo_seq;                            // 선택된 출역 순번
	private String[] sel_ilbo_date;                           // 선택된 출역날짜 순번
	private String[] sel_company_seq;                      // 선택된 회사 순번
	private String[] sel_work_seq;                            // 선택된 현장 순번
	private String[] sel_code;

	private int copy_rows = 1;                                    // 복사할 행 수
	private String copy_worker;                               // 구인자 정보를 같이 복사할 지 여부 - Y: 복사
	private String from_ilbo_date;                            // 복사 시작일자
	private String to_ilbo_date;                              // 복사 대상일자
	private String mod_output_date;							// 앱에서 상태 병경 일시

	private List<IlboDTO> list;

	private String ilbo_worker_jumin_file_name;                 // 주민등록증 scan 여부
	private String ilbo_worker_bankbook_file_name;              // 통장사본 scan 여부
	private String ilbo_worker_OSH_file_name;                   // 이수증 scan 여부
	private String ilbo_worker_certificate_file_name;           // 증명서 scan 여부
	private String ilbo_worker_photo_file_name;				//본인사진

	/* 오더메니져*/
	private String manager_seq;
	private String manager_name;
	private String manager_phone;
	private String manager_type;								//메니져에서 사용 O: 오더메니져 E:구인처메니져
	private String visit_type;
	private String os_type;
	private String manager_push_yn;
	private String push_token;

	private List<OtherWorkerDTO> otherWorkerList = new ArrayList<OtherWorkerDTO>();


	private String worker_owner;								//worker 소유자
	private String work_owner;								//work 소유자

	private String worker_company_seq;
	private String worker_company_name;
	private String work_company_seq;
	private String work_company_name;
	private String share_fee;				//구직자를 보낸 지점의 수수료
	
	private String code_type;		//코드에대한 로그를 남기기 위해 추가함. 
	private String code_group;

	private String day_count;									// 출역일수
	private String evaluate_grade;								// 구직자 평가.
	private String evaluate_comment;							// 구직자 평가 코멘트
	private String seq;										// 구직자 평가 순번

	private String distance;


	private String counselor_fee;				//상담사 수수료
	private String counselor_rate;				//상담사 수수료 율
	
	private String json_seq;
	private String worker_cnt;
	private String companion_cnt;
	private String job_kind;
	private String source_work_seq;
	private String job_work_fee;
	private String job_worker_fee;
	private String work_limit_count;
	private String employer_limit_count;
	private String worker_limit_count;
	private String limit_count;
	private LimitCount limitCount;
	private boolean employer_limit;
	private boolean work_limit;
	
	private String payment;
	
	private String surrogate;
	private String labor_contract;
	private String management_seq;
	
	private String labor_contract_seq;
	private String labor_contract_name;
	private String receive_contract_seq;
	private String receive_contract_name;
	private String sign_manager_seq;
	
	private String ilbo_career_name;
	private String ilbo_career_use_seq;
	private String selCareerName;
	
	private String work_skip_addr;
	
	private String labor_management_seq;
	private String receive_management_seq;
	
	private String before_worker_seq;
	private String before_worker_company_seq;
	
	private String deductible_sum;
	private String wages_received;
	private String work_fee;
	private String worker_fee;
	
	private String ago_count;
	
	private String deduction_seq;
	private String deduction_status;
	
	private String employer_income_code;
	private String employer_income_name;
	private String employer_pay_code;
	private String employer_pay_name;
	
	private String national_pension;
	private String health_insurance;
	private String employer_insurance;
	
	private String insurance_check_date;
	
	private String auto_status;
	private String auto_status_name;
	private String auto_send_worker_count;
	private String auto_company_use_yn;
	
	private String manager_use_status;
	
	private String parking_option;
	private String employer_detail;
	private String work_day;
	private String ilbo_worker_add_memo;
	
	private String companion_count;
	
	public boolean isAutoSendStopStatus() {
		return "2".equals(auto_status) || "3".equals(auto_status)
				|| "4".equals(auto_status);
	}
	
	public String getAuto_company_use_yn() {
		return auto_company_use_yn;
	}

	public void setAuto_company_use_yn(String auto_company_use_yn) {
		this.auto_company_use_yn = auto_company_use_yn;
	}

	public String getAuto_status_name() {
		return auto_status_name;
	}

	public void setAuto_status_name(String auto_status_name) {
		this.auto_status_name = auto_status_name;
	}

	public String getAuto_status() {
		return auto_status;
	}

	public void setAuto_status(String auto_status) {
		this.auto_status = auto_status;
	}

	public String getInsurance_check_date() {
		return insurance_check_date;
	}

	public void setInsurance_check_date(String insurance_check_date) {
		this.insurance_check_date = insurance_check_date;
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

	public String getEmployer_insurance() {
		return employer_insurance;
	}

	public void setEmployer_insurance(String employer_insurance) {
		this.employer_insurance = employer_insurance;
	}

	public String getEmployer_income_code() {
		return employer_income_code;
	}

	public void setEmployer_income_code(String employer_income_code) {
		this.employer_income_code = employer_income_code;
	}

	public String getEmployer_income_name() {
		return employer_income_name;
	}

	public void setEmployer_income_name(String employer_income_name) {
		this.employer_income_name = employer_income_name;
	}

	public String getEmployer_pay_code() {
		return employer_pay_code;
	}

	public void setEmployer_pay_code(String employer_pay_code) {
		this.employer_pay_code = employer_pay_code;
	}

	public String getEmployer_pay_name() {
		return employer_pay_name;
	}

	public void setEmployer_pay_name(String employer_pay_name) {
		this.employer_pay_name = employer_pay_name;
	}

	public String getDeduction_seq() {
		return deduction_seq;
	}

	public void setDeduction_seq(String deduction_seq) {
		this.deduction_seq = deduction_seq;
	}

	public String getAgo_count() {
		return ago_count;
	}

	public void setAgo_count(String ago_count) {
		this.ago_count = ago_count;
	}

	public String getWages_received() {
		return wages_received;
	}

	public void setWages_received(String wages_received) {
		this.wages_received = wages_received;
	}

	public String getDeductible_sum() {
		return deductible_sum;
	}

	public void setDeductible_sum(String deductible_sum) {
		this.deductible_sum = deductible_sum;
	}

	public String getWork_fee() {
		return work_fee;
	}

	public void setWork_fee(String work_fee) {
		this.work_fee = work_fee;
	}

	public String getWorker_fee() {
		return worker_fee;
	}

	public void setWorker_fee(String worker_fee) {
		this.worker_fee = worker_fee;
	}

	public String getBefore_worker_seq() {
		return before_worker_seq;
	}

	public void setBefore_worker_seq(String before_worker_seq) {
		this.before_worker_seq = before_worker_seq;
	}

	public String getSelCareerName() {
		return selCareerName;
	}

	public void setSelCareerName(String selCareerName) {
		this.selCareerName = selCareerName;
	}

	public String getIlbo_work_breaktime() {
		return ilbo_work_breaktime;
	}

	public void setIlbo_work_breaktime(String ilbo_work_breaktime) {
		this.ilbo_work_breaktime = ilbo_work_breaktime;
	}

	public String getIlbo_career_name() {
		return ilbo_career_name;
	}

	public void setIlbo_career_name(String ilbo_career_name) {
		this.ilbo_career_name = ilbo_career_name;
	}

	public String getIlbo_career_use_seq() {
		return ilbo_career_use_seq;
	}

	public void setIlbo_career_use_seq(String ilbo_career_use_seq) {
		this.ilbo_career_use_seq = ilbo_career_use_seq;
	}

	public LimitCount getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(LimitCount limitCount) {
		this.limitCount = limitCount;
	}

	public String getSign_manager_seq() {
		return sign_manager_seq;
	}

	public void setSign_manager_seq(String sign_manager_seq) {
		this.sign_manager_seq = sign_manager_seq;
	}

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

	public String getManagement_seq() {
		return management_seq;
	}

	public void setManagement_seq(String management_seq) {
		this.management_seq = management_seq;
	}

	public String getSurrogate() {
		return surrogate;
	}

	public void setSurrogate(String surrogate) {
		this.surrogate = surrogate;
	}

	public String getLabor_contract() {
		return labor_contract;
	}

	public void setLabor_contract(String labor_contract) {
		this.labor_contract = labor_contract;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public boolean isEmployer_limit() {
		return employer_limit;
	}

	public void setEmployer_limit(boolean employer_limit) {
		this.employer_limit = employer_limit;
	}

	public boolean isWork_limit() {
		return work_limit;
	}

	public void setWork_limit(boolean work_limit) {
		this.work_limit = work_limit;
	}

	public String getLimit_count() {
		return limit_count;
	}

	public void setLimit_count(String limit_count) {
		this.limit_count = limit_count;
	}

	public String getWork_limit_count() {
		return work_limit_count;
	}

	public void setWork_limit_count(String work_limit_count) {
		this.work_limit_count = work_limit_count;
	}

	public String getEmployer_limit_count() {
		return employer_limit_count;
	}

	public void setEmployer_limit_count(String employer_limit_count) {
		this.employer_limit_count = employer_limit_count;
	}

	public String getWorker_limit_count() {
		return worker_limit_count;
	}

	public void setWorker_limit_count(String worker_limit_count) {
		this.worker_limit_count = worker_limit_count;
	}

	public String getJob_worker_fee() {
		return job_worker_fee;
	}

	public void setJob_worker_fee(String job_worker_fee) {
		this.job_worker_fee = job_worker_fee;
	}
	
	public String getJob_work_fee() {
		return job_work_fee;
	}

	public void setJob_work_fee(String job_work_fee) {
		this.job_work_fee = job_work_fee;
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

	public String getJob_kind() {
		return job_kind;
	}

	public void setJob_kind(String job_kind) {
		this.job_kind = job_kind;
	}

	public String getCompanion_cnt() {
		return companion_cnt;
	}

	public void setCompanion_cnt(String companion_cnt) {
		this.companion_cnt = companion_cnt;
	}

	public String getWorker_cnt() {
		return worker_cnt;
	}

	public void setWorker_cnt(String worker_cnt) {
		this.worker_cnt = worker_cnt;
	}

	public String getJson_seq() {
		return json_seq;
	}

	public void setJson_seq(String json_seq) {
		this.json_seq = json_seq;
	}

	public String getIlbo_work_order_code() {
		return ilbo_work_order_code;
	}

	public void setIlbo_work_order_code(String ilbo_work_order_code) {
		this.ilbo_work_order_code = ilbo_work_order_code;
	}

	public String getIlbo_work_order_name() {
		return ilbo_work_order_name;
	}

	public void setIlbo_work_order_name(String ilbo_work_order_name) {
		this.ilbo_work_order_name = ilbo_work_order_name;
	}

	public String getIlbo_status_code() {
		return ilbo_status_code;
	}

	public void setIlbo_status_code(String ilbo_status_code) {
		this.ilbo_status_code = ilbo_status_code;
	}

	public String getIlbo_status_name() {
		return ilbo_status_name;
	}

	public void setIlbo_status_name(String ilbo_status_name) {
		this.ilbo_status_name = ilbo_status_name;
	}

	public String getIlbo_use_code() {
		return ilbo_use_code;
	}

	public void setIlbo_use_code(String ilbo_use_code) {
		this.ilbo_use_code = ilbo_use_code;
	}

	public String getIlbo_use_name() {
		return ilbo_use_name;
	}

	public void setIlbo_use_name(String ilbo_use_name) {
		this.ilbo_use_name = ilbo_use_name;
	}

	public String getIlbo_worker_status_code() {
		return ilbo_worker_status_code;
	}

	public void setIlbo_worker_status_code(String ilbo_worker_status_code) {
		this.ilbo_worker_status_code = ilbo_worker_status_code;
	}

	public String getIlbo_worker_status_name() {
		return ilbo_worker_status_name;
	}

	public void setIlbo_worker_status_name(String ilbo_worker_status_name) {
		this.ilbo_worker_status_name = ilbo_worker_status_name;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}


	public String getEvaluate_comment() {
		return evaluate_comment;
	}

	public void setEvaluate_comment(String evaluate_comment) {
		this.evaluate_comment = evaluate_comment;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getIlbo_date() {
		return ilbo_date;
	}
	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
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
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getWorker_seq() {
		return worker_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}

	public String getIlbo_work_name() {
		return ilbo_work_name;
	}
	public void setIlbo_work_name(String ilbo_work_name) {
		this.ilbo_work_name = ilbo_work_name;
	}
	public String getIlbo_work_arrival() {
		return ilbo_work_arrival;
	}
	public void setIlbo_work_arrival(String ilbo_work_arrival) {
		this.ilbo_work_arrival = ilbo_work_arrival.replace(":", "");
	}
	public String getIlbo_work_addr() {
		return ilbo_work_addr;
	}
	public void setIlbo_work_addr(String ilbo_work_addr) {
		this.ilbo_work_addr = ilbo_work_addr;
	}
	public String getIlbo_work_latlng() {
		return ilbo_work_latlng;
	}
	public void setIlbo_work_latlng(String ilbo_work_latlng) {
		this.ilbo_work_latlng = ilbo_work_latlng;
	}
	public String getIlbo_work_breakfast_yn() {
		return ilbo_work_breakfast_yn;
	}
	public void setIlbo_work_breakfast_yn(String ilbo_work_breakfast_yn) {
		this.ilbo_work_breakfast_yn = ilbo_work_breakfast_yn;
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
	public String getIlbo_work_manager_name() {
		return ilbo_work_manager_name;
	}
	public void setIlbo_work_manager_name(String ilbo_work_manager_name) {
		this.ilbo_work_manager_name = ilbo_work_manager_name;
	}
	public String getIlbo_work_manager_fax() {
		return ilbo_work_manager_fax;
	}
	public void setIlbo_work_manager_fax(String ilbo_work_manager_fax) {
		this.ilbo_work_manager_fax = ilbo_work_manager_fax;
	}
	public String getIlbo_work_manager_phone() {
		return ilbo_work_manager_phone;
	}
	public void setIlbo_work_manager_phone(String ilbo_work_manager_phone) {
		this.ilbo_work_manager_phone = ilbo_work_manager_phone;
	}
	public String getIlbo_work_manager_email() {
		return ilbo_work_manager_email;
	}
	public void setIlbo_work_manager_email(String ilbo_work_manager_email) {
		this.ilbo_work_manager_email = ilbo_work_manager_email;
	}
	public String getIlbo_order_code() {
		return ilbo_order_code;
	}
	public void setIlbo_order_code(String ilbo_order_code) {
		if ( "".equals(ilbo_order_code) )             this.ilbo_order_code = null;
		else                                          this.ilbo_order_code = ilbo_order_code;
	}
	public String getIlbo_order_name() {
		return ilbo_order_name;
	}
	public void setIlbo_order_name(String ilbo_order_name) {
		this.ilbo_order_name = ilbo_order_name;
	}
	public String getIlbo_kind_code() {
		return ilbo_kind_code;
	}
	public void setIlbo_kind_code(String ilbo_kind_code) {
		if ( "".equals(ilbo_kind_code) )              this.ilbo_kind_code = null;
		else                                          this.ilbo_kind_code = ilbo_kind_code;
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
	public String getIlbo_worker_name() {
		return ilbo_worker_name;
	}
	public void setIlbo_worker_name(String ilbo_worker_name) {
		this.ilbo_worker_name = ilbo_worker_name;
	}
	public String getIlbo_worker_phone() {
		return ilbo_worker_phone;
	}
	public void setIlbo_worker_phone(String ilbo_worker_phone) {
		this.ilbo_worker_phone = ilbo_worker_phone;
	}
	public String getIlbo_worker_addr() {
		return ilbo_worker_addr;
	}
	public void setIlbo_worker_addr(String ilbo_worker_addr) {
		this.ilbo_worker_addr = ilbo_worker_addr;
	}
	public String getIlbo_worker_latlng() {
		return ilbo_worker_latlng;
	}
	public void setIlbo_worker_latlng(String ilbo_worker_latlng) {
		this.ilbo_worker_latlng = ilbo_worker_latlng;
	}

	public String getIlbo_worker_jumin() {
		return ilbo_worker_jumin;
	}
	public void setIlbo_worker_jumin(String ilbo_worker_jumin) {
		this.ilbo_worker_jumin = ilbo_worker_jumin;
	}
	public String getIlbo_worker_job_code() {
		return ilbo_worker_job_code;
	}
	public void setIlbo_worker_job_code(String ilbo_worker_job_code) {
		if ( "".equals(ilbo_worker_job_code))         this.ilbo_worker_job_code = null;
		else                                          this.ilbo_worker_job_code = ilbo_worker_job_code;
	}
	public String getIlbo_worker_job_name() {
		return ilbo_worker_job_name;
	}
	public void setIlbo_worker_job_name(String ilbo_worker_job_name) {
		this.ilbo_worker_job_name = ilbo_worker_job_name;
	}
	public String getIlbo_worker_barcode() {
		return ilbo_worker_barcode;
	}
	public void setIlbo_worker_barcode(String ilbo_worker_barcode) {
		this.ilbo_worker_barcode = ilbo_worker_barcode;
	}
	public String getIlbo_worker_memo() {
		return ilbo_worker_memo;
	}
	public void setIlbo_worker_memo(String ilbo_worker_memo) {
		this.ilbo_worker_memo = ilbo_worker_memo;
	}
	public String getIlbo_worker_bank_code() {
		return ilbo_worker_bank_code;
	}
	public void setIlbo_worker_bank_code(String ilbo_worker_bank_code) {
		if ( "".equals(ilbo_worker_bank_code) )       this.ilbo_worker_bank_code = null;
		else                                          this.ilbo_worker_bank_code = ilbo_worker_bank_code;
	}
	public String getIlbo_worker_bank_name() {
		return ilbo_worker_bank_name;
	}
	public void setIlbo_worker_bank_name(String ilbo_worker_bank_name) {
		this.ilbo_worker_bank_name = ilbo_worker_bank_name;
	}
	public String getIlbo_worker_bank_account() {
		return ilbo_worker_bank_account;
	}
	public void setIlbo_worker_bank_account(String ilbo_worker_bank_account) {
		this.ilbo_worker_bank_account = ilbo_worker_bank_account;
	}
	public String getIlbo_worker_bank_owner() {
		return ilbo_worker_bank_owner;
	}
	public void setIlbo_worker_bank_owner(String ilbo_worker_bank_owner) {
		this.ilbo_worker_bank_owner = ilbo_worker_bank_owner;
	}
	public String getIlbo_worker_feature() {
		return ilbo_worker_feature;
	}
	public void setIlbo_worker_feature(String ilbo_worker_feature) {
		this.ilbo_worker_feature = ilbo_worker_feature;
	}
	public String getIlbo_worker_blood_pressure() {
		return ilbo_worker_blood_pressure;
	}
	public void setIlbo_worker_blood_pressure(String ilbo_worker_blood_pressure) {
		this.ilbo_worker_blood_pressure = ilbo_worker_blood_pressure;
	}
	public String getIlbo_worker_OSH_yn() {
		return ilbo_worker_OSH_yn;
	}
	public void setIlbo_worker_OSH_yn(String ilbo_worker_OSH_yn) {
		this.ilbo_worker_OSH_yn = ilbo_worker_OSH_yn;
	}
	public String getIlbo_worker_jumin_scan_yn() {
		return ilbo_worker_jumin_scan_yn;
	}
	public void setIlbo_worker_jumin_scan_yn(String ilbo_worker_jumin_scan_yn) {
		this.ilbo_worker_jumin_scan_yn = ilbo_worker_jumin_scan_yn;
	}
	public String getIlbo_worker_bankbook_scan_yn() {
		return ilbo_worker_bankbook_scan_yn;
	}
	public void setIlbo_worker_bankbook_scan_yn(String ilbo_worker_bankbook_scan_yn) {
		this.ilbo_worker_bankbook_scan_yn = ilbo_worker_bankbook_scan_yn;
	}
	public String getIlbo_worker_OSH_scan_yn() {
		return ilbo_worker_OSH_scan_yn;
	}
	public void setIlbo_worker_OSH_scan_yn(String ilbo_worker_OSH_scan_yn) {
		this.ilbo_worker_OSH_scan_yn = ilbo_worker_OSH_scan_yn;
	}
	public String getIlbo_worker_certificate_scan_yn() {
		return ilbo_worker_certificate_scan_yn;
	}
	public void setIlbo_worker_certificate_scan_yn(String ilbo_worker_certificate_scan_yn) {
		this.ilbo_worker_certificate_scan_yn = ilbo_worker_certificate_scan_yn;
	}
	public String getIlbo_worker_app_status() {
		return ilbo_worker_app_status;
	}
	public void setIlbo_worker_app_status(String ilbo_worker_app_status) {
		this.ilbo_worker_app_status = ilbo_worker_app_status;
	}
	public String getIlbo_worker_reserve_push_status() {
		return ilbo_worker_reserve_push_status;
	}
	public void setIlbo_worker_reserve_push_status(String ilbo_worker_reserve_push_status) {
		this.ilbo_worker_reserve_push_status = ilbo_worker_reserve_push_status;
	}
	public String getIlbo_worker_app_use_status() {
		return ilbo_worker_app_use_status;
	}
	public void setIlbo_worker_app_use_status(String ilbo_worker_app_use_status) {
		this.ilbo_worker_app_use_status = ilbo_worker_app_use_status;
	}
	public String getIlbo_unit_price() {
		return ilbo_unit_price;
	}
	public void setIlbo_unit_price(String ilbo_unit_price) {
		this.ilbo_unit_price = ilbo_unit_price;
	}
	public String getIlbo_gongsu() {
		return ilbo_gongsu;
	}
	public void setIlbo_gongsu(String ilbo_gongsu) {
		this.ilbo_gongsu = ilbo_gongsu;
	}
	public String getIlbo_fee() {
		return ilbo_fee;
	}
	public void setIlbo_fee(String ilbo_fee) {
		this.ilbo_fee = ilbo_fee;
	}
	public String getIlbo_extra_pay() {
		return ilbo_extra_pay;
	}
	public void setIlbo_extra_pay(String ilbo_extra_pay) {
		this.ilbo_extra_pay = ilbo_extra_pay;
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
	public String getIlbo_pay_code() {
		return ilbo_pay_code;
	}
	public void setIlbo_pay_code(String ilbo_pay_code) {
		if ( "".equals(ilbo_pay_code) )               this.ilbo_pay_code = null;
		else                                          this.ilbo_pay_code = ilbo_pay_code;
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
	public String getIlbo_pay_memo() {
		return ilbo_pay_memo;
	}
	public void setIlbo_pay_memo(String ilbo_pay_memo) {
		this.ilbo_pay_memo = ilbo_pay_memo;
	}
	public String getIlbo_income_code() {
		return ilbo_income_code;
	}
	public void setIlbo_income_code(String ilbo_income_code) {
		if ( "".equals(ilbo_income_code) )            this.ilbo_income_code = null;
		else                                          this.ilbo_income_code = ilbo_income_code;
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
	public String getIlbo_income_memo() {
		return ilbo_income_memo;
	}
	public void setIlbo_income_memo(String ilbo_income_memo) {
		this.ilbo_income_memo = ilbo_income_memo;
	}
	public String getIlbo_confirm() {
		return ilbo_confirm;
	}
	public void setIlbo_confirm(String ilbo_confirm) {
		this.ilbo_confirm = ilbo_confirm;
	}
	public String getIlbo_memo() {
		return ilbo_memo;
	}
	public void setIlbo_memo(String ilbo_memo) {
		this.ilbo_memo = ilbo_memo;
	}
	public String getIlbo_status() {
		return ilbo_status;
	}
	public void setIlbo_status(String ilbo_status) {
		this.ilbo_status = ilbo_status;
	}
	public String getOutput_status_code() {
		return output_status_code;
	}
	public void setOutput_status_code(String output_status_code) {
		if ( "".equals(output_status_code) )          this.output_status_code = null;
		else                                          this.output_status_code = output_status_code;
	}
	public String getOutput_status_name() {
		return output_status_name;
	}
	public void setOutput_status_name(String output_status_name) {
		this.output_status_name = output_status_name;
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
	public String getWork_name() {
		if ( work_name != null && !"".equals(work_name) ) {
			return work_name;
		} else {
			return getIlbo_work_name();
		}
	}
	public void setWork_name(String work_name) {
		if ( work_name != null && !"".equals(work_name) ) {
			this.work_name = work_name;
		} else {
			this.work_name = getIlbo_work_name();
		}
	}
	public String getIlbo_employer_feature() {
		return ilbo_employer_feature;
	}
	public void setIlbo_employer_feature(String ilbo_employer_feature) {
		this.ilbo_employer_feature = ilbo_employer_feature;
	}


	public String getIlbo_worker_age() {
		if ( ilbo_worker_age != null && !"".equals(ilbo_worker_age) ) {
			return ilbo_worker_age;
		} else {
			return StringUtil.isNullToString(StringUtil.getjuminAge(getIlbo_worker_jumin()), "00");
		}
	}
	public void setIlbo_worker_age(String ilbo_worker_age) {
		if ( ilbo_worker_age != null && !"".equals(ilbo_worker_age) ) {
			this.ilbo_worker_age = ilbo_worker_age;
		} else {
			this.ilbo_worker_age = StringUtil.getjuminAge(getIlbo_worker_jumin());
		}
	}
	public String getIlbo_worker_sex() {
		if ( ilbo_worker_sex != null && !"".equals(ilbo_worker_sex) ) {
			return ilbo_worker_sex;
		} else {
			return StringUtil.getjuminSex(getIlbo_worker_jumin());
		}
	}
	public void setIlbo_worker_sex(String ilbo_worker_sex) {
		if ( ilbo_worker_sex != null && !"".equals(ilbo_worker_sex) ) {
			this.ilbo_worker_sex = ilbo_worker_sex;
		} else {
			this.ilbo_worker_sex = StringUtil.getjuminSex(getIlbo_worker_jumin());
		}
	}


	public String getStart_ilbo_date() {
		return start_ilbo_date;
	}
	public void setStart_ilbo_date(String start_ilbo_date) {
		this.start_ilbo_date = start_ilbo_date;
	}
	public String getEnd_ilbo_date() {
		return end_ilbo_date;
	}
	public void setEnd_ilbo_date(String end_ilbo_date) {
		this.end_ilbo_date = end_ilbo_date;
	}


	public String getSrh_ilbo_worker_reserve_push_status() {
		return srh_ilbo_worker_reserve_push_status;
	}
	public void setSrh_ilbo_worker_reserve_push_status(String srh_ilbo_worker_reserve_push_status) {
		this.srh_ilbo_worker_reserve_push_status = srh_ilbo_worker_reserve_push_status;
	}
	public String getSrh_ilbo_worker_app_use_status() {
		return srh_ilbo_worker_app_use_status;
	}
	public void setSrh_ilbo_worker_app_use_status(String srh_ilbo_worker_app_use_status) {
		this.srh_ilbo_worker_app_use_status = srh_ilbo_worker_app_use_status;
	}
	public String getSrh_ilbo_status() {
		return srh_ilbo_status;
	}
	public void setSrh_ilbo_status(String srh_ilbo_status) {
		this.srh_ilbo_status = srh_ilbo_status;
	}


	public String getSrh_seq() {
		return srh_seq;
	}
	public void setSrh_seq(String srh_seq) {
		this.srh_seq = srh_seq;
	}


	public String[] getSel_ilbo_seq() {
		return sel_ilbo_seq;
	}
	public void setSel_ilbo_seq(String[] sel_ilbo_seq) {
		this.sel_ilbo_seq = sel_ilbo_seq;
	}
	public String[] getSel_ilbo_date() {
		return sel_ilbo_date;
	}
	public void setSel_ilbo_date(String[] sel_ilbo_date) {
		this.sel_ilbo_date = sel_ilbo_date;
	}
	public String[] getSel_company_seq() {
		return sel_company_seq;
	}
	public void setSel_company_seq(String[] sel_company_seq) {
		this.sel_company_seq = sel_company_seq;
	}
	public String[] getSel_work_seq() {
		return sel_work_seq;
	}
	public void setSel_work_seq(String[] sel_work_seq) {
		this.sel_work_seq = sel_work_seq;
	}


	public int getCopy_rows() {
		return copy_rows;
	}
	public void setCopy_rows(int copy_rows) {
		this.copy_rows = copy_rows;
	}
	public String getCopy_worker() {
		return copy_worker;
	}
	public void setCopy_worker(String copy_worker) {
		this.copy_worker = copy_worker;
	}
	public String getFrom_ilbo_date() {
		return from_ilbo_date;
	}
	public void setFrom_ilbo_date(String from_ilbo_date) {
		this.from_ilbo_date = from_ilbo_date;
	}
	public String getTo_ilbo_date() {
		return to_ilbo_date;
	}
	public void setTo_ilbo_date(String to_ilbo_date) {
		this.to_ilbo_date = to_ilbo_date;
	}


	public List<IlboDTO> getList() {
		return list;
	}
	public void setList(List<IlboDTO> list) {
		this.list = list;
	}

	public String getIlbo_worker_ilgaja_addr() {
		return ilbo_worker_ilgaja_addr;
	}
	public String getIlbo_worker_ilgaja_latlng() {
		return ilbo_worker_ilgaja_latlng;
	}
	public void setIlbo_worker_ilgaja_latlng(String ilbo_worker_ilgaja_latlng) {
		this.ilbo_worker_ilgaja_latlng = ilbo_worker_ilgaja_latlng;
	}

	public String getCode_group() {
		return code_group;
	}

	public void setCode_group(String code_group) {
		this.code_group = code_group;
	}

	public String getIlbo_work_addr_comment() {
		if(ilbo_work_addr_comment == null )
			return "";
		else
			return ilbo_work_addr_comment;
	}

	public void setIlbo_work_addr_comment(String ilbo_work_addr_comment) {
		this.ilbo_work_addr_comment = ilbo_work_addr_comment;
	}

	public void setIlbo_worker_ilgaja_addr(String ilbo_worker_ilgaja_addr) {
		this.ilbo_worker_ilgaja_addr = ilbo_worker_ilgaja_addr;
	}

	public String getIlbo_work_finish() {
		return ilbo_work_finish;
	}

	public void setIlbo_work_finish(String ilbo_work_finish) {

		this.ilbo_work_finish = ilbo_work_finish.replace(":", "");
	}

	public List<OtherWorkerDTO> getOtherWorkerList() {
		return otherWorkerList;
	}

	public void setOtherWorkerList(List<OtherWorkerDTO> otherWorkerList) {
		this.otherWorkerList = otherWorkerList;
	}


	public String getIlbo_assign_type() {
		return ilbo_assign_type;
	}

	public void setIlbo_assign_type(String ilbo_assign_type) {
		this.ilbo_assign_type = ilbo_assign_type;
	}

	public String getMod_output_date() {
		return mod_output_date;
	}

	public void setMod_output_date(String mod_output_date) {
		this.mod_output_date = mod_output_date;
	}

	public String getIlbo_worker_jumin_file_name() {
		return ilbo_worker_jumin_file_name;
	}

	public void setIlbo_worker_jumin_file_name(String ilbo_worker_jumin_file_name) {
		this.ilbo_worker_jumin_file_name = ilbo_worker_jumin_file_name;
	}

	public String getIlbo_worker_bankbook_file_name() {
		return ilbo_worker_bankbook_file_name;
	}

	public void setIlbo_worker_bankbook_file_name(
			String ilbo_worker_bankbook_file_name) {
		this.ilbo_worker_bankbook_file_name = ilbo_worker_bankbook_file_name;
	}

	public String getIlbo_worker_OSH_file_name() {
		return ilbo_worker_OSH_file_name;
	}

	public void setIlbo_worker_OSH_file_name(String ilbo_worker_OSH_file_name) {
		this.ilbo_worker_OSH_file_name = ilbo_worker_OSH_file_name;
	}

	public String getIlbo_worker_certificate_file_name() {
		return ilbo_worker_certificate_file_name;
	}

	public void setIlbo_worker_certificate_file_name(
			String ilbo_worker_certificate_file_name) {
		this.ilbo_worker_certificate_file_name = ilbo_worker_certificate_file_name;
	}

	public String getManager_seq() {
		return manager_seq;
	}

	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}

	public String getManager_type() {
		return manager_type;
	}

	public void setManager_type(String manager_type) {
		this.manager_type = manager_type;
	}

	public String getWorker_owner() {
		return worker_owner;
	}

	public void setWorker_owner(String worker_owner) {
		this.worker_owner = worker_owner;
	}

	public String getWork_owner() {
		return work_owner;
	}

	public void setWork_owner(String work_owner) {
		this.work_owner = work_owner;
	}

	public String getWorker_company_seq() {
		return worker_company_seq;
	}

	public void setWorker_company_seq(String worker_company_seq) {
		this.worker_company_seq = worker_company_seq;
	}

	public String getWorker_company_name() {
		return worker_company_name;
	}

	public void setWorker_company_name(String worker_company_name) {
		this.worker_company_name = worker_company_name;
	}

	public String getWork_company_name() {
		return work_company_name;
	}

	public String getShare_fee() {
		return share_fee;
	}

	public void setShare_fee(String share_fee) {
		this.share_fee = share_fee;
	}

	public String getManager_phone() {
		return manager_phone;
	}

	public void setManager_phone(String manager_phone) {
		this.manager_phone = manager_phone;
	}

	public String getVisit_type() {
		return visit_type;
	}

	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public void setWork_company_name(String work_company_name) {
		this.work_company_name = work_company_name;
	}

	public String getOs_type() {
		return os_type;
	}

	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}

	public String getManager_push_yn() {
		return manager_push_yn;
	}

	public void setManager_push_yn(String manager_push_yn) {
		this.manager_push_yn = manager_push_yn;
	}

	public String getPush_token() {
		return push_token;
	}

	public void setPush_token(String push_token) {
		this.push_token = push_token;
	}

	public String getDay_count() {
		return day_count;
	}

	public void setDay_count(String day_count) {
		this.day_count = day_count;
	}

	public String getOrder_work_seq() {
		return order_work_seq;
	}

	public void setOrder_work_seq(String order_work_seq) {
		this.order_work_seq = order_work_seq;
	}

	public String getIlbo_worker_photo_file_name() {
		return ilbo_worker_photo_file_name;
	}

	public void setIlbo_worker_photo_file_name(String ilbo_worker_photo_file_name) {
		this.ilbo_worker_photo_file_name = ilbo_worker_photo_file_name;
	}

	public String getIlbo_work_age_min() {
		return ilbo_work_age_min;
	}

	public void setIlbo_work_age_min(String ilbo_work_age_min) {
		this.ilbo_work_age_min = ilbo_work_age_min;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}

	public String[] getSel_code() {
		return sel_code;
	}

	public void setSel_code(String[] sel_code) {
		this.sel_code = sel_code;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getEvaluate_grade() {
		return evaluate_grade;
	}

	public void setEvaluate_grade(String evaluate_grade) {
		this.evaluate_grade = evaluate_grade;
	}

	public String getIlbo_chief_memo() {
		return ilbo_chief_memo;
	}

	public void setIlbo_chief_memo(String ilbo_chief_memo) {
		this.ilbo_chief_memo = ilbo_chief_memo;
	}


	public String getEmployer_rating() {
		return employer_rating;
	}

	public void setEmployer_rating(String employer_rating) {
		this.employer_rating = employer_rating;
	}

	public String getManager_rating() {
		return manager_rating;
	}

	public void setManager_rating(String manager_rating) {
		this.manager_rating = manager_rating;
	}

	public String getCounselor_fee() {
		return counselor_fee;
	}

	public void setCounselor_fee(String counselor_fee) {
		this.counselor_fee = counselor_fee;
	}

	public String getCounselor_rate() {
		return counselor_rate;
	}

	public void setCounselor_rate(String counselor_rate) {
		this.counselor_rate = counselor_rate;
	}

	public String getIlbo_job_detail_code() {
		return ilbo_job_detail_code;
	}

	public void setIlbo_job_detail_code(String ilbo_job_detail_code) {
		this.ilbo_job_detail_code = ilbo_job_detail_code;
	}

	public String getIlbo_job_detail_name() {
		return ilbo_job_detail_name;
	}

	public void setIlbo_job_detail_name(String ilbo_job_detail_name) {
		this.ilbo_job_detail_name = ilbo_job_detail_name;
	}

	public String getIlbo_job_add_code() {
		return ilbo_job_add_code;
	}

	public void setIlbo_job_add_code(String ilbo_job_add_code) {
		this.ilbo_job_add_code = ilbo_job_add_code;
	}

	public String getWork_company_seq() {
		return work_company_seq;
	}

	public void setWork_company_seq(String work_company_seq) {
		this.work_company_seq = work_company_seq;
	}

	public String getIlbo_job_add_name() {
		return ilbo_job_add_name;
	}

	public void setIlbo_job_add_name(String ilbo_job_add_name) {
		this.ilbo_job_add_name = ilbo_job_add_name;
	}

	public String getFee_info() {
		return fee_info;
	}

	public void setFee_info(String fee_info) {
		this.fee_info = fee_info;
	}

	public String getPay_info() {
		return pay_info;
	}

	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}

	public String getWork_skip_addr() {
		return work_skip_addr;
	}

	public void setWork_skip_addr(String work_skip_addr) {
		this.work_skip_addr = work_skip_addr;
	}

	public String getLabor_management_seq() {
		return labor_management_seq;
	}

	public void setLabor_management_seq(String labor_management_seq) {
		this.labor_management_seq = labor_management_seq;
	}

	public String getReceive_management_seq() {
		return receive_management_seq;
	}

	public void setReceive_management_seq(String receive_management_seq) {
		this.receive_management_seq = receive_management_seq;
	}

	public String getBefore_worker_company_seq() {
		return before_worker_company_seq;
	}

	public void setBefore_worker_company_seq(String before_worker_company_seq) {
		this.before_worker_company_seq = before_worker_company_seq;
	}
	
	public boolean isEmployerPayCode() {
		if( this.ilbo_pay_code == null || this.ilbo_pay_code.isEmpty() ) {
			return false;
		}
		//구인처가 직접 지급하는 코드 = 직수, (직수), 페이, (페이)
		if( this.ilbo_pay_code.equals("200003") || this.ilbo_pay_code.equals("200007") 
				|| this.ilbo_pay_code.equals("200004") || this.ilbo_pay_code.equals("200008") ) {
			return true;
		}
		return false;
	}
	
	//현장 취소 상태
	public boolean isWorkCancel() {
		if( this.ilbo_status_code == null || this.ilbo_status_code.isEmpty() ) {
			return false;
		}
		//휴지,취소,메모
		if( this.ilbo_status_code.equals("STA007") || this.ilbo_status_code.equals("STA008") 
				|| this.ilbo_status_code.equals("STA009") ) {
			return true;
		}
		
		return false;
	}
	
	//구직자 취소 상태
	public boolean isWorkerCancel() {
		if( this.output_status_code == null || this.output_status_code.isEmpty() ) {
			return false;
		}
		
		//펑크, 째앰, 대마
		if( this.output_status_code.equals("100009") || this.output_status_code.equals("100010")
				|| this.output_status_code.equals("100008") ) {
			return true;
		}
		
		return false;
	}
	
	//공제액 계산시점 => 구직자상태(출발, 도착, 완료)
	public boolean isInsuranceCalculate() {
		return "100003".equals(output_status_code) || "100014".equals(output_status_code) || "100015".equals(output_status_code); 
	}
	
	public String getDeduction_status() {
		return deduction_status;
	}

	public void setDeduction_status(String deduction_status) {
		this.deduction_status = deduction_status;
	}
	
	public boolean matchIlboSeq(String ilboSeq) {
		return this.ilbo_seq.equals(ilboSeq);
	}
	public boolean isEnableEmployerInsurance() {
		return "1".equals(this.employer_insurance);
	}
	public boolean isEnableHealthInsuranceAndNationalPension() {
		return this.insurance_check_date != null;
	}

	public String getManager_use_status() {
		return manager_use_status;
	}

	public void setManager_use_status(String manager_use_status) {
		this.manager_use_status = manager_use_status;
	}

	public String getAuto_send_worker_count() {
		return auto_send_worker_count;
	}

	public void setAuto_send_worker_count(String auto_send_worker_count) {
		this.auto_send_worker_count = auto_send_worker_count;
	}

	public String getParking_option() {
		return parking_option;
	}

	public void setParking_option(String parking_option) {
		this.parking_option = parking_option;
	}

	public String getEmployer_detail() {
		return employer_detail;
	}

	public void setEmployer_detail(String employer_detail) {
		this.employer_detail = employer_detail;
	}

	public String getWork_day() {
		return work_day;
	}

	public void setWork_day(String work_day) {
		this.work_day = work_day;
	}

	public String getIlbo_worker_add_memo() {
		return ilbo_worker_add_memo;
	}

	public void setIlbo_worker_add_memo(String ilbo_worker_add_memo) {
		this.ilbo_worker_add_memo = ilbo_worker_add_memo;
	}

	public String getSrh_worker_request() {
		return srh_worker_request;
	}

	public void setSrh_worker_request(String srh_worker_request) {
		this.srh_worker_request = srh_worker_request;
	}

	public String getCompanion_count() {
		return companion_count;
	}

	public void setCompanion_count(String companion_count) {
		this.companion_count = companion_count;
	}
	
}