package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.JobAlimDTO;
import com.nemo.kr.dto.JobAlimReceiveDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.mapper.ilgaja.write.JobAlimMapper;
import com.nemo.kr.service.JobAlimService;

@Service
public class JobAlimServiceImpl implements JobAlimService{
	@Autowired
	JobAlimMapper jobAlimMapper;
	
	@Override
	public int selectListCntWorkerSeq(JobAlimReceiveDTO jobAlimReceiveDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectListCntWorkerSeq(jobAlimReceiveDTO);
	}
	
	@Override
	public List<JobAlimReceiveDTO> getWorkerSeq(JobAlimReceiveDTO jobAlimReceiveDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.getWorkerSeq(jobAlimReceiveDTO);
	}

	@Override
	public void insertInfo(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		jobAlimMapper.insertInfo(jobAlimDTO);
	}

	@Override
	public void insertInfoReceive(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		jobAlimMapper.insertInfoReceive(jobAlimDTO);
	}

	@Override
	public int selectListCnt(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectListCnt(jobAlimDTO);
	}

	@Override
	public List<JobAlimDTO> selectList(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectList(jobAlimDTO);
	}

	@Override
	public List<WorkerDTO> selectInfoToken(WorkerDTO workerDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectInfoToken(workerDTO);
	}

	@Override
	public JobAlimDTO selectInfo(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectInfo(jobAlimDTO);
	}

	@Override
	public List<JobAlimReceiveDTO> selectListReceive(JobAlimReceiveDTO jobAlimReceiveDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectListReceive(jobAlimReceiveDTO);
	}

	@Override
	public void updateInfo(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		jobAlimMapper.updateInfo(jobAlimDTO);
	}

	@Override
	public void deleteInfo(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		jobAlimMapper.deleteInfo(jobAlimDTO);
	}
	
	@Override
	public void deleteInfoReceive(JobAlimDTO jobAlimDTO) {
		// TODO Auto-generated method stub
		jobAlimMapper.deleteInfoReceive(jobAlimDTO);
	}

	@Override
	public int selectListCntToAPI(JobAlimReceiveDTO jobAlimReceiveDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectListCntToAPI(jobAlimReceiveDTO);
	}
	
	@Override
	public List<JobAlimDTO> selectListToAPI(JobAlimReceiveDTO jobAlimReceiveDTO) {
		// TODO Auto-generated method stub
		return jobAlimMapper.selectListToAPI(jobAlimReceiveDTO);
	}

	@Override
	public void updateInfoToAPI(JobAlimReceiveDTO jobAlimReceiveDTO) {
		// TODO Auto-generated method stub
		jobAlimMapper.updateInfoToAPI(jobAlimReceiveDTO);
	}
}
