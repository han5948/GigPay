package com.nemo.kr.dto;

import java.util.List;

import com.nemo.kr.dto.GridDefaultDTO;



/**
 * 현장매니저 
 * @author nemojjang
 *
 */
public class ManagerDTO extends GridDefaultDTO {
	
	private String manager_seq;				//메니져 순번     
	private String manager_phone;   
	private String manager_pass;  
	private String manager_new_pass;
	private String manager_name;    
	private String manager_type;    
	private String manager_memo;
	private String company_seq;
	private String company_tel;
	private String employer_seq;    
	private String manager_push_yn; 
	private String push_token;      
	private String os_type;         
	private String auth_num;        
	private String use_yn;          
	private String reg_date;        
	private String reg_admin;       
	private String mod_date;        
	private String mod_admin;
	private String app_version;
	private String visit_date;
	private String visit_count;
	private String visit_type;
	private String parent_company_seq;		//처음 등록한 회사 seq
	
	private String[] sel_manager_seq;                           // 선택된 관리자 순번
	private String company_name;
	private String employer_name;
	private String employer_num;		//사업자 등록 번호
	private String employer_pass;
	
	private String manager_etc;
	
	private String partner_tel;
	
	private String work_seq;
	private String wm_seq;
	private String em_seq;
	private String counselor_rate;
	
	private String e_count;
	private String w_count;
	private String manager_use_status;
	private String auth_level;
	
	private String[] sel_manager_use_status;
	private String interval;
	
	private String join_route;			//가입경로
	private String order_seq;			// manager_name 처리

	public String getAuth_level() {
		return auth_level;
	}
	public void setAuth_level(String auth_level) {
		this.auth_level = auth_level;
	}
	public String getManager_use_status() {
		return manager_use_status;
	}
	public void setManager_use_status(String manager_use_status) {
		this.manager_use_status = manager_use_status;
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
		if(manager_phone != null) {
			this.manager_phone = manager_phone.replaceAll("-", "");
		}else {
			this.manager_phone = manager_phone ;
		}
	}
	public String getManager_pass() {
		return manager_pass;
	}
	public void setManager_pass(String manager_pass) {
		this.manager_pass = manager_pass;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getManager_type() {
		return manager_type;
	}
	public void setManager_type(String manager_type) {
		this.manager_type = manager_type;
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
	public String getOs_type() {
		return os_type;
	}
	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}
	public String getAuth_num() {
		return auth_num;
	}
	public void setAuth_num(String auth_num) {
		this.auth_num = auth_num;
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
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	public String getMod_admin() {
		return mod_admin;
	}
	public void setMod_admin(String mod_admin) {
		this.mod_admin = mod_admin;
	}
	
	public String getManager_memo() {
		return manager_memo;
	}
	public void setManager_memo(String manager_memo) {
		this.manager_memo = manager_memo;
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
	public void setSel_manager_seq(String[] sel_manager_seq) {
		this.sel_manager_seq = sel_manager_seq;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String[] getSel_manager_seq() {
		return sel_manager_seq;
	}
	public String getCompany_tel() {
		return company_tel;
	}
	public void setCompany_tel(String company_tel) {
		this.company_tel = company_tel;
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
	public String getPartner_tel() {
		return partner_tel;
	}
	public void setPartner_tel(String partner_tel) {
		this.partner_tel = partner_tel;
	}
	public String getParent_company_seq() {
		return parent_company_seq;
	}
	public void setParent_company_seq(String parent_company_seq) {
		this.parent_company_seq = parent_company_seq;
	}
	public String getEmployer_num() {
		return employer_num;
	}
	public void setEmployer_num(String employer_num) {
		this.employer_num = employer_num;
	}
	public String getEmployer_pass() {
		return employer_pass;
	}
	public void setEmployer_pass(String employer_pass) {
		this.employer_pass = employer_pass;
	}
	public String getManager_new_pass() {
		return manager_new_pass;
	}
	public void setManager_new_pass(String manager_new_pass) {
		this.manager_new_pass = manager_new_pass;
	}
	public String getManager_etc() {
		return manager_etc;
	}
	public void setManager_etc(String manager_etc) {
		this.manager_etc = manager_etc;
	}
	public String getWork_seq() {
		return work_seq;
	}
	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}
	public String getWm_seq() {
		return wm_seq;
	}
	public void setWm_seq(String wm_seq) {
		this.wm_seq = wm_seq;
	}
	public String getEm_seq() {
		return em_seq;
	}
	public void setEm_seq(String em_seq) {
		this.em_seq = em_seq;
	}
	public String getCounselor_rate() {
		return counselor_rate;
	}
	public void setCounselor_rate(String counselor_rate) {
		this.counselor_rate = counselor_rate;
	}
	public String getE_count() {
		return e_count;
	}
	public void setE_count(String e_count) {
		this.e_count = e_count;
	}
	public String getW_count() {
		return w_count;
	}
	public void setW_count(String w_count) {
		this.w_count = w_count;
	}
	public String[] getSel_manager_use_status() {
		return sel_manager_use_status;
	}
	public void setSel_manager_use_status(String[] sel_manager_use_status) {
		this.sel_manager_use_status = sel_manager_use_status;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getJoin_route() {
		return join_route;
	}
	public void setJoin_route(String join_route) {
		this.join_route = join_route;
	}

	public String getOrder_seq() {
		return order_seq;
	}

	public void setOrder_seq(String order_seq) {
		this.order_seq = order_seq;
	}
}
