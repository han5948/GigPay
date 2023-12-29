package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.TransferWorkerLocationDTO;

public interface ReplicationTransferWorkerLocationMapper {

	public List<TransferWorkerLocationDTO> selectTransferWorkerLocationList(TransferWorkerLocationDTO transferWorkerLocationDTO);
	
}
