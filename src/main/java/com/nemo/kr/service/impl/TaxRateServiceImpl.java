package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.TaxRateDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationTaxRateMapper;
import com.nemo.kr.service.TaxRateService;

@Service
public class TaxRateServiceImpl implements TaxRateService {

	@Autowired ReplicationTaxRateMapper replicationTaxRateMapper;
	
	@Override
	public List<TaxRateDTO> getTaxRateList(TaxRateDTO taxRateDTO) {
		// TODO Auto-generated method stub
		return replicationTaxRateMapper.selectTaxRateList(taxRateDTO);
	}

	@Override
	public TaxRateDTO getTaxRateInfo(TaxRateDTO taxRateDTO) {
		// TODO Auto-generated method stub
		return replicationTaxRateMapper.selectTaxRateInfo(taxRateDTO);
	}
	
	@Override
	public TaxRateDTO getLastTaxRateInfo(TaxRateDTO taxRateDTO) {
		// TODO Auto-generated method stub
		return replicationTaxRateMapper.selectLastTaxRateInfo(taxRateDTO);
	}

	@Override
	public void addTaxRate(TaxRateDTO taxRateDTO) {
		// TODO Auto-generated method stub
		replicationTaxRateMapper.insertTaxRate(taxRateDTO);
	}

	@Override
	public void setTaxRate(TaxRateDTO taxRateDTO) {
		// TODO Auto-generated method stub
		replicationTaxRateMapper.updateTaxRate(taxRateDTO);
	}

	@Override
	public TaxRateDTO selectApplyTaxRate(IlboDTO ilboDTO) {
		// TODO Auto-generated method stub
		return replicationTaxRateMapper.selectApplyTaxRate(ilboDTO);
	}

}
