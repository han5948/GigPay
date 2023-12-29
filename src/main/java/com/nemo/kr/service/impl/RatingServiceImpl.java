package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.RatingDTO;
import com.nemo.kr.mapper.ilgaja.write.RatingMapper;
import com.nemo.kr.service.RatingService;


/**
 * 평가 관리 ServiceImpl
 * @date 2018. 7. 5.
 * @desc  
 *
 */
@Service
public class RatingServiceImpl implements RatingService {

	@Autowired RatingMapper ratingMapper;


	@Override
	public int selectListCnt(RatingDTO paramDTO) {
		// TODO Auto-generated method stub
		return ratingMapper.selectListCnt(paramDTO);
	}

	@Override
	public List<RatingDTO> selectList(RatingDTO paramDTO) {
		// TODO Auto-generated method stub
		return ratingMapper.selectList(paramDTO);
	}

	@Override
	public void insertInfo(RatingDTO paramDTO) {
		// TODO Auto-generated method stub
		ratingMapper.insertInfo(paramDTO);
	}

	@Override
	public RatingDTO selectInfo(RatingDTO paramDTO) {
		// TODO Auto-generated method stub
		return ratingMapper.selectInfo(paramDTO);
	}

	@Override
	public void updateInfo(RatingDTO paramDTO) {
		// TODO Auto-generated method stub
		ratingMapper.updateInfo(paramDTO);
	}

	@Override
	public void deleteInfo(RatingDTO paramDTO) {
		// TODO Auto-generated method stub
		ratingMapper.deleteInfo(paramDTO);
	}


}