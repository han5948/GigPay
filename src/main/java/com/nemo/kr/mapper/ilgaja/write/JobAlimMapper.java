package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.JobAlimDTO;
import com.nemo.kr.dto.JobAlimReceiveDTO;
import com.nemo.kr.dto.WorkerDTO;

public interface JobAlimMapper {
	public int selectListCntWorkerSeq(JobAlimReceiveDTO jobAlimReceiveDTO);
	
	public List<JobAlimReceiveDTO> getWorkerSeq(JobAlimReceiveDTO jobAlimReceiveDTO);
	
	public void insertInfo(JobAlimDTO jobAlimDTO);
	
	public void insertInfoReceive(JobAlimDTO jobAlimDTO);
	
	public int selectListCnt(JobAlimDTO jobAlimDTO);
	
	public List<JobAlimDTO> selectList(JobAlimDTO jobAlimDTO);
	
	public List<WorkerDTO> selectInfoToken(WorkerDTO workerDTO);
	
	public JobAlimDTO selectInfo(JobAlimDTO jobAlimDTO);
	
	public List<JobAlimReceiveDTO> selectListReceive(JobAlimReceiveDTO jobAlimReceiveDTO);
	
	public void updateInfo(JobAlimDTO jobAlimDTO);
	
	public void deleteInfo(JobAlimDTO jobAlimDTO);
	
	public void deleteInfoReceive(JobAlimDTO jobAlimDTO);
	
	public int selectListCntToAPI(JobAlimReceiveDTO jobAlimReceiveDTO);
	
	public List<JobAlimDTO> selectListToAPI(JobAlimReceiveDTO jobAlimReceiveDTO);
	
	public void updateInfoToAPI(JobAlimReceiveDTO jobAlimReceiveDTO);
}
