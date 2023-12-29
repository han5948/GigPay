package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.JobDTO;
import com.nemo.kr.mapper.ilgaja.write.JobMapper;
import com.nemo.kr.service.JobService;

@Service
public class JobServiceImpl implements JobService {
	@Autowired
	JobMapper jobMapper;

	@Override
	public int selectJobListCnt(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		return jobMapper.selectJobListCnt(jobDTO);
	}

	@Override
	public List<JobDTO> selectJobList(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		return jobMapper.selectJobList(jobDTO);
	}

	@Override
	public void insertJob(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.insertJob(jobDTO);
	}

	@Override
	public JobDTO selectInfo(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		return jobMapper.selectInfo(jobDTO);
	}

	@Override
	public void updateJob(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.updateJob(jobDTO);
	}

	@Override
	public void deleteJob(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.deleteJob(jobDTO);
	}

	@Override
	public void insertJobDetail(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.insertJobDetail(jobDTO);
	}

	@Override
	public void updateJobDetail(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.updateJobDetail(jobDTO);
	}

	@Override
	public void deleteJobDetail(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.deleteJobDetail(jobDTO);
		
	}

	@Override
	public void insertAddJobChoose(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.insertAddJobChoose(jobDTO);
	}

	@Override
	public void updateAddJobChoose(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.updateAddJobChoose(jobDTO);
	}

	@Override
	public void deleteAddJobChoose(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.deleteAddJobChoose(jobDTO);
	}

	@Override
	public List<JobDTO> selectChooseList(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		return jobMapper.selectChooseList(jobDTO);
	}

	@Override
	public List<JobDTO> selectJob(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		return jobMapper.selectJob(jobDTO);
	}

	@Override
	public void updateJobOrder(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.updateJobOrder(jobDTO);
	}

	@Override
	public List<JobDTO> getJobList(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		return jobMapper.getJobList(jobDTO);
	}

	@Override
	public void updateWorkerJobName(JobDTO jobDTO) {
		// TODO Auto-generated method stub
		jobMapper.updateWorkerJobName(jobDTO);
	}
}
