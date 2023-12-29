package com.nemo.kr.dto;

import java.util.List;

public class JobAlimReceiveDTO extends JobAlimDTO{
	private static final long serialVersionUID = -2231950057788485849L;
	
	private String receive_seq;		//알림 받는 번호
	private String jobAlim_seq;		//알림 번호
	private String worker_seq;		//알림 받는 구직자 번호
	private String read_yn;			//알림 읽었는지 확인 유무(0 : 안읽음, 1 : 읽음)
	private String read_date;		//알림 확인 날짜
	private String updateFlag; 		//알림 읽음 또는 삭제 flag(R : 읽음, D : 삭제)
	private String ilbo_date;		//일일대장 출역 일자
	private List<WorkerDTO> workerSeqList;
	
	public List<WorkerDTO> getWorkerSeqList() {
		return workerSeqList;
	}
	public void setWorkerSeqList(List<WorkerDTO> workerSeqList) {
		this.workerSeqList = workerSeqList;
	}
	public String getIlbo_date() {
		return ilbo_date;
	}
	public void setIlbo_date(String ilbo_date) {
		this.ilbo_date = ilbo_date;
	}
	public String getReceive_seq() {
		return receive_seq;
	}
	public void setReceive_seq(String receive_seq) {
		this.receive_seq = receive_seq;
	}
	public String getJobAlim_seq() {
		return jobAlim_seq;
	}
	public void setJobAlim_seq(String jobAlim_seq) {
		this.jobAlim_seq = jobAlim_seq;
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
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

}
