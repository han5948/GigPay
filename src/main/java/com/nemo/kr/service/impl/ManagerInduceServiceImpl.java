package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.ManagerInduceDTO;
import com.nemo.kr.dto.ManagerSubInduceDTO;
import com.nemo.kr.mapper.ilgaja.write.ManagerInduceMapper;
import com.nemo.kr.service.ManagerInduceService;

@Service
public class ManagerInduceServiceImpl implements ManagerInduceService {
	@Autowired
	ManagerInduceMapper managerInduceMapper;

	@Override
	public int selectManagerInduceListCnt(ManagerInduceDTO managerInduceDTO) {
		// TODO Auto-generated method stub
		return managerInduceMapper.selectManagerInduceListCnt(managerInduceDTO);
	}

	@Override
	public List<ManagerInduceDTO> selectManagerInduceList(ManagerInduceDTO managerInduceDTO) {
		// TODO Auto-generated method stub
		return managerInduceMapper.selectManagerInduceList(managerInduceDTO);
	}

	@Override
	public List<ManagerSubInduceDTO> selectManagerSubInduceList(ManagerSubInduceDTO managerSubInduceDTO) {
		// TODO Auto-generated method stub
		return managerInduceMapper.selectManagerSubInduceList(managerSubInduceDTO);
	}

	@Override
	public void updateManagerInduce(ManagerInduceDTO managerInduceDTO) {
		// TODO Auto-generated method stub
		managerInduceMapper.updateManagerInduce(managerInduceDTO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteManagerInduce(ManagerInduceDTO managerInduceDTO) throws Exception{
		// TODO Auto-generated method stub
		if(managerInduceDTO.getMainGridArr() != null) {
			managerInduceMapper.deleteManagerInduce(managerInduceDTO);
		}
		
		managerInduceMapper.deleteManagerInduceSub(managerInduceDTO);
	}

	@Override
	public void deleteManagerInduceSub(ManagerInduceDTO managerInduceDTO) {
		// TODO Auto-generated method stub
		managerInduceMapper.deleteManagerInduceSub(managerInduceDTO);
	}
}
