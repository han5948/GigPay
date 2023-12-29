package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.TransferWorkerLocationDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationTransferWorkerLocationMapper;
import com.nemo.kr.mapper.ilgaja.write.TransferWorkerLocationMapper;
import com.nemo.kr.service.TransferWorkerLocationService;

@Service
public class TransferWorkerLocationServiceImpl implements TransferWorkerLocationService {

	@Autowired ReplicationTransferWorkerLocationMapper replTransferWorkerLocationMapper;
	@Autowired TransferWorkerLocationMapper writeTransferWorkerLocationMapper;
	
	@Override
	public List<TransferWorkerLocationDTO> getTransferWorkerLocationList(TransferWorkerLocationDTO transferWorkerLocationDTO) {
		// TODO Auto-generated method stub
		return replTransferWorkerLocationMapper.selectTransferWorkerLocationList(transferWorkerLocationDTO);
	}

	@Override
	public void locationSave(TransferWorkerLocationDTO transferWorkerLocationDTO) {
		// TODO Auto-generated method stub
		if( transferWorkerLocationDTO.getDel_location_list() != null && transferWorkerLocationDTO.getDel_location_list().length > 0 ) {
			writeTransferWorkerLocationMapper.deleteTransferWorkerLocation(transferWorkerLocationDTO);
		}
		
		if( transferWorkerLocationDTO.getAdd_location_list() != null && transferWorkerLocationDTO.getAdd_location_list().size() > 0 ) {
			writeTransferWorkerLocationMapper.insertTransferWorkerLocationList(transferWorkerLocationDTO);
		}
	}
	
}
