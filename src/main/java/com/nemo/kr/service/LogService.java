package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.PriceLogDTO;

/**
  * @FileName : NoticeService.java

  * @Project : ilgaja

  * @Date : 2020. 6. 1. 

  * @작성자 : Park Yun Soo

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface LogService {

	void insertCodeLog(CodeLogDTO logDTO);

	public void insertPriceLog(PriceLogDTO priceLogDTO);
	
	public void insertEmployerCodeLog(CodeLogDTO logDTO);
	
	public List<CodeLogDTO> selectEmployerCodeLogList(CodeLogDTO logDTO);
	
	public List<PriceLogDTO> getPriceLogList(PriceLogDTO priceLogDTO);
}
