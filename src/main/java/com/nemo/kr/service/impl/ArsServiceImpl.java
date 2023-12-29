package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.ArsDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationArsMapper;
import com.nemo.kr.mapper.ilgaja.write.ArsMapper;
import com.nemo.kr.service.ArsService;


/**
 * 관리자 서비스 구현체
 *
 * @author NEMODREAM Co., Ltd.
 */
@Service
public class ArsServiceImpl implements ArsService {

	@Autowired ArsMapper arsMapper;
	@Autowired ReplicationArsMapper replicationArsMapper;

	@Override
	public WorkDTO getWork(String uSERCID) {
		// TODO Auto-generated method stub
		return replicationArsMapper.getWork(uSERCID);
	}

	@Override
	public IlboDTO getMilbo(String uSERCID) {
		// TODO Auto-generated method stub
		return replicationArsMapper.getMilbo(uSERCID);
	}

	@Override
	public IlboDTO getWilbo(String uSERCID) {
		// TODO Auto-generated method stub
		return replicationArsMapper.getWilbo(uSERCID);
	}

	@Override
	public ManagerDTO getManager(String uSERCID) {
		// TODO Auto-generated method stub
		return replicationArsMapper.getManager(uSERCID);
	}

	@Override
	public WorkerDTO getWorker(String uSERCID) {
		// TODO Auto-generated method stub
		return replicationArsMapper.getWorker(uSERCID);
	}

	@Override
	public CompanyDTO getComapny(String companySeq) {
		// TODO Auto-generated method stub
		return replicationArsMapper.getComapny(companySeq);
	}

	@Override
	public void insertInfo(ArsDTO arsDTO) {
		// TODO Auto-generated method stub
		arsMapper.insertInfo(arsDTO);
	}

	@Override
	public ArsDTO getSelectInfoLimitOne(ArsDTO arsDTO) {
		// TODO Auto-generated method stub
		return replicationArsMapper.getSelectInfoLimitOne(arsDTO);
	}


}
