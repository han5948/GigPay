package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;
import java.util.Map;

import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IlboDeductibleDTO;
import com.nemo.kr.dto.IlboDeductionDTO;
import com.nemo.kr.dto.OutputDTO;
import com.nemo.kr.dto.StatusSumDTO;


/**
 * 일가자 일일대장 관리 mapper
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
public interface IlboMapper {

	public int getIlboTotalCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboList(IlboDTO ilboDTO);

	public IlboDTO getIlboExcelInfo(IlboDTO ilboDTO);

	public List<Map<String, Object>> getIlboExcel(IlboDTO ilboDTO);

	public void setIlboCell(IlboDTO ilboDTO);                                     // Insert

	public void setIlboInfo(IlboDTO ilboDTO);                                     // Update

	public void copyIlboCell(IlboDTO ilboDTO);

	public void addWorkToIlboCell(IlboDTO ilboDTO);

	public void addWorkerToIlboCell(IlboDTO ilboDTO);

	public void removeIlboInfo(IlboDTO ilboDTO);                                  // delete

	public IlboDTO selectIlboInfo(IlboDTO ilboDTO);

	public void setIlboOutputStatus(IlboDTO ilboDTO);

	public List<IlboDTO> getIlgajaIlboList(IlboDTO ilboDTO);

	public void setICopyWorkerCell(IlboDTO ilboDTO);

	public List<IlboDTO> getIlgajaStatus(IlboDTO ilboDTO);

	public void insertIlboWorker(IlboDTO ilboDTO);

	public int getPrevIlboCount(IlboDTO ilboDTO);

	public List<IlboDTO> getPrevIlboList(IlboDTO ilboDTO);

	public IlboDTO selectPushInfo(IlboDTO ilboDTO);

	public void addOrderToIlboCell(IlboDTO ilboDTO);

	public void setCopyIlbo(IlboDTO ilboDTO);	//구인처 앱에서 사용

	public IlboDTO getIlboInfoW2(IlboDTO paramDTO);
	
	public List<IlboDTO> getIlboListW2(IlboDTO paramDTO);

	public List<IlboDTO> getIlboOutputCodeListW2(IlboDTO ilboDTO);

	public void copyIlboWork(IlboDTO ilboDTO);

	public int selectIlboHistCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboHistList(IlboDTO ilboDTO);

	public int selectIlboWorkCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboWorkList(IlboDTO ilboDTO);

	public IlboDTO selectSmsInfo(String ilbo_seq);

	public List<IlboDTO> selectIlboTimeList(IlboDTO paramDTO);

	public List<IlboDTO> selectWorkerIlbo(IlboDTO paramDTO);

	public CodeLogDTO selectCode(CodeLogDTO paramDTO);
	
	public void insertIlbo(IlboDTO ilboDTO);

	public List<IlboDTO> selectCompanionList(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboListV3(IlboDTO paramDTO);

	public List<IlboDTO> getMyIlboList(IlboDTO paramDTO);

	public void setIlboWorkerReset(IlboDTO workerIlbo);

	public List<IlboDTO> getSelectIlboList(IlboDTO ilboDTO);

	public OutputDTO getOutputStatusSum(IlboDTO ilboDTO);

	public StatusSumDTO getAssignWorkSum(IlboDTO ilboDTO);

	public StatusSumDTO getNotAssignWorkSum(IlboDTO ilboDTO);
	
	public IlboDTO selectWebPushIlboInfo(IlboDTO ilboDTO);
	
	public IlboDTO selectManagerPhoneInfo(IlboDTO ilboDTO);
	
	public IlboDTO selectWorkManagerPhoneInfo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectIlboResetList(IlboDTO ilboDTO);
	
	public IlboDTO selectIlboPriceInfo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectLimitList(IlboDTO ilboDTO);
	
	public IlboDTO selectIlboSignInfo(IlboDTO ilboDTO);
	
	public void insertIlboDeduction(IlboDeductionDTO ilboDeductionDTO);
	
	public void updateIlboDeduction(IlboDeductionDTO ilboDeductionDTO);
	
	public void insertDeductible(IlboDeductibleDTO ilboDeductibleDTO);
	
	public void setAutoIlbo(IlboDTO ilboDTO);
	
	public void insertAutoIlboStatusLog(IlboDTO ilboDTO);
}
