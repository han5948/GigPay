package com.nemo.kr.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountListDTO {


	private String companySeq;                 // 회사 순번
	private String companyName;

	private String adminId;
	private String adminName;
	
	private List<AccountingDTO> resultData = new ArrayList<AccountingDTO>();
	private List<AccountingDTO> resultConsultingData = new ArrayList<AccountingDTO>();
	
	
	public String getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(String companySeq) {
		this.companySeq = companySeq;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<AccountingDTO> getResultData() {
		return resultData;
	}

	public void setResultData(List<AccountingDTO> resultData) {
		this.resultData = resultData;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public List<AccountingDTO> getResultConsultingData() {
		return resultConsultingData;
	}

	public void setResultConsultingData(List<AccountingDTO> resultConsultingData) {
		this.resultConsultingData = resultConsultingData;
	}
}
