package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.SignHistoryDTO;
import com.nemo.kr.dto.SignManagementDTO;
import com.nemo.kr.mapper.ilgaja.write.EDocumentMapper;
import com.nemo.kr.service.EDocumentService;

@Service
public class EDocumentServiceImpl implements EDocumentService {
	@Autowired
	EDocumentMapper eDocumentMapper;

	@Override
	public int selectEDocumentListCnt(SignManagementDTO signManagementDTO) {
		// TODO Auto-generated method stub
		return eDocumentMapper.selectEDocumentListCnt(signManagementDTO);
	}

	@Override
	public List<SignManagementDTO> selectEDocumentList(SignManagementDTO signManagementDTO) {
		// TODO Auto-generated method stub
		List<SignManagementDTO> resultList = eDocumentMapper.selectEDocumentList(signManagementDTO);

		return resultList;
	}

	@Override
	public List<SignHistoryDTO> getHistoryList(SignHistoryDTO signHistoryDTO) {
		// TODO Auto-generated method stub
		List<SignHistoryDTO> resultList = eDocumentMapper.getHistoryList(signHistoryDTO);
		
		return resultList;
	}
}
