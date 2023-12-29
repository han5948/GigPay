package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.InsuranceDTO;
import com.nemo.kr.mapper.ilgaja.write.InsuranceLogMapper;
import com.nemo.kr.service.InsuranceLogService;

@Service
public class InsuranceLogServiceImpl implements InsuranceLogService {
	@Autowired
	InsuranceLogMapper insuranceLogMapper;

	@Override
	public void insertInsuranceLog(InsuranceDTO insuranceDTO) {
		// TODO Auto-generated method stub
		insuranceLogMapper.insertInsuranceLog(insuranceDTO);
	}
}
