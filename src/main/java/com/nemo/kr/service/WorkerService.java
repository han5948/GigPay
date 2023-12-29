package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.WorkerDTO;


/**
 * 일가자 구직자 관리 Service
 *
 * @author  nemojang
 * @version 1.0
 * @since   2017-04-20
 */
public interface WorkerService {
	public int getWorkerTotalCnt(WorkerDTO workerDTO) throws Exception;

	public List<WorkerDTO> getWorkerList(WorkerDTO workerDTO) throws Exception;

	public WorkerDTO getWorkerView(WorkerDTO workerDTO) throws Exception;

	public void setWorkerCell(WorkerDTO workerDTO) throws Exception;              // 행추가
  
	public void insertWorker(WorkerDTO workerDTO) throws Exception;              // insert

	public void setWorkerInfo(WorkerDTO workerDTO) throws Exception;              // Update

	public WorkerDTO getWorkerInfo(WorkerDTO workerDTO) throws Exception;

	public WorkerDTO getAppLoginToKenProc(WorkerDTO workerDTO) throws Exception;

	public void setWorkerPassword(WorkerDTO workerDTO) throws Exception;

	public void setWorkerConsulting(WorkerDTO workerDTO)  throws Exception;

	public String getAuthNum(WorkerDTO workerDTO)  throws Exception;

	public void updateWorkerInfo(WorkerDTO workerDTO) throws Exception;

	public void removeWorkerInfo(WorkerDTO workerDTO) throws Exception;           // delete

	public int chkWorkerPhone(WorkerDTO workerDTO);

	public int chkWorkerJumin(WorkerDTO workerDTO);

	public int selectWorkerCount(WorkerDTO paramDTO);

	public void updateWorkerLogout(WorkerDTO paramDTO);

	public String getJoinAuthNum(WorkerDTO workerDTO) throws Exception;

	public WorkerDTO getAppLoginToKenProcV2(WorkerDTO workerDTO) throws Exception;

	public List<WorkerDTO> selectReceiveTokenInfo(WorkerDTO workerDTO);

	public List<WorkerDTO> locationMap(WorkerDTO workerDTO) throws Exception;

	public List<WorkerDTO> selectWaitApprovalCount(WorkerDTO workerDTO) throws Exception;
	
	public WorkerDTO selectWorkerInfo(WorkerDTO workerDTO) throws Exception;
	
	public WorkerDTO selectWorkerInfoMasterDB(WorkerDTO workerDTO);
}
