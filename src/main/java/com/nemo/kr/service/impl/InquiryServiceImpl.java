package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.InquiryDTO;
import com.nemo.kr.mapper.ilgaja.write.InquiryMapper;
import com.nemo.kr.service.InquiryService;

@Service
public class InquiryServiceImpl implements InquiryService {
	@Autowired
	InquiryMapper inquiryMapper;

	@Override
	public int selectInquiryListCnt(InquiryDTO inquiryDTO) {
		return inquiryMapper.selectInquiryListCnt(inquiryDTO);
	}
	
	@Override
	public List<InquiryDTO> selectInquiryList(InquiryDTO inquiryDTO) {
		// TODO Auto-generated method stub
		return inquiryMapper.selectInquiryList(inquiryDTO);
	}

	@Override
	public void updateInquiry(InquiryDTO inquiryDTO) {
		inquiryMapper.updateInquiry(inquiryDTO);
	}
	
	@Override
	public void deleteInquiry(InquiryDTO inquiryDTO) {
		// TODO Auto-generated method stub
		inquiryMapper.deleteInquiry(inquiryDTO);
	}

}
