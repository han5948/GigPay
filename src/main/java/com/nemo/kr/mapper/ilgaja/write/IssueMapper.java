package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.IssueDTO;

public interface IssueMapper {
	public void insertIssue(IssueDTO issueDTO);
	
	public int selectIssueListCnt(IssueDTO issueDTO);
	
	public List<IssueDTO> selectIssueList(IssueDTO issueDTO);
	
	public IssueDTO selectIssueInfo(IssueDTO issueDTO);
	
	public void deleteIssueInfo(IssueDTO issueDTO);
	
	public void updateIssueInfo(IssueDTO issueDTO);
}
