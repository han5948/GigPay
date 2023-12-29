package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.TaxRateDTO;

public interface ReplicationTaxRateMapper {

	public List<TaxRateDTO> selectTaxRateList(TaxRateDTO taxRateDTO);
	
	public TaxRateDTO selectTaxRateInfo(TaxRateDTO taxRateDTO);
	
	public TaxRateDTO selectLastTaxRateInfo(TaxRateDTO taxRateDTO);
	
	public void insertTaxRate(TaxRateDTO taxRateDTO);
	
	public void updateTaxRate(TaxRateDTO taxRateDTO);

	public TaxRateDTO selectApplyTaxRate(IlboDTO ilboDTO);
}
