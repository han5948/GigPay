package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.RatingDTO;


/**
 * 평가 Mapper
 * @author NEMODREAM Co., Ltd.
 *
 */
public interface RatingMapper {

	public int selectListCnt(RatingDTO paramDTO);

	public List<RatingDTO> selectList(RatingDTO paramDTO);

	public void insertInfo(RatingDTO paramDTO);

	public RatingDTO selectInfo(RatingDTO paramDTO);

	public void updateInfo(RatingDTO paramDTO);

	public void deleteInfo(RatingDTO paramDTO);


}