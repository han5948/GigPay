package com.nemo.kr.dto;

import java.util.List;



/**
 * 오더Work DTO
 * @author  : nemojjang
 * @date    : 2019. 7. 4.
 * @desc    : 
 *
 */
public class OrderWorkDTO extends GridDefaultDTO {
	
	// nemojjang 2019. 7. 4.
	private static final long serialVersionUID = 6878004921182719367L;
	
	private String work_seq; 			// 현장순번
	private String order_seq;			//
	private String work_kind_name; //
	private String work_kind_code; //
	private String work_price; 		//
	private String work_memo; 		//
	private String worker_count; 	// 작업자수
	private String ilbo_state;
	private String work_days;			//작업일수
	
	private String[] sel_work_seq;                            // 선택된 현장 순번
	private List<OrderWorkDTO> list;
	
	private String[] arr_kind_name;		//직종
	private String[] arr_kind_code;		//직종코드
	
	private String[] arr_job_detail_name;		//직종
	private String[] arr_job_detail_code;		//직종코드
	
	private String[] arr_job_add_name;		//직종
	private String[] arr_job_add_code;		//직종코드
	
	private String work_job_detail_code;
	private String work_job_detail_name;
	private String work_job_add_code;
	private String work_job_add_name;
	
	private String[] arr_price;				//단가
	private String[] arr_memo;			//설명
	private String[] arr_count;			//인원수
	
	private String worker_company_seq;
	private String worker_company_name;
	private String use_code;
	private String use_name;
	
	private String career_name;
	private String career_use_seq;
	
	private String hold_work_price;

	public String getHold_work_price() {
		return hold_work_price;
	}
	public void setHold_work_price(String hold_work_price) {
		this.hold_work_price = hold_work_price;
	}
	public String getCareer_use_seq() {
		return career_use_seq;
	}
	public void setCareer_use_seq(String career_use_seq) {
		this.career_use_seq = career_use_seq;
	}
	public String getCareer_name() {
		return career_name;
	}
	public void setCareer_name(String career_name) {
		this.career_name = career_name;
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
	public String getUse_code() {
		return use_code;
	}
	public void setUse_code(String use_code) {
		this.use_code = use_code;
	}
	public String getUse_name() {
		return use_name;
	}
	public void setUse_name(String use_name) {
		this.use_name = use_name;
	}
	public String getWork_kind_name() {
		return work_kind_name;
	}
	public void setWork_kind_name(String work_kind_name) {
		this.work_kind_name = work_kind_name;
	}
	public String getWork_kind_code() {
		return work_kind_code;
	}
	public void setWork_kind_code(String work_kind_code) {
		this.work_kind_code = work_kind_code;
	}
	public String getWork_price() {
		return work_price;
	}
	public void setWork_price(String work_price) {
		this.work_price = work_price;
	}
	public String getWork_memo() {
		return work_memo;
	}
	public void setWork_memo(String work_memo) {
		this.work_memo = work_memo;
	}
	public String getWorker_count() {
		return worker_count;
	}
	public void setWorker_count(String worker_count) {
		this.worker_count = worker_count;
	}
	public String[] getSel_work_seq() {
		return sel_work_seq;
	}
	public void setSel_work_seq(String[] sel_work_seq) {
		this.sel_work_seq = sel_work_seq;
	}
	public List<OrderWorkDTO> getList() {
		return list;
	}
	public void setList(List<OrderWorkDTO> list) {
		this.list = list;
	}
	public String getOrder_seq() {
		return order_seq;
	}
	public void setOrder_seq(String order_seq) {
		this.order_seq = order_seq;
	}
	public String getIlbo_state() {
		return ilbo_state;
	}
	public void setIlbo_state(String ilbo_state) {
		this.ilbo_state = ilbo_state;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
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
	public String[] getArr_count() {
		return arr_count;
	}
	public void setArr_count(String[] arr_count) {
		this.arr_count = arr_count;
	}
	public String getWork_days() {
		return work_days;
	}
	public void setWork_days(String work_days) {
		this.work_days = work_days;
	}
	public String[] getArr_job_detail_name() {
		return arr_job_detail_name;
	}
	public void setArr_job_detail_name(String[] arr_job_detail_name) {
		this.arr_job_detail_name = arr_job_detail_name;
	}
	public String[] getArr_job_detail_code() {
		return arr_job_detail_code;
	}
	public void setArr_job_detail_code(String[] arr_job_detail_code) {
		this.arr_job_detail_code = arr_job_detail_code;
	}
	public String[] getArr_job_add_name() {
		return arr_job_add_name;
	}
	public void setArr_job_add_name(String[] arr_job_add_name) {
		this.arr_job_add_name = arr_job_add_name;
	}
	public String[] getArr_job_add_code() {
		return arr_job_add_code;
	}
	public void setArr_job_add_code(String[] arr_job_add_code) {
		this.arr_job_add_code = arr_job_add_code;
	}
	public String getWork_job_detail_code() {
		return work_job_detail_code;
	}
	public void setWork_job_detail_code(String work_job_detail_code) {
		this.work_job_detail_code = work_job_detail_code;
	}
	public String getWork_job_detail_name() {
		return work_job_detail_name;
	}
	public void setWork_job_detail_name(String work_job_detail_name) {
		this.work_job_detail_name = work_job_detail_name;
	}
	public String getWork_job_add_code() {
		return work_job_add_code;
	}
	public void setWork_job_add_code(String work_job_add_code) {
		this.work_job_add_code = work_job_add_code;
	}
	public String getWork_job_add_name() {
		return work_job_add_name;
	}
	public void setWork_job_add_name(String work_job_add_name) {
		this.work_job_add_name = work_job_add_name;
	}
	
	
	
 }
