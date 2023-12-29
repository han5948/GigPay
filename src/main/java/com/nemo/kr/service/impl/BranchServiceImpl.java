package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationBranchMapper;
import com.nemo.kr.service.BranchService;



@Service
public class BranchServiceImpl implements BranchService {

	@Autowired ReplicationBranchMapper replicationBranchMapper;
	
	@Override
	public WorkDTO getWork(String callPhoneNum) {
		// TODO Auto-generated method stub
		return replicationBranchMapper.getWork(callPhoneNum);
	}

	
	@Override
	public IlboDTO selectIlboLimitOne(IlboDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationBranchMapper.selectIlboLimitOne(paramDTO);
	}

	@Override
	public ManagerDTO selectManagerInfo(ManagerDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationBranchMapper.selectManagerInfo(paramDTO);
	}

	@Override
	public WorkerDTO selectWorkerInfo(WorkerDTO paramDTO) {
		// TODO Auto-generated method stub
		return replicationBranchMapper.selectWorkerInfo(paramDTO);
	}


}