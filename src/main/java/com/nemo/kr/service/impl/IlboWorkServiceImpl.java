package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.IlboWorkOptionDTO;
import com.nemo.kr.dto.JobLimitCountDTO;
import com.nemo.kr.dto.ViewTimeDTO;
import com.nemo.kr.mapper.ilgaja.write.IlboWorkMapper;
import com.nemo.kr.service.IlboWorkService;

@Service
public class IlboWorkServiceImpl implements IlboWorkService {
	@Autowired
	IlboWorkMapper ilboWorkMapper;

	@Override
	public List<IlboWorkOptionDTO> selectOptionList(IlboWorkOptionDTO ilboWorkOptionDTO) {
		// TODO Auto-generated method stub
		return ilboWorkMapper.selectOptionList(ilboWorkOptionDTO);
	}

	@Override
	public void updateOption(IlboWorkOptionDTO ilboWorkOptionDTO) {
		// TODO Auto-generated method stub
		ilboWorkMapper.updateOption(ilboWorkOptionDTO);
	}

	@Override
	public void insertOption(IlboWorkOptionDTO ilboWorkOptionDTO) {
		// TODO Auto-generated method stub
		ilboWorkMapper.insertOption(ilboWorkOptionDTO);
	}

	@Override
	public int selectJobLimitCountCnt() {
		// TODO Auto-generated method stub
		return ilboWorkMapper.selectJobLimitCountCnt();
	}
	

	@Override
	public JobLimitCountDTO selectJobLimitCount(String limitType) {
		// TODO Auto-generated method stub
		return ilboWorkMapper.selectJobLimitCount(limitType);
	}

	@Override
	public void updateJobLimitCount(JobLimitCountDTO jobLimitCountDTO) {
		// TODO Auto-generated method stub
		ilboWorkMapper.updateJobLimitCount(jobLimitCountDTO);
	}

	@Override
	public void insertJobLimitCount(JobLimitCountDTO jobLimitCountDTO) {
		// TODO Auto-generated method stub
		ilboWorkMapper.insertJobLimitCount(jobLimitCountDTO);
	}
	
	@Override
	public ViewTimeDTO selectViewTime() {
		// TODO Auto-generated method stub
		return ilboWorkMapper.selectViewTime();
	}
	
	@Override
	public int selectViewTimeCnt() {
		// TODO Auto-generated method stub
		return ilboWorkMapper.selectViewTimeCnt();
	}

	@Override
	public void insertViewTime(ViewTimeDTO viewTimeDTO) {
		// TODO Auto-generated method stub
		ilboWorkMapper.insertViewTime(viewTimeDTO);
	}

	@Override
	public void updateViewTime(ViewTimeDTO viewTimeDTO) {
		// TODO Auto-generated method stub
		ilboWorkMapper.updateViewTime(viewTimeDTO);
	}
}
