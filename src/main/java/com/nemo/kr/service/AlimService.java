package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.AlimDTO;
import com.nemo.kr.dto.AlimReceiveDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkerDTO;

public interface AlimService {
	//알림 보내기
	public void insertAlim(AlimDTO alimDTO);
	//알림 보내기(받는 사람)
	public void insertAlimReceive(AlimDTO alimDTO);
	//매니저 알림 보내기
	public void insertManagerAlimReceive(AlimReceiveDTO alimReceiveDTO);
	//알림 리스트 개수
	public int selectListCnt(AlimDTO alimDTO);
	//알림 리스트
	public List<AlimDTO> selectList(AlimDTO alimDTO);
	
	public List<AlimDTO> selectSearchList(AlimDTO alimDTO);
	//알림 받는 사람 token
	public List<WorkerDTO> selectReceiveTokenInfo(WorkerDTO workerDTO);
	//알림 받는 매니저 token
	public List<ManagerDTO> selectManagerReceiveTokenInfo(ManagerDTO managerDTO);
	//알림 수정
	public AlimDTO selectInfo(AlimDTO alimDTO);
	//알림 받은 사람 리스트
	public List<AlimReceiveDTO> selectReceiveList(AlimReceiveDTO alimReceiveDTO);
	//알림 수정 로직
	public void updateAlim(AlimDTO alimDTO);
	//알림 삭제 로직
	public void deleteAlim(AlimDTO alimDTO);
	//알림 받는사람 테이블 데이터 삭제
	public void deleteReceiveAlim(AlimDTO alimDTO);
	//API list 쿼리
	public List<AlimDTO> selectAlimList(AlimReceiveDTO alimReceiveDTO);
	//API list cnt
	public int selectAlimListCnt(AlimReceiveDTO alimReceiveDTO);
	
	//API 알림 확인, 삭제
	public void setUpdateAlim(AlimReceiveDTO alimReceiveDTO);
}
