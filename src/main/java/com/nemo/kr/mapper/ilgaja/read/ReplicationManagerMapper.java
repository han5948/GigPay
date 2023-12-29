package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;

public interface ReplicationManagerMapper {
	
	public ManagerDTO selectManagerInfo(ManagerDTO managerDTO);

	public int getIlboTotalCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboList(IlboDTO ilboDTO);

	public IlboDTO getIlboWorker(IlboDTO ilboDTO);

	public int getManagerTotalCnt(ManagerDTO managerDTO);

	public List<ManagerDTO> getManagerList(ManagerDTO managerDTO);

	public List<WorkDTO> getWorkList(WorkDTO paramDTO);

	public List<ManagerDTO> getCheckManagerEmployerNumList(ManagerDTO managerDTO);

	public List<ManagerDTO> selectManagerList(ManagerDTO paramDTO);

	public List<WorkDTO> getEmployerList(WorkDTO paramDTO);

	public String selectParentCompanySeq(ManagerDTO managerDTO);

	public List<ManagerDTO> getWorkManagerList(ManagerDTO managerDTO);

	public ManagerDTO selectWmInfo(ManagerDTO managerDTO);

	public ManagerDTO selectEmInfo(ManagerDTO managerDTO);
	
	public int selectWmCount(ManagerDTO managerDTO);

	public List<ManagerDTO> getSearchManagerList(ManagerDTO managerDTO);

	public int selectManagerCount(ManagerDTO managerDTO);

	public List<EmDTO> getManagerEWTreeList(EmDTO emDTO);
	
}
