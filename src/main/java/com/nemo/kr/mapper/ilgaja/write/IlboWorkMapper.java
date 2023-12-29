package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.IlboWorkOptionDTO;
import com.nemo.kr.dto.JobLimitCountDTO;
import com.nemo.kr.dto.ViewTimeDTO;




public interface IlboWorkMapper {
	public List<IlboWorkOptionDTO> selectOptionList(IlboWorkOptionDTO ilboWorkOptionDTO);
	
	public void updateOption(IlboWorkOptionDTO ilboWorkOptionDTO);
	
	public void insertOption(IlboWorkOptionDTO ilboWorkOptionDTO);
	
	public JobLimitCountDTO selectJobLimitCount(String limitType);
	
	public void updateJobLimitCount(JobLimitCountDTO jobLimitCountDTO);
	
	public void insertJobLimitCount(JobLimitCountDTO jobLimitCountDTO);
	
	public int selectJobLimitCountCnt();
	
	public ViewTimeDTO selectViewTime();
	
	public int selectViewTimeCnt();
	
	public void insertViewTime(ViewTimeDTO viewTimeDTO);
	
	public void updateViewTime(ViewTimeDTO viewTimeDTO);
}
