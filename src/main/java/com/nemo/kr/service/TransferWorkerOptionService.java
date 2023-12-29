package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.TransferWorkerOptionDTO;

public interface TransferWorkerOptionService {

	public int getTransferWorkerOptionListCnt(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public List<TransferWorkerOptionDTO> getTransferWorkerOptionList(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public void addTracsferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public void setTracsferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public void removeTracsferWorkerOption(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
	public void rankChange(TransferWorkerOptionDTO transferWorkerOptionDTO);
	
}
