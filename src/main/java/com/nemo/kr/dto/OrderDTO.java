package com.nemo.kr.dto;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;



/**
 * 오더 DTO
 * @author  : nemojjang
 * @date    : 2019. 7. 4.
 * @desc    : 
 *
 */
public class OrderDTO extends GridDefaultDTO {
	
	// nemojjang 2019. 7. 4.
	private static final long serialVersionUID = 914061109831884706L;
	
	@Expose
	private String order_seq;		//구인처현장순번
	
	private String parent_seq;
	@Expose
	private String order_date;
	@Expose
	private String order_state;		//orderList에서사용0:기본1:구인처오더접수2:처리중3:처리완료4:삭제
	@Expose
	private String company_seq;		//회사순번 매니져
	@Expose
	private String company_name;
	@Expose
	private String employer_seq;		//구인처순번
	@Expose
	private String employer_name;		//구인처이름
	@Expose
	private String employer_num;		//구인처 사업자번호(주민번호)
	@Expose
	private String work_seq;		//현장순번
	@Expose
	private String work_name;		// 현장명
	@Expose
	private String owner_id;			// 소유자
	@Expose
	private String breakfast_yn;		//조식유무
	@Expose
	private String work_arrival;		//현장도착시간
	@Expose
	private String work_finish;		//현장마감시간
	@Expose
	private String work_breaktime;
	@Expose
	private String work_addr;		//현장주소
	
	@Expose
	private String work_sido;
	@Expose
	private String work_sigugun;
	@Expose
	private String work_dongmyun;
	@Expose
	private String work_rest;
	
	@Expose
	private String work_latlng;
	@Expose
	private String work_day;		//작업일수
	@Expose
	private String manager_seq;		//현장오더메니져고유키
	@Expose
	private String manager_name;		//현장오더메니져이름
	@Expose
	private String manager_phone;		//현장오더메니져폰번호
	
	private String[] sel_order_seq;                            // 선택된 현장 순번
	
	private String search_employer_seq;
	
	@Expose
	private String work_manager_name;
	@Expose
	private String work_manager_phone;
	@Expose
	private String pay_type;
	@Expose
	private String work_age;
	@Expose
	private String work_blood_pressure;
	@Expose
	private String is_tax;
	@Expose
	private String is_worker_info;
	
	private String tax_name;
	private String tax_phone;
	
	@Expose
	private String search_flag;
	
	private String manager_type;
	private String access_view;
	private String total_work;
	private String order_request;
	private String order_json_seq;
	private String admin_id;
	private String order_company_seq;
	private String order_company_name;
	private String order_owner_id;
	
	private String labor_contract_seq;
	private String labor_contract_name;
	private String receive_contract_seq;
	private String receive_contract_name;
	private String sign_manager_seq;
	@Expose
	private String employer_search_yn;
	
	private String employer_income_code;
	private String employer_income_name;
	private String employer_pay_code;
	private String employer_pay_name;
	
	@Expose
	private String employer_name_exists;
	@Expose
	private String manager_use_status;
	@Expose
	private String employer_detail;
	@Expose
	private String parking_option;

	@Expose
	private String manager_reg_date;

	@Expose
	private String work_latlng_count;

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
	public String getWork_breaktime() {
		return work_breaktime;
	}
	public void setWork_breaktime(String work_breaktime) {
		this.work_breaktime = work_breaktime;
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
	public String getOrder_owner_id() {
		return order_owner_id;
	}
	public void setOrder_owner_id(String order_owner_id) {
		this.order_owner_id = order_owner_id;
	}
	public String getOrder_company_name() {
		return order_company_name;
	}
	public void setOrder_company_name(String order_company_name) {
		this.order_company_name = order_company_name;
	}
	public String getOrder_company_seq() {
		return order_company_seq;
	}
	public void setOrder_company_seq(String order_company_seq) {
		this.order_company_seq = order_company_seq;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
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
	public String getOrder_json_seq() {
		return order_json_seq;
	}
	public void setOrder_json_seq(String order_json_seq) {
		this.order_json_seq = order_json_seq;
	}
	@Expose
	private String callcenter_memo;
	
	public String getCallcenter_memo() {
		return callcenter_memo;
	}
	public void setCallcenter_memo(String callcenter_memo) {
		this.callcenter_memo = callcenter_memo;
	}
	public String getOrder_request() {
		return order_request;
	}
	public void setOrder_request(String order_request) {
		this.order_request = order_request;
	}
	private List<OrderDTO> list;
	public String getOrder_seq() {
		return order_seq;
	}
	public void setOrder_seq(String order_seq) {
		this.order_seq = order_seq;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
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
	public String getEmployer_name() {
		return employer_name;
	}
	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
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
		 this.work_arrival = work_arrival.replace(":", "");
	}
	public String getWork_finish() {
		return work_finish;
	}
	public void setWork_finish(String work_finish) {
		this.work_finish = work_finish.replace(":", "");
	}
	public String getWork_addr() {
		return StringUtil.isNullToString(work_addr);
	}
	public void setWork_addr(String work_addr) {
		this.work_addr = StringUtil.isNullToString(work_addr);
	}
	public String getWork_day() {
		return work_day;
	}
	public void setWork_day(String work_day) {
		this.work_day = work_day;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
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
		this.manager_phone = manager_phone.replaceAll("-", "");
	}
	public String[] getSel_order_seq() {
		return sel_order_seq;
	}
	public void setSel_order_seq(String[] sel_order_seq) {
		this.sel_order_seq = sel_order_seq;
	}
	public List<OrderDTO> getList() {
		return list;
	}
	public void setList(List<OrderDTO> list) {
		this.list = list;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getSearch_employer_seq() {
		return search_employer_seq;
	}
	public void setSearch_employer_seq(String search_employer_seq) {
		this.search_employer_seq = search_employer_seq;
	}
	
	public String getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(String parent_seq) {
		this.parent_seq = parent_seq;
	}
	public String getWork_manager_name() {
		return work_manager_name;
	}
	public void setWork_manager_name(String work_manager_name) {
		this.work_manager_name = work_manager_name;
	}
	public String getWork_manager_phone() {
		return work_manager_phone;
		
	}
	public void setWork_manager_phone(String work_manager_phone) {
		this.work_manager_phone = work_manager_phone.replaceAll("-", "");
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
	public String getIs_tax() {
		return is_tax;
	}
	public void setIs_tax(String is_tax) {
		this.is_tax = is_tax;
	}
	public String getIs_worker_info() {
		return is_worker_info;
	}
	public void setIs_worker_info(String is_worker_info) {
		this.is_worker_info = is_worker_info;
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
	public String getSearch_flag() {
		return search_flag;
	}
	public void setSearch_flag(String search_flag) {
		this.search_flag = search_flag;
	}
	public String getWork_latlng() {
		return StringUtil.isNullToString(work_latlng);
	}
	public void setWork_latlng(String work_latlng) {
		this.work_latlng = StringUtil.isNullToString(work_latlng);
	}
	
	
	public static void main(String[] args) {
		// 2. Java object to JSON string
		
		OrderDTO resultDTO = new OrderDTO();
		resultDTO.setOrder_seq("1");

				
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonInString = gson.toJson(resultDTO);
	}
	
	public String getAccess_view() {
		return access_view;
	}
	public void setAccess_view(String access_view) {
		this.access_view = access_view;
	}
	public String getTotal_work() {
		return total_work;
	}
	public void setTotal_work(String total_work) {
		this.total_work = total_work;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getBreakfast_yn() {
		return breakfast_yn;
	}
	public void setBreakfast_yn(String breakfast_yn) {
		this.breakfast_yn = breakfast_yn;
	}
	public String getManager_type() {
		return manager_type;
	}
	public void setManager_type(String manager_type) {
		this.manager_type = manager_type;
	}
	public String getEmployer_search_yn() {
		return employer_search_yn;
	}
	public void setEmployer_search_yn(String employer_search_yn) {
		this.employer_search_yn = employer_search_yn;
	}
	public String getEmployer_name_exists() {
		return employer_name_exists;
	}
	public void setEmployer_name_exists(String employer_name_exists) {
		this.employer_name_exists = employer_name_exists;
	}
	public String getManager_use_status() {
		return manager_use_status;
	}
	public void setManager_use_status(String manager_use_status) {
		this.manager_use_status = manager_use_status;
	}
	public String getEmployer_detail() {
		return employer_detail;
	}
	public void setEmployer_detail(String employer_detail) {
		this.employer_detail = employer_detail;
	}
	public String getParking_option() {
		return parking_option;
	}
	public void setParking_option(String parking_option) {
		this.parking_option = parking_option;
	}

	public String getManager_reg_date(){
		return this.manager_reg_date;
	}
	public void setManager_reg_date(String manager_reg_date) {
		this.manager_reg_date = manager_reg_date;
	}
	public String getEmployer_num(){
		return this.employer_num;
	}
	public void setEmployer_num(String employer_num) {
		this.employer_num = employer_num;
	}

	public String getWork_latlng_count() {
		return work_latlng_count;
	}

	public void setWork_latlng_count(String work_latlng_count) {
		this.work_latlng_count = work_latlng_count;
	}
}
