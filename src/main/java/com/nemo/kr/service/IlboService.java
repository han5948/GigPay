package com.nemo.kr.service;

import java.util.List;
import java.util.Map;

import com.nemo.kr.dto.AutoIlboDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IlboDeductibleDTO;
import com.nemo.kr.dto.IlboDeductionDTO;
import com.nemo.kr.dto.OutputDTO;
import com.nemo.kr.dto.StatusSumDTO;


/**
 * 일가자 일일대장 관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
public interface IlboService {

	public int getIlboTotalCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboList(IlboDTO ilboDTO);

	public IlboDTO getIlboExcelInfo(IlboDTO ilboDTO);

	public List<Map<String, Object>> getIlboExcel(IlboDTO ilboDTO);

	public IlboDTO selectIlboInfo(IlboDTO ilboDTO);

	public void setIlboCell(IlboDTO ilboDTO);                                     // Insert

	public void setIlboInfo(IlboDTO ilboDTO);                                     // Update

	public void copyIlboCell(IlboDTO ilboDTO);

	public void addWorkToIlboCell(IlboDTO ilboDTO);

	public void addWorkerToIlboCell(IlboDTO ilboDTO);

	public void removeIlboInfo(IlboDTO ilboDTO);                                  // delete

	public void setIlboOutputStatus(IlboDTO ilboDTO);										//수동 푸쉬 정보 저장

	public List<IlboDTO> getIlgajaIlboList(IlboDTO paramDTO);						//일가자(앱) 에서 리스트

	public void setICopyWorkerCell(IlboDTO ilboDTO);

	public List<IlboDTO> getIlgajaStatus(IlboDTO ilboDTO);

	public void insertIlboWorker(IlboDTO ilboDTO);

	public int getPrevIlboCount(IlboDTO ilboDTO);

	public List<IlboDTO> getPrevIlboList(IlboDTO ilboDTO);

	public void insertCodeLog(CodeLogDTO logDTO);

	public void sendManagerPush(IlboDTO iDTO);

	public void addOrderToIlboCell(IlboDTO ilboDTO);

	public void setCopyIlbo(IlboDTO ilboDTO);

	public IlboDTO getIlboInfoW2(IlboDTO paramDTO);

	public List<IlboDTO> getIlboListW2(IlboDTO paramDTO);

	public List<IlboDTO> getIlboOutputCodeListW2(IlboDTO ilboDTO);

	public void copyIlboWork(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboHistList(IlboDTO ilboDTO);

	public int selectIlboHistCnt(IlboDTO ilboDTO);

	public int selectIlboWorkCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboWorkList(IlboDTO ilboDTO);
	
	public IlboDTO selectSmsInfo(String ilbo_seq);

	public List<IlboDTO> selectIlboTimeList(IlboDTO paramDTO);

	public void setIlboReserve(IlboDTO ilboInfo, IlboDTO ilboDTO);

	public CodeLogDTO selectCode(CodeLogDTO paramDTO);
	
	public void insertIlbo(IlboDTO ilboDTO);

	public List<IlboDTO> selectCompanionList(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboListV3(IlboDTO paramDTO);

	public List<IlboDTO> getMyIlboList(IlboDTO paramDTO);

	public void setIlboWorkerReset(IlboDTO ilboDTO);

	public List<IlboDTO> getSelectIlboList(IlboDTO ilboDTO);

	public OutputDTO getOutputStatusSum(IlboDTO ilboDTO);

	public StatusSumDTO getAssignWorkSum(IlboDTO ilboDTO);

	public StatusSumDTO getNotAssignWorkSum(IlboDTO ilboDTO);
	
	public IlboDTO selectWebPushIlboInfo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectIlboResetList(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectLimitList(IlboDTO ilboDTO);
	
	public IlboDTO selectIlboSignInfo(IlboDTO ilboDTO);
	
	public int selectAgoIlboCount(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectAgoIlbo(IlboDTO ilboDTO);
	
	public IlboDTO selectFirstIlbo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectAgoLastIlbo(IlboDTO ilboDTO);
	
	public void insertIlboDeduction(IlboDeductionDTO ilboDeductionDTO);
	
	public List<IlboDTO> selectDeductionStatusList(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectDeductionList(IlboDTO ilboDTO);
	
	public void updateIlboDeduction(IlboDeductionDTO ilboDeductionDTO);
	
	public void insertDeductible(IlboDeductibleDTO ilboDeductibleDTO);
	
	public IlboDeductibleDTO selectDeductibleInfo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectFirstLastIlbo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectAgoIlboList(IlboDTO ilboDTO);

	public void setAutoIlbo(IlboDTO ilboDTO);
	
	public List<AutoIlboDTO> selectAutoIlboLog(IlboDTO ilboDTO);
	
	public AutoIlboDTO selectAutoIlbo(IlboDTO ilboDTO);
	
	public String getAutoSendWorkerCount(IlboDTO ilboDTO);
}

