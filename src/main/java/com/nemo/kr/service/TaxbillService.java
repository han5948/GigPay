package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.TaxbillDTO;

public interface TaxbillService {
	
	public List<TaxbillDTO> selectTaxbillList(TaxbillDTO taxbillDTO);
	
	public int selectTaxbillListCnt(TaxbillDTO taxbillDTO);
	
	public int updateTaxbill(TaxbillDTO taxbillDTO);
}
