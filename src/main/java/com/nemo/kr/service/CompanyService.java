package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.CompanyDTO;


/**
 * 일가자 회사관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-13
 */
public interface CompanyService {
	public int getCompanyTotalCnt(CompanyDTO companyDTO);

	public List<CompanyDTO> getCompanyList(CompanyDTO companyDTO);

	public void setCompanyCell(CompanyDTO companyDTO);                            // Insert

	public void setCompanyInfo(CompanyDTO companyDTO);                            // Update

	public void removeCompanyInfo(CompanyDTO companyDTO);                         // Delete

	public CompanyDTO getCompanyInfo(CompanyDTO paramDTO);

	public String getAuthNum(CompanyDTO companyDTO) throws Exception;
}
