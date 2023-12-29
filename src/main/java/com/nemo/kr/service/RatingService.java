package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.RatingDTO;


/**
 * 평가 관리 Service
 * @date 2018. 7. 5.
 * @desc 
 *
 */
public interface RatingService {

	public int selectListCnt(RatingDTO paramDTO);

	public List<RatingDTO> selectList(RatingDTO paramDTO);

	public void insertInfo(RatingDTO paramDTO);

	public RatingDTO selectInfo(RatingDTO paramDTO);

	public void updateInfo(RatingDTO paramDTO);

	public void deleteInfo(RatingDTO paramDTO);


}