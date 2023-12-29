package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WmDTO;
import com.nemo.kr.dto.WorkDTO;


/**
  * @FileName : WorkMergeMapper.java
  * @Project : ilgaja
  * @Date : 2021. 8. 24. 
  * @작성자 : pys
  * @변경이력 :
  * @프로그램 설명 : 현장 병합
  */
public interface WorkMergeMapper {
	// Ilbo의 work정보 update
	public void updateIlboWork(WorkDTO workDTO);
	
	// Work delete
	public void deleteWork(WorkDTO workDTO);
	
	// wm 중복 2건 이상인 것들 List 
	public List<WmDTO> selectMergeWmList(WorkDTO workDTO);
	
	// wm work_seq 변경
	public int updateWmWork(WmDTO wmDTO);
	
	// wm use_yn 변경
	public void updateWmUse(WmDTO wmDTO);
	
	public WmDTO selectWm(WmDTO wmDTO);
}
