package com.nemo.kr.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.nemo.kr.dto.LaborContractDTO;
import com.nemo.kr.dto.WorkDTO;


/**
 * 일가자 현장 관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-19
 */
public interface WorkService {

	public int getWorkTotalCnt(WorkDTO workDTO);

	public List<WorkDTO> getWorkList(WorkDTO workDTO);

	public void setWorkCell(WorkDTO workDTO);                                     // Insert
	
	public void setWorkInfo(WorkDTO workDTO);                                     // Update
	
	public void removeWorkInfo(WorkDTO workDTO);                                  // delete
	
	public WorkDTO getSelectInfo(WorkDTO wDTO);								//select
	
	public WorkDTO selectWorkInfo(WorkDTO workDTO);
	
	public List<WorkDTO> selectInList(WorkDTO workDTO);
	
	public JSONObject setWorkMerge(WorkDTO workDTO, HttpSession session);
	
	public void insertLaborContractLog(LaborContractDTO laborContractDTO);
}
