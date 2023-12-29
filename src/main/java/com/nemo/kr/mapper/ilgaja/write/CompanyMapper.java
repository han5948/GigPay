package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.CompanyDTO;


/**
 * 일가자 회사관리 mapper
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-13
 */
public interface CompanyMapper {
	public int getCompanyTotalCnt(CompanyDTO companyDTO);

	public List<CompanyDTO> getCompanyList(CompanyDTO companyDTO);

	public void setCompanyCell(CompanyDTO companyDTO);                            // Insert

	public void setCompanyInfo(CompanyDTO companyDTO);                            // Update

	public void removeCompanyInfo(CompanyDTO companyDTO);                         // delete

	public CompanyDTO getCompanyInfo(CompanyDTO paramDTO);
}
