package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.TransferWorkerOptionDTO;

public interface ReplicationTransferWorkerOptionMapper {

	public List<TransferWorkerOptionDTO> selectTransferWorkerOptionList(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public int selectTransferWorkerOptionListCnt(TransferWorkerOptionDTO transferWorkerOptionDTO);
}
