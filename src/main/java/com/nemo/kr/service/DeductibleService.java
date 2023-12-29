package com.nemo.kr.service;

import com.nemo.kr.dto.IlboDTO;

public interface DeductibleService {

	public boolean isDeletePermisson(IlboDTO ilboDTO) throws Exception;
	
	public String joinInsurance(IlboDTO ilboDTO) throws Exception;
	
	public void cancelInsurance(IlboDTO ilboDTO, String regAdmin) throws Exception;
	
	public String updateInsurance(IlboDTO ilboDTO) throws Exception;
	
}
