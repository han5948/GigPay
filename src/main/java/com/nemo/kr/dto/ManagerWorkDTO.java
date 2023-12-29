package com.nemo.kr.dto;


/**
 * @author  : nemojjang
 * @date    : 2019. 9. 6.
 * @desc    : 매니저 앱에서 오더 받는 DTO
 *
 */
public class ManagerWorkDTO extends DefaultDTO {
	

	
	// nemojjang 2019. 9. 6.
	private static final long serialVersionUID = -403393129617066635L;
	
	private String work_seq; 					// 현장순번
	private String work_date;					// 작업일
	private String work_days;					// 작업일수
	private String work_arrival;
	private String work_finish;
	private String work_manager;				//현장 담당자
	private String work_manager_phone;
	private String work_memo;
	
	private String[] arr_kind_name;		//직종
	private String[] arr_kind_code;		//직종코드
	private String[] arr_price;				//단가
	private String[] arr_memo;			//설명
	private String[] arr_worker_num;			//인원수
	
	private String pay_type;
	private String work_age;
	private String work_blood_pressure;
	
	
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public String getWork_days() {
		return work_days;
	}
	public void setWork_days(String work_days) {
		this.work_days = work_days;
	}
	public String getWork_arrival() {
		return work_arrival;
	}
	public void setWork_arrival(String work_arrival) {
		this.work_arrival = work_arrival;
	}
	public String getWork_finish() {
		return work_finish;
	}
	public void setWork_finish(String work_finish) {
		this.work_finish = work_finish;
	}
	
	public String[] getArr_kind_name() {
		return arr_kind_name;
	}
	public void setArr_kind_name(String[] arr_kind_name) {
		this.arr_kind_name = arr_kind_name;
	}
	public String[] getArr_kind_code() {
		return arr_kind_code;
	}
	public void setArr_kind_code(String[] arr_kind_code) {
		this.arr_kind_code = arr_kind_code;
	}
	public String[] getArr_price() {
		return arr_price;
	}
	public void setArr_price(String[] arr_price) {
		this.arr_price = arr_price;
	}
	public String[] getArr_memo() {
		return arr_memo;
	}
	public void setArr_memo(String[] arr_memo) {
		this.arr_memo = arr_memo;
	}
	public String[] getArr_worker_num() {
		return arr_worker_num;
	}
	public void setArr_worker_num(String[] arr_worker_num) {
		this.arr_worker_num = arr_worker_num;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
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
	public String getWork_memo() {
		return work_memo;
	}
	public void setWork_memo(String work_memo) {
		this.work_memo = work_memo;
	}
	public String getWork_manager() {
		return work_manager;
	}
	public void setWork_manager(String work_manager) {
		this.work_manager = work_manager;
	}
	public String getWork_manager_phone() {
		return work_manager_phone;
	}
	public void setWork_manager_phone(String work_manager_phone) {
		this.work_manager_phone = work_manager_phone;
	}	
		
	
	
	
 }
