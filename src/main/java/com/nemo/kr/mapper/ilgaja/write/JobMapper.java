package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.JobDTO;




public interface JobMapper {
	public int selectJobListCnt(JobDTO jobDTO);
	
	public List<JobDTO> selectJobList(JobDTO jobDTO);
	
	public JobDTO selectInfo(JobDTO jobDTO);
	
	public void insertJob(JobDTO jobDTO);
	
	public void updateJob(JobDTO jobDTO);
	
	public void deleteJob(JobDTO jobDTO);
	
	public void insertJobDetail(JobDTO jobDTO);
	
	public void updateJobDetail(JobDTO jobDTO);
	
	public void deleteJobDetail(JobDTO jobDTO);
	
	public void insertAddJobChoose(JobDTO jobDTO);
	
	public void updateAddJobChoose(JobDTO jobDTO);
	
	public void deleteAddJobChoose(JobDTO jobDTO);
	
	public List<JobDTO> selectChooseList(JobDTO jobDTO);
	
	public List<JobDTO> selectJob(JobDTO jobDTO);
	
	public void updateJobOrder(JobDTO jobDTO);

	public List<JobDTO> getJobList(JobDTO jobDTO);
	
	public void updateWorkerJobName(JobDTO jobDTO);
}