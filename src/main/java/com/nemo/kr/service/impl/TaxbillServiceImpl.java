package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.TaxbillDTO;
import com.nemo.kr.mapper.ilgaja.write.TaxbillMapper;
import com.nemo.kr.service.TaxbillService;


@Service
public class TaxbillServiceImpl implements TaxbillService {

	@Autowired TaxbillMapper taxbillMapper;

	@Override
	public List<TaxbillDTO> selectTaxbillList(TaxbillDTO taxbillDTO) {
		// TODO Auto-generated method stub
		return taxbillMapper.selectTaxbillList(taxbillDTO);
	}

	@Override
	public int selectTaxbillListCnt(TaxbillDTO taxbillDTO) {
		// TODO Auto-generated method stub
		return taxbillMapper.selectTaxbillListCnt(taxbillDTO);
	}

	@Override
	public int updateTaxbill(TaxbillDTO taxbillDTO) {
		// TODO Auto-generated method stub
		return taxbillMapper.updateTaxbill(taxbillDTO);
	}

	
}
