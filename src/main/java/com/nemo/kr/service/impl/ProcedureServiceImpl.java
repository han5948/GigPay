package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.ProcedureDTO;
import com.nemo.kr.mapper.ilgaja.write.ProcedureMapper;
import com.nemo.kr.service.ProcedureService;


@Service
public class ProcedureServiceImpl implements ProcedureService {

	@Autowired ProcedureMapper procedureMapper;
	
	@Override
	public List<ProcedureDTO> getProcedureList(ProcedureDTO procedureDTO) {
		// TODO Auto-generated method stub
		return procedureMapper.getProcedureList(procedureDTO);
	}

	@Override
	public int getProcedureTotalCnt(ProcedureDTO procedureDTO) {
		// TODO Auto-generated method stub
		return procedureMapper.getProcedureTotalCnt(procedureDTO);
	}
	
}
