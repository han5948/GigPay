package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.AlimDTO;
import com.nemo.kr.dto.AlimReceiveDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkerDTO;

public interface AlimMapper {
	public void insertAlim(AlimDTO alimDTO);
	
	public void insertAlimReceive(AlimDTO alimDTO);
	
	public void insertManagerAlimReceive(AlimReceiveDTO alimReceiveDTO);
	
	public int selectListCnt(AlimDTO alimDTO);
	
	public List<AlimDTO> selectList(AlimDTO alimDTO);
	
	public List<AlimDTO> selectSearchList(AlimDTO alimDTO);
	
	public List<AlimReceiveDTO> selectReceiveList(AlimReceiveDTO alimReceiveDTO);
	
	public List<WorkerDTO> selectReceiveTokenInfo(WorkerDTO workerDTO);
	
	public List<ManagerDTO> selectManagerReceiveTokenInfo(ManagerDTO managerDTO);
	
	public AlimDTO selectInfo(AlimDTO alimDTO);
	
	public void updateAlim(AlimDTO alimDTO);
	
	public void deleteAlim(AlimDTO alimDTO);
	
	public void deleteReceiveAlim(AlimDTO alimDTO);
	
	public List<AlimDTO> selectAlimList(AlimReceiveDTO alimReceiveDTO);
	
	public int selectAlimListCnt(AlimReceiveDTO alimReceiveDTO);
	
	public void setUpdateAlim(AlimReceiveDTO alimReceiveDTO);
}
