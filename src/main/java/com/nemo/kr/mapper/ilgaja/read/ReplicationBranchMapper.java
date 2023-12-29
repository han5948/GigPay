package com.nemo.kr.mapper.ilgaja.read;

import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;



public interface ReplicationBranchMapper {

	public WorkDTO getWork(String callPhoneNum);

	public IlboDTO selectIlboLimitOne(IlboDTO paramDTO);

	public ManagerDTO selectManagerInfo(ManagerDTO paramDTO);

	public WorkerDTO selectWorkerInfo(WorkerDTO paramDTO);

}