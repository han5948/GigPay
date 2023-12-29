package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.TaxRateDTO;

public interface TaxRateService {

	public List<TaxRateDTO> getTaxRateList(TaxRateDTO taxRateDTO);
	
	public TaxRateDTO getTaxRateInfo(TaxRateDTO taxRateDTO);
	
	public TaxRateDTO getLastTaxRateInfo(TaxRateDTO taxRateDTO);
	
	public void addTaxRate(TaxRateDTO taxRateDTO);
	
	public void setTaxRate(TaxRateDTO taxRateDTO);
	
	public TaxRateDTO selectApplyTaxRate(IlboDTO ilboDTO);
}
