package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.TransferWorkerLocationDTO;
import com.nemo.kr.dto.TransferWorkerOptionDTO;
import com.nemo.kr.mapper.ilgaja.read.ReplicationTransferWorkerOptionMapper;
import com.nemo.kr.mapper.ilgaja.write.TransferWorkerLocationMapper;
import com.nemo.kr.mapper.ilgaja.write.TransferWorkerOptionMapper;
import com.nemo.kr.service.TransferWorkerOptionService;

@Service
public class TransferWorkerOptionServiceImpl implements TransferWorkerOptionService {

	@Autowired ReplicationTransferWorkerOptionMapper replTransferWorkerOptionMapper;
	@Autowired TransferWorkerOptionMapper writeTransferWorkerOptionMapper;
	@Autowired TransferWorkerLocationMapper writeTransferWorkerLocationMapper;
	
	@Override
	public int getTransferWorkerOptionListCnt(TransferWorkerOptionDTO transferWorkerOptionDTO) {
		// TODO Auto-generated method stub
		return replTransferWorkerOptionMapper.selectTransferWorkerOptionListCnt(transferWorkerOptionDTO);
	}

	@Override
	public List<TransferWorkerOptionDTO> getTransferWorkerOptionList(TransferWorkerOptionDTO transferWorkerOptionDTO) {
		// TODO Auto-generated method stub
		return replTransferWorkerOptionMapper.selectTransferWorkerOptionList(transferWorkerOptionDTO);
	}

	@Override
	public void addTracsferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO) {
		// TODO Auto-generated method stub
		//transferWorkerOptionDTO.setSrh_use_yn("1");
		int rowCount = replTransferWorkerOptionMapper.selectTransferWorkerOptionListCnt(transferWorkerOptionDTO);
		int lastRank = rowCount + 1;
		transferWorkerOptionDTO.setOption_rank(lastRank + "");
		
		transferWorkerOptionDTO.setUse_yn("1");
		
		writeTransferWorkerOptionMapper.insertTransferWorkerOption(transferWorkerOptionDTO);
	}

	@Override
	public void setTracsferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO) {
		// TODO Auto-generated method stub
		writeTransferWorkerOptionMapper.updateTransferWorkerOption(transferWorkerOptionDTO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeTracsferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO) {
		// TODO Auto-generated method stub
		transferWorkerOptionDTO.setUse_yn("0");
		writeTransferWorkerOptionMapper.updateTransferWorkerOptionList(transferWorkerOptionDTO);
		
		//writeTransferWorkerOptionMapper.updateRankReset();
		
		//TransferWorkerLocationDTO param = new TransferWorkerLocationDTO();
		//param.setSel_transfer_seq(transferWorkerOptionDTO.getSel_transfer_seq());
		//writeTransferWorkerLocationMapper.deleteTransferWorkerLocation(param);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void rankChange(TransferWorkerOptionDTO transferWorkerOptionDTO) {
		// TODO Auto-generated method stub
		writeTransferWorkerOptionMapper.updateTransferWorkerOption(transferWorkerOptionDTO);
		
		TransferWorkerOptionDTO destOptionDTO = new TransferWorkerOptionDTO();
		destOptionDTO.setTransfer_seq(transferWorkerOptionDTO.getDest_transfer_seq());
		destOptionDTO.setOption_rank(transferWorkerOptionDTO.getDest_option_rank());
		destOptionDTO.setMod_admin(transferWorkerOptionDTO.getMod_admin());
		writeTransferWorkerOptionMapper.updateTransferWorkerOption(destOptionDTO);
	}

}
