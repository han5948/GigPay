package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.IssueDTO;
import com.nemo.kr.mapper.ilgaja.write.IssueMapper;
import com.nemo.kr.service.IssueService;

@Service
public class IssueServiceImpl implements IssueService {
	@Autowired
	IssueMapper issueMapper;
	
	@Override
	public void insertIssue(IssueDTO issueDTO) {
		// TODO Auto-generated method stub
		issueMapper.insertIssue(issueDTO);
	}

	@Override
	public int selectIssueListCnt(IssueDTO issueDTO) {
		// TODO Auto-generated method stub
		return issueMapper.selectIssueListCnt(issueDTO);
	}

	@Override
	public List<IssueDTO> selectIssueList(IssueDTO issueDTO) {
		// TODO Auto-generated method stub
		return issueMapper.selectIssueList(issueDTO);
	}

	@Override
	public IssueDTO selectIssueInfo(IssueDTO issueDTO) {
		// TODO Auto-generated method stub
		return issueMapper.selectIssueInfo(issueDTO);
	}

	@Override
	public void deleteIssueInfo(IssueDTO issueDTO) {
		// TODO Auto-generated method stub
		issueMapper.deleteIssueInfo(issueDTO);
	}

	@Override
	public void updateIssueInfo(IssueDTO issueDTO) {
		// TODO Auto-generated method stub
		issueMapper.updateIssueInfo(issueDTO);
	}
}
