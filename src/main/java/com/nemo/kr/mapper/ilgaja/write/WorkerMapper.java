package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.WorkerDTO;


/**
 * 일가자 구직자 관리 mapper
 *
 * @author  nemojang
 * @version 1.0
 * @since   2017-04-20
 */
public interface WorkerMapper {

	public int getWorkerTotalCnt(WorkerDTO workerDTO);

	public List<WorkerDTO> getWorkerList(WorkerDTO workerDTO);

	public WorkerDTO getWorkerView(WorkerDTO workerDTO);

	public void setWorkerCell(WorkerDTO workerDTO);                               // Insert

//  public void setWorkerInfo(WorkerDTO workerDTO);                               // Update

	public void getWorkerFile(WorkerDTO workerDTO);

	public WorkerDTO selectWorkerInfo(WorkerDTO workerDTO);

	public void updateWorkerInfo(WorkerDTO workerDTO);

	public int setWorkerPassword(WorkerDTO workerDTO);

	public int selectWorker(WorkerDTO workerDTO);

	public int setWorkerConsulting(WorkerDTO workerDTO);

	public void updateSmsAuth(WorkerDTO workerDTO);

	public void removeWorkerInfo(WorkerDTO workerDTO);                              // delete

	public int chkWorkerPhone(WorkerDTO workerDTO);
  
	public int chkWorkerJumin(WorkerDTO workerDTO);

	public void insertWorker(WorkerDTO workerDTO);

	public int selectWorkerCount(WorkerDTO paramDTO);

	public void updateWorkerLogout(WorkerDTO paramDTO);

	public List<WorkerDTO> locationMap(WorkerDTO workerDTO);
	
	public List<WorkerDTO> selectReceiveTokenInfo(WorkerDTO workerDTO);
	
	public List<WorkerDTO> selectWaitApprovalCount(WorkerDTO workerDTO);
}
