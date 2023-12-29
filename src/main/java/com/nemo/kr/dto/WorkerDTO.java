package com.nemo.kr.dto;

import java.util.List;

//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


public class WorkerDTO extends GridDefaultDTO {

	// nemojjang 2019. 7. 4.
	private static final long serialVersionUID = -5294743880954844412L;

	private String ilbo_seq;									//일보 순번
	private String worker_seq;                                // 구직자 순번 - PK
	private String company_seq;                              // 소속회사 순번
	
	private String worker_name;                               // 근로자명
	//  private String worker_sex;                                // 성별 - M: 남자 F: 여자
	private String worker_phone;                              // 핸드폰번호
	private String worker_addr;                               // 주소
	private String worker_addr_replace;							// 바꿔서 넘어 오는 주소
	private String worker_latlng;                             // 일하러 가는 주소좌표
	private String worker_ilgaja_addr;                        // 주소 (모바일)
	private String worker_ilgaja_latlng;                      // 좌표 (모바일)
	private String worker_jumin;                              // 주민번호
	private String worker_pass;                               // 패스워드
	private String login_key;                                 // 로그인 키
	private String worker_job_code;                           // 전문분야 코드
	private String worker_job_name;                           // 전문분야 명
	private String worker_tel1;                               // 연락처1
	private String worker_tel2;                               // 연락처2
	private String worker_tel3;                               // 연락처3
	private String worker_barcode;                            // 구직자 바코드
	private String worker_memo;                               // 메모
	private String worker_bank_code;                          // 은행코드 순번
	private String worker_bank_name;                          // 은행명
	private String worker_bank_account;                       // 계좌번호
	private String worker_bank_owner;                         // 예금주
	private String worker_feature;                            // 특징
	private String worker_blood_pressure;                     // 혈압 - 0:혈압없음 0> 혈압 수치
	private String worker_OSH_yn;                             // 건설업 기초안전보건교육 이수 여부 - 0:미이수 1:이수
	private String worker_myphoto_scan_yn;
	private String worker_jumin_scan_yn;                      // 주민등록증 scan 여부
	private String worker_bankbook_scan_yn;                   // 통장사본 scan 여부
	private String worker_OSH_scan_yn;                        // 이수증 scan 여부
	private String worker_job_scan_yn;                        // 구직신청서 scan 여부
	private String worker_certificate_scan_yn;                // 증명서 scan 여부

	private String worker_rating;						//소장평가.
	private String worker_app_status;                         // 일가자 탈퇴 유무 - 0:승인대기 1:승인완료 3:강제중지 4:본인중지 5:컨설팅 신청
	private String worker_app_status_kr;                          // 일가자 탈퇴 유무 (한글 표시)
	private String worker_reserve_push_status;                          // 일가자 작업 예약 상태 0:본인중지 1:예약받음 2:관리자가 중지 시킴
	private String worker_app_use_status;                            // 앱사용유무  -1:미사용 0:승인대기 1:사용 2:관리자중지 3:사용자중지
	private String worker_push_yn;                                   // 일반푸쉬 사용유무 -0:본인중지 1:사용 2:강제중지
	private String admin_push_yn;									 // 관리자가 설정하는 푸쉬유무 0:미사용, 1:사용
	private String sms_auth;                                  // 회원인증 sms
	private String os_type;									// A:안드로이드 I : IOS
	private String push_token;								// push 토큰
	private String company_name;                              // 소속회사 명
	private String company_account;							// 소속지점 계좌정보
	private String company_license_num;						// 소속회사 자격 번호
	private String company_phone;								// 소속회사 폰번호

	private String ilbo_output_count;                         // 출역 횟수
	private String ilbo_total_count;                          // 일보 횟수

	private String complete_count;							  // 완료 횟수
	private String ilbo_last_date;							  // 마지막 출역 날짜

	private String wait_approval_count;

	private String worker_age = "";                           // 구직자 나이
	private String worker_sex = "";                           // 구직자 성별

	private String worker_OSH_kr;                             // 건설업 기초안전보건교육 이수 여부 (한글 표시)
	private String worker_jumin_scan_kr;                      // 주민등록증 scan 여부 (한글 표시)
	private String worker_OSH_scan_kr;                        // 이수증 scan 여부 (한글 표시)


	private String[] arr_worker_app_status    = {"승인대기", "승인완료", "", "강제중지", "본인중지", "컨설팅 신청"};
	private String[] arr_worker_reserve_push_status = {"본인중지", "예약받음", "관리자 중지"};
	private String worker_reserve_push_status_kr;                       // 일가자 작업 예약 상태 (한글 표시)
	private String[] arr_worker_app_use_status   = {"승인대기", "사용", "관리자 중지", "사용자 중지"};  // -1: 미사용
	private String worker_app_use_status_kr;                         // 앱사용유무 (한글 표시)
	private String[] arr_worker_push_yn          = {"본인중지", "사용", "강제중지"};
	private String worker_push_yn_kr;                                // 일반푸쉬 사용유무 (한글 표시)

	private int auth_type;
	private String app_last_date;

	// 검색 조건
	private String srh_worker_app_status;                         // 일가자 탈퇴 유무 - 0:승인대기 1:승인완료 3:관리자중지 4:사용자중지 5:컨설팅 신청'
	private String[] sel_worker_seq;                          // 선택된 구직자 순번
	private List<WorkerDTO> list;

	private String app_version;
	private String phone_ct;
	private String term_date;							//약관 동의 날짜

	private String work_lat;
	private String work_lng;
	private String radius;
	private String ilbo_date;
	private String output_status_code;

	private String employer_seq;
	private String manager_seq;

	private String employer_rating;
	private String manager_rating;
	private String work_seq;
	private String rating_avg_cnt;
	private String rating_avg;

	private int detailSearch = 0;					//상세검색 유무 0: 미검색 1:검색
	private String[] search_worker_rating;	// 소장평가
	private String[] search_worker_age;		// 나이선택
	private String[] search_job_kind;			// 직종선택
	private String[] search_worker_car;
	private String search_worker_today;	// 오늘 출역 제외
	private String search_worker_tommorrow;	//내일 출역 제외
	private String search_worker_etc;	// 보통인부제외
	private String search_ilbo_worker;
	private String search_company;
	private String search_work_latlng;
	private String[] search_worker_latlng;
	private String companyFlag;
	private String search_worker_osh;		//기초안전보건교육증 보유자
	private String search_work_seq;			//일자리선택한 현장
	private String done_join_query;			//기출역자 조인 쿼리
	private String search_worker_sex;
	private String re_count;
	private String punk_cnt;
	private String convert_score;
	
	private String worker_education_yn;
	
	private String join_route;
	
	public String getConvert_score() {
		return convert_score;
	}
	public void setConvert_score(String convert_score) {
		this.convert_score = convert_score;
	}
	public String getPunk_cnt() {
		return punk_cnt;
	}
	public void setPunk_cnt(String punk_cnt) {
		this.punk_cnt = punk_cnt;
	}
	public String getrating_avg_cnt() {
		return rating_avg_cnt;
	}
	public void setrating_avg_cnt(String rating_avg_cnt) {
		this.rating_avg_cnt = rating_avg_cnt;
	}
	public String getrating_avg() {
		return rating_avg;
	}
	public void setrating_avg(String rating_avg) {
		this.rating_avg = rating_avg;
	}
	public String getRe_count() {
		return re_count;
	}
	public void setRe_count(String re_count) {
		this.re_count = re_count;
	}
	public String getSearch_worker_sex() {
		return search_worker_sex;
	}
	public void setSearch_worker_sex(String search_worker_sex) {
		this.search_worker_sex = search_worker_sex;
	}
	public String getCompanyFlag() {
		return companyFlag;
	}
	public void setCompanyFlag(String companyFlag) {
		this.companyFlag = companyFlag;
	}
	public String[] getSearch_worker_latlng() {
		return search_worker_latlng;
	}
	public void setSearch_worker_latlng(String[] search_worker_latlng) {
		this.search_worker_latlng = search_worker_latlng;
	}
	public String getSearch_work_latlng() {
		return search_work_latlng;
	}
	public void setSearch_work_latlng(String search_work_latlng) {
		this.search_work_latlng = search_work_latlng;
	}
	public String getSearch_company() {
		return search_company;
	}
	public void setSearch_company(String search_company) {
		this.search_company = search_company;
	}
	public String getSearch_ilbo_worker() {
		return search_ilbo_worker;
	}
	public void setSearch_ilbo_worker(String search_ilbo_worker) {
		this.search_ilbo_worker = search_ilbo_worker;
	}
	public String getOutput_status_code() {
		return output_status_code;
	}
	public void setOutput_status_code(String output_status_code) {
		this.output_status_code = output_status_code;
	}
	public String getIlbo_date() {
		return ilbo_date;
	}
	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public String getWork_lat() {
		return work_lat;
	}
	public void setWork_lat(String work_lat) {
		this.work_lat = work_lat;
	}
	public String getWork_lng() {
		return work_lng;
	}
	public void setWork_lng(String work_lng) {
		this.work_lng = work_lng;
	}

	public String getWorker_seq() {
		return worker_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}


	public String getWorker_name() {
		return worker_name;
	}
	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
	}
	/*
  public String getWorker_sex() {
    return worker_sex;
  }
  public void setWorker_sex(String worker_sex) {
    this.worker_sex = worker_sex;
  }
	 */
	public String getWorker_phone() {
		return worker_phone;
	}
	public void setWorker_phone(String worker_phone) {
		this.worker_phone = worker_phone.replaceAll("[^0-9]", "");
	}
	public String getWorker_addr() {
		return worker_addr;
	}
	public void setWorker_addr(String worker_addr) {
		this.worker_addr = worker_addr;
	}
	public String getWorker_latlng() {
		return worker_latlng;
	}
	public void setWorker_latlng(String worker_latlng) {
		this.worker_latlng = worker_latlng;
	}

	public String getWorker_jumin() {
		return worker_jumin;
	}
	public void setWorker_jumin(String worker_jumin) {
		this.worker_jumin = worker_jumin.replaceAll("-", "");;
	}
	public String getWorker_pass() {
		return worker_pass;
	}
	public void setWorker_pass(String worker_pass) {
		this.worker_pass = worker_pass;
	}
	public String getLogin_key() {
		return login_key;
	}
	public void setLogin_key(String login_key) {
		this.login_key = login_key;
	}
	public String getWorker_job_code() {
		return worker_job_code;
	}
	public void setWorker_job_code(String worker_job_code) {
		this.worker_job_code = worker_job_code;
	}
	public String getWorker_job_name() {
		return worker_job_name;
	}
	public void setWorker_job_name(String worker_job_name) {
		this.worker_job_name = worker_job_name;
	}
	public String getWorker_tel1() {
		return worker_tel1;
	}
	public void setWorker_tel1(String worker_tel1) {
		this.worker_tel1 = worker_tel1;
	}
	public String getWorker_tel2() {
		return worker_tel2;
	}
	public void setWorker_tel2(String worker_tel2) {
		this.worker_tel2 = worker_tel2;
	}
	public String getWorker_tel3() {
		return worker_tel3;
	}
	public void setWorker_tel3(String worker_tel3) {
		this.worker_tel3 = worker_tel3;
	}
	public String getWorker_barcode() {
		return worker_barcode;
	}
	public void setWorker_barcode(String worker_barcode) {
		this.worker_barcode = worker_barcode;
	}
	public String getWorker_memo() {
		return worker_memo;
	}
	public void setWorker_memo(String worker_memo) {
		this.worker_memo = worker_memo;
	}
	public String getWorker_bank_code() {
		return worker_bank_code;
	}
	public void setWorker_bank_code(String worker_bank_code) {
		this.worker_bank_code = worker_bank_code;
	}
	public String getWorker_bank_name() {
		return worker_bank_name;
	}
	public void setWorker_bank_name(String worker_bank_name) {
		this.worker_bank_name = worker_bank_name;
	}
	public String getWorker_bank_account() {
		return worker_bank_account;
	}
	public void setWorker_bank_account(String worker_bank_account) {
		this.worker_bank_account = worker_bank_account;
	}
	public String getWorker_bank_owner() {
		return worker_bank_owner;
	}
	public void setWorker_bank_owner(String worker_bank_owner) {
		this.worker_bank_owner = worker_bank_owner;
	}
	public String getWorker_feature() {
		return worker_feature;
	}
	public void setWorker_feature(String worker_feature) {
		this.worker_feature = worker_feature;
	}
	public String getWorker_blood_pressure() {
		return worker_blood_pressure;
	}
	public void setWorker_blood_pressure(String worker_blood_pressure) {
		this.worker_blood_pressure = worker_blood_pressure;
	}
	public String getWorker_OSH_yn() {
		return worker_OSH_yn;
	}
	public void setWorker_OSH_yn(String worker_OSH_yn) {
		this.worker_OSH_yn = worker_OSH_yn;
	}
	public String getWorker_jumin_scan_yn() {
		return worker_jumin_scan_yn;
	}
	public void setWorker_jumin_scan_yn(String worker_jumin_scan_yn) {
		this.worker_jumin_scan_yn = worker_jumin_scan_yn;
	}
	public String getWorker_bankbook_scan_yn() {
		return worker_bankbook_scan_yn;
	}
	public void setWorker_bankbook_scan_yn(String worker_bankbook_scan_yn) {
		this.worker_bankbook_scan_yn = worker_bankbook_scan_yn;
	}
	public String getWorker_OSH_scan_yn() {
		return worker_OSH_scan_yn;
	}
	public void setWorker_OSH_scan_yn(String worker_OSH_scan_yn) {
		this.worker_OSH_scan_yn = worker_OSH_scan_yn;
	}
	public String getWorker_certificate_scan_yn() {
		return worker_certificate_scan_yn;
	}
	public void setWorker_certificate_scan_yn(String worker_certificate_scan_yn) {
		this.worker_certificate_scan_yn = worker_certificate_scan_yn;
	}
	public String getWorker_app_status() {
		return worker_app_status;
	}
	public void setWorker_app_status(String worker_app_status) {
		this.worker_app_status = worker_app_status;
	}


	public String getWorker_reserve_push_status() {
		return worker_reserve_push_status;
	}
	public void setWorker_reserve_push_status(String worker_reserve_push_status) {
		this.worker_reserve_push_status = worker_reserve_push_status;
	}
	public String getWorker_app_use_status() {
		return worker_app_use_status;
	}
	public void setWorker_app_use_status(String worker_app_use_status) {
		this.worker_app_use_status = worker_app_use_status;
	}
	public String getWorker_push_yn() {
		return worker_push_yn;
	}
	public void setWorker_push_yn(String worker_push_yn) {
		this.worker_push_yn = worker_push_yn;
	}
	public String getSms_auth() {
		return sms_auth;
	}
	public void setSms_auth(String sms_auth) {
		this.sms_auth = sms_auth;
	}


	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
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


	public String getWorker_age() {
		if ( worker_age != null && !"".equals(worker_age) ) {
			return worker_age;
		} else {
			return StringUtil.isNullToString(StringUtil.getjuminAge(getWorker_jumin()), "00");
		}
	}
	public void setWorker_age(String worker_age) {
		if ( worker_age != null && !"".equals(worker_age) ) {
			this.worker_age = worker_age;
		} else {
			this.worker_age = StringUtil.getjuminAge(getWorker_jumin());
		}
	}
	public String getWorker_sex() {
		if ( worker_sex != null && !"".equals(worker_sex) ) {
			return worker_sex;
		} else {
			return StringUtil.getjuminSex(getWorker_jumin());
		}
	}
	public void setWorker_sex(String worker_sex) {
		if ( worker_sex != null && !"".equals(worker_sex) ) {
			this.worker_sex = worker_sex;
		} else {
			this.worker_sex = StringUtil.getjuminSex(getWorker_jumin());
		}
	}


	public String getWorker_OSH_kr() {
		if ( worker_OSH_kr != null && !"".equals(worker_OSH_kr) ) {
			return worker_OSH_kr;
		} else {
			return "1".equals(worker_OSH_yn)?"이수":"미이수";
		}
	}
	public void setWorker_OSH_kr(String worker_OSH_kr) {
		if ( worker_OSH_kr != null && !"".equals(worker_OSH_kr) ) {
			this.worker_OSH_kr = worker_OSH_kr;
		} else {
			this.worker_OSH_kr = "1".equals(getWorker_OSH_yn())?"이수":"미이수";
		}
	}
	public String getWorker_jumin_scan_kr() {
		if ( worker_jumin_scan_kr != null && !"".equals(worker_jumin_scan_kr) ) {
			return worker_jumin_scan_kr;
		} else {
			return "1".equals(worker_jumin_scan_kr)?"스캔":"미스캔";
		}
	}
	public void setWorker_jumin_scan_kr(String worker_jumin_scan_kr) {
		if ( worker_jumin_scan_kr != null && !"".equals(worker_jumin_scan_kr) ) {
			this.worker_jumin_scan_kr = worker_jumin_scan_kr;
		} else {
			this.worker_jumin_scan_kr = "1".equals(getWorker_jumin_scan_yn())?"스캔":"미스캔";
		}
	}
	public String getWorker_OSH_scan_kr() {
		if ( worker_OSH_scan_kr != null && !"".equals(worker_OSH_scan_kr) ) {
			return worker_OSH_scan_kr;
		} else {
			return "1".equals(worker_OSH_scan_kr)?"스캔":"미스캔";
		}
	}
	public void setWorker_OSH_scan_kr(String worker_OSH_scan_kr) {
		if ( worker_OSH_scan_kr != null && !"".equals(worker_OSH_scan_kr) ) {
			this.worker_OSH_scan_kr = worker_OSH_scan_kr;
		} else {
			this.worker_OSH_scan_kr = "1".equals(getWorker_OSH_scan_yn())?"스캔":"미스캔";
		}
	}


	public String getWorker_app_status_kr() {
		if ( worker_app_status_kr != null && !"".equals(worker_app_status_kr) ) {
			return worker_app_status_kr;
		} else {
			int idx = StringUtil.isNullToInt(worker_app_status);

			if ( !"".equals(worker_app_status) && idx >= 0 && idx <= 5 ) {
				return arr_worker_app_status[idx];
			} else {
				return getWorker_app_status();
			}
		}
	}
	public void setWorker_app_status_kr(String worker_app_status_kr) {
		if ( worker_app_status_kr != null && !"".equals(worker_app_status_kr) ) {
			this.worker_app_status_kr = worker_app_status_kr;
		} else {
			int idx = StringUtil.isNullToInt(worker_app_status);

			if ( !"".equals(worker_app_status) && idx >= 0 && idx <= 5 ) {
				this.worker_app_status_kr = arr_worker_app_status[idx];
			} else {
				this.worker_app_status_kr = getWorker_app_status();
			}
		}
	}
	public String getWorker_reserve_push_status_kr() {
		if ( worker_reserve_push_status_kr != null && !"".equals(worker_reserve_push_status_kr) ) {
			return worker_reserve_push_status_kr;
		} else {
			int idx = StringUtil.isNullToInt(worker_reserve_push_status);

			if ( !"".equals(worker_reserve_push_status) && idx >= 0 && idx <= 2 ) {
				return arr_worker_reserve_push_status[idx];
			} else {
				return getWorker_reserve_push_status();
			}
		}
	}
	public void setWorker_reserve_push_status_kr(String worker_reserve_push_status_kr) {
		if ( worker_reserve_push_status_kr != null && !"".equals(worker_reserve_push_status_kr) ) {
			this.worker_reserve_push_status_kr = worker_reserve_push_status_kr;
		} else {
			int idx = StringUtil.isNullToInt(worker_reserve_push_status);

			if ( !"".equals(worker_reserve_push_status) && idx >= 0 && idx <= 2 ) {
				this.worker_reserve_push_status_kr = arr_worker_reserve_push_status[idx];
			} else {
				this.worker_reserve_push_status_kr = getWorker_reserve_push_status();
			}
		}
	}
	public String getWorker_app_use_status_kr() {
		if ( worker_app_use_status_kr != null && !"".equals(worker_app_use_status_kr) ) {
			return worker_app_use_status_kr;
		} else {
			int idx = StringUtil.isNullToInt(worker_app_use_status);

			if ( !"".equals(worker_app_use_status) && idx >= 0 && idx <= 3 ) {
				return arr_worker_app_use_status[idx];
			} else if ( "-1".equals(worker_app_use_status) ) {
				return "미사용";
			} else {
				return getWorker_app_use_status();
			}
		}
	}
	public void setWorker_app_use_status_kr(String worker_app_use_status) {
		if ( worker_app_use_status != null && !"".equals(worker_app_use_status) ) {
			this.worker_app_use_status_kr = worker_app_use_status;
		} else {
			int idx = StringUtil.isNullToInt(worker_app_use_status);

			if ( !"".equals(worker_app_use_status) && idx >= 0 && idx <= 3 ) {
				this.worker_app_use_status_kr = arr_worker_app_use_status[idx];
			} else if ( "-1".equals(worker_app_use_status) ) {
				this.worker_app_use_status_kr = "미사용";
			}  else {
				this.worker_app_use_status_kr = getWorker_app_use_status();
			}
		}
	}
	public String getWorker_push_yn_kr() {
		if ( worker_push_yn_kr != null && !"".equals(worker_push_yn_kr) ) {
			return worker_push_yn_kr;
		} else {
			int idx = StringUtil.isNullToInt(worker_push_yn);

			if ( !"".equals(worker_push_yn) && idx >= 0 && idx <= 2 ) {
				return arr_worker_push_yn[idx];
			} else {
				return getWorker_push_yn();
			}
		}
	}
	public void setWorker_push_yn_kr(String Worker_push_yn_kr) {
		if ( Worker_push_yn_kr != null && !"".equals(Worker_push_yn_kr) ) {
			this.worker_push_yn_kr = Worker_push_yn_kr;
		} else {
			int idx = StringUtil.isNullToInt(worker_push_yn);

			if ( !"".equals(worker_push_yn) && idx >= 0 && idx <= 2 ) {
				this.worker_push_yn_kr = arr_worker_push_yn[idx];
			} else {
				this.worker_push_yn_kr = getWorker_push_yn();
			}
		}
	}


	public String getSrh_worker_app_status() {
		return srh_worker_app_status;
	}
	public void setSrh_worker_app_status(String srh_worker_app_status) {
		this.srh_worker_app_status = srh_worker_app_status;
	}

	public String[] getSel_worker_seq() {
		return sel_worker_seq;
	}
	public void setSel_worker_seq(String[] sel_worker_seq) {
		this.sel_worker_seq = sel_worker_seq;
	}


	public List<WorkerDTO> getList() {
		return list;
	}
	public void setList(List<WorkerDTO> list) {
		this.list = list;
	}
	public String getWorker_addr_replace() {
		return worker_addr_replace;
	}
	public void setWorker_addr_replace(String worker_addr_replace) {
		this.worker_addr_replace = worker_addr_replace;
	}
	public String getWorker_ilgaja_addr() {
		return worker_ilgaja_addr;
	}
	public String getWorker_ilgaja_latlng() {
		return worker_ilgaja_latlng;
	}
	public void setWorker_ilgaja_addr(String worker_ilgaja_addr) {
		this.worker_ilgaja_addr = worker_ilgaja_addr;
	}
	public void setWorker_ilgaja_latlng(String worker_ilgaja_latlng) {
		this.worker_ilgaja_latlng = worker_ilgaja_latlng;
	}
	public String getOs_type() {
		return os_type;
	}
	public String getPush_token() {
		return push_token;
	}
	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}
	public void setPush_token(String push_token) {
		this.push_token = push_token;
	}
	public String getCompany_account() {
		return company_account;
	}
	public String getCompany_license_num() {
		return company_license_num;
	}
	public void setCompany_account(String company_account) {
		this.company_account = company_account;
	}
	public void setCompany_license_num(String company_license_num) {
		this.company_license_num = company_license_num;
	}
	public String getCompany_phone() {
		return company_phone;
	}
	public void setCompany_phone(String company_phone) {
		this.company_phone = company_phone;
	}
	public String getIlbo_seq() {
		return ilbo_seq;
	}
	public void setIlbo_seq(String ilbo_seq) {
		this.ilbo_seq = ilbo_seq;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getWorker_job_scan_yn() {
		return worker_job_scan_yn;
	}
	public void setWorker_job_scan_yn(String worker_job_scan_yn) {
		this.worker_job_scan_yn = worker_job_scan_yn;
	}
	public String getPhone_ct() {
		return phone_ct;
	}
	public void setPhone_ct(String phone_ct) {
		this.phone_ct = phone_ct;
	}
	public String getWorker_rating() {
		return worker_rating;
	}
	public void setWorker_rating(String worker_rating) {
		this.worker_rating = worker_rating;
	}
	public String getEmployer_seq() {
		return employer_seq;
	}
	public void setEmployer_seq(String employer_seq) {
		this.employer_seq = employer_seq;
	}
	public String getApp_last_date() {
		return app_last_date;
	}
	public void setApp_last_date(String app_last_date) {
		this.app_last_date = app_last_date;
	}
	public int getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(int auth_type) {
		this.auth_type = auth_type;
	}
	public String getWorker_myphoto_scan_yn() {
		return worker_myphoto_scan_yn;
	}
	public void setWorker_myphoto_scan_yn(String worker_myphoto_scan_yn) {
		this.worker_myphoto_scan_yn = worker_myphoto_scan_yn;
	}
	public String getTerm_date() {
		return term_date;
	}
	public void setTerm_date(String term_date) {
		this.term_date = term_date;
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
	public String getWait_approval_count() {
		return wait_approval_count;
	}
	public void setWait_approval_count(String wait_approval_count) {
		this.wait_approval_count = wait_approval_count;
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
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}

	public int getDetailSearch() {
		return detailSearch;
	}
	public void setDetailSearch(int detailSearch) {
		this.detailSearch = detailSearch;
	}
	public String[] getSearch_worker_rating() {
		return search_worker_rating;
	}
	public void setSearch_worker_rating(String[] search_worker_rating) {
		this.search_worker_rating = search_worker_rating;
	}
	public String[] getSearch_worker_age() {
		return search_worker_age;
	}
	public void setSearch_worker_age(String[] search_worker_age) {
		this.search_worker_age = search_worker_age;
	}
	public String[] getSearch_job_kind() {
		return search_job_kind;
	}
	public void setSearch_job_kind(String[] search_job_kind) {
		this.search_job_kind = search_job_kind;
	}
	public String getSearch_worker_today() {
		return search_worker_today;
	}
	public void setSearch_worker_today(String search_worker_today) {
		this.search_worker_today = search_worker_today;
	}
	public String getSearch_worker_tommorrow() {
		return search_worker_tommorrow;
	}
	public void setSearch_worker_tommorrow(String search_worker_tommorrow) {
		this.search_worker_tommorrow = search_worker_tommorrow;
	}
	public String getSearch_worker_etc() {
		return search_worker_etc;
	}
	public void setSearch_worker_etc(String search_worker_etc) {
		this.search_worker_etc = search_worker_etc;
	}
	public String[] getSearch_worker_car() {
		return search_worker_car;
	}
	public void setSearch_worker_car(String[] search_worker_car) {
		this.search_worker_car = search_worker_car;
	}
	public String getSearch_worker_osh() {
		return search_worker_osh;
	}
	public void setSearch_worker_osh(String search_worker_osh) {
		this.search_worker_osh = search_worker_osh;
	}
	public String getSearch_work_seq() {
		return search_work_seq;
	}
	public void setSearch_work_seq(String search_work_seq) {
		this.search_work_seq = search_work_seq;
	}
	public String getDone_join_query() {
		return done_join_query;
	}
	public void setDone_join_query(String done_join_query) {
		this.done_join_query = done_join_query;
	}
	public String getAdmin_push_yn() {
		return admin_push_yn;
	}
	public void setAdmin_push_yn(String admin_push_yn) {
		this.admin_push_yn = admin_push_yn;
	}
	public String getWorker_education_yn() {
		return worker_education_yn;
	}
	public void setWorker_education_yn(String worker_education_yn) {
		this.worker_education_yn = worker_education_yn;
	}
	public String getJoin_route() {
		return join_route;
	}
	public void setJoin_route(String join_route) {
		this.join_route = join_route;
	}



}
