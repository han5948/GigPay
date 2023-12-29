package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WmDTO;
import com.nemo.kr.dto.WorkDTO;


/**
  * @FileName : EmployerMergeMapper.java
  * @Project : ilgaja
  * @Date : 2021. 7. 16. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 구인처 병합 만 따로 모아 놓음..
  */
public interface EmployerMergeMapper {

	public void updateWorkEmployer(EmployerDTO employerDTO);


	public List<WorkDTO> selectWorkList(EmployerDTO employerDTO);


	public List<WmDTO> selectWmList(WmDTO wmParam);


	public WmDTO selectWm(WmDTO wmDTO);


	public void updateWm(WmDTO wmDTO);


	public void deleteWm(WmDTO wmDTO);


	public List<EmDTO> selectEmList(EmDTO emParam);


	public EmDTO selectEm(EmDTO emDTO);


	public void updateEm(EmDTO emDTO);


	public void deleteEm(EmDTO emDTO);


	public void updateManagerEmployer(EmployerDTO employerDTO);


	public void updateIlboEmployer(EmployerDTO employerDTO);


	public void deleteEmployer(EmployerDTO employerDTO);


}
