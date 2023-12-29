package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.ProcedureDTO;

public interface ProcedureService {

	public List<ProcedureDTO> getProcedureList(ProcedureDTO procedureDTO);
	
	public int getProcedureTotalCnt(ProcedureDTO procedureDTO);
}
