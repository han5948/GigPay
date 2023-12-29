package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.InquiryDTO;

public interface InquiryService {
	public int selectInquiryListCnt(InquiryDTO inquiryDTO);
	
	public List<InquiryDTO> selectInquiryList(InquiryDTO inquiryDTO);
	
	public void updateInquiry(InquiryDTO inquiryDTO);
	
	public void deleteInquiry(InquiryDTO inquiryDTO);
}	
