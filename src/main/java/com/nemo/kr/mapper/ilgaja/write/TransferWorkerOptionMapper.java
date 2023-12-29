package com.nemo.kr.mapper.ilgaja.write;

import com.nemo.kr.dto.TransferWorkerOptionDTO;

public interface TransferWorkerOptionMapper {

	public void insertTransferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public void updateTransferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public void updateTransferWorkerOptionList(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public void updateRankReset();
}
