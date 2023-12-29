package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.CareerLogDTO;
import com.nemo.kr.mapper.ilgaja.write.CareerLogMapper;
import com.nemo.kr.service.CareerLogService;

@Service
public class CareerLogServiceImpl implements CareerLogService {
	@Autowired
	CareerLogMapper careerLogMapper;

	@Override
	public void insertCareerLog(CareerLogDTO careerLogDTO) {
		// TODO Auto-generated method stub
		careerLogMapper.insertCareerLog(careerLogDTO);
	}
}
