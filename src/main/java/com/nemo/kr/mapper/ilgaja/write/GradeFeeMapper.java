package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.GradeFeeDTO;

public interface GradeFeeMapper {

	public List<GradeFeeDTO> selectGradeFeeList();
	
	public void updateGradeFee(GradeFeeDTO gradeFeeDTO);
}
