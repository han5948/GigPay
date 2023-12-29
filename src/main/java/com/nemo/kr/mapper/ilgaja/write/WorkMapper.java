package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.LaborContractDTO;
import com.nemo.kr.dto.WorkDTO;


/**
 * 일가자 현장 관리 mapper
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-19
 */
public interface WorkMapper {
	public int getWorkTotalCnt(WorkDTO workDTO);

	public List<WorkDTO> getWorkList(WorkDTO workDTO);

	public void setWorkCell(WorkDTO workDTO);                                     // Insert

	public void setWorkInfo(WorkDTO workDTO);                                     // Update

	public void removeWorkInfo(WorkDTO workDTO);                                  // delete

	public WorkDTO getSelectInfo(WorkDTO workDTO);
  
	public WorkDTO selectWorkInfo(WorkDTO workDTO);
  
	public List<WorkDTO> selectInList(WorkDTO workDTO);
	
	public void insertLaborContractLog(LaborContractDTO laborContractDTO);
}
