package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.ManagerDTO;


/**
 * 일가자 구인처 관리 mapper
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-14
 */
public interface EmployerMapper {

	public int getEmployerTotalCnt(EmployerDTO employerDTO);

	public List<EmployerDTO> getEmployerList(EmployerDTO employerDTO);

	public EmployerDTO getEmployerView(EmployerDTO employerDTO);

	public void setEmployerCell(EmployerDTO employerDTO);                         // Insert

	public void setEmployerInfo(EmployerDTO employerDTO);                         // Update

	public void removeEmployerInfo(EmployerDTO employerDTO);                      // delete

	public int chkWorkerPhone(EmployerDTO employerDTO);

	public  List<EmployerDTO> chkEmployerNum(EmployerDTO employerDTO);

	public EmployerDTO selectEmployerInfo(EmployerDTO paramDTO);

	public void updateEmployerVisit(EmployerDTO resultDTO);

	public List<EmployerDTO> selectInList(EmployerDTO employerDTO);
}
