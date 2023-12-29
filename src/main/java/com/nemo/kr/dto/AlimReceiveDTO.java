package com.nemo.kr.dto;

import java.util.List;

public class AlimReceiveDTO extends AlimDTO{
	private static final long serialVersionUID = -2231950057788485849L;
	
	private String receive_seq;		//알림 받는 번호
	private String alim_seq;		//알림 번호
	private String worker_seq;		//알림 받는 구직자 번호
	private String read_yn;			//알림 읽었는지 확인 유무(0 : 안읽음, 1 : 읽음)
	private String read_date;		//알림 확인 날짜
	private String updateFlag; 		//알림 읽음 또는 삭제 flag
	private String worker_name;		
	private String manager_name;
	private String manager_seq;
	private List<WorkerDTO> workerSeqList;
	private List<ManagerDTO> managerSeqList;
	
	public List<WorkerDTO> getWorkerSeqList() {
		return workerSeqList;
	}
	public void setWorkerSeqList(List<WorkerDTO> workerSeqList) {
		this.workerSeqList = workerSeqList;
	}
	public String getManager_seq() {
		return manager_seq;
	}
	public void setManager_seq(String manager_seq) {
		this.manager_seq = manager_seq;
	}
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getWorker_name() {
		return worker_name;
	}
	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getReceive_seq() {
		return receive_seq;
	}
	public void setReceive_seq(String receive_seq) {
		this.receive_seq = receive_seq;
	}
	public String getAlim_seq() {
		return alim_seq;
	}
	public void setAlim_seq(String alim_seq) {
		this.alim_seq = alim_seq;
	}
	public String getWorker_seq() {
		return worker_seq;
	}
	public void setWorker_seq(String worker_seq) {
		this.worker_seq = worker_seq;
	}
	public String getRead_yn() {
		return read_yn;
	}
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}
	public String getRead_date() {
		return read_date;
	}
	public void setRead_date(String read_date) {
		this.read_date = read_date;
	}
	public List<ManagerDTO> getManagerSeqList() {
		return managerSeqList;
	}
	public void setManagerSeqList(List<ManagerDTO> managerSeqList) {
		this.managerSeqList = managerSeqList;
	}
}
