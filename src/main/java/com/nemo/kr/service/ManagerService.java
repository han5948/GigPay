package com.nemo.kr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.ManagerLoginDTO;
import com.nemo.kr.dto.ManagerWorkDTO;
import com.nemo.kr.dto.WorkDTO;


/**
 * 구인처 Service
 * @author nemo *
 */

public interface ManagerService {
	
	/**
	 * 구인처 운영자 로그인 프로세스
	 *
	 */
	public Map managerLoginProc(HttpServletRequest request, ManagerDTO managerDTO)throws Exception ;

	public String getAuthNum(ManagerDTO managerDTO)throws Exception;

	public ManagerDTO selectManagerInfo(ManagerDTO managerDTO);

	public void setManagerInfo(ManagerDTO paramDTO);
	
	public void setManagerInfo(HttpServletRequest request, HttpSession session,  ManagerDTO paramDTO);

	public int getIlboTotalCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboList(IlboDTO ilboDTO);

	public IlboDTO getIlboWorker(IlboDTO ilboDTO);

	public void setManager(ManagerDTO managerDTO) throws Exception;

	public int getManagerTotalCnt(ManagerDTO managerDTO);

	public List<ManagerDTO> getManagerList(ManagerDTO managerDTO);

	public void setManagerCell(ManagerDTO managerDTO);

	public void removeManagerInfo(ManagerDTO managerDTO);

	public List<WorkDTO> getWorkList(WorkDTO paramDTO);

	public void updateManagerVisit(ManagerDTO managerDTO);

	public List<ManagerDTO> getCheckManagerEmployerNumList(ManagerDTO managerDTO);

	public List<ManagerDTO> selectManagerList(ManagerDTO paramDTO);

	public List<WorkDTO> getEmployerList(WorkDTO paramDTO);

	public void regWorkProcess( HttpSession session, ManagerWorkDTO mDTO);

	public Map<String, String> setManagerWork(ManagerLoginDTO sessionDTO, WorkDTO workDTO);

	public String selectParentCompanySeq(ManagerDTO managerDTO);

	public List<ManagerDTO> getWorkManagerList(ManagerDTO managerDTO);

	//관계 테이블에서 삭제 EM, VM 모두
	public void deleteRelationM(ManagerDTO managerDTO);

	public void insertRelationM(ManagerDTO managerDTO) throws Exception;

	public void updateWm(ManagerDTO managerDTO);

	public void updateEm(ManagerDTO managerDTO);

	public void updateRelation(ManagerDTO managerDTO);

	public List<ManagerDTO> getSearchManagerList(ManagerDTO managerDTO);

	public void setProcessEm(ManagerDTO managerDTO);

	public List<EmDTO> getManagerEWTreeList(EmDTO emDTO);

	public Map<String, String> insertEm(ManagerDTO managerDTO);

	Map<String, String> insertWm(ManagerDTO managerDTO);

	public Map<String, String> deleteEm(ManagerDTO managerDTO);

	public Map<String, String> deleteWm(ManagerDTO managerDTO);

}	
