package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.AccessDTO;
import com.nemo.kr.mapper.ilgaja.write.AccessMapper;
import com.nemo.kr.service.AccessService;


@Service
public class AccessServiceImpl implements AccessService {

	@Autowired AccessMapper accessMapper;

	@Override
	public int getTotalCnt(AccessDTO accessDTO) {
		// TODO Auto-generated method stub

		return accessMapper.getTotalCnt(accessDTO);
	}

	@Override
	public List<AccessDTO> getList(AccessDTO accessDTO) {
		// TODO Auto-generated method stub

		return (List<AccessDTO>) accessMapper.getList(accessDTO);
	}
	
	@Override
	@Transactional
	public void insertInfo(AccessDTO accessDTO) {
		// TODO Auto-generated method stub

		accessMapper.insertInfo(accessDTO);
	}

	@Override
	@Transactional
	public void updateInfo(AccessDTO accessDTO) {
		// TODO Auto-generated method stub

		accessMapper.updateInfo(accessDTO);
	}
	
	@Override
	@Transactional
	public void removeInfo(AccessDTO accessDTO) {
		// TODO Auto-generated method stub

		accessMapper.removeInfo(accessDTO);
	}

	@Override
	public AccessDTO selectInfo(AccessDTO accessDTO) {
		// TODO Auto-generated method stub
		return accessMapper.selectInfo(accessDTO);
	}
}
