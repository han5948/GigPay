package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.mapper.ilgaja.write.CompanyMapper;
import com.nemo.kr.mapper.sms.SmsMapper;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.util.CommonUtil;


/**
 * 일가자 회사관리 Service Impl
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-13
 */
@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired CompanyMapper companyMapper;
	@Autowired SmsMapper smsMapper;

	@Override
	public int getCompanyTotalCnt(CompanyDTO companyDTO) {
		// TODO Auto-generated method stub

		return companyMapper.getCompanyTotalCnt(companyDTO);
	}

	@Override
	public List<CompanyDTO> getCompanyList(CompanyDTO companyDTO) {
		// TODO Auto-generated method stub

		return (List<CompanyDTO>) companyMapper.getCompanyList(companyDTO);
	}

	@Override
	@Transactional
	public void setCompanyCell(CompanyDTO companyDTO) {
		// TODO Auto-generated method stub

		companyMapper.setCompanyCell(companyDTO);
	}

	@Override
	@Transactional
	public void setCompanyInfo(CompanyDTO companyDTO) {
		// TODO Auto-generated method stub

		companyMapper.setCompanyInfo(companyDTO);
	}

	@Override
	@Transactional
	public void removeCompanyInfo(CompanyDTO companyDTO) {
		// TODO Auto-generated method stub

		companyMapper.removeCompanyInfo(companyDTO);
	}

	@Override
	public CompanyDTO getCompanyInfo(CompanyDTO paramDTO) {
		// TODO Auto-generated method stub
		return (CompanyDTO) companyMapper.getCompanyInfo(paramDTO);
	}

	@Override
	public String getAuthNum(CompanyDTO companyDTO) throws Exception {
		// TODO Auto-generated method stub
		String authNum = "";
		CompanyDTO result = companyMapper.getCompanyInfo(companyDTO);

		if ( result == null ) {
			throw new Exception(Const.CODE_USER_0007+"!;!"+ Const.MSG_USER_0007);
		}

		authNum = CommonUtil.getOrderNum(4);

		//sms 보내기=====================================
		String message = "[일가자] 소장 인증을 위한 인증번호 : " + authNum;
		SmsDTO smsDTO = new SmsDTO();
	  	smsDTO.setTr_msg(message);
	  	smsDTO.setTr_etc1("15");	//구직자 회원인증
	  	smsDTO.setTr_phone(companyDTO.getArs_phone());
	  	smsMapper.insertInfo(smsDTO);

	  	return authNum;
	}
}
