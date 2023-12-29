package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.EvaluateDTO;
import com.nemo.kr.mapper.ilgaja.write.EvaluateMapper;
import com.nemo.kr.service.EvaluateService;


/**
 * 평가하기 관리 ServiceImpl
 * @date 2018. 7. 5.
 * @desc  
 *
 */
@Service
public class EvaluateServiceImpl implements EvaluateService {

	@Autowired
	EvaluateMapper evaluateMapper;


	@Override
	public int selectListCnt(EvaluateDTO paramDTO) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectListCnt(paramDTO);
	}

	@Override
	public List<EvaluateDTO> selectList(EvaluateDTO paramDTO) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectList(paramDTO);
	}

	@Override
	public void insertInfo(EvaluateDTO paramDTO) {
		// TODO Auto-generated method stub
		evaluateMapper.insertInfo(paramDTO);
	}

	@Override
	public EvaluateDTO selectInfo(EvaluateDTO paramDTO) {
		// TODO Auto-generated method stub
		return evaluateMapper.selectInfo(paramDTO);
	}

	@Override
	public void updateInfo(EvaluateDTO paramDTO) {
		// TODO Auto-generated method stub
		evaluateMapper.updateInfo(paramDTO);
	}

	@Override
	public void deleteInfo(EvaluateDTO paramDTO) {
		// TODO Auto-generated method stub
		evaluateMapper.deleteInfo(paramDTO);
	}



}