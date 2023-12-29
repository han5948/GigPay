package com.nemo.kr.service;

import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;


/**
 * @date 2018. 7. 5.
 * @desc 
 *
 */
public interface BranchService {

	WorkDTO getWork(String callPhoneNum);


	IlboDTO selectIlboLimitOne(IlboDTO paramDTO);

	ManagerDTO selectManagerInfo(ManagerDTO paramDTO);

	WorkerDTO selectWorkerInfo(WorkerDTO paramDTO);
	
}