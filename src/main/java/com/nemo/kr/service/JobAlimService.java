package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.JobAlimDTO;
import com.nemo.kr.dto.JobAlimReceiveDTO;
import com.nemo.kr.dto.WorkerDTO;

public interface JobAlimService {
	//worker_seq cnt 조회
	public int selectListCntWorkerSeq(JobAlimReceiveDTO jobAlimReceiveDTO);
	//worker_seq 조회
	public List<JobAlimReceiveDTO> getWorkerSeq(JobAlimReceiveDTO jobAlimReceiveDTO); 
	//일자리 알림 보내기
	public void insertInfo(JobAlimDTO jobAlimDTO);
	//일자리 알림 보내기(받는 사람)
	public void insertInfoReceive(JobAlimDTO jobAlimDTO);
	//일자리 알림 리스트 개수
	public int selectListCnt(JobAlimDTO jobAlimDTO);
	//일자리 알림 리스트
	public List<JobAlimDTO> selectList(JobAlimDTO jobAlimDTO);
	//일자리 알림 받는 사람 token
	public List<WorkerDTO> selectInfoToken(WorkerDTO workerDTO);
	//일자리 알림 수정
	public JobAlimDTO selectInfo(JobAlimDTO jobAlimDTO);
	//일자리 알림 받은 사람 리스트
	public List<JobAlimReceiveDTO> selectListReceive(JobAlimReceiveDTO jobAlimReceiveDTO);
	//일자리 알림 수정 로직
	public void updateInfo(JobAlimDTO jobAlimDTO);
	//일자리 알림 삭제 로직
	public void deleteInfo(JobAlimDTO jobAlimDTO);
	//일자리 알림 받는 사람 테이블 삭제 로직
	public void deleteInfoReceive(JobAlimDTO jobAlimDTO);
	//API list cnt
	public int selectListCntToAPI(JobAlimReceiveDTO jobAlimReceiveDTO);
	//API list 쿼리
	public List<JobAlimDTO> selectListToAPI(JobAlimReceiveDTO jobAlimReceiveDTO);
	//API 알림 확인, 삭제
	public void updateInfoToAPI(JobAlimReceiveDTO jobAlimReceiveDTO);
}
