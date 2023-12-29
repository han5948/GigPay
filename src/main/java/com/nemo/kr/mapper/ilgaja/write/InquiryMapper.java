package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.InquiryDTO;




public interface InquiryMapper {
	public int selectInquiryListCnt(InquiryDTO inquiryDTO);
	
	public List<InquiryDTO> selectInquiryList(InquiryDTO inquiryDTO);
	
	public void updateInquiry(InquiryDTO inquiryDTO);
	
	public void deleteInquiry(InquiryDTO inquiryDTO);
}
