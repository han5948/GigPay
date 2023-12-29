package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.WorkDTO;

public interface ReplicationWorkMapper {
	
	public int getWorkTotalCnt(WorkDTO workDTO);

	public List<WorkDTO> getWorkList(WorkDTO workDTO);

	public WorkDTO getSelectInfo(WorkDTO workDTO);
  
	public WorkDTO selectWorkInfo(WorkDTO workDTO);
  
	public List<WorkDTO> selectInList(WorkDTO workDTO);
	
}
