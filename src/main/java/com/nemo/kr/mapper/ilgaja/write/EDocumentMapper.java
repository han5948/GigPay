package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.SignHistoryDTO;
import com.nemo.kr.dto.SignManagementDTO;




public interface EDocumentMapper {
	public int selectEDocumentListCnt(SignManagementDTO signManagementDTO);
	
	public List<SignManagementDTO> selectEDocumentList(SignManagementDTO signManagementDTO);
	
	public List<SignHistoryDTO> getHistoryList(SignHistoryDTO signHistoryDTO);
}
