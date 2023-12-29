package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;
import java.util.Map;

import com.nemo.kr.dto.AutoIlboDTO;
import com.nemo.kr.dto.CodeLogDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.IlboDeductibleDTO;
import com.nemo.kr.dto.OutputDTO;
import com.nemo.kr.dto.StatusSumDTO;

public interface ReplicationIlboMapper {

	public int getIlboTotalCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboList(IlboDTO ilboDTO);

	public IlboDTO getIlboExcelInfo(IlboDTO ilboDTO);

	public List<Map<String, Object>> getIlboExcel(IlboDTO ilboDTO);

	public IlboDTO selectIlboInfo(IlboDTO ilboDTO);

	public List<IlboDTO> getIlgajaIlboList(IlboDTO ilboDTO);

	public List<IlboDTO> getIlgajaStatus(IlboDTO ilboDTO);

	public int getPrevIlboCount(IlboDTO ilboDTO);

	public List<IlboDTO> getPrevIlboList(IlboDTO ilboDTO);

	public IlboDTO selectPushInfo(IlboDTO ilboDTO);

	public IlboDTO getIlboInfoW2(IlboDTO paramDTO);
	
	public List<IlboDTO> getIlboListW2(IlboDTO paramDTO);

	public List<IlboDTO> getIlboOutputCodeListW2(IlboDTO ilboDTO);

	public int selectIlboHistCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboHistList(IlboDTO ilboDTO);

	public int selectIlboWorkCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboWorkList(IlboDTO ilboDTO);

	public IlboDTO selectSmsInfo(String ilbo_seq);

	public List<IlboDTO> selectIlboTimeList(IlboDTO paramDTO);

	public List<IlboDTO> selectWorkerIlbo(IlboDTO paramDTO);

	public CodeLogDTO selectCode(CodeLogDTO paramDTO);
	
	public List<IlboDTO> selectCompanionList(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboListV3(IlboDTO paramDTO);

	public List<IlboDTO> getMyIlboList(IlboDTO paramDTO);

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
	
	public int selectAgoIlboCount(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectAgoIlbo(IlboDTO ilboDTO);
	
	public IlboDTO selectFirstIlbo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectAgoLastIlbo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectDeductionStatusList(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectDeductionList(IlboDTO ilboDTO);
	
	public IlboDeductibleDTO selectDeductibleInfo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectFirstLastIlbo(IlboDTO ilboDTO);
	
	public List<IlboDTO> selectAgoIlboList(IlboDTO ilboDTO);
	
	public List<AutoIlboDTO> selectAutoIlboLog(IlboDTO ilboDTO);
	
	public AutoIlboDTO selectAutoIlbo(IlboDTO ilboDTO);
}
