package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.ProcedureDTO;

public interface ProcedureMapper {
	
	public List<ProcedureDTO> getProcedureList(ProcedureDTO procedureDTO);
	
	public int getProcedureTotalCnt(ProcedureDTO procedureDTO);
}
