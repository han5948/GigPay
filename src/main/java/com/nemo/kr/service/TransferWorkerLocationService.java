package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.TransferWorkerLocationDTO;

public interface TransferWorkerLocationService {
	
	public List<TransferWorkerLocationDTO> getTransferWorkerLocationList(TransferWorkerLocationDTO transferWorkerLocationDTO);
	
	public void locationSave(TransferWorkerLocationDTO transferWorkerLocationDTO);
	
}
