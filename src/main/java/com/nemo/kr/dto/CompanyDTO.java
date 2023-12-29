package com.nemo.kr.dto;

import java.util.ArrayList;
import java.util.List;



/**
 * 일가자 회사관리 DTO
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-13
 */
public class CompanyDTO extends GridDefaultDTO {
	private String company_seq;                               // 회사 순번 - PK

	private String company_num;                               // 회사 사업자번호
	private String company_license_num;
	private String company_name;                              // 회사 명
	private String business_name;								// 사업자 상호
	private String company_owner;                             // 회사 대표자
	private String company_business;                          // 회사 업태
	private String company_sector;                            // 회사 업종
	private String company_tel;                               // 회사 전화번호
	private String company_fax;                               // 회사 팩스
	private String company_phone;                             // 회사 폰번호
	private String company_email;                             // 회사 이메일
	private String company_addr;                              // 회사 주소
	private String company_account;                           //계좌정보
	private String company_feature;                           // 회사 특징
	private String company_memo;                              // 회사 메모
	
	private String company_license_scan_yn;
	
	private String company_corp_scan_yn;
	private String company_bank_scan_yn;
	private String company_etc_scan_yn;
	
	private String ars_phone;
	private String minimum_reserves;
	private String system_fee;
	private String running_royalty;
	private String share_yn;
	
	private String company_bank_name;						//api v2에서 추가됨
	private String company_bank_account;
	private String company_bank_account_holder;
	
	private String[] sel_company_seq;                         // 선택된 회사 순번
	
	private List<CompanyDTO> list;
	private String work_fee_rate;				//구인제공 정산수수료
	private String worker_fee_rate; 			//구직제공 정산수수료
	
	private String recommendation;
	private String company_sign_scan_yn;
	
	private String company_status;
	
	private String auto_company_use_yn;
	private String auto_worker_use_yn;
	
	public String getAuto_company_use_yn() {
		return auto_company_use_yn;
	}
	public void setAuto_company_use_yn(String auto_company_use_yn) {
		this.auto_company_use_yn = auto_company_use_yn;
	}
	public String getAuto_worker_use_yn() {
		return auto_worker_use_yn;
	}
	public void setAuto_worker_use_yn(String auto_worker_use_yn) {
		this.auto_worker_use_yn = auto_worker_use_yn;
	}
	public String getCompany_sign_scan_yn() {
		return company_sign_scan_yn;
	}
	public void setCompany_sign_scan_yn(String company_sign_scan_yn) {
		this.company_sign_scan_yn = company_sign_scan_yn;
	}
	public String getWork_fee_rate() {
		return work_fee_rate;
	}
	public void setWork_fee_rate(String work_fee_rate) {
		this.work_fee_rate = work_fee_rate;
	}
	public String getWorker_fee_rate() {
		return worker_fee_rate;
	}
	public void setWorker_fee_rate(String worker_fee_rate) {
		this.worker_fee_rate = worker_fee_rate;
	}
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	
	
	public String getCompany_num() {
		return company_num;
	}
	public void setCompany_num(String company_num) {
		this.company_num = company_num;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_owner() {
		return company_owner;
	}
	public void setCompany_owner(String company_owner) {
		this.company_owner = company_owner;
	}
	public String getCompany_business() {
		return company_business;
	}
	public void setCompany_business(String company_business) {
		this.company_business = company_business;
	}
	public String getCompany_sector() {
		return company_sector;
	}
	public void setCompany_sector(String company_sector) {
		this.company_sector = company_sector;
	}
	public String getCompany_tel() {
		return company_tel;
	}
	public void setCompany_tel(String company_tel) {
		this.company_tel = company_tel;
	}
	public String getCompany_fax() {
		return company_fax;
	}
	public void setCompany_fax(String company_fax) {
		this.company_fax = company_fax;
	}
	public String getCompany_phone() {
		return company_phone;
	}
	public void setCompany_phone(String company_phone) {
		this.company_phone = company_phone;
	}
	public String getCompany_email() {
		return company_email;
	}
	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}
	public String getCompany_addr() {
		return company_addr;
	}
	public void setCompany_addr(String company_addr) {
		this.company_addr = company_addr;
	}
	public String getCompany_feature() {
		return company_feature;
	}
	public void setCompany_feature(String company_feature) {
		this.company_feature = company_feature;
	}
	public String getCompany_memo() {
		return company_memo;
	}
	public void setCompany_memo(String company_memo) {
		this.company_memo = company_memo;
	}
	
	
	public String[] getSel_company_seq() {
		return sel_company_seq;
	}
	public void setSel_company_seq(String[] sel_company_seq) {
		this.sel_company_seq = sel_company_seq;
	}
	
	
	public List<CompanyDTO> getList() {
		return list;
	}
	public void setList(List<CompanyDTO> list) {
		this.list = list;
	}
	public String getCompany_license_num() {
		return company_license_num;
	}
	public String getCompany_account() {
		return company_account;
	}
	public void setCompany_license_num(String company_license_num) {
		this.company_license_num = company_license_num;
	}
	public void setCompany_account(String company_account) {
		this.company_account = company_account;
	}
	public String getShare_yn() {
		return share_yn;
	}
	public void setShare_yn(String share_yn) {
		this.share_yn = share_yn;
	}
	public String getCompany_license_scan_yn() {
		return company_license_scan_yn;
	}
	public void setCompany_license_scan_yn(String company_license_scan_yn) {
		this.company_license_scan_yn = company_license_scan_yn;
	}
	public String getCompany_corp_scan_yn() {
		return company_corp_scan_yn;
	}
	public void setCompany_corp_scan_yn(String company_corp_scan_yn) {
		this.company_corp_scan_yn = company_corp_scan_yn;
	}
	public String getCompany_bank_scan_yn() {
		return company_bank_scan_yn;
	}
	public void setCompany_bank_scan_yn(String company_bank_scan_yn) {
		this.company_bank_scan_yn = company_bank_scan_yn;
	}
	public String getCompany_etc_scan_yn() {
		return company_etc_scan_yn;
	}
	public void setCompany_etc_scan_yn(String company_etc_scan_yn) {
		this.company_etc_scan_yn = company_etc_scan_yn;
	}
	public String getArs_phone() {
		return ars_phone;
	}
	public void setArs_phone(String ars_phone) {
		this.ars_phone = ars_phone;
	}
	public String getCompany_bank_name() {
		return company_bank_name;
	}
	public void setCompany_bank_name(String company_bank_name) {
		this.company_bank_name = company_bank_name;
	}
	public String getCompany_bank_account() {
		return company_bank_account;
	}
	public void setCompany_bank_account(String company_bank_account) {
		this.company_bank_account = company_bank_account;
	}
	public String getCompany_bank_account_holder() {
		return company_bank_account_holder;
	}
	public void setCompany_bank_account_holder(String company_bank_account_holder) {
		this.company_bank_account_holder = company_bank_account_holder;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getSystem_fee() {
		return system_fee;
	}
	public void setSystem_fee(String system_fee) {
		this.system_fee = system_fee;
	}
	public String getRunning_royalty() {
		return running_royalty;
	}
	public void setRunning_royalty(String running_royalty) {
		this.running_royalty = running_royalty;
	}
	public String getCompany_status() {
		return company_status;
	}
	public void setCompany_status(String company_status) {
		this.company_status = company_status;
	}
	public String getMinimum_reserves() {
		return minimum_reserves;
	}
	public void setMinimum_reserves(String minimum_reserves) {
		this.minimum_reserves = minimum_reserves;
	}
}
