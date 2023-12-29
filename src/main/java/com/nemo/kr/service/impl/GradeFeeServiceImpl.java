package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.GradeFeeDTO;
import com.nemo.kr.mapper.ilgaja.write.GradeFeeMapper;
import com.nemo.kr.service.GradeFeeService;


@Service
public class GradeFeeServiceImpl implements GradeFeeService{
	
	@Autowired GradeFeeMapper gradeFeeMapper;

	@Override
	public List<GradeFeeDTO> selectGradeFeeList() {
		// TODO Auto-generated method stub
		return gradeFeeMapper.selectGradeFeeList();
	}

	@Override
	public void updateGradeFee(GradeFeeDTO gradeFeeDTO) {
		// TODO Auto-generated method stub
		gradeFeeMapper.updateGradeFee(gradeFeeDTO);
	}
}