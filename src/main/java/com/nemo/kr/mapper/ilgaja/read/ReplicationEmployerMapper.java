package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.EmployerDTO;

public interface ReplicationEmployerMapper {

	public int getEmployerTotalCnt(EmployerDTO employerDTO);

	public List<EmployerDTO> getEmployerList(EmployerDTO employerDTO);

	public EmployerDTO getEmployerView(EmployerDTO employerDTO);

	public int chkWorkerPhone(EmployerDTO employerDTO);

	public List<EmployerDTO> chkEmployerNum(EmployerDTO employerDTO);

	public EmployerDTO selectEmployerInfo(EmployerDTO paramDTO);

	public List<EmployerDTO> selectInList(EmployerDTO employerDTO);
	
	public int selectEmployerNameCount(List<String> employerNames);
}
