package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.EvaluateDTO;


/**
 * 평가하기 Mapper
 * @author NEMODREAM Co., Ltd.
 *
 */
public interface EvaluateMapper {

	public int selectListCnt(EvaluateDTO paramDTO);

	public List<EvaluateDTO> selectList(EvaluateDTO paramDTO);

	public void insertInfo(EvaluateDTO paramDTO);

	public EvaluateDTO selectInfo(EvaluateDTO paramDTO);

	public void updateInfo(EvaluateDTO paramDTO);

	public void deleteInfo(EvaluateDTO paramDTO);

}