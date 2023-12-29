package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.TaxbillDTO;

public interface TaxbillMapper {

	public List<TaxbillDTO> selectTaxbillList(TaxbillDTO taxbillDTO);
	
	public int selectTaxbillListCnt(TaxbillDTO taxbillDTO);
	
	public int updateTaxbill(TaxbillDTO taxbillDTO);
}
