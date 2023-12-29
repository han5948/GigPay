package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.ManagerInduceDTO;
import com.nemo.kr.dto.ManagerSubInduceDTO;




public interface ManagerInduceMapper {
	public int selectManagerInduceListCnt(ManagerInduceDTO managerInduceDTO);
	
	public List<ManagerInduceDTO> selectManagerInduceList(ManagerInduceDTO managerInduceDTO);
	
	public List<ManagerSubInduceDTO> selectManagerSubInduceList(ManagerSubInduceDTO managerSubInduceDTO);
	
	public void updateManagerInduce(ManagerInduceDTO managerInduceDTO);
	
	public void deleteManagerInduce(ManagerInduceDTO managerInduceDTO);
	
	public void deleteManagerInduceSub(ManagerInduceDTO managerInduceDTO);
}
