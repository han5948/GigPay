package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;





/**
 * 구인처 관리자 Mapper
 *
 */
public interface ManagerMapper {
	
	
	public ManagerDTO selectManagerInfo(ManagerDTO managerDTO);

	public void setManagerInfo(ManagerDTO paramDTO);

	public int getIlboTotalCnt(IlboDTO ilboDTO);

	public List<IlboDTO> getIlboList(IlboDTO ilboDTO);

	public IlboDTO getIlboWorker(IlboDTO ilboDTO);

	public Object setManager(ManagerDTO managerDTO);

	public int getManagerTotalCnt(ManagerDTO managerDTO);

	public List<ManagerDTO> getManagerList(ManagerDTO managerDTO);

	public void setManagerCell(ManagerDTO managerDTO);

	public void removeManagerInfo(ManagerDTO managerDTO);

	public List<WorkDTO> getWorkList(WorkDTO paramDTO);

	public void updateManagerVisit(ManagerDTO managerDTO);

	public List<ManagerDTO> getCheckManagerEmployerNumList(ManagerDTO managerDTO);

	public List<ManagerDTO> selectManagerList(ManagerDTO paramDTO);

	public List<WorkDTO> getEmployerList(WorkDTO paramDTO);

	public String selectParentCompanySeq(ManagerDTO managerDTO);

	public List<ManagerDTO> getWorkManagerList(ManagerDTO managerDTO);

	public ManagerDTO selectWmInfo(ManagerDTO managerDTO);

	public void insertWm(ManagerDTO managerDTO);
	
	public ManagerDTO selectEmInfo(ManagerDTO managerDTO);
	
	public void insertEm(ManagerDTO managerDTO);

	public void deleteWm(ManagerDTO managerDTO);

	public int selectWmCount(ManagerDTO managerDTO);

	public void deleteEm(ManagerDTO managerDTO);

	public void updateWm(ManagerDTO managerDTO);

	public void updateEm(ManagerDTO managerDTO);

	public List<ManagerDTO> getSearchManagerList(ManagerDTO managerDTO);

	public int selectManagerCount(ManagerDTO managerDTO);

	public List<EmDTO> getManagerEWTreeList(EmDTO emDTO);

	
}
