package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.GradeFeeDTO;

public interface GradeFeeService {

	public List<GradeFeeDTO> selectGradeFeeList();
	
	public void updateGradeFee(GradeFeeDTO gradeFeeDTO);
}
