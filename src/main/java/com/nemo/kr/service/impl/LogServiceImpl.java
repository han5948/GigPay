package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.PriceLogDTO;
import com.nemo.kr.mapper.ilgaja.write.LogMapper;
import com.nemo.kr.service.LogService;


/**
  * @FileName : LogServiceImpl.java
  * @Project : ilgaja
  * @Date : 2020. 10. 30. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 각종 로그 구현
  */
@Service
public class LogServiceImpl implements LogService {
	@Autowired 
	LogMapper logMapper;

	@Override
	public void insertCodeLog(CodeLogDTO logDTO) {
		// TODO Auto-generated method stub
		logMapper.insertCodeLog(logDTO);
	}

	@Override
	public void insertPriceLog(PriceLogDTO priceLogDTO) {
		// TODO Auto-generated method stub
		logMapper.insertPriceLog(priceLogDTO);
	}

	@Override
	public void insertEmployerCodeLog(CodeLogDTO logDTO) {
		// TODO Auto-generated method stub
		logMapper.insertEmployerCodeLog(logDTO);
	}

	@Override
	public List<CodeLogDTO> selectEmployerCodeLogList(CodeLogDTO logDTO) {
		// TODO Auto-generated method stub
		return logMapper.selectEmployerCodeLogList(logDTO);
	}

	@Override
	public List<PriceLogDTO> getPriceLogList(PriceLogDTO priceLogDTO) {
		// TODO Auto-generated method stub
		return logMapper.selectPriceLogList(priceLogDTO);
	}
}
