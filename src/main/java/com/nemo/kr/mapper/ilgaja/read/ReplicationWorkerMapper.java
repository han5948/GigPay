package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.WorkerDTO;

public interface ReplicationWorkerMapper {

	public int getWorkerTotalCnt(WorkerDTO workerDTO);

	public List<WorkerDTO> getWorkerList(WorkerDTO workerDTO);

	public WorkerDTO getWorkerView(WorkerDTO workerDTO);

	public WorkerDTO selectWorkerInfo(WorkerDTO workerDTO);

	public int setWorkerPassword(WorkerDTO workerDTO);

	public int selectWorker(WorkerDTO workerDTO);

	public int chkWorkerPhone(WorkerDTO workerDTO);
  
	public int chkWorkerJumin(WorkerDTO workerDTO);

	public int selectWorkerCount(WorkerDTO paramDTO);

	public List<WorkerDTO> locationMap(WorkerDTO workerDTO);
	
	public List<WorkerDTO> selectReceiveTokenInfo(WorkerDTO workerDTO);
	
	public List<WorkerDTO> selectWaitApprovalCount(WorkerDTO workerDTO);
}
