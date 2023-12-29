package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.PriceLogDTO;



/**
 * 각종 로그 mapper
 * @author  : nemojjang
 * @date    : 2019. 5. 17.
 * @desc    : 
 *
 */
public interface LogMapper {

	public int getCodeLogTotalCnt(CodeLogDTO paramDTO);

	public List<CodeLogDTO> getCodeLogList(CodeLogDTO paramDTO);
	
	public void insertCodeLog(CodeLogDTO paramDTO);                                     // Insert
	
	public void insertPriceLog(PriceLogDTO priceLogDTO);
	
	public void insertEmployerCodeLog(CodeLogDTO logDTO);
	
	public List<CodeLogDTO> selectEmployerCodeLogList(CodeLogDTO logDTO);
	
	public List<PriceLogDTO> selectPriceLogList(PriceLogDTO priceLogDTO);
}
