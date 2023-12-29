package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.AlimDTO;
import com.nemo.kr.dto.AlimReceiveDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.mapper.ilgaja.write.AlimMapper;
import com.nemo.kr.service.AlimService;

@Service
public class AlimServiceImpl implements AlimService{
	@Autowired
	AlimMapper alimMapper;
	
	@Override
	public void insertAlim(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		alimMapper.insertAlim(alimDTO);
	}
	
	@Override
	public void insertAlimReceive(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		alimMapper.insertAlimReceive(alimDTO);
	}
	
	@Override
	public void insertManagerAlimReceive(AlimReceiveDTO alimReceiveDTO) {
		// TODO Auto-generated method stub
		alimMapper.insertManagerAlimReceive(alimReceiveDTO);
	}
	
	@Override
	public int selectListCnt(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectListCnt(alimDTO);
	}
	
	@Override
	public List<AlimDTO> selectList(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectList(alimDTO);
	}
	
	@Override
	public List<AlimReceiveDTO> selectReceiveList(AlimReceiveDTO alimReceiveDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectReceiveList(alimReceiveDTO);
	}
	
	@Override
	public List<WorkerDTO> selectReceiveTokenInfo(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectReceiveTokenInfo(workerDTO);
	}
	
	@Override
	public AlimDTO selectInfo(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectInfo(alimDTO);
	}
	
	@Override
	public void updateAlim(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		alimMapper.updateAlim(alimDTO);
	}
	
	@Override
	public void deleteAlim(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		alimMapper.deleteAlim(alimDTO);
	}
	
	@Override
	public void deleteReceiveAlim(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		alimMapper.deleteReceiveAlim(alimDTO);
	}
	
	@Override
	public List<AlimDTO> selectAlimList(AlimReceiveDTO alimReceiveDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectAlimList(alimReceiveDTO);
	}
	
	@Override
	public int selectAlimListCnt(AlimReceiveDTO alimReceiveDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectAlimListCnt(alimReceiveDTO);
	}
	
	@Override
	public void setUpdateAlim(AlimReceiveDTO alimReceiveDTO) {
		// TODO Auto-generated method stub
		alimMapper.setUpdateAlim(alimReceiveDTO);
	}

	@Override
	public List<ManagerDTO> selectManagerReceiveTokenInfo(ManagerDTO managerDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectManagerReceiveTokenInfo(managerDTO);
	}

	@Override
	public List<AlimDTO> selectSearchList(AlimDTO alimDTO) {
		// TODO Auto-generated method stub
		return alimMapper.selectSearchList(alimDTO);
	}
}
