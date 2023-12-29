package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.IlboContractLogDTO;
import com.nemo.kr.mapper.ilgaja.write.IlboContractLogMapper;
import com.nemo.kr.service.IlboContractLogService;

@Service
public class IlboContractLogServiceImpl implements IlboContractLogService {
	@Autowired
	IlboContractLogMapper ilboContractLogMapper;

	@Override
	public void insertLog(IlboContractLogDTO ilboContractLogDTO) {
		// TODO Auto-generated method stub
		ilboContractLogMapper.insertLog(ilboContractLogDTO);
	}
}
