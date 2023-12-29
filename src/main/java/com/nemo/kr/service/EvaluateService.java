package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.EvaluateDTO;


/**
 * 게시판 관리 Service
 * @date 2018. 7. 5.
 * @desc 
 *
 */
public interface EvaluateService {
	
	public int selectListCnt(EvaluateDTO paramDTO);

	public List<EvaluateDTO> selectList(EvaluateDTO paramDTO);

	public void insertInfo(EvaluateDTO paramDTO);

	public EvaluateDTO selectInfo(EvaluateDTO paramDTO);

	public void updateInfo(EvaluateDTO paramDTO);

	public void deleteInfo(EvaluateDTO paramDTO);

}